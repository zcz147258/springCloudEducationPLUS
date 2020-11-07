package com.example.ossservice.sevice;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    /**
     * 上传头像到service
     */
    String uploadFileAvatar(MultipartFile file);
}
