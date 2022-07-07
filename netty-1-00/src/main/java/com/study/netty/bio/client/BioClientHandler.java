package com.study.netty.bio.client;

import ch.qos.logback.core.net.ssl.SSLNestedComponentRegistryRules;
import com.study.netty.bio.ChannelAdapter;
import com.study.netty.bio.ChannelHandler;
import sun.awt.geom.AreaOp;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/7 下午2:05
 * @menu
 */
public class BioClientHandler extends ChannelAdapter {

    public BioClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println("链接报告LocalAddress:" + ctx.socket().getLocalAddress());
        ctx.writeAndFlush("hi, 我是BioClient to message for you \r\n");
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }
}
