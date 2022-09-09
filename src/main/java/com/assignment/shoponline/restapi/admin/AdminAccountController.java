package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.AccountRegisterDto;
import com.assignment.shoponline.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/v1/accounts")
public class AdminAccountController {
    final AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterDto accountRegisterDto) {
        try {
            accountService.register(accountRegisterDto);
            return ResponseEntity.ok("Successfully create new account");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can not create new account");
        }
    }

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody AccountLoginDto accountLoginDto) {
        try {
            accountService.login(accountLoginDto);
            return ResponseEntity.ok("Login successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to login");
        }
    }
}
