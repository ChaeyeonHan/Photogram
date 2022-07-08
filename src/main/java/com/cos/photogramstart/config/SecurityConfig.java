package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  // 해당 파일로 시큐리티를 활성화시킨다
@Configuration   // IoC에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()  // 인증이 필요한 페이지
                .anyRequest().permitAll()
                .and()
                .formLogin()// 접속시 로그인 페이지(/auth/login)로 이동
                .loginPage("/auth/signin")
                .defaultSuccessUrl("/");  // 로그인 성공시 /로 이동

    }
}
