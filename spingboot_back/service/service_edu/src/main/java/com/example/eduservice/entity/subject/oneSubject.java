package com.example.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class oneSubject {

    private String id;

    private String title;

    //一个一级分类有多个二级分类
    private List<twoSubject> children = new ArrayList<>();
}
