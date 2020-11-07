package com.example.eduservice.entity.frontvo;


import lombok.Data;

@Data
public class CourseFrontVo {
    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

    private String buyCountSort;

    private String gmtCreateSort;

    private String priceSort;

}
