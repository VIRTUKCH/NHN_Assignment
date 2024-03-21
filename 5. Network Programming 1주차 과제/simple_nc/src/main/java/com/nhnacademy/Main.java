package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// 서버 - 대기
// 클라이언트 - Request

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. 옵션 관리
        Options options = new Options();
        Option serverOption = new Option("l", true, "ServerOption"); // -l 옵션은 뒤에 값이 이어서 온다.
        options.addOption(serverOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            // 1-1) -l 옵션 -> Server로 동작하게 만들기 -> 내가 서버를 열었다. ex) -l 1234 == 내가 서버다
            if (commandLine.hasOption(serverOption.getOpt())) {
                int portNumberOfServer = Integer.parseInt(commandLine.getOptionValue(serverOption.getOpt()));

                try (ServerSocket serverSocket = new ServerSocket(portNumberOfServer)) {
                    System.out.println("서버가 " + portNumberOfServer + " 포트에서 대기중입니다.");
                    Socket socket = serverSocket.accept(); // 서버를 열어 둔다.
                    System.out.println("클라이언트가 연결 되었습니다.");

                    // 메세지를 보내는 쓰레드
                    Thread sendMessageThread = new Thread(() -> {
                        try {
                            BufferedReader serverMessageReader = new BufferedReader(new InputStreamReader(System.in));
                            BufferedWriter serverMessageWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            String sendMessage;
                            while ((sendMessage = serverMessageReader.readLine()) != null) {
                                serverMessageWriter.write(sendMessage + "\n");
                                serverMessageWriter.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    // 메세지를 받는 쓰레드
                    Thread receiveMessageThread = new Thread(() -> {
                        try {
                            BufferedReader clientMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String receivedMessage;
                            while ((receivedMessage = clientMessageReader.readLine()) != null) {
                                System.out.println("클라이언트: " + receivedMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    sendMessageThread.start();
                    receiveMessageThread.start();

                    try {
                        sendMessageThread.join();
                        receiveMessageThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // nc -l 1234로 일단 서버를 열어야 할 것.
            // 1-2) 옵션이 없으면 -> Client로 동작하게 만들기. ex) 그냥 두면 -> 1234로 연결됨 -> 나는 Client다.
            else {
                String host = "localhost";
                int portNumberOfClient = 1234;

                try (Socket socket = new Socket(host, portNumberOfClient)) {
                    System.out.println("서버에 연결되었습니다.");
        
                    // 메시지를 받는 스레드
                    Thread receiveMessageThread = new Thread(() -> {
                        try {
                            BufferedReader serverBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String receivedMessage;
                            while ((receivedMessage = serverBufferedReader.readLine()) != null) {
                                System.out.println("서버: " + receivedMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        
                    // 메시지를 보내는 스레드
                    Thread sendMessageThread = new Thread(() -> {
                        try {
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            String str;
                            while (!(str = br.readLine()).equals("exit")) {
                                bw.write(str + "\n");
                                bw.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    receiveMessageThread.start();
                    sendMessageThread.start();

                    try {
                        sendMessageThread.join();
                        receiveMessageThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}