package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 서버와 클라이언트의 일대일 통신으로 넘어가게 됨
public class ClientHandler extends Thread {
    int index;
    BufferedReader clientMessageReader;
    BufferedWriter clientMessageWriter;
    BufferedReader serverMessageReader;
    BufferedWriter serverMessageWriter;

    public ClientHandler(Socket clientSocket, int index) {
        this.index = index;
        try {
            clientMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientMessageWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            serverMessageReader = new BufferedReader(new InputStreamReader(System.in));
            serverMessageWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 클라이언트의 메시지 받기
        try {
            String msgOfClient;
            while ((msgOfClient = clientMessageReader.readLine()) != null) {
                // 클라이언트로부터 메시지를 받아서 처리하는 로직을 구현
                serverMessageWriter.write("클라이언트 " + index + "의 메세지: " + msgOfClient + "\n");
                serverMessageWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}