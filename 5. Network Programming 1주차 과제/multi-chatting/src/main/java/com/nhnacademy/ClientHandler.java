package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 서버 -> 클라이언트 1:1 처럼 처리하면 됨.
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

    // 클라이언트 -> 서버 메시지 수신
    private class ReceiveMessageFromClient_Thread extends Thread {
        public void run() {
            try {
                String msgOfClient;
                while ((msgOfClient = clientMessageReader.readLine()) != null) {
                    serverMessageWriter.write("클라이언트 " + index + "의 메세지: " + msgOfClient + "\n");
                    serverMessageWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 서버 -> 클라이언트 메시지 발신 (발신에는 메세지만 적어 두기. "서버로부터 온 메세지" 이런 거 X)
    private class SendMessageToClient_Thread extends Thread {
        public void run() {
            try {
                String messageToSever;
                while ((messageToSever = serverMessageReader.readLine()) != null) {
                    clientMessageWriter.write(messageToSever + "\n"); // 줄바꿈은 꼭 붙여야 함 - readLine() 메서드 때문
                    clientMessageWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        ReceiveMessageFromClient_Thread receiveThread = new ReceiveMessageFromClient_Thread();
        SendMessageToClient_Thread sendThread = new SendMessageToClient_Thread();

        receiveThread.start();
        sendThread.start();
    }
}