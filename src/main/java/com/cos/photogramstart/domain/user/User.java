package com.cos.photogramstart.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

// JPA - Java Persistence API(자바로 데이터를 영구적으로 저장(DB에)할 수 있는 API를 제공)

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data  // Getter, Setter
@Entity  // db에 테이블을 생성해준다
public class User {

    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략을 db를 따라가도록 설정.
    private int id;

    @Column(length = 20, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;  // 자기소개

    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;  // 권한

    private LocalDateTime createDate;  // 언제 데이터가 들어왔는지

   @PrePersist  // db에 insert되기 직전에 실행된다.
   public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
