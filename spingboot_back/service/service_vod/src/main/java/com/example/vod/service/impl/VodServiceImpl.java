package com.example.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.servicebase.exception.GuliExecption;
import com.example.vod.service.VodService;
import com.example.vod.utils.ConstantVodUtils;
import com.example.vod.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAlyiVideo(MultipartFile file) {
        try {
            //原始文件名称
            String fileName = file.getOriginalFilename();
            //截取文件名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();

            System.out.println(ConstantVodUtils.ACCESS_KEY_ID);
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName,inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初始化
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置id

            String str =  StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(str);
            //调用初始化方法
            client.getAcsResponse(request);


        }catch (Exception e){
            e.printStackTrace();
            throw new GuliExecption(20001,"删除失败");
        }
    }
}
