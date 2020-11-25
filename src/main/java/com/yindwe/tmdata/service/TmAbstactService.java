package com.yindwe.tmdata.service;

import com.yindwe.tmdata.dto.ListUrlVO;

import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2020/11/25
 */
public abstract class TmAbstactService implements IExcelService{

    public void genExcel(List<String> cookies){
        List<ListUrlVO> listUrlVOS = getAllTrendUrl(cookies);
        Object obj = getAllUrlAndMerge(listUrlVOS);
    }

    protected abstract Object getAllUrlAndMerge(List<ListUrlVO> listUrlVOS);

    protected abstract List<ListUrlVO> getAllTrendUrl(List<String> cookies);

}
