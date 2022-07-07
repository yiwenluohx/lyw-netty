package com.study.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * 通道帮助类
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/6 下午2:10
 * @menu
 */
public class ChannelHandler {
    /**
     * 异步套节字通道
     */
    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelHandler(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        byte[] bytes = msg.toString().getBytes(charset);
        //定义字节缓存区
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //写入字节数组
        writeBuffer.put(bytes);
        //设置读写指针到缓存头部，并且设置最多只能读出之前写入的数据长度
        writeBuffer.flip();
        channel.write(writeBuffer);
    }

    /**
     * Gets the value of channel.
     *
     * @return the value of channel
     */
    public AsynchronousSocketChannel channel() {
        return channel;
    }

    /**
     * Sets the channel. *
     * <p>You can use getChannel() to get the value of channel</p>
     * * @param channel channel
     */
    public void setChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }
}
