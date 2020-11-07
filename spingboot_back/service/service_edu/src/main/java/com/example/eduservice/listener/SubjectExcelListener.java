package com.example.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.SubjectData;
import com.example.eduservice.service.EduSubjectService;
import com.example.servicebase.exception.GuliExecption;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为不能交给spring进行管理,需要自己new,
    public EduSubjectService subjectService;
    //通过有参构造传入  subjectService
    /**
     * 读取excel
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliExecption(20001,"文件数据为空");
        }

        //一行一行读取,每次读取两个值,分别判断一二级分类
        EduSubject eduOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(subjectData.getOneSubjectName());//一级类添加
            subjectService.save(eduOneSubject);
        }
        //添加二级
        String pid = eduOneSubject.getId();
        EduSubject edutwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(edutwoSubject == null){
            edutwoSubject = new EduSubject();
            edutwoSubject.setParentId(pid);
            edutwoSubject.setTitle(subjectData.getTwoSubjectName());//二级类添加
            subjectService.save(edutwoSubject);
        }

    }

    /**
     * 判断一级分类
     *
     */
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类
     *
     */
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    /**
     * 有参
     * @param subjectService
     */
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     *
     *无参
     */
    public SubjectExcelListener(){}
}
