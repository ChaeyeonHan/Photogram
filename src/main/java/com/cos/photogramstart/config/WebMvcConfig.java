package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {  // web설정파일

    @Value("${file.path}")
    private String uploadFoler;  // C:/workspace/springbootwork/upload/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
         .addResourceHandler("/upload/**")  // jsp페이지에서 "/upload/**" 의 주소가 나오면 아래가 발동된다
         .addResourceLocations("file:///" + uploadFoler)
         .setCachePeriod(60*10*6)  // 1시간동안 이미지를 캐싱한다
         .resourceChain(true)
         .addResolver(new PathResourceResolver());
    }
}
