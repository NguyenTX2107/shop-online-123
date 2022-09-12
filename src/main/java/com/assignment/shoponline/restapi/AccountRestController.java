package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.Credential;
import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.AccountRegisterDto;
import com.assignment.shoponline.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRestController {
    final AccountService accountService;

    final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountRegisterDto accountRegisterDto) {
        try {
            Account account = accountService.register(accountRegisterDto);
            if (null == account) {
                log.error("Fail to create new account");
                return ResponseEntity.ok("Fail to create new account");
            }
            log.info("Successfully create new account");
            return ResponseEntity.ok("Successfully create new account");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating new account");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountLoginDto accountLoginDto) {
        try{
            return ResponseEntity.ok().body("Successfully login");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while login");
        }
    }

    @GetMapping
    //de get detail can gui kem token, decode token o front se co id luu vao local storage
    // => send id len lai backend de lay info (cho buoc update info)
    public ResponseEntity<?> getDetail(Authentication authentication) {
        try {
            String username = authentication.getPrincipal().toString(); //lay username tu token gui len
            Account account = accountService.findByUsername(username);
            if (null == account) {
                log.error("Account not found");
                return ResponseEntity.badRequest().body("Account not found");
            }
            log.info("Found account with username {}", account.getUsername());
            AccountRegisterDto accountRegisterDto = new AccountRegisterDto();
            BeanUtils.copyProperties(account, accountRegisterDto);
            return ResponseEntity.ok().body(accountRegisterDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while fetching account detail");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDetail(@RequestBody AccountRegisterDto accountRegisterDto, Authentication authentication) {
        try {
            String username = authentication.getPrincipal().toString();
            Account checkAccount = accountService.findByUsername(username);
            if (null != checkAccount) { //tai khoan khong ton tai => token truyen len co van de ?
                log.error("Token payload is not fit with data in database");
                return ResponseEntity.badRequest().body("Error while updating account detail");
            }
            //update detail
            Account account = accountService.register(accountRegisterDto);
            if (null == account) {
                log.error("Fail to update account");
                return ResponseEntity.badRequest().body("Fail to update account");
            }
            log.info("Successfully update account");
            return ResponseEntity.ok().body("Successfully update account");
        } catch (Exception e) {
            log.error("Error while updating account with username {}", accountRegisterDto.getUsername());
            return ResponseEntity.badRequest().body("Error while updating account detail");
        }
    }

}
