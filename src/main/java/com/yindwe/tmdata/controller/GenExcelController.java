package com.yindwe.tmdata.controller;

import com.yindwe.tmdata.service.impl.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2020/11/25
 */
@RestController
public class GenExcelController {

    @Resource
    private ExcelService excelService;

    @PostMapping
    public void genExcel(@RequestBody List<String> cookies){
        excelService.getAllTrendUrl(cookies);
    }
}
