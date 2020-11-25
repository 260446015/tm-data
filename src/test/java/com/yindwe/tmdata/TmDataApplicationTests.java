package com.yindwe.tmdata;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yindwe.tmdata.dto.DemoData;
import com.yindwe.tmdata.dto.ListUrlVO;
import com.yindwe.tmdata.service.impl.ExcelService;
import com.yindwe.tmdata.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class TmDataApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        List<String> cookies = Stream.of("PHPSESSID=leaabofochrsebn1o6lmjd9oc1").collect(Collectors.toList());
        String res = HttpUtil.sendHttpGet(restTemplate, "https://f6.shengejing.com/index.php?login=true", cookies, String.class);
        Document parse = Jsoup.parse(res);
        System.out.println(res);
    }

    @Test
    void test1(){
        String fileName = "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        DemoData demoData = new DemoData();
        demoData.set全店销售额("123");
        demoData.set时间("111");
        demoData.set真实销售额("321");
        // 如果这里想使用03 则 传入excelType参数即可
        ExcelWriter excelWriter = null;
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoData.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(Arrays.asList(demoData), writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Test
    public void test2(){
        List<String> cookies = Stream.of("PHPSESSID=q6r93ero2rkr63nras66nt6eno").collect(Collectors.toList());
        ExcelService excelService = new ExcelService(restTemplate);
        List<ListUrlVO> allTrendUrl = excelService.getAllTrendUrl(cookies);
    }
    @Test
    public void dynamicHeadWrite() {
        String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(head()).sheet("模板")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(null);
    }

    // 动态表头的数据格式List<List<String>>
    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

}
