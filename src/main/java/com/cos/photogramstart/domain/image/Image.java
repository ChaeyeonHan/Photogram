package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;  // 이미지 설명.

    private String postImageUrl;  // 경로. 사진을 전송받아서 그 사진을 서버의 특정폴더에 저장 -> DB에는 저장된 경로를 insert해준다.

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;  // 1명의 유저가 이미지 여러개 올릴 수 있음

    // 이미지 좋아요

    // 댓글

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

}