package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    // mappedBy : 연관관계의 주인이 아님을 뜻한다 => 테이블에 컬럼 생성X,
    // User를 select할때 해당 userId로 등록된 image를 모두 가져온다.
    // Lazy : User를 select할때 해당 userId로 등록된 image가져오지X -> 대신 getImages()함수가 호출될때 가져온다.(user를 select한다고 해서 무조건 가져오는 것이 아님)
    // EAGER : User를 select할때 해당 userId로 등록된 iamge들을 전부 join해서 가져온다.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})  // Image내부에 있는 user를 무시하고 파싱해준다(즉, getter호출을 막아준다)
    private List<Image> images;  // 양방향 매핑

    private LocalDateTime createDate;  // 언제 데이터가 들어왔는지

   @PrePersist  // db에 insert되기 직전에 실행된다.
   public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
