package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController  // 데이터로 리턴한다
@ControllerAdvice  // exception이 발생할때 모두 낚아챈다
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)  // RuntimeException이 발생하면 이 함수가 가로채서 실행된다
    public CMRespDto<?> validationException(CustomValidationException e){
        return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
    }
}
