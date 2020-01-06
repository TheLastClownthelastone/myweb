package com.pt.util;

import com.pt.exception.PropertiesNotFindException;
import com.pt.netty.NettyConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author :pt
 * @Date :Created in 16:31 2020/1/2
 * 用来解析properties 文件,项目的启动文件 用来读取项目中。
 */
public class PropertiesUtil {

    private static final String  SERVER_PORT="server.port";


    private static   Properties properties;

    //将类路径下面的properties文件进行加载
    static {
        properties=new Properties();
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            properties=null;
        }
    }


    //获取项目的端口号
    public static  int   getPort(){
        //进行校验查看是否加载到了该properties文件
        if(properties==null){
            throw  new PropertiesNotFindException("当前项目项目下面没有找到properties问价");
        }
        String property = properties.getProperty("server.port");
        int port = Integer.parseInt(property);
        return port;
    }

    public static  String getIp(){
        if(properties==null){
            throw  new PropertiesNotFindException("当前项目项目下面没有找到properties问价");
        }
        String ip = properties.getProperty("server.ip");
        return ip;
    }

    public static  String getKey(String con){
        if(properties==null){
            throw  new PropertiesNotFindException("当前项目项目下面没有找到properties问价");
        }
        String key = properties.getProperty(con);
        return key;
    }

    public static void main(String[] args) {
        NettyConfig nettyConfig = (NettyConfig) AnnoUtil.getObj("NettyConfig");
        String string = nettyConfig.toString();
        System.out.println(string);
    }
}
