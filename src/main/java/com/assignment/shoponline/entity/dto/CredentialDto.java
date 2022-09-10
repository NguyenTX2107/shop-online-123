package com.assignment.shoponline.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDto {
    private String accessToken;
    private String refreshToken;
    private int expiredAt;
    private String scope;
}
