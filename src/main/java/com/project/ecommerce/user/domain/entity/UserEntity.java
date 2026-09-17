package com.project.ecommerce.user.domain.entity;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    @Builder.Default
    private Set<String> roles = new HashSet<>();
}
