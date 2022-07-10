package com.cos.photogramstart.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 데이터로 리턴한다
@ControllerAdvice  // exception이 발생할때 낚아챈다
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)  // RuntimeException이 발생하면 이 함수가 가로채서 실행된다
    public String validationException(RuntimeException e){
        return e.getMessage();
    }
}
