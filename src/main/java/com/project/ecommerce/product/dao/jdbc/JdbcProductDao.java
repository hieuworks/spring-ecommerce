package com.project.ecommerce.product.dao.jdbc;

import com.project.ecommerce.product.dao.ProductDao;
import com.project.ecommerce.product.domain.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcProductDao implements ProductDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int createProduct(ProductEntity product) {
        return 0;
    }
}
