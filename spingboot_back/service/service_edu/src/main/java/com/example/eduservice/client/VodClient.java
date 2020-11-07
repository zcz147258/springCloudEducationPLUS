package com.example.eduservice.client;

import com.example.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)//调用服务名
@Component
public interface VodClient {

    //定义调用方法的路径
    @DeleteMapping("/eduvod/video/removeAlyiVideo/{id}")
    public R removeAlyiVideo(@PathVariable("id") String id);

    /**
     * 多个方法
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("VideoIdList") List<String> videoIdList);
}
