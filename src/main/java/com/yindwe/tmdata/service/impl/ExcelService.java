package com.yindwe.tmdata.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yindwe.tmdata.config.UrlConfigProperties;
import com.yindwe.tmdata.dto.DemoData;
import com.yindwe.tmdata.dto.ListUrlVO;
import com.yindwe.tmdata.service.TmAbstactService;
import com.yindwe.tmdata.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2020/11/25
 */
@Service
public class ExcelService extends TmAbstactService {
    @Resource
    private UrlConfigProperties urlConfigProperties;

    @Override
    public void genExcel() {

    }

    private final RestTemplate restTemplate;
    private static final String url = "https://f6.shengejing.com/index.php?tab=4";

    public ExcelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected Object getAllUrlAndMerge(List<ListUrlVO> listUrlVOS) {
        return null;
    }

    @Override
    public List<ListUrlVO> getAllTrendUrl(List<String> cookies) {
        String resStr = HttpUtil.sendHttpGet(restTemplate, urlConfigProperties.getTrendUrl(), cookies, String.class);
        JSONObject res = JSONObject.parseObject(resStr);
        JSONArray appData = res.getJSONArray("aaData");
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write("test.xlsx").build();
            for (int i = 0; i < appData.size(); i++) {
                Object appDatum = appData.get(i);
                String id = (String) ((JSONArray) appDatum).get(2);
                String trendDetailUrl = urlConfigProperties.getTrendDetailUrl();
                trendDetailUrl += id;
                String itemsUrl = HttpUtil.sendHttpGet(restTemplate, trendDetailUrl, cookies, String.class);
                Document root = Jsoup.parse(itemsUrl);
                Element central = root.getElementById("central");
                Elements ths = central.getElementsByTag("th");
                List<List<String>> headList = new ArrayList<>();
                for (Element th : ths) {
                    List<String> thList = new ArrayList<>();
                    thList.add(th.html());
                    headList.add(thList);
                }
                Element tbody = central.getElementsByTag("tbody").get(0);
                Elements trs = tbody.getElementsByTag("tr");
                List<List<String>> tdList = new ArrayList<>();
                for (Element tr : trs) {
                    Elements tds = tr.getElementsByTag("td");
                    List<String> itemList = new ArrayList<>();
                    for (Element td : tds) {
                        itemList.add(td.html());
                    }
                    tdList.add(itemList);
                }
                String title = (String) ((JSONArray) appDatum).get(1);
                String titleStr = Jsoup.parse(title).getElementsByTag("a").get(0).html();
                WriteSheet writeSheet = EasyExcel.writerSheet(i, titleStr).head(headList).build();
                excelWriter.write(tdList, writeSheet);
            }
        }finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        excelWriter.finish();
        return null;
    }
}
