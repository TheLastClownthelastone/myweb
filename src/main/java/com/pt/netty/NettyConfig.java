package com.pt.netty;

import com.mysql.cj.util.LogUtils;
import com.pt.annition.PtAnno;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Logger;

/**
 * @Author :pt
 * @Date :Created in 16:51 2020/1/2
 * 进行netty模式开启http服务
 */
@PtAnno
public class NettyConfig {
    private Log log= LogFactory.getLog(NettyConfig.class);
    public void  init(int port) throws InterruptedException {
        //设置netty的模式并发不阻塞的
        EventLoopGroup group=new NioEventLoopGroup();
        //设置bootsrap
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioServerSocketChannel.class);
        //自定义过滤器
        bootstrap.childHandler(new NettyServerFilter());
        //进行端口的绑定
        ChannelFuture sync = bootstrap.bind(port).sync();
        log.info("服务器端口绑定成功》》》》》》》》》》》》》》 端口号："+port);
        sync.channel().closeFuture().sync();
        //释放资源
        group.shutdownGracefully();
    }

}
