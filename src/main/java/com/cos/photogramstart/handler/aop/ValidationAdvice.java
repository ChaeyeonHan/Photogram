package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component  // RestController, Service 모든것들이 Component의 구현체이다.(컴포넌트를 상속해서 만들어져 있음)
@Aspect  // aop처리를 위한 어노테이션
public class ValidationAdvice {  // 공통기능을 적어준다

    //  (..) : 파라미터에 관계없이 모두
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")  // 함수 선택하는 부분. public, protected..
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("web api 컨트롤러");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg: args) {
            if (arg instanceof BindingResult){
                System.out.println("유효성 검사를 하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()){
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error: bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }
        // proceedingJoinPoint : 특정 메소드의 모든 내부 정보에 접근할 수 있는 파라미터
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // profile 메소드보다 이부분이 먼저 실행된다.

        return proceedingJoinPoint.proceed();  // 메소드로 다시 돌아간다 => profile 메소드가 실행된다
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object Advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("web 컨트롤러");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg: args) {
            if (arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                System.out.println("유효성 검사를 하는 함수입니다.");
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
//                System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
