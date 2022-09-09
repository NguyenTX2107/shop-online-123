package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.Credential;
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
@RequestMapping("api/v1/accounts")
public class AccountRestController {
    final AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody AccountRegisterDto accountRegisterDto) {
        try {
            Account account = accountService.register(accountRegisterDto);
            if (null == account) {
                return ResponseEntity.ok("Fail to create new account");
            }
            return ResponseEntity.ok("Successfully create new account");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating new account");
        }
    }

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody AccountLoginDto accountLoginDto) {
        try{
            Credential credential = accountService.login(accountLoginDto);
            if (null == credential) {
                return ResponseEntity.badRequest().body("Username or Password is incorrect");
            }
            return ResponseEntity.ok().body(credential);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while login");
        }
    }
}
