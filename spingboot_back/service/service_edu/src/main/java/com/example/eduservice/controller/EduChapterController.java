package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.chapter.ChapterVo;
import com.example.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mikasa
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 课程大纲列表
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list =  eduChapterService.getChapterVideoById(courseId);

        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 章节id查询
     */
    @GetMapping("getChapterInfo/{chapterId]")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        Boolean flag =  eduChapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

