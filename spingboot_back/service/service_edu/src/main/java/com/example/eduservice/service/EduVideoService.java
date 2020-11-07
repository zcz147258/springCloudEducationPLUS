package com.example.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
public interface EduVideoService extends IService<EduVideo> {

    void list(QueryWrapper<EduChapter> wrapperVideo);

    void removeVideoByCourseId(String courseId);
}
