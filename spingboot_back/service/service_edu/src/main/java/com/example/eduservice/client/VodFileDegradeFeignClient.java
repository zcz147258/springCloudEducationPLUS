package com.example.eduservice.client;

import com.example.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyiVideo(String id) {
        return R.error().message("删除失败");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除失败");
    }
}
