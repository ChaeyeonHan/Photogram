package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})
    public String story(){
        return "image/story";
    }

    // API로 구현한다면, 브라우저가 아니라 안드로이드, IOS로 요청하게 되는 경우(html페이지를 넘겨주는게 아니라 데이터를 넘겨줘야 하기에)
    @GetMapping("/image/popular")  // 인기페이지 구현
    public String popular(Model model){  // 인기 사진 담아가기
        // api는 데이터를 리턴하는 서버.
        List<Image> images = imageService.인기사진();

        model.addAttribute("images", images);
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
