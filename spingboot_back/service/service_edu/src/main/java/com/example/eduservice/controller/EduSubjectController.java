package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.entity.subject.oneSubject;
import com.example.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;


    /**
     * //添加课程分类
     * //获取上传过来的文件,把文件读取下来\
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }


    /**
     * 课程分类
     * 树形结构
     */
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<oneSubject> list =  eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

