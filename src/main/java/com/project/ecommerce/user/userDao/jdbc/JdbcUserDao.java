package com.project.ecommerce.user.userDao.jdbc;

import com.project.ecommerce.user.domain.entity.UserEntity;
import com.project.ecommerce.user.userDao.UserDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String USER_WITH_ROLE_SQL_TEMPLATE = """
            SELECT u.id, u.name, u.email, u.password_hash, u.status, r.name AS role
            FROM users u
            JOIN roles r ON r.id = u.role_id
            WHERE u.deleted_at IS NULL AND %s
            """;

    private static final String FIND_BY_ID_SQL = USER_WITH_ROLE_SQL_TEMPLATE.formatted("u.id = ?");
    private static final String FIND_BY_EMAIL_SQL = USER_WITH_ROLE_SQL_TEMPLATE.formatted("u.email = ?");

    private static final String CREATE_USER_SQL = """
            INSERT INTO users (name, email, password_hash, role_id)
            SELECT ?, ?, ?, id
            FROM roles
            WHERE name = ?
            """;

    private static final RowMapper<UserEntity> ROW_MAPPER =
            new BeanPropertyRowMapper<>(UserEntity.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jdbcTemplate.query(FIND_BY_EMAIL_SQL, ROW_MAPPER, email).stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE email = ? AND deleted_at IS NULL)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, email);
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public int create(UserEntity user) {
        return jdbcTemplate.update(
                CREATE_USER_SQL,
                user.getName(),
                user.getEmail(),
                user.getPassword_hash(),
                user.getRole()
        );
    }
}
