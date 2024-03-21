// TODO : 목표 - 다대일 소켓 통신으로 메세지 주고 받기만 일단 해보자.

package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server implements Runnable {
    static final int PORT = 1234;
    static final int MAX_CLIENT = 2;
    static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    static int indexOfClient = 0;

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 " + PORT + " 포트에서 대기중입니다.");

            int currentClient = 0;
            while (currentClient < MAX_CLIENT) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결 되었습니다.");

                // 클라이언트 핸들러 생성 및 실행
                ClientHandler clientHandler = new ClientHandler(clientSocket, indexOfClient++);
                Thread clientThread = new Thread(clientHandler); // 이래야 프로그램이 안 끝남. 구조를 개선하면 쓰레드가 필요없을 것 같기도 한데...
                clientThread.start();

                currentClient++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}