package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id, Model model){  // 사용자 화면으로 이동할때 이미지 데이터를 들고 이동한다.
        User userEntity = userService.회원프로필(id);  // 해당 id의 사용자가 있는지 확인
        model.addAttribute("user", userEntity);  // "변수명", 값
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        // 1. 어노테이션 사용(추천)
        System.out.println("세션 정보: " + principalDetails.getUser());

        // 2. 어노테이션 사용 안하고 꺼내는 방법
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails1 = (PrincipalDetails) auth.getPrincipal();
        System.out.println("직접 찾은 세션 정보: " + principalDetails1.getUser());

        model.addAttribute("principal", principalDetails.getUser());
        return "user/update";
    }
}
