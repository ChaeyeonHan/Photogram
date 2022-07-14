package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id){
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
