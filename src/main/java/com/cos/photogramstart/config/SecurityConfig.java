package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity  // 해당 파일로 시큐리티를 활성화시킨다
@Configuration   // IoC에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean  // SecurityConfig가 IoC에 등록될때 @Bean어노테이션을 읽어서 리턴해서 IoC가 들고있다
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        // super삭제 -> 기존 시큐리티가 가지고 있는 기능이 다 비활성화 된다
        http.csrf().disable();  // 비활성화(postman/홈페이지를 통한 방법 구분X)
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()  // 인증이 필요한 페이지
                .anyRequest().permitAll()
                .and()
                .formLogin()// 접속시 로그인 페이지(/auth/login)로 이동
                .loginPage("/auth/signin")  // GET, 인증이 필요한 주소 요청시 실행됨
                .loginProcessingUrl("/auth/signin")  // POST, 로그인 요청시 실행됨 => 스프링 시큐리티가 로그인 프로세스를 진행
                .defaultSuccessUrl("/")  // 로그인 성공시 /로 이동
                .and()
                .oauth2Login() // form로그인도 하는데, oauth2로그인도 한다는 의미
                .userInfoEndpoint() // oauth2로그인을 하면 최종 응답을 회원정보가 바로 오도록 (코드 -> 엑세스 토큰 -> 회원정보받아오기) => 이 과정을 oauth2-client 프레임워크가 알아서 처리해준다
                .userService(oAuth2DetailsService);

    }
}
