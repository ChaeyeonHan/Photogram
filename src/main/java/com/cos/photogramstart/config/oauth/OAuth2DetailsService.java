package com.cos.photogramstart.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// OAuth2DetailsService : 페이스북에서 오는 응답을 처리해준다
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    // 회원정보를 넘겨줄때 loadUser함수가 실행되고, 유저 정보가 userRequest 안에 담겨있다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth2 서비스 진입");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());
        return null;
    }
}
