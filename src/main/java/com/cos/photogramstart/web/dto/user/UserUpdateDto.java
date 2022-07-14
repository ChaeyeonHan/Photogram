package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {  // 유저네임, 이메일 빼고 모두 받아와준다(수정가능)
    private String name;  // 회원정보 수정시 필수로 넘어오는 데이터
    private String password;  // 필수
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 필수로 들어오는 값들인 경우 엔티티로 만들어두면 조금 위험(수정할 예정)
    public User toEntity(){
        return User.builder()
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }

}
