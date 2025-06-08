package com.mss.nio;

import com.mss.utils.PrintHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

public class NioClient {
    private String host;
    private int port;

    public NioClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startClient() {
        try {
            // 创建 Selector
            Selector selector = Selector.open();

            // 创建 SocketChannel
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false); // 设置为非阻塞模式

            // 连接到服务器
            socketChannel.connect(new InetSocketAddress(host, port));

            // 注册到 Selector，监听连接和读取事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            System.out.println(PrintHelper.printContentByRed("Client started, trying to connect to " + host + ":" + port));

            while (true) {
                // 等待事件
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (key.isConnectable()) {
                        // 完成连接
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                            System.out.println(PrintHelper.printContentByRed("Connected to server: " + host + ":" + port));
                        }

                        // 发送消息
                        String message = "Hello, Server!";
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put(message.getBytes());
                        buffer.flip();
                        channel.write(buffer);
                        System.out.println(PrintHelper.printContentByRed("Sent to server: " + message));
                    }

                    if (key.isReadable()) {
                        // 读取服务器响应
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = channel.read(buffer);

                        if (bytesRead > 0) {
                            buffer.flip();
                            byte[] data = new byte[buffer.remaining()];
                            buffer.get(data);
                            System.out.println(PrintHelper.printContentByRed("Received from server: " + new String(data)));
                        } else if (bytesRead == -1) {
                            // 服务器关闭连接
                            System.out.println(PrintHelper.printContentByRed("Server closed the connection."));
                            channel.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(PrintHelper.printExceptionMark(e));
        }
    }

    public static void main(String[] args) {
        NioClient client = new NioClient("localhost", 8080);
        client.startClient();
    }
}

