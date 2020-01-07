package com.pt.netty;

import com.pt.util.DateUtil;
import com.pt.util.HtmlUtil;
import com.pt.util.HttpUtil;
import com.pt.util.PropertiesUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author :pt
 * @Date :Created in 17:13 2020/1/2
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {
    private Log log= LogFactory.getLog(NettyHandler.class);


    /**
     * 处理请求
     * @param ctx
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("建立连接，开始处理逻辑");
        //进行日志打印访问路径
        FullHttpRequest request= (FullHttpRequest) msg;
        System.out.println(request.getUri());
       //通过对应的方法进行映射到页面或者响应json数据
       if(request.uri().equals("/")||request.uri().equals("/index")){
           Consumer c=System.out::println;
           c.accept("访问地址为"+ PropertiesUtil.getIp()+":"+PropertiesUtil.getPort() +request.uri());
           send(ctx, HtmlUtil.getHtml("index"),HttpResponseStatus.OK);
       }
       //根据请求方法进行判断
        HttpMethod method = request.getMethod();
        if(HttpMethod.GET.equals(method)){
            String uri = request.getUri();

            //判断当前不是网站图标
            if(!uri.equals("/favicon.ico")){
                //获取路径后面的参数值
                String finalPath=uri.split("\\?")[0];
                String s = uri.split("\\?")[1];
                String  param="";
                if(s.contains("&")){
                    String[] split = s.split("&");
                    for(String str : split){
                        param=str.split("=")[1];
                    }
                }else {
                    param=s.split("=")[1];
                }
                String o = (String) HttpUtil.doGet(finalPath, Collections.singletonList(param), String.class);
                sendJson(ctx,o,HttpResponseStatus.OK);
            }else{
                //返回网站左上角图片
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("favicon.ico");
                Reader reader=new InputStreamReader(in);
                BufferedReader br=new BufferedReader(reader);
                sendIco(ctx,HttpResponseStatus.OK,br);
            }


        }



    }


    /**
     * 收到请求之前操作的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Consumer c=System.out::println;
        c.accept("我是最先执行的方法在处理业务逻辑之前");
        ctx.writeAndFlush(ctx);

    }

    /**
     * f发送普通页面
     * @param ctx
     * @param context
     * @param status
     */
    public void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发送json
     * @param ctx
     * @param context
     * @param status
     */
    public void sendJson(ChannelHandlerContext ctx, String context,HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public void sendIco(ChannelHandlerContext ctx,HttpResponseStatus status,BufferedReader reader) throws IOException {
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/x-ico");
        ByteBuf content = response.content();
        byte[] bytes=new byte[66533];
        int len= -1;
        while ((len=reader.read())!=-1){
            content.writeBytes(bytes,0,len);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
