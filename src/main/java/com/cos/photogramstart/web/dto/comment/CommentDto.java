package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private int imageId;
    private String content;

    // toEntity가 필요X
}
