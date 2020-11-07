package com.example.ossservice.controller;

import com.example.commonutils.R;
import com.example.ossservice.sevice.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    /**
     * 上传头像
     */
    @Autowired
    OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file){//获取上传文件

        String url =  ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
