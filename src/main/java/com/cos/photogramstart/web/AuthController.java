package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor  // final 필드를 DI할때 사용
@Controller
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

//    @Autowired
//    private AuthService authService;

    private final AuthService authService;

//    public AuthController(AuthService authService){
//        this.authService = authService;
//    }

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
    public String signup(SignupDto signupDto){  // 회원가입 실제 진행  key=value의 형태로 들어온다
//        log.info(signupDto.toString());
        // User <- signupDto
        User user = signupDto.toEntity();
        log.info(user.toString());

        User userEntity = authService.회원가입(user);
        System.out.println("userEntity = " + userEntity);
        return "/auth/signin";  // 회원가입 성공시 로그인하러 로그인페이지로 이동
    }

}
