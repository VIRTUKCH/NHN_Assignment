package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    static final String HOST = "localhost";
    static final int PORT = 1234;

    BufferedReader serverMessageReader;
    BufferedWriter serverMessageWriter;

    BufferedReader clientMessageReader;
    BufferedWriter clientMessageWriter;

    public Client() {
        try (Socket socket = new Socket(HOST, PORT)) {
            serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverMessageWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientMessageReader = new BufferedReader(new InputStreamReader(System.in));
            clientMessageWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void communicate() {
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
        });

        // 서버에게서 메세지를 받는 메서드
        new Thread(() -> {
            try {
                String receivedMessageFromServer;
                while ((receivedMessageFromServer = serverMessageReader.readLine()) != null) {
                    clientMessageWriter.write(receivedMessageFromServer + "\n");
                    clientMessageWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}