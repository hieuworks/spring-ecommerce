package com.project.ecommerce.user.domain.entity;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String name;
    private String email;
    private String password_hash;
    private String status; // ACTIVE, DISABLE
    private String role; // USER, ADMIN
}
