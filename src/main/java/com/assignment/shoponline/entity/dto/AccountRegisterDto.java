package com.assignment.shoponline.entity.dto;

import com.assignment.shoponline.utils.Enums;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRegisterDto {
    private Long id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String avatarUrl;
    private Enums.Role role;

    public boolean isDataOK() {
        if (null == this.getUserName() || this.getUserName().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account name is not null");
            return false;
        }
        if (null == this.getPassword() || this.getPassword().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is not null");
            return false;
        }
        if (null == this.getPhone() || this.getPhone().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number is not null");
            return false;
        }
        if (null == this.getEmail() || this.getEmail().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is not null");
            return false;
        }
        return true;
    }
}
