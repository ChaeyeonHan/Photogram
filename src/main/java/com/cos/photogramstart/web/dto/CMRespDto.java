package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {  // 응답 Dto : 제네릭으로 구현

    private int code;  // 1(성공), -1(실패)
    private String message;
    private T data;
}
