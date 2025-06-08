package com.mss.nio;


import com.mss.utils.PrintHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * nio服务端
 */
public class NioServer {
    private final Selector selector;
    private final ServerSocketChannel serverChannel;

    public NioServer(int port) throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println(PrintHelper.printContentByRed("Server started on port: " + port));
    }

    public void start() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Accepted connection from: " + clientChannel.getRemoteAddress());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            clientChannel.close();
            System.out.println("Connection closed by client.");
            return;
        }
        buffer.flip();
        String message = new String(buffer.array()).trim();
        System.out.println("Received: " + message);
        // Echo the message back to the client
        clientChannel.write(ByteBuffer.wrap(("Echo: " + message).getBytes()));
    }

    public static void main(String[] args) {
        try {
            NioServer server = new NioServer(8080);
            server.start();
        } catch (IOException e) {
            System.out.println(PrintHelper.printExceptionMark(e));
        }
    }
}

