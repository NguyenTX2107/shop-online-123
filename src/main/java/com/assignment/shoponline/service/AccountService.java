package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.Credential;
import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.AccountRegisterDto;
import com.assignment.shoponline.repository.AccountRepository;
import com.assignment.shoponline.utils.Enums;
import com.assignment.shoponline.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccountService implements UserDetailsService{
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;
    final int expiredAfterDay = 7;

    /**
     *
     * @param accountRegisterDto gồm các thông tin tạo mới tài khoản
     * @return null nếu đã có tài khoản này tồn tại
     */
    public Account register(AccountRegisterDto accountRegisterDto) {
        List<Account> optionalAccount =
                accountRepository.findAccountsByUserNameOrEmailOrPhone(accountRegisterDto.getUserName(), accountRegisterDto.getEmail(), accountRegisterDto.getPhone());
        if (optionalAccount.size() > 0) { //nếu có tài khoản tồn tại rồi
            return null;
        }
        Account account = Account.builder()
                        .userName((accountRegisterDto.getUserName()))
                        .passwordHash(passwordEncoder.encode(accountRegisterDto.getPassword())) //mã hóa password
                        .email(accountRegisterDto.getEmail())
                        .phone(accountRegisterDto.getPhone())
                        .avatarUrl(accountRegisterDto.getAvatarUrl())
                        .role(accountRegisterDto.getRole())
                        .build();
        //lưu account vào database
        return accountRepository.save(account);
    }

    //chạy sau khi chạy
    public Credential login(AccountLoginDto accountLoginDto) {
        //kiểm tra xem có user này chưa
        Optional<Account> optionalAccount = accountRepository.findAccountByUserName(accountLoginDto.getUserName());
        if (!optionalAccount.isPresent()) {
            return null;
        }
        Account account = optionalAccount.get();
        //kiểm tra xem pass truyền vào có chuẩn không
        boolean isMatch = passwordEncoder.matches(accountLoginDto.getPassword(), account.getPasswordHash());
        if (isMatch) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(account.getRole() == Enums.Role.ADMIN ? "ADMIN" : "USER");
            authorities.add(simpleGrantedAuthority);
            String accessToken =
                    JwtUtil.generateTokenByAccount(new User(account.getUserName(), account.getPasswordHash(), authorities));
            String refreshToken =
                    JwtUtil.generateTokenByAccount(new User(account.getUserName(), account.getPasswordHash(), authorities));
            Credential credential = new Credential();
            credential.setAccessToken(accessToken);
            credential.setRefreshToken(refreshToken);
            credential.setScope("basic_information");
            return credential;
        } else {
            throw new UsernameNotFoundException("Password is not match");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findAccountByUserName(username);
        if(!optionalAccount.isPresent()){
            throw  new UsernameNotFoundException("Username is not found");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(account.getRole() == Enums.Role.ADMIN ? "ADMIN" : "USER");
        authorities.add(simpleGrantedAuthority);
        return new User(account.getUserName(),account.getPasswordHash(),authorities);
    }
}

