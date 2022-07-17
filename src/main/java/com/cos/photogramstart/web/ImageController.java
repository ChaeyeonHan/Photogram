package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular(){
        return "image/popular";
    }

    @GetMapping("/image/upload")  // 이미지 업로드 api
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        if (imageUploadDto.getFile().isEmpty()){
//            System.out.println("이미지가 첨부되지 않았습니다.");
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        // 서비스 호출
        imageService.사진업로드(imageUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();  // "/user/유저인덱스번호"로 돌아가게끔
    }
}
