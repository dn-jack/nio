package com.dongnao.jack.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * @Description 传统IO，服务端 
 * @ClassName   BIOServer 
 * @Date        2017年6月24日 下午9:24:10 
 * @Author      动脑学院-jack
 */

public class BIOServer {
    
    ServerSocket server;
    
    public BIOServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("IO服务已经启动，监控的端口是：" + port);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void start() {
        while (true) {
            try {
                Socket client = server.accept();
                InputStream is = client.getInputStream();
                byte[] buff = new byte[1024];
                int len = is.read(buff);
                if (len > 0) {
                    String msg = new String(buff, 0, len);
                    System.out.println("服务端已经接受到客户端数据，数据内容是：" + msg);
                }
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new BIOServer(8080).start();
    }
}
