package com.pt.netty;

import com.pt.util.DateUtil;
import com.pt.util.HtmlUtil;
import com.pt.util.PropertiesUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

       //通过对应的方法进行映射到页面或者响应json数据
       if(request.uri().equals("/")||request.uri().equals("/index")){
           Consumer c=System.out::println;
           c.accept("访问地址为"+ PropertiesUtil.getIp()+":"+PropertiesUtil.getPort() +request.uri());
           send(ctx, HtmlUtil.getHtml("index"),HttpResponseStatus.OK);
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


    public void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
