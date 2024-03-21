// TODO : 목표 - 다대일 소켓 통신으로 메세지 주고 받기만 일단 해보자.

package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 서버 -> 클라이언트 연결
public class Server {
    static final int PORT = 1234;
    static final int MAX_CLIENT = 2;
    static int indexOfClient = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 " + PORT + " 포트에서 대기중입니다.");

            int currentClient = 0;
            while (currentClient < MAX_CLIENT) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결 되었습니다.");

                // 클라이언트 핸들러 생성 및 실행
                ClientHandler clientHandler = new ClientHandler(clientSocket, indexOfClient++);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                currentClient++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}