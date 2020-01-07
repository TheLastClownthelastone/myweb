package com.pt.util;

import com.pt.controller.FirstController;
import com.pt.model.HttpMethodAnno;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author :pt
 * @Date :Created in 16:07 2020/1/6
 */
public class HttpUtil {
    /**
     * 通过对应的路径执行映射get的方法
     * @param path 路劲地址
     * @param paramType 参数类型
     * @param params 参数的值
     */
    public  static Object doGet(String path, List<Object> params, Class<?>... paramType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Object param:
                paramType) {
            System.out.println("参数为："+param);
        }
        HttpMethodAnno methodAnno = AnnoUtil.methodMap.get(path);
        String className = methodAnno.getClassName();
        String methodName = methodAnno.getValue();
        Class<?> aClass = AnnoUtil.classMap.get(className);
        Object o = AnnoUtil.getObj(className);

        Method method = aClass.getMethod(methodName, paramType);
        Object result = method.invoke(o, params.get(0));
        return result;
    }

    /**
     * 对应路径映射post方法
     * @param path
     * @param params
     * @return
     */
    public  static Object doPost(String path, List<Object> params, Class<?>... paramType){
        for (Object param:
                paramType) {
            System.out.println("参数为："+param);
        }
        HttpMethodAnno methodAnno = AnnoUtil.methodMap.get(path);
        String className = methodAnno.getClassName();
        String methodName = methodAnno.getValue();
        Class<?> aClass=null;
        Object result=null;
        try {
            aClass = Class.forName(className);
            Object o = aClass.newInstance();
            Method method = aClass.getMethod(methodName, paramType);
            result = method.invoke(o, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
