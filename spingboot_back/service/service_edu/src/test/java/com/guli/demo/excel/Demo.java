package com.guli.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        //实现写Excel的操作
        //1.设置写入excel的文件夹地址和名称
        String filename = "E:\\write.xlsx";

        //2.实现写excel的方法  第二个参数实体类 sheet 和 数据
//        EasyExcel.write(filename,Test.class).sheet("学生列表").doWrite(getData());


//        1.读
        EasyExcel.read(filename,Test.class,new ExcelListener()).sheet().doRead();
    }
    //返回list
    private static List<Test> getData(){
        List<Test> list = new ArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            Test test = new Test();
            test.setSno(i);
            test.setSname("lucy"+i);
            list.add(test);
        }
        return list;
    }
}
