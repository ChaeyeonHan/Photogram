package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 권한이 한개가 아니라 여러개 일 수 있기에
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
//        collector.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });

        // 람다식으로
        collector.add(()->{
            return user.getRole();
        });

        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
