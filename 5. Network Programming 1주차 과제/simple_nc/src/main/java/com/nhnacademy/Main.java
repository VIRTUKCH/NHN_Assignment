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
        Option serVerOption = new Option("l", true, "ServerOption"); // -l 옵션은 뒤에 값이 이어서 온다.
        options.addOption(serVerOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            // 1-1) -l 옵션 -> Server로 동작하게 만들기
            if (commandLine.hasOption(serVerOption.getOpt())) {
                int portNumberOfServer = Integer.parseInt(commandLine.getOptionValue(serVerOption.getOpt()));
                try (ServerSocket serverSocket = new ServerSocket(portNumberOfServer)) {
                    Socket socket = serverSocket.accept(); // 서버를 열어 둔다.
                    System.out.println("Connected : " + serverSocket.getInetAddress().getHostAddress());
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String str;
                    while (!(str = br.readLine()).equals("exit")) {
                        bw.write("서버에서 전송한 메시지 : " + str + "\n");
                        bw.flush();
                    }
                }
            }

            // nc -l 1234로 일단 서버를 열어야 할 것.
            // 1-2) 옵션이 없으면 -> Client로 동작하게 만들기
            else {
                String host = "localhost";
                int portNumberOfClient = 1234;

                try (Socket socket = new Socket(host, portNumberOfClient)) {
                    System.out.println("서버에 연결되었습니다.");
                    System.out.println("Local address : " + socket.getLocalAddress()); // 소켓의 로컬 주소
                    System.out.println("Local port : " + socket.getLocalPort()); // 소켓의 로컬 포트
                    System.out.println("Remote address : " + socket.getRemoteSocketAddress()); // 소켓의 원격 주소
                    System.out.println("Remote port : " + portNumberOfClient);

                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String str;
                    while (!(str = br.readLine()).equals("exit")) {
                        bw.write(str + "\n");
                        bw.flush();
                    }

                } catch (Exception e) {
                    System.err.println("Host를 찾을 수 없습니다. " + e.getMessage());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}