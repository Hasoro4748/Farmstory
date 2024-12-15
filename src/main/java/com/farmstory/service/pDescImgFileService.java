package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.pDescImgFileDTO;
import com.farmstory.entity.pDescImgFile;
import com.farmstory.repository.pDescImgFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class pDescImgFileService {

    private final pDescImgFileRepository pDescImgFileRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public pDescImgFileDTO uploadpDescImgFile(ProductDTO productDTO) {

        // 파일 업로드 경로 파일 객체 생성
        File fileUploadPath = new File(uploadPath+"/product");

        // sName 담는 객체 리스트 생성
        List<String> sNameList = new ArrayList<>();

        // 파일 업로드 경로가 존재하지 않으면 디렉터리 생성
        if(!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        // 파일 업로드 시스템 경로 구하기
        String path = fileUploadPath.getAbsolutePath();

        // 파일 정보 객체 가져오기 -> 이름 바꿀 필요 PList_fNo
        List<MultipartFile> files = productDTO.getMultipartFiles();

        // 첨부파일 정보 객체 생성
        pDescImgFileDTO uploadFiles = null;



        for (MultipartFile file : files) {

            if(!file.isEmpty()){
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    file.transferTo(new File(path, sName));
                } catch (IOException e) {
                    log.error(e);
                }
                sNameList.add(sName);
            }
        }
        // 3개 다 넣어야 한다고 스크립트 처리 필요! 이미지가 3개 있다는 가정하에 로직 작성
        pDescImgFileDTO PDescImgFileDTO = pDescImgFileDTO.builder()
                .p_sName1(sNameList.get(0))
                .p_sName2(sNameList.get(1))
                .p_sName3(sNameList.get(2))
                .build();

        return PDescImgFileDTO;
    }

    public void insertpDescImgFile(pDescImgFileDTO pDescImgFileDTO) {
        pDescImgFile DescImgFile =  modelMapper.map(pDescImgFileDTO, pDescImgFile.class);
        pDescImgFileRepository.save(DescImgFile);
    }


}
