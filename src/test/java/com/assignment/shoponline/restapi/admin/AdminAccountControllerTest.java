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
            accountRegisterDto.setUsername("Admin2");
            accountRegisterDto.setPassword("123456");
            accountRegisterDto.setPhone("04134498");
            accountRegisterDto.setEmail("admin2@gmail.com");
            accountRegisterDto.setRole(Enums.Role.ADMIN);
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
            accountLoginDto.setUsername("LKJHG");
            accountLoginDto.setPassword("45");
            //
            accountService.login(accountLoginDto);
            System.out.println("Login thanh cong");
        } catch (Exception e) {
            System.out.println("Login that bai");
        }
    }
}