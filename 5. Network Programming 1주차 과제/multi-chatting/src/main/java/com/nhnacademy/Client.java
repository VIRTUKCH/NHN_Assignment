package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 클라이언트 -> 서버
public class Client {
    static final String HOST = "localhost";
    static final int PORT = 1234;

    // I/O(Reader, Writer, Socket)의 try-catch-resource문의 사용을 위해 하나의 메서드로 묶었음.
    public void runClient() {
        try (Socket socket = new Socket(HOST, PORT);
            BufferedReader serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter serverMessageWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader clientMessageReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter clientMessageWriter = new BufferedWriter(new OutputStreamWriter(System.out))) {

            clientMessageWriter.write("서버에 연결되었습니다.\n");
            clientMessageWriter.flush();

            // 클라이언트 -> 서버 메시지 발신 (발신에는 메세지만 적어 두기. "서버로부터 온 메세지" 이런 거 X)
            Thread sendThread = new Thread(() -> {
                try {
                    String sendMessage;
                    while (true) {
                        sendMessage = clientMessageReader.readLine();
                        if(sendMessage.equals("exit") || sendMessage == null) {
                            socket.close();
                            break;
                        }
                        serverMessageWriter.write(sendMessage + "\n"); // 줄바꿈은 꼭 붙여야 함 - readLine() 메서드 때문
                        serverMessageWriter.flush();
                    }
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            });

            // 서버로부터 메세지를 받는 쓰레드
            Thread receiveThread = new Thread(() -> {
                try {
                    String receivedMessageFromServer;
                    while (true) {
                        receivedMessageFromServer = serverMessageReader.readLine();
                        if(receivedMessageFromServer.equals("exit") || receivedMessageFromServer == null) {
                            socket.close();
                            break;
                        }
                        clientMessageWriter.write("서버의 메세지 : " + receivedMessageFromServer + "\n");
                        clientMessageWriter.flush();
                    }
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            });

            sendThread.start();
            receiveThread.start();

            // 모든 쓰레드가 종료될 때까지 기다립니다.
            try {
                sendThread.join();
                receiveThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}