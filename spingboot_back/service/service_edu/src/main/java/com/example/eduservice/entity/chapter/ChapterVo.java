package com.example.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String title;

    private String id;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
