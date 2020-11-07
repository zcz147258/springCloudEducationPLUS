package com.example.eduservice.mapper;

import com.example.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {

    void selectList();
}
