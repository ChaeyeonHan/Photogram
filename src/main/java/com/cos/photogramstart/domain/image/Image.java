package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;  // 1명의 유저가 이미지 여러개 올릴 수 있음

    // 이미지 좋아요
    @JsonIgnoreProperties({"image"})  // 무한 참조 방지
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @Transient  // db에 컬럼이 생기지 않는다
    private boolean likeState;

    @Transient
    private int likeCount;  // 좋아요 갯수

    // 댓글. 양방향 매핑
    @OrderBy("id DESC")
    @JsonIgnoreProperties("{image}")
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    // 오브젝트를 콘솔에 출력할때 문제가 발생할 수 있어서 User부분을 제외하고 출력시킴.
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                ", createDate=" + createDate +
//                '}';
//    }
}