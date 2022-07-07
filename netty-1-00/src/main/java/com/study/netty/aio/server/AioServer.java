package com.study.netty.aio.server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Aio服务端
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/6 下午2:46
 * @menu Aio服务端
 */
public class AioServer extends Thread {

    /**
     * 服务端异步套节字通道
     */
    private AsynchronousServerSocketChannel serverSocketChannel;

    @Override
    public void run() {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10));
            serverSocketChannel.bind(new InetSocketAddress(7397));
            System.out.println("aio server start done.");
            // CountDownLatch的作用就是等待其他的线程都执行完任务，必要时可以对各个任务的执行结果进行汇总，然后主线程才继续往下执行
            //构造CountDownLatch的时候需要传入一个整数n，在这个整数“倒数”到0之前，主线程需要等待在门口
            CountDownLatch latch = new CountDownLatch(1);
            serverSocketChannel.accept(this, new AioServerChannelInitializer());
            latch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsynchronousServerSocketChannel serverSocketChannel() {
        return serverSocketChannel;
    }

    public static void main(String[] args) {
        new AioServer().start();
    }
}
