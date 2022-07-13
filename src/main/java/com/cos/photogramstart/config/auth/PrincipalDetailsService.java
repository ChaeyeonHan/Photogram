package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1.password는 알아서 체크하므로, 우리는 username만 확인해주면 된다.
    // 2.리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만들어준다.(리턴된 PrincipalDetails가 세션에 저장된다)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null){
            return null;
        }else{
            return new PrincipalDetails(userEntity);
        }
    }
}
