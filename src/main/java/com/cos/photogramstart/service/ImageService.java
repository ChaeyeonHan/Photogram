package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")  // application.yml에서 file-path에 있는 경로를 가져온다
    private String uploadFolder;  // yml에 적은 값도 가져올 수 있다!

//    private String uploadFolder = "C:/workspace/springbootwork/upload/";  // 경로뒤에 / 반드시 있어야함

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();  // uuid(Universally Unique IDentifier) : 네트워크상에서 고유성이 보장되는 id를 만들기 위해 사용한다. 중복이 되지 않게 나온다.
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();  // 실제 파일 이름이 들어간다  ex) bag.jpg
        // 서버의 upload라는 폴더에 bag.jpg가 저장되고, db에는 "/upload/bag.jpg"라는 경로가 저장된다.
        // 따라서 동일한 파일명이 저장되면 덮어씌워지기에 우리가 사진을 받아서 구분해줘야 한다. -> 구분에 uuid사용

        System.out.println("이미지 파일 이름: " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);  // 실제 저장되는 경로를 지정(경로 + 파일명)

        // 통신이 일어나거나 I/O가(하드디스크에 기록하거나 읽을때) 일어날때 -> 예외가 발생
        // 어떤 파일을 읽어오고 싶은데 하드디스크에 해당 파일이 없는 경우 -> 컴파일시에 잡아낼 수가 없어 런타임시에만(실제 실행될때) 잡아낼 수 있음 -> 예외처리 해줘야한다
        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());  // 경로, 실제 이미지 파일(바이트화해서)
        } catch (Exception e){
            e.printStackTrace();
        }

        // image테이블에 저장 (imageUploadDto로 image객체로 변환하는 과정이 필요)
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());  // 13b00cd8-e623-47c5-be31-0a9c5acb6aef_cat.jpg가 db에 저장된다
        Image imageEntity = imageRepository.save(image);

//        System.out.println(imageEntity);
    }

    @Transactional(readOnly = true)  // 영속성 컨텍스트 변경감지를 해서, 변경이 되었으면 더티체킹을해서 db에 flush(반영) X -> 성능이 좀 더 좋아짐
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // images에 좋아요 상태 담기
        images.forEach((image) ->{

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((likes -> {
                if (likes.getUser().getId() == principalId){ // 해당 이미지에 좋아요를 누른 사람들을 찾아서 현재 로그인한 사람이 좋아요를 눌렀는지 확인
                    image.setLikeState(true);

                }
            }));
        });

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> 인기사진(){
        return imageRepository.mPopular();
    }
}
