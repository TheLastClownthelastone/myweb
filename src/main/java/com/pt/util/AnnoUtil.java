package com.pt.util;

import com.pt.annition.PtAnno;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

/**
 * @Author :pt
 * @Date :Created in 17:14 2020/1/3
 * 用来读取自定义注解实例化对应的bean
 *
 */


public class AnnoUtil {
    private static  final Log log=LogFactory.getLog(AnnoUtil.class);


    //设置的对应的map用来存取自定义注解中value和对应的该类的对象
    public static Map<String,Class<?>> classMap=new HashMap<>();
    private static List<String> list=new ArrayList<>();

    static {
        log.info("项目开始加载，将配置文件中对应的bean类进行加载中》》》》》》》》》》》》》");
        //通过配置配置文件指定该包名下面那些类型需要实例化
        String pag = PropertiesUtil.getKey("base-packgage");
        String pageName = pag.replace(".", "/");
        Enumeration<URL> dirs=null;
        //获取当前目录下面的所有的子目录url
        try {
            dirs=Thread.currentThread().getContextClassLoader().getResources(pageName);

        }catch (Exception e){
            e.printStackTrace();
        }
        while (dirs.hasMoreElements()){
            URL url=dirs.nextElement();
            //得到当前url的类型
            String protocol = url.getProtocol();
            //如果当前类型是文件类型的话
            if("file".equals(protocol)){
                //获取包路径
                String filePath=null;
                try {
                    filePath= URLDecoder.decode(url.getPath(),"UTF-8");
                    filePath=filePath.substring(1);
                    //获取当前路径下面的所有的子目录和文件
                    Path path=Paths.get(filePath);
                    //获取当前目录下面的steam 流
                    DirectoryStream<Path> stream=Files.newDirectoryStream(path);
                    for (Path p: stream){
                        //将其转换成一个文件
                        File file = p.toFile();
                        //通过递归的形式将所有的文件的物理全路径进行获取
                        List<String> fileNames = getFileNames(file);
                        for(String fileName:fileNames){
                            String classes = fileName.split("classes\\\\")[1];
                            classes=classes.replace("\\",".");
                            String className = classes.substring(0,classes.lastIndexOf("."));
                            //利用java 放射将所有的含有自定义注解的类进行装载
                            Class<?> aClass = Class.forName(className);
                            if(aClass!=null&&null!=aClass.getAnnotation(PtAnno.class)){
                                //向静态成员中添加该数据
                                String value = aClass.getAnnotation(PtAnno.class).value();
                                //如果没有显示的话则用默认的显示
                                if("noname".equals(value)){
                                    classMap.put(className.substring(className.lastIndexOf(".")+1),aClass);
                                }else {
                                    //如果设置value的话则用设置的value内容进行显示
                                    classMap.put(value,aClass);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> getFileNames(File file){
        String str=file.getPath();
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1:files){
                getFileNames(file1);
            }
        }else {
            list.add(file.getPath());
        }
        return list;
    }





    public static void main(String[] args) {
        String path="com.pt";
        String pag=path.replace(".","/");
        Enumeration<URL> resources=null;
        try {
             resources = Thread.currentThread().getContextClassLoader().getResources(pag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (resources.hasMoreElements()){
            URL url=resources.nextElement();
            String protocol = url.getProtocol();
            if("file".equals(protocol)){
                try {
                    //获取到当前文件的物理位置,是从计算机名开始获取所以会多一个/
                    String filepath = URLDecoder.decode(url.getFile(), "UTF-8");
                    filepath = filepath.substring(1);
                    Path dir= Paths.get(filepath);
                    DirectoryStream<Path> stream=null;
                    try {
                        //获取当前目录下的文件的stream流
                        stream= Files.newDirectoryStream(dir);
                    }catch (Exception e){
                            e.printStackTrace();
                    }
                    for (Path p :stream){
                        String fileName = String.valueOf(p.getFileName());
                        File file = p.toFile();
                        //当前文件下面中的所有的类名
                        List<String> fileNames = getFileNames(file);
                        //System.out.println("当前的类名为："+fileNames);
                        for(String str : fileNames){
                            String classes = str.split("classes\\\\")[1];
                            classes=classes.replace("\\",".");
                            String substring = classes.substring(0,classes.lastIndexOf("."));
                            Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(substring);
                            if(aClass!=null&&null!=aClass.getAnnotation(PtAnno.class)){
                                System.out.println(substring);
                            }
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取对应class 进行实例化
      * @param key
     * @return
     */
  public static  Object getObj(String key){
      Class<?> aClass = classMap.get(key);
      Object o=null;
      try {
           o = aClass.newInstance();
      } catch (InstantiationException e) {
          e.printStackTrace();
      } catch (IllegalAccessException e) {
          e.printStackTrace();
      }
      return o;
  }


}
