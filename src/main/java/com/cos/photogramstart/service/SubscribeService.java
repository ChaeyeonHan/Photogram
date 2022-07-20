package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;  // qlrm : 스프링부트에서 제공해주는 라이브러리X
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;  // 모든 repository는 EntityManger를 구현해서 만들어져 있는 구현체이다.

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(Integer principalId, Integer pageUserId){
        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?");  // 세미콜론 넣어주지X
        // 첫번째, 두번째 물음표 = principalId
        // 마지막 물음표 = pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);
        // 쿼리 실행(qlrm 라이브러리가 여기서 필요. Dto에 db결과를 매핑해주기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);  // 리스트로 리턴받음
        return subscribeDtos;
    }

    @Transactional  // db에 영향을 주는 작업이므로 걸어준다
    public void 구독하기(int fromUserId, int toUserId){
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);  // m은 사용자가 만들었다는 것에 대한 약어
            // 오류가 나서 exception이 터지면 try-catch로 handler에서 잡아줄 예정이기에 리턴값을 없도록 설정했다.
        }catch(Exception e){
            throw new CustomApiException("이미 구독을 했습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
