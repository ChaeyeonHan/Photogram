package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data  // Getter, Setter
public class SignupDto {

    @Size(min = 2, max = 20)  // 최소 2글자 ~ 최대 20글자
    @NotBlank
    private String username;

    @NotBlank  // 비밀번호 무조건 입력 받아야함
    private String password;

    @NotBlank
    private String email;

    @NotBlank
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