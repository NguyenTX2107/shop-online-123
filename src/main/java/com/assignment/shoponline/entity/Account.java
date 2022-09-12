package com.assignment.shoponline.entity;

import com.assignment.shoponline.entity.basic.BaseEntity;
import com.assignment.shoponline.utils.Enums;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String passwordHash;
    private String phone;
    @Email
    private String email;
    private String avatarUrl; //default
    @Enumerated(EnumType.ORDINAL)
    private Enums.Role role;
}
