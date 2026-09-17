package com.project.ecommerce.product.dao;

import com.project.ecommerce.product.domain.entity.ProductEntity;

public interface ProductDao {
    int createProduct(ProductEntity product);
}
