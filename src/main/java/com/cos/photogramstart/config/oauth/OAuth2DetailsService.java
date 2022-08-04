package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

// OAuth2DetailsService : 페이스북에서 오는 응답을 처리해준다
@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    // + SecurityConfig보다 OAuth2DetailsService가 먼저 메모리에 뜬다.

    // 회원정보를 넘겨줄때 loadUser함수가 실행되고, 유저 정보가 userRequest 안에 담겨있다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("OAuth2 서비스 진입");
        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println(oAuth2User.getAttributes());

        Map<String, Object> userInfo = oAuth2User.getAttributes();
        String username = "facebook_" + (String)userInfo.get("id"); // 우리가 강제로 만들어준다
        // 어차피 facebook쪽으로 로그인을 확인함으로 중복되지 않게 유저이름을 만들어주면 된다 => id값 사용
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());  // unique한 비밀번호를 만들어 암호화해서 넣어준다
        String email = (String)userInfo.get("email");
        String name = (String)userInfo.get("name");

        // 페이스북으로 로그인해서 가입한 사용자인지 확인
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null){  // 페이스북 최초 로그인
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());
        }else{  // 페이스북으로 이미 회원가입이 되어 있다는 뜻
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());  // 기존에 있는 유저 반환
        }
    }
}
