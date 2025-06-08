package com.mss.nio;

import com.mss.utils.PrintHelper;
import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio服务端
 */
@Data
public class NioServer {
    public static int port = 8080;

    public void startServer() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(PrintHelper.printContentByRed("Server started on port " + port));;

            while (true) {
                // 没有检测到通道事件的时候，select()方法会阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keys = selectionKeys.iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isAcceptable()) {
                        acceptService(selector, serverSocketChannel);
                    }

                    if (key.isReadable()) {
                        readService(key);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(PrintHelper.printExceptionMark(e));
        }
    }

    private void acceptService(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.connect(new InetSocketAddress(port));
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println(PrintHelper.printContentByRed("Accepted connection from " + clientChannel.getRemoteAddress()));
    }

    private void readService(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == -1) {
            System.out.println(PrintHelper.printContentByRed("Client disconnected: "  + socketChannel.getRemoteAddress()));
            socketChannel.close();
        }

        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        String message = new String(data);
        System.out.println(PrintHelper.printContentByRed("Received from client: " + message));

        // 发送响应
        buffer.clear();
        buffer.put(("Echo: " + message).getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        nioServer.startServer();
    }
}
