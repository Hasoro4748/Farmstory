package com.farmstory.service;

import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.CsArticleDTO;
import com.farmstory.dto.FileDTO;
import com.farmstory.entity.FileEntity;
import com.farmstory.repository.ArticleRepository;
import com.farmstory.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final ModelMapper modelMapper;

    //upload file
    public List<FileDTO> uploadFile(ArticleDTO articleDTO){

        //파일 시스템 경로 구하기
        File fileuploadpath = new File(uploadPath+"article/");
        if(!fileuploadpath.exists()){
            fileuploadpath.mkdirs();
        }

          String path=  fileuploadpath.getAbsolutePath();
            //파일 정보객체 불러오기
          List<MultipartFile> files = articleDTO.getFiles();
          //파일 fl
          List<FileDTO> fileDTOs = new ArrayList<>();
          log.info("file size : "+files.size());
          for(MultipartFile file : files){
              log.info("file resource " + file.getResource());
              String OName = file.getOriginalFilename();
              if(OName != null && !OName.isEmpty()){

                  log.info("original name:"+OName);
                    //확장자
                  String ext = OName.substring(OName.lastIndexOf("."));
                  String Sname= UUID.randomUUID().toString()+ext;

                  //파일 저장
                  try {
                      file.transferTo(new File(path,Sname));

                      FileDTO fileDTO  =  FileDTO.builder()
                              .oName(OName)
                              .sName(Sname)
                              .build();
                      FileEntity savedFile= modelMapper.map(fileDTO, FileEntity.class);
                      fileRepository.save(savedFile);
                      fileDTOs.add(fileDTO);


                  } catch (IOException e) {
                     log.error(e);
                  }

          }

      } //for문 종료
        log.info(fileDTOs);

        return fileDTOs;
    }

    //updateFile csArticle
    //upload file
    public List<FileDTO> uploadFile(CsArticleDTO articleDTO){

        //파일 시스템 경로 구하기
        File fileuploadpath = new File(uploadPath+"article/");
        if(!fileuploadpath.exists()){
            fileuploadpath.mkdirs();
        }

        String path=  fileuploadpath.getAbsolutePath();
        //파일 정보객체 불러오기
        List<MultipartFile> files = articleDTO.getFiles();
        //파일 fl
        List<FileDTO> fileDTOs = new ArrayList<>();
        log.info("file size : "+files.size());
        for(MultipartFile file : files){
            log.info("file resource " + file.getResource());
            String OName = file.getOriginalFilename();
            if(OName != null && !OName.isEmpty()){

                log.info("original name:"+OName);
                //확장자
                String ext = OName.substring(OName.lastIndexOf("."));
                String Sname= UUID.randomUUID().toString()+ext;

                //파일 저장
                try {
                    file.transferTo(new File(path,Sname));

                    FileDTO fileDTO  =  FileDTO.builder()
                            .oName(OName)
                            .sName(Sname)
                            .build();
                    FileEntity savedFile= modelMapper.map(fileDTO, FileEntity.class);
                    fileRepository.save(savedFile);
                    fileDTOs.add(fileDTO);


                } catch (IOException e) {
                    log.error(e);
                }

            }

        } //for문 종료
        log.info(fileDTOs);

        return fileDTOs;
    }

    //download File
    public ResponseEntity<Resource> downloadFile(int fno) {
        Optional<FileEntity> optFile = fileRepository.findById(fno);

        FileEntity fileEntity = null;
        Resource resource =null;
        if (optFile.isPresent()) {
            fileEntity = optFile.get();
            fileEntity.setDownload();
            FileEntity savedFile = fileRepository.save(fileEntity);
            Path path = Paths.get(uploadPath+"article/"+ fileEntity.getSName());
            try {
                String ContentType = Files.probeContentType(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(
                        ContentDisposition.builder("attachment")
                                .filename(fileEntity.getOName(), StandardCharsets.UTF_8)
                                .build());
                headers.add(HttpHeaders.CONTENT_TYPE, ContentType);


                resource = new InputStreamResource(Files.newInputStream(path));

                return ResponseEntity.ok().headers(headers).body(resource);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }
        //java.nio.file
        //uploadPath ==
        return ResponseEntity.ok().body(resource);

    }


    public int insertFile(FileDTO fileDTOS) {
        FileEntity file = modelMapper.map(fileDTOS, FileEntity.class);
        if(file !=null){
            FileEntity savedfile = fileRepository.save(file);
            return savedfile.getFno();
        }
        return 0;
    }
    public FileDTO selectFileByFno(int fno){return null;}

    public FileDTO updateFileDownload(int fno){
        Optional<FileEntity> optFile = fileRepository.findById(fno);
        if (optFile.isPresent()) {
            FileEntity fileEntity = optFile.get();
            fileEntity.setDownload();
            FileEntity savedFile = fileRepository.save(fileEntity);
            return  modelMapper.map(savedFile, FileDTO.class);
        }
        return null;
    }

    public List<FileDTO> selectFileAllByAno(int ano){return null;}
    public void updateFile(MultipartFile file,ArticleDTO articleDTO) {


    }
    public int deleteFile(int fno){

        Optional<FileEntity> optFile = fileRepository.findById(fno);
        int result=0;
        if (optFile.isPresent()) {
            FileEntity fileEntity = optFile.get();
            Path path = Paths.get(uploadPath+"article/"+ fileEntity.getSName());
            File file = new File(path.toString());
            if(file.exists()){
                boolean deleted = file.delete();
                if(deleted){
                    fileRepository.deleteById(fno);
                   result=1;
                }else {
                    throw new RuntimeException("파일 삭제 실패: " + path.toString());
                }
            } else {
                throw new RuntimeException("파일을 찾을 수 없음: " + path.toString());
            }
        } else {
            throw new RuntimeException("파일 정보를 찾을 수 없음: fno = " + fno);
        }

        return result;

    }

}
