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

    BufferedReader serverMessageReader;
    BufferedWriter serverMessageWriter;

    BufferedReader clientMessageReader;
    BufferedWriter clientMessageWriter;

    Socket socket;

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverMessageWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientMessageReader = new BufferedReader(new InputStreamReader(System.in));
            clientMessageWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            
            clientMessageWriter.write("서버에 연결되었습니다.\n");
            clientMessageWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicate() {
        // 서버에게 메세지를 보내는 쓰레드
        new Thread(() -> {
            try {
                String sendMessage;
                while ((sendMessage = clientMessageReader.readLine()) != null) {
                    serverMessageWriter.write(sendMessage + "\n");
                    serverMessageWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 서버에게서 메세지를 받는 쓰레드
        new Thread(() -> {
            try {
                String receivedMessageFromServer;
                while ((receivedMessageFromServer = serverMessageReader.readLine()) != null) {
                    clientMessageWriter.write(receivedMessageFromServer + "\n");
                    clientMessageWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 통신이 끝나면 소켓을 닫습니다.
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
