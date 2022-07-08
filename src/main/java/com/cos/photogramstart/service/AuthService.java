package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service  // 1.IoC 등록  2.트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional // 함수가 실행되는 동안 트랜잭션 관리를 해준다 => write(insert, update, delete)할때 적어줘야 한다.
    public User 회원가입(User user){  // user : 외부에서 통신을 통해 받은 데이터를 user객체에 담은 것
        // 회원가입을 진행
        String rawPassword = user.getPassword();
        String encPassword =  bCryptPasswordEncoder.encode(rawPassword); // 암호화가 된 비밀번호
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");  // 관리자는 ROLE_ADMIN
        User userEntity =  userRepository.save(user);  // userEntity : db에 있는 데이터를 user객체에 담은것

        return userEntity;
    }
}
