package com.mss.nio;

import com.mss.utils.PrintHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    private SocketChannel clientChannel;

    public NioClient(String host, int port) throws IOException {
        clientChannel = SocketChannel.open(new InetSocketAddress(host, port));
        clientChannel.configureBlocking(false);
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to server. Type messages to send:");

        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                sendMessage("Client is disconnecting...");
                break;
            }
            sendMessage(message);
            receiveResponse();
        }
        scanner.close();
        clientChannel.close();
    }

    private void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        clientChannel.write(buffer);
    }

    private void receiveResponse() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = clientChannel.read(buffer);
        if (bytesRead > 0) {
            String response = new String(buffer.array()).trim();
            System.out.println("Server response: " + response);
        }
    }

    public static void main(String[] args) {
        try {
            NioClient client = new NioClient("localhost", 8080);
            client.start();
        } catch (IOException e) {
            System.out.println(PrintHelper.printExceptionMark(e));
        }
    }
}


