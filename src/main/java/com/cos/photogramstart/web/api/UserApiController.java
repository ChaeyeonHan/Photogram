package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {  // 데이터로 응답하는 컨트롤러

    private final UserService userService;
    private final SubscribeService subscribeService;

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        List<SubscribeDto> subscribeDtos = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", subscribeDtos), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult,  // @Valid 바로 뒤 파라미터로 오도록 적어주기!
            @AuthenticationPrincipal PrincipalDetails principalDetails){

        if (bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            } throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
        } else {
            User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity);  // 세션의 정보도 변경해준다
            return new CMRespDto<>(1, "회원수정완료", userEntity);  // 응답시에 userEntity의 모든 getter함수가 호출되고,JSON으로 파싱하여 응답한다.
        }
    }
}
