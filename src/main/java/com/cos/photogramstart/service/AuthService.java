package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service  // 1.IoC 등록  2.트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;

    public User 회원가입(User user){  // user : 외부에서 통신을 통해 받은 데이터를 user객체에 담은 것
        // 회원가입을 진행
        User userEntity =  userRepository.save(user);  // userEntity : db에 있는 데이터를 user객체에 담은것

        return userEntity;
    }
}
