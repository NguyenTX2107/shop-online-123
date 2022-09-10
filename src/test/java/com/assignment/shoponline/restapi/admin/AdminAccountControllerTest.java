package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.AccountRegisterDto;
import com.assignment.shoponline.service.AccountService;
import com.assignment.shoponline.utils.Enums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class AdminAccountControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    void register() {
        try {
            //
            AccountRegisterDto accountRegisterDto = new AccountRegisterDto();
            accountRegisterDto.setUserName("User_hello");
            accountRegisterDto.setPassword("123456");
            accountRegisterDto.setPhone("04191657498");
            accountRegisterDto.setEmail("fgs98@gmail.com");
            accountRegisterDto.setRole(Enums.Role.USER);
            accountRegisterDto.setAvatarUrl("/dsf.png");
            //
            Account account = accountService.register(accountRegisterDto);
            if (null == account) {
                System.out.println("Register fail");
                return;
            }
            ResponseEntity.ok("Successfully create new account");
            System.out.println("Tao tai khoan thanh cong");
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Can not create new account");
            System.out.println("Tao tai khoan that bai");
        }
    }

    @Test
    void login() {
        try {
            //
            AccountLoginDto accountLoginDto = new AccountLoginDto();
            accountLoginDto.setUserName("LKJHG");
            accountLoginDto.setPassword("45");
            //
            accountService.login(accountLoginDto);
            System.out.println("Login thanh cong");
        } catch (Exception e) {
            System.out.println("Login that bai");
        }
    }
}