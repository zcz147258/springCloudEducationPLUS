package com.example.eduservice.service;

import com.example.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.subject.oneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<oneSubject> getAllOneTwoSubject();
}
