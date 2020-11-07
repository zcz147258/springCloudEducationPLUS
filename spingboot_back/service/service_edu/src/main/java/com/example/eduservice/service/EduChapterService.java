package com.example.eduservice.service;

import com.example.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);

    Boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
