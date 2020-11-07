package com.example.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.service.EduTeacherService;
import com.example.servicebase.exception.GuliExecption;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    /**
     * 查询所有
     * @return
     */
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public R findAllData(){
        List<EduTeacher> list = eduTeacherService.list(null);
//        try{
//            int a = 30/0;
//        }catch (Exception e){
//            //执行自定义异常
//            throw new GuliExecption(505,"执行了自定义异常");
//        }
        return R.ok().data("items",list).code(200).message("成功");
    }

    /**
     * 删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("page_teacher/{currentPage}/{pageSize}")
    public R pageTeacher(@PathVariable long currentPage,
                         @PathVariable long pageSize){
        Page<EduTeacher> page = new Page<>(currentPage,pageSize);
        eduTeacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
    /**
     * 多条件组合分页查询
     */
    @PostMapping("pageTeacherCondition/{currentPage}/{pageSize}")
    public R pageTeacherCondition(@PathVariable long currentPage,@PathVariable long pageSize,@RequestBody TeacherQuery teacherQuery){
        System.out.println(teacherQuery);
        Page<EduTeacher> page = new Page<>(currentPage,pageSize);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        System.out.println("'teacherQuery:'"+teacherQuery);
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        eduTeacherService.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> rows = page.getRecords();
        return R.ok().data("total",total).data("rows",rows);
    }


    /**
     * 添加讲师
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }

    }

    /**
     * 讲师id查询
     */
    @GetMapping("getTeacher/{id}")
    public  R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

