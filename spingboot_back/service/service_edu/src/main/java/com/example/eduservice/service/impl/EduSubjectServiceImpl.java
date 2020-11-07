package com.example.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.SubjectData;
import com.example.eduservice.entity.subject.oneSubject;
import com.example.eduservice.entity.subject.twoSubject;
import com.example.eduservice.listener.SubjectExcelListener;
import com.example.eduservice.mapper.EduSubjectMapper;
import com.example.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 上传表格
     * @param file
     * @param eduSubjectService
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try{
            InputStream in = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回树形结构
     * @return
     */
    @Override
    public List<oneSubject> getAllOneTwoSubject() {
        //查询所有的一二级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOne);

        //查询所有的一二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> TwoSubjectsList = baseMapper.selectList(wrapperTwo);

        List<oneSubject> finalSubjectsList = new ArrayList<>();

        for (int i = 0; i < oneSubjectsList.size(); i++) {
            EduSubject eduSubject = oneSubjectsList.get(i);
            oneSubject one = new oneSubject();

            //复制属性
            BeanUtils.copyProperties(eduSubject,one);

            //二级
            List<twoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < TwoSubjectsList.size() ; j++) {
                EduSubject tsubject  = TwoSubjectsList.get(j);
                //判断parent_id 是否一样
                if(tsubject.getParentId().equals(eduSubject.getId())){
                    twoSubject twosubjcet = new twoSubject();
                    //放入
                    BeanUtils.copyProperties(tsubject,twosubjcet);
                    twoFinalSubjectList.add(twosubjcet);
                }
            }
            //把二级放入一级
            one.setChildren(twoFinalSubjectList);
            finalSubjectsList.add(one);
        }

        return finalSubjectsList;
    }
}
