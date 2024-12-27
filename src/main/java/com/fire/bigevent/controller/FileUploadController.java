package com.fire.bigevent.controller;

import com.fire.bigevent.pojo.Result;
import com.fire.bigevent.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // 原始文件名
        String originalFilename = file.getOriginalFilename();

        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = aliOssUtil.upload(file.getBytes(),filename);

        System.out.println(url);

        return Result.success(url);


    }
}
