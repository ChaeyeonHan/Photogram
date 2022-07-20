package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDto {
    private int id;
    private String username;
    private String profileImageUrl;  // 프로필 사진
    private Integer subscribeState;  // 구독 여부
    private Integer equalUserState;  // 로그인한 사용자와 동일한지(동일하면 구독하기&구독취소 버튼 활성화X)
    // 마리아db는 int로 하면 리턴을 못받기에 Integer로 설정함.
}
