package com.guli.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.ConverterUtils;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<Test> {

    /**
     * 一行一行读取
     * @param test
     * @param analysisContext
     */
    @Override
    public void invoke(Test test, AnalysisContext analysisContext) {
        System.out.println("************："+test);
    }

    /**
     * 读取表头
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头！！！！！"+headMap);
    }

    /**
     * 读取完成之后
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
