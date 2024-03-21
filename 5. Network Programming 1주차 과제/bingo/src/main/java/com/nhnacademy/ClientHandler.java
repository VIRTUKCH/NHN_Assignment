package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 서버 -> 클라이언트 주고 받기
public class ClientHandler extends Thread {
    int index;
    private BufferedReader clientMessageReader;
    private BufferedWriter clientMessageWriter;
    private BufferedReader serverMessageReader;
    private BufferedWriter serverMessageWriter;

    public ClientHandler(Socket clientSocket, int index) {
        this.index = index;
        try {
            clientMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientMessageWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 클라이언트에게 듣기
        try {
            String msgOfClient;
            while ((msgOfClient = clientMessageReader.readLine()) != null) {
                // 클라이언트로부터 메시지를 받아서 처리하는 로직을 구현
                System.out.println("클라이언트 " + index + "의 메세지: " + msgOfClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}