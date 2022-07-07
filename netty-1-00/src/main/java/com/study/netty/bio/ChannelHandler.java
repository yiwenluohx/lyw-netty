package com.study.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/7 上午11:00
 * @menu
 */
public class ChannelHandler {
    private Socket socket;
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        OutputStream out;
        try{
            out = socket.getOutputStream();
            out.write(msg.toString().getBytes(charset));
            //刷新输出
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Socket socket() {
        return socket;
    }
}
