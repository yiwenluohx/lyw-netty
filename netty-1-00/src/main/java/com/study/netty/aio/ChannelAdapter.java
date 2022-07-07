package com.study.netty.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 消息处理器
 * CompletionHandler<Integer, Object>  定义异步通道处理完成方法
 * Integer： I/O操作返回结果类型
 * Object：启动时，操作的对象
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/6 下午2:47
 * @menu 消息处理器
 */
public abstract class ChannelAdapter implements CompletionHandler<Integer, Object> {
    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelAdapter(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
        if (channel.isOpen()) {
            this.channelActive(new ChannelHandler(channel, charset));
        }
    }

    /**
     * 完成
     *
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Integer result, Object attachment) {
        try {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            final long timeout = 60 * 60L;
            channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    if (result == -1) {
                        try {
                            channelInactive(new ChannelHandler(channel, charset));
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    buffer.flip();
                    channelRead(new ChannelHandler(channel, charset), charset.decode(buffer));
                    buffer.clear();
                    channel.read(buffer, timeout, TimeUnit.SECONDS, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 失败
     *
     * @param exc
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.getStackTrace();
    }

    /**
     * 活跃/使用的通道
     *
     * @param ctx
     */
    public abstract void channelActive(ChannelHandler ctx);

    /**
     * 不活跃/未使用通道
     *
     * @param ctx
     */
    public abstract void channelInactive(ChannelHandler ctx);

    /**
     * 读取消息抽象类
     *
     * @param ctx
     * @param msg
     */
    public abstract void channelRead(ChannelHandler ctx, Object msg);
}
