package com.dongnao.jack.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class BIOClient {
    
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 8080);
            OutputStream out = client.getOutputStream();
            out.write("jack要连接到服务器".getBytes());
            out.close();
            client.close();
        }
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
