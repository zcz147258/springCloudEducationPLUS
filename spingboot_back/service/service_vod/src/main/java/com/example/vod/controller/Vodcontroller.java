package com.example.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.ram.model.v20150501.DeleteAccessKeyRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.commonutils.R;
import com.example.servicebase.exception.GuliExecption;
import com.example.vod.service.VodService;
import com.example.vod.utils.ConstantVodUtils;
import com.example.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class Vodcontroller {

    @Autowired
    private VodService vodService;

    /**
     * 上传视屏到阿里云
     */
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        String videoId =  vodService.uploadAlyiVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除
     */
    @DeleteMapping("removeAlyiVideo/{id}")
    public R removeAlyiVideo(@PathVariable String id){
        try {
            //初始化
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置id
            request.setVideoIds(id);
            //调用初始化方法
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliExecption(20001,"删除失败");
        }
    }

    /**
     * 删除多个视频的方法
     * 参数多个
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("VideoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
