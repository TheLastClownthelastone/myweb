package com.pt.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author :pt
 * @Date :Created in 11:41 2020/1/3
 * 时间工具类
 */
public class DateUtil {

    private static  List<String> list=new ArrayList<>();

    public  static String getnewDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

    public static List<String> getListFile(File file){

        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f:
                 files) {
                getListFile(f);
            }
        }else {
            list.add(file.getName());
        }
        return list;
    }


    public static void main(String[] args) {
        String path="E:\\code\\myweb\\target\\classes\\com\\pt";
        File file=new File(path);
        List<String> listFile = getListFile(file);
        System.out.println(listFile);
    }

}
