package com.farmstory.controller.article;

import com.farmstory.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/download/{fno}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fno") int fno) {
        log.info(fno);
        return fileService.downloadFile(fno);
    }
}
