package com.project.ecommerce.common.mapper;

public interface Mapper<A, B> {
    A toEntity(B b);
    B toDto(A a);
}
