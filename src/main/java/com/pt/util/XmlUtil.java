package com.pt.util;

import com.pt.netty.NettyConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.rmi.CORBA.Util;
import java.io.InputStream;

/**
 * @Author :pt
 * @Date :Created in 11:05 2020/1/3
 * 解析xml文件
 */
public class XmlUtil {

    private static Element rootElement;

    static {
        SAXReader saxReader=new SAXReader();
        InputStream in = XmlUtil.class.getClassLoader().getResourceAsStream("server.xml");
        try {
            Document document = saxReader.read(in);
            rootElement=document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


    public static NettyConfig getNettyConfig() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String netty = rootElement.element("netty").getText();
        Class<?> aClass = Class.forName(netty);
        NettyConfig nettyConfig= (NettyConfig) aClass.newInstance();
        return nettyConfig;
    }
}
