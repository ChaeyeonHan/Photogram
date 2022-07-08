package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "/auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "/auth/signup";
    }

    // 회원가입버튼 클릭 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(){  // 회원가입 실제 진행
        return "/auth/signin";  // 회원가입 성공시 로그인하러 로그인페이지로 이동
    }

}
