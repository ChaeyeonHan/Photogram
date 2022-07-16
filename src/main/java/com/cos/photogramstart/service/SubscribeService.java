package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional  // db에 영향을 주는 작업이므로 걸어준다
    public void 구독하기(int fromUserId, int toUserId){
        subscribeRepository.mSubscribe(fromUserId, toUserId);  // m은 사용자가 만들었다는 것에 대한 약어
        // 오류가 나서 exception이 터지면 try-catch로 handler에서 잡아줄 예정이기에 리턴값을 없도록 설정했다.
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
