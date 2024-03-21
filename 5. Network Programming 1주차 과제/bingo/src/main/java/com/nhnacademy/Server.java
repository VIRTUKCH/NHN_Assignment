// TODO : 목표 - 다대일 소켓 통신으로 메세지 주고 받기만 일단 해보자.

package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 1234;
    static final int MAX_CLIENT = 2;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 " + PORT + " 포트에서 대기중입니다.");

            int currentClient = 0;
            while (currentClient < MAX_CLIENT) {
                Socket clientSocket = serverSocket.accept(); // 클라이언트의 연결 요청을 수락
                System.out.println("클라이언트가 연결 되었습니다.");

                // 클라이언트 핸들러 생성 및 실행
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();

                currentClient++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// public class Server {
//     static final int PORT = 1234;

//     List<Socket> clientSocketList;
//     List<BufferedReader> clientReaderList;
//     List<BufferedWriter> clientWriterList;

//     static final int MAX_CLIENT = 2;

//     // 기본적인 소켓 연결
//     Server() {
//         clientSocketList = new ArrayList<>();
//         clientReaderList = new ArrayList<>();
//         clientWriterList = new ArrayList<>();

//         try {
//             try (ServerSocket serverSocket = new ServerSocket(PORT)) {

//                 System.out.println("서버가 " + PORT + " 포트에서 대기중입니다.");

//                 int currentClient = 0;
//                 while (currentClient < MAX_CLIENT) {
//                     Socket clientSocket = serverSocket.accept(); // 클라이언트의 연결 요청을 수락
//                     clientSocketList.add(clientSocket); // 연결된 클라이언트 소켓을 리스트에 추가
//                     System.out.println("클라이언트가 연결 되었습니다.");

//                     // 클라이언트와의 통신을 위한 입력 및 출력 스트림 생성
//                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                     clientReaderList.add(in);
//                     clientWriterList.add(out);
//                     currentClient++;
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     // 모든 클라이언트에게 메세지 전달하기
//     public void sendMessageAll(String msg) {
//         for (BufferedWriter bw : clientWriterList) {
//             try {
//                 bw.write(msg);
//                 bw.flush();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     // 선택한 클라이언트 한 명에게만 메세지 전달하기
//     public void sendMessageForOne(String msg, int index) {
//         BufferedWriter br = clientWriterList.get(index);
//         try {
//             br.write(msg);
//             br.flush();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }