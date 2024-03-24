package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

// Server 클래스의 역할 : 클라이언트가 한 명 늘어날 때마다 
public class Server {
    static final int PORT = 1234;
    static final int MAX_CLIENT = 2;
    static int indexOfClient = 0; // 클라이언트의 번호는 서버가 결정한다.
    static List<Socket> clientSocketList;

    public static List<Socket> getClientSocketList() {
        return clientSocketList;
    }

    public void runServer() {
        clientSocketList = new LinkedList<>();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 " + PORT + " 포트에서 대기중입니다.");

            int currentClient = 0;
            while (currentClient < MAX_CLIENT) {
                Socket clientSocket = serverSocket.accept();
                clientSocketList.add(clientSocket);
                System.out.println("클라이언트가 연결 되었습니다.");

                // 클라이언트 핸들러 생성 및 실행
                ClientHandler clientHandler = new ClientHandler(clientSocket, indexOfClient++);
                // 클라이언트 한 명 당 서버의 쓰레드 하나씩 더 만들기
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                currentClient++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}