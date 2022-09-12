package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.Credential;
import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.AccountRegisterDto;
import com.assignment.shoponline.repository.AccountRepository;
import com.assignment.shoponline.utils.Enums;
import com.assignment.shoponline.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;

    public Account findByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (!optionalAccount.isPresent()) {
            log.error("Can not find account with username {}", username);
            return null;
        }
        return optionalAccount.get();
    }

    /**
     * Register Service Method
     * @param accountRegisterDto gồm các thông tin tạo mới tài khoản
     * @return null nếu đã có tài khoản này tồn tại
     */
    public Account register(AccountRegisterDto accountRegisterDto) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(accountRegisterDto.getUsername());
        if (optionalAccount.isPresent()) {
            log.info("Account exists"); //khong cho username giong nhau
            return null;
        }
        if (null == accountRegisterDto.getAvatarUrl() || accountRegisterDto.getAvatarUrl().length() == 0) {
            accountRegisterDto.setAvatarUrl("/src/resources/static/images/default_avatar.png"); //set defaut avatar
        }
        if (!accountRegisterDto.isDataOK()) { //neu data nhap vao khong hop le thi return null
            log.info("Input data is incorrect");
            return null;
        }
        Account account = Account.builder()
                .username(accountRegisterDto.getUsername())
                .passwordHash(passwordEncoder.encode(accountRegisterDto.getPassword()))
                .phone(accountRegisterDto.getPhone())
                .email(accountRegisterDto.getEmail())
                .avatarUrl(accountRegisterDto.getAvatarUrl())
                .role(accountRegisterDto.getRole())
                .build();
        return accountRepository.save(account);
    }

    /**
     * Login Service Method
     * Không cần thiết nữa rồi (xem MyAuthenticationFilter)
     * @param accountLoginDto gồm username và password đc truyền vào
     * @return Crendential gồm token, scope, expired time
     */
    public Credential login(AccountLoginDto accountLoginDto) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(accountLoginDto.getUsername());
        if(!optionalAccount.isPresent()){
            log.error("User not found");
            return null;
        }
        Account account = optionalAccount.get();
        //kiểm tra xem pass truyền vào có chuẩn không
        boolean isMatch = passwordEncoder.matches(accountLoginDto.getPassword(), account.getPasswordHash());
        if (isMatch) {
            String accessToken =
                    JwtUtil.generateTokenByAccount(account);
            String refreshToken =
                    JwtUtil.generateTokenByAccount(account);
            Credential credential = new Credential();
            credential.setAccessToken(accessToken);
            credential.setRefreshToken(refreshToken);
            credential.setExpiredAt(JwtUtil.EXPIRED_TIME);
            if (account.getRole() == Enums.Role.ADMIN) {
                credential.setScope("admin");
            } else if (account.getRole() == Enums.Role.USER) {
                credential.setScope("user");
            }
            return credential;
        } else {
            log.error("Password is not match"); //pass sai => throw exception
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("Username is not found");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(account.getRole() == Enums.Role.ADMIN ? "ADMIN" : "USER");
        authorities.add(simpleGrantedAuthority);
        return new User(account.getUsername(),account.getPasswordHash(),authorities);
    }
}

