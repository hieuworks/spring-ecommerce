package com.project.ecommerce.product.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    private Long id;
    private Long category_id; //problem to practice in the ft: what if I want one product have > 1 category; ex: smartphone, iphone
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}
