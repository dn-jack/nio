package com.dongnao.jack.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientSocket {
    
    Selector selector;
    
    SocketChannel client;
    
    public void start() {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress("localhost", 8080));
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void bindEvent() {
        while (true) {
            try {
                int i = selector.select();
                
                if (i == 0) {
                    continue;
                }
                
                /*
                 * selectionKey  可以把它理解成某个channel通道的封装。
                 * 可以把它认为就是一个变形的通道
                 */
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    
                    if (key.isConnectable()) {
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                            client.register(selector, SelectionKey.OP_WRITE);
                        }
                    }
                    else if (key.isWritable()) {
                        ByteBuffer bb = ByteBuffer.allocate(1024);
                        bb.clear();
                        bb.put("我要写数据到客户端通道！".getBytes());
                        bb.flip();
                        client.write(bb);
                    }
                }
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        ClientSocket ss = new ClientSocket();
        ss.start();
        ss.bindEvent();
    }
    
}
