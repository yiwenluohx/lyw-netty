package com.study.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/7 下午5:06
 * @menu
 */
public class NioServer {
    private Selector selector;
    private ServerSocketChannel socketChannel;

    public static void main(String[] args) throws IOException {
        new NioServer().bind(7397);
    }

    public void bind(int port) {
        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(port), 1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("nio server start done. ");
            new NioServerHandler(selector, Charset.forName("GBK")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
