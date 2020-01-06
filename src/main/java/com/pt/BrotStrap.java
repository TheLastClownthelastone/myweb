package com.pt;

import com.pt.annition.PtAnno;
import com.pt.netty.NettyConfig;
import com.pt.util.AnnoUtil;
import com.pt.util.PropertiesUtil;
import com.pt.util.XmlUtil;

/**
 * @Author :pt
 * @Date :Created in 16:28 2020/1/2
 * 程序启动类
 */
@PtAnno
public class BrotStrap {



    /**
     * 程序启动方法
     */
    public  void start(){
        //获取端口
        int port = PropertiesUtil.getPort();
        //通过netty启动数据运输以及开启项目部署。
        try {
            NettyConfig nettyConfig = XmlUtil.getNettyConfig();
            nettyConfig.init(port);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BrotStrap brotStrap= (BrotStrap) AnnoUtil.getObj("BrotStrap");
        brotStrap.start();
    }



}
