package com.cos.photogramstart.web.api;

import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {  // 데이터로 응답하는 컨트롤러

    @PutMapping("/api/user/{id}")
    public String update(UserUpdateDto userUpdateDto){
        System.out.println(userUpdateDto);
        return "ok";
    }
}
