package com.study.netty.bio.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/7/7 下午2:01
 * @menu
 */
public class BioClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.0.111", 7397);
            System.out.println("bio client start done. ");
            BioClientHandler bioClientHandler = new BioClientHandler(socket, Charset.forName("utf-8"));
            bioClientHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
