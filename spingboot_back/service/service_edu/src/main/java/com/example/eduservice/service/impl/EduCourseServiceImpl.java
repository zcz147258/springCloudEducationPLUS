package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduCourseDescription;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.frontvo.CourseFrontVo;
import com.example.eduservice.entity.frontvo.CourseWebVo;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.mapper.EduCourseMapper;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseDescriptionService;
import com.example.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import com.example.servicebase.exception.GuliExecption;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 添加课程
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //courseInfoVo转化属性到eduCourse
        //向添加第一张表
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            throw new GuliExecption(20001,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
        //向第二张表添加
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(eduCourseDescription.getDescription());


        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw new GuliExecption(20001,"修改课程信息失败");
        }

        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //删除描述
        eduCourseDescriptionService.removeById(courseId);

        //删除课程本身
        int i = baseMapper.deleteById(courseId);
        if(i == 0){
            throw new GuliExecption(20001,"删除失败");
        }else{

        }
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> page1, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(courseFrontVo != null){
            //判断条件值
            if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
                wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
            }
            if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
                wrapper.eq("subject_id",courseFrontVo.getSubjectId());
            }
            if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
                wrapper.orderByDesc("buy_count");
            }
            if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
                wrapper.orderByDesc("gmt_create");
            }
            if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
                wrapper.orderByDesc("price");
            }
        }
        baseMapper.selectPage(page1,wrapper);

        List<EduCourse> records = page1.getRecords();
        long current = page1.getCurrent();
        long total = page1.getTotal();
        long pages = page1.getPages();
        long size = page1.getSize();
        boolean hasNext = page1.hasNext();
        boolean hasPrevious = page1.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("total",total);
        map.put("pages",pages);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
