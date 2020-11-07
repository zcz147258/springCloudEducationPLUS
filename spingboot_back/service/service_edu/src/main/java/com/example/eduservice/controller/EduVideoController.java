package com.example.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.example.commonutils.R;
import com.example.eduservice.client.VodClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.service.EduVideoService;
import com.example.servicebase.exception.GuliExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;


    @Autowired
    private VodClient vodClient;
    /**
     * 添加
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * TODo 删除完善
     * 服务调用
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id查询视频id
        EduVideo eduvideo = eduVideoService.getById(id);
        String videoSourceId = eduvideo.getVideoSourceId();
        //根据视频id
        if(!StringUtils.isEmpty(videoSourceId)){
            //熔断器判断
            R res =  vodClient.removeAlyiVideo(videoSourceId);
            if(res.getCode() == 20001){
                throw  new GuliExecption(20001,"删除视频失败 熔断器....");
            }

        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("updateVideo")
    public R updateVideo(@PathVariable EduVideo eduVideo ){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

