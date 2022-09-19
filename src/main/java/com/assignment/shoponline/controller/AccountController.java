//package com.assignment.shoponline.controller;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import com.assignment.shoponline.entity.Account;
//import com.assignment.shoponline.entity.Credential;
//import com.assignment.shoponline.entity.dto.AccountLoginDto;
//import com.assignment.shoponline.entity.dto.AccountRegisterDto;
//import com.assignment.shoponline.service.AccountService;
//import com.assignment.shoponline.utils.Enums.Role;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@CrossOrigin("*")
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("accounts")
//public class AccountController {
//    final AccountService accountService;
//
//    final AuthenticationManager authenticationManager;
//
//    @GetMapping("register") //chuyen den trang dang ki
//    public String getRegisterForm(AccountRegisterDto accountRegisterDto , Model model) {
//        try {
//            log.info("Go to Register Form");
//            accountRegisterDto = new AccountRegisterDto();
//            model.addAttribute("accountRegisterDto", accountRegisterDto);
//            return "register";
//        } catch (Exception e) {
//            log.error("Can not go to login form");
//            model.addAttribute("getRegisterFormError", "Can not go to register form");
//            return "redirect:/products";
//        }
//    }
//
//    @PostMapping("register/add")
//    public String register(@ModelAttribute("accountRegisterDto") AccountRegisterDto accountRegisterDto, Model model) {
//        try {
//            accountRegisterDto.setRole(Role.USER); //day la controller cua User nen chi cho dk tai khoan user
//            Account account = accountService.register(accountRegisterDto);
//            if (null == account) {
//                log.error("Fail to create new account");
//                model.addAttribute("registerFail", "Fail to create new account");
//                return "redirect:/accounts/register"; //van tra ve form dang ki
//            }
//            log.info("Successfully create new account");
//            model.addAttribute("registerSuccess", "Successfully create new account");
//            //tra ve form dang ki, hien ra message de nhay ve main page hoac tien hanh dang nhap
//            return "redirect:/accounts/register";
//        } catch (Exception e) {
//            model.addAttribute("registerError", "Error while creating new account");
//            return "redirect:/accounts/register";
//        }
//    }
//
//    @GetMapping("login")
//    public String getLoginForm(@ModelAttribute("accountLoginDto") AccountLoginDto accountLoginDto , Model model) { //chuyen den trang login
//        try {
//            log.info("Go to Login Form");
//            accountLoginDto = new AccountLoginDto();
//            model.addAttribute("accountLoginDto", accountLoginDto);
//            return "login";
//        } catch (Exception e) {
//            log.error("Can not go to login form");
//            model.addAttribute("getLoginFormError", "Can not go to login form");
//            return "redirect:/";
//        }
//    }
////    @PostMapping("auth") //dung de goi den moi khi can check token
////    public String login(@RequestBody AccountLoginDto accountLoginDto, Model model, HttpServletResponse response) {
////        try{
////            Account account = accountService.login(accountLoginDto);
////            if (null == account) {
////                log.info("No Bearer Token found");
////                model.addAttribute("login", "Username or Password is incorrect");
////                return "redirect:/accounts/login";
////            }
////            log.info("User {} logging successfully", account.getUsername());
////            AccountRegisterDto accountRegisterDto = new AccountRegisterDto(); //chuan bi du lieu de truyen xuong front end
////            model.addAttribute("loginSuccess", "Login successfully");
////            model.addAttribute("accountRegisterDto", accountRegisterDto);
////            System.out.println("Heelo World");
////            //Xet role xem chuyen den trang admin hay user
////            if (account.getRole() == Role.ADMIN) {
////                return "redirect:/admin/products"; //k dc return view vi no se hien pass va username len thanh link :v
////            }
////            if (account.getRole() == Role.USER) {
////                model.addAttribute("accountRegisterDto");
////                return "index"; //tra ve trang chu
////            }
////            return "redirect:/"; //Anonymous
////        } catch (Exception e) {
////            log.error("Error while login");
////            model.addAttribute("loginError", "Error while login");
////            return "redirect:/accounts/login";
////        }
////    }
//
//}
