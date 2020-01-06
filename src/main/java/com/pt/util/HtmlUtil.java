package com.pt.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @Author :pt
 * @Date :Created in 11:59 2020/1/3
 * 用来发送不通的HTML页面
 */
public class HtmlUtil {



    public static  String getHtml(String name) throws DocumentException {
        SAXReader saxReader=new SAXReader();
        InputStream in = HtmlUtil.class.getClassLoader().getResourceAsStream("html/" + name + ".html");
        Document read = saxReader.read(in);
        String html = read.asXML();
        return html;
    }
}
