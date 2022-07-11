package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController  // 데이터로 리턴한다
@ControllerAdvice  // exception이 발생할때 모두 낚아챈다
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)  // RuntimeException이 발생하면 이 함수가 가로채서 실행된다
    public String validationException(CustomValidationException e){
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할때는 Script가 좋음
        // 2. Ajax 통신 - CMRespDto
        // 3. Android 통신 - CMRespDto
        return Script.back(e.getErrorMap().toString());
    }
}
