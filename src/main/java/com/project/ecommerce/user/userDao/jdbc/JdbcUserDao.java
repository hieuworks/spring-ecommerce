package com.project.ecommerce.user.userDao.jdbc;

import com.project.ecommerce.user.domain.entity.UserEntity;
import com.project.ecommerce.user.userDao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
@Repository
public class JdbcUserDao implements UserDao {
    private static final RowMapper<UserEntity> ROW_MAPPER = (resultSet, rowNum) -> {
        Array rolesArray = resultSet.getArray("roles");
        String[] roles = rolesArray == null ? new String[0] : (String[]) rolesArray.getArray();
        return UserEntity.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password_hash(resultSet.getString("password_hash"))
                .status(resultSet.getString("status"))
                .roles(new HashSet<>(Arrays.asList(roles)))
                .build();
    };

    private static final String USER_WITH_ROLES = """
            SELECT u.id, u.name, u.email, u.password_hash, u.status,
                   COALESCE(array_agg(r.name) FILTER (WHERE r.name IS NOT NULL), ARRAY[]::VARCHAR[]) AS roles
            FROM users u
            LEFT JOIN user_roles ur ON ur.user_id = u.id
            LEFT JOIN roles r ON r.id = ur.role_id
            WHERE u.deleted_at IS NULL AND %s
            GROUP BY u.id, u.name, u.email, u.password_hash, u.status
            """;
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        String sql = USER_WITH_ROLES.formatted("u.id = ?");
        return jdbcTemplate.query(sql, ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        String sql = USER_WITH_ROLES.formatted("u.email = ?");
        return jdbcTemplate.query(sql, ROW_MAPPER, email).stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE email = ? AND deleted_at IS NULL)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, email);
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public int create(UserEntity user) {
        String sql = """
                INSERT INTO users (name, email, password_hash)
                VALUES (?, ?, ?)
                """;
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword_hash());
    }
}
