package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data  // Getter, Setter
public class SignupDto {

    private String username;
    private String password;
    private String email;
    private String name;

    public User toEntity(){
        return User.builder()  // 4개의 데이터에 대해 User객체가 만들어지고 이를 리턴
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}