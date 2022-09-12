package com.assignment.shoponline.entity.dto;

import com.assignment.shoponline.utils.Enums;
import lombok.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class AccountRegisterDto {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatarUrl;
    private Enums.Role role;

    public boolean isDataOK() {
        if (null == this.getUsername() || this.getUsername().equals("")) {
            log.error("Account name is not null");
            return false;
        }
        if (null == this.getPassword() || this.getPassword().equals("")) {
            log.error("Password is not null");
            return false;
        }
        if (null == this.getPhone() || this.getPhone().equals("")) {
            log.error("Phone number is not null");
            return false;
        }
        if (null == this.getEmail() || this.getEmail().equals("")) {
            log.error("Email is not null");
            return false;
        }
        return true;
    }
}
