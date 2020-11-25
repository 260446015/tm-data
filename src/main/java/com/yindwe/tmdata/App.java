package com.yindwe.tmdata;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * @Author yindwe@yonyou.com
 * @Date 2020/11/25
 */
public class App {
    private static String url = "https://f6.shengejing.com/index.php?tab=4";

    public static void main(String[] args) throws IOException, SAXException {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.addCookie("PHPSESSID=q6r93ero2rkr63nras66nt6eno", new URL(url), null);
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.setJavaScriptTimeout(100000);//设置JS执行的超时时间
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX
        wc.setWebConnection(new WebConnectionWrapper(wc) {
                                public WebResponse getResponse(WebRequest request) throws IOException {
                                    WebResponse response = super.getResponse(request);
                                    String data = response.getContentAsString();

                                    writeFile(data);//将js中获取的数据写入指定路径的txt文件中
                                    return response;
                                }
                            }
        );
        HtmlPage page = wc.getPage(url);
        System.out.println("page:" + page);
        try {
            Thread.sleep(1000);//设置
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //关闭webclient
        wc.close();
    }

    /**
     * 写入TXT文件
     */
    public static void writeFile(String data) {
        try {
            File writeName = new File("data.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try {
                FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer);
                out.write(data);
                out.flush(); // 把缓存区内容压入文件
            }finally {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
