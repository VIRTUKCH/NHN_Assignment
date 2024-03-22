package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

// 서버 -> 클라이언트 1:1 처럼 처리하면 됨.
public class ClientHandler extends Thread {
    int index;

    BufferedReader clientMessageReader;
    BufferedWriter clientMessageWriter;
    BufferedReader serverMessageReader;
    BufferedWriter serverMessageWriter;

    List<Socket> clientSocketList;

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
                clientSocketList = Server.getClientSocketList();
                
                String messageToServer;
                while ((messageToServer = serverMessageReader.readLine()) != null) {
                    // 연결된 모든 클라이언트에게 메시지 전송
                    for (Socket clientSocket : clientSocketList) {
                        if (!clientSocket.isClosed()) {
                            // 각 클라이언트 소켓에 대한 출력 스트림 생성
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                            writer.write(messageToServer + "\n");
                            writer.flush();
                        }
                    }
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