package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    static final int HTTP_PORT = 80;
    static final String HTTP_VERSION = "HTTP/1.1";
    static String location = "GET";
    // static String request;

    public static void main(String[] args) {
        Options options = new Options();

        // 1. -v 옵션
        Option verboseOption = Option.builder("v")
                .argName("verbose")
                .hasArg(false) // 이 옵션은 인자(값)을 가질 수 없음
                .desc("요청, 응답 헤더 출력")
                .build();

        Option headerOption = Option.builder("H")
                .argName("임의의 헤더를 서버로 전송")
                .hasArg(true)
                .desc("임의의 헤더를 서버로 전송")
                .build();

        Option dataOption = Option.builder("d")
                .argName("POST, PUT 등에 데이터를 전송")
                .hasArg(true)
                .desc("POST, PUT 등에 데이터를 전송")
                .build();

        Option methodOption = Option.builder("X")
                .argName("사용할 method를 지정. 지정되지 않으면, 기본 값은 GET")
                .hasArg(true)
                .optionalArg(true) // 옵션의 인자(값)은 선택적임
                .desc("사용할 method를 지정. 지정되지 않으면, 기본 값은 GET")
                .build();

        Option lOption = Option.builder("L")
                .argName("서버의 응답이 30x 계열이면 다음 응답을 따라 감.")
                .hasArg(false)
                .desc("서버의 응답이 30x 계열이면 다음 응답을 따라 감.")
                .build();

        Option fileOption = Option.builder("F")
                .argName("multipart/form-data를 구성하여 전송. content 부분에 @filename 사용할 수 있음.")
                .hasArg(true)
                .desc("multipart/form-data를 구성하여 전송. content 부분에 @filename 사용할 수 있음.")
                .build();

        options.addOption(verboseOption);
        options.addOption(headerOption);
        options.addOption(dataOption);
        options.addOption(methodOption);
        options.addOption(lOption);
        options.addOption(fileOption);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("v")) {
                System.out.println("-v 옵션 들어있음");
                // 1. 요청 헤더 출력

                // 2. 응답 헤더 출력
            }

            if (commandLine.hasOption("H")) {
                System.out.println("-H 옵션 들어있음");
                String h_OptionValue = commandLine.getOptionValue("H");

                int line = Integer.parseInt(h_OptionValue);
                // 헤더의 line을 출력
            }

            if (commandLine.hasOption("d")) {
                System.out.println("-d 옵션 들어있음");
                String d_OptionValue = commandLine.getOptionValue("d");
                // POST, PUT 등에 데이터를 전달함.
            }

            if (commandLine.hasOption("X")) {
                System.out.println("-X 옵션 들어있음");
                String x_OptionValue = commandLine.getOptionValue("X");

                if (x_OptionValue.equals("GET")) {
                    location = "GET";
                    System.out.println("GET Option");
                } else if (x_OptionValue.equals("POST")) {
                    location = "POST";
                    System.out.println("POST Option");
                } else if (x_OptionValue.equals("PUT")) {
                    location = "PUT";
                    System.out.println("PUT Option");
                } else if (x_OptionValue.equals("DELETE")) {
                    location = "DELETE";
                    System.out.println("DELETE Option");
                } else {
                    System.out.println("잘못된 값입니다.");
                }
            }

            if (commandLine.hasOption("L")) {
                System.out.println("-L 옵션 들어있음");
                // 서버의 응답이 30x 계열이면 다음 응답을 따라 간다.
            }

            if (commandLine.hasOption("F")) {
                System.out.println("-F 옵션 들어있음");
                String filePath = commandLine.getOptionValue("F");
                System.out.println("filePath : " + filePath);
            }

            // 옵션 다 제끼고 URL/get 이런 것만 남음.
            if (commandLine.getArgs().length > 0) {
                String urlString = commandLine.getArgs()[0];
                URL url = new URL(urlString);
                String purePath = url.getPath(); // /get 같은 것들
                String pureHost = url.getHost(); // naver.com

                
                try (Socket socket = new Socket(pureHost, HTTP_PORT)) { // host(주소 - naver.com), 80
                    System.out.println("-----------------내가 출력-----------------");
                    System.out.println("Inet Address : " + socket.getInetAddress()); // naver.com/223.130.192.248
                    System.out.println("Port Number : " + socket.getPort());
                    System.out.println("-----------------내가 출력-----------------");

                    Thread receiver = new Thread(() -> {
                        try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        ) {
                            String request = location + " / " + HTTP_VERSION + "\r\n" +
                                    "Host: " + pureHost + "\r\n" +
                                    "\r\n";
                            socketWriter.write(request);
                            socketWriter.flush();
                            String line;
                            while ((line = socketReader.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (SocketException e) { // BufferedReader에서의 Exception
                            System.out.println("소켓이 닫혔습니다.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    receiver.start();

                    // 소켓 닫힘 방지
                    try {
                        Thread.currentThread().join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) { // Socket에서의 Exceptio
                    e.printStackTrace();
                }

            } else {
                System.err.println("URL이 필요합니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}