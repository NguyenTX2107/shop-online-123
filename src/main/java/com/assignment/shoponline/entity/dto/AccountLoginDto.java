package com.assignment.shoponline.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLoginDto {
    private String userName;
    private String password;
}
