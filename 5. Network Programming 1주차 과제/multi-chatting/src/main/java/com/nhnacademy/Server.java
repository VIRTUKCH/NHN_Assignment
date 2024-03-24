package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    
    // 모든 클라이언트에게 메시지를 브로드캐스트하는 메서드
    public void broadcastMessage(String message) {
        for (Socket clientSocket : this.clientSocketList) { // clientSocketList는 연결된 모든 클라이언트 소켓을 저장하는 리스트
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                writer.write(message + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
        
        new Thread(() -> {
            try (BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in))) {
                String inputMessage;
                while ((inputMessage = serverInput.readLine()) != null) {
                    server.broadcastMessage(inputMessage); // 모든 클라이언트에게 메시지 브로드캐스트
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}