package com.pt.exception;

/**
 * @Author :pt
 * @Date :Created in 16:39 2020/1/2
 * 自定义异常用来定位properties文件找不到的情况
 */
public class PropertiesNotFindException  extends  RuntimeException{
    public PropertiesNotFindException(String  message){
        super(message);
    }
}
