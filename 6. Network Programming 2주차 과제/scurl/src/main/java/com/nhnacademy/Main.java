package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    static final int HTTP_PORT = 80;

    public static void main(String[] args) {
        String version = "HTTP/1.1";
        String command = "GET";
        String location = "/get";
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

            // 구현 완료
            if (commandLine.hasOption("v")) {
                
            }

            // POST, GET 등과 같은 커맨드
            if (commandLine.hasOption("X")) {
                String x_OptionValue = commandLine.getOptionValue("X");
                command = x_OptionValue;
            }

            // 미구현
            if (commandLine.hasOption("H")) {
                System.out.println("-H 옵션 들어있음");
                String h_OptionValue = commandLine.getOptionValue("H");
                // 헤더의 line을 출력
            }

            // 미구현
            if (commandLine.hasOption("d")) {
                System.out.println("-d 옵션 들어있음");
                String d_OptionValue = commandLine.getOptionValue("d");
                // POST, PUT 등에 데이터를 전달함.
            }

            // 미구현
            if (commandLine.hasOption("L")) {
                System.out.println("-L 옵션 들어있음");
                // 서버의 응답이 30x 계열이면 다음 응답을 따라 간다.
            }

            // 미구현
            if (commandLine.hasOption("F")) {
                System.out.println("-F 옵션 들어있음");
                String filePath = commandLine.getOptionValue("F");
                System.out.println("filePath : " + filePath);
            }

            // 옵션 외 URL
            if (commandLine.getArgs().length > 0) {
                String urlString = commandLine.getArgs()[0];
                URL url = new URL(urlString);
                String host = url.getHost(); // naver.com

                try (Socket socket = new Socket(host, HTTP_PORT)) { // host(주소 - naver.com), 80
                    PrintStream writer = new PrintStream(socket.getOutputStream());

                    writer.printf("%s %s %s\r\n", command, location, version);
                    writer.printf("Host: %s\r\n", host);
                    writer.printf("\r\n");

                    Thread receiver = new Thread(() -> {
                        try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
                            String line;
                            boolean isBody = false;
                            while ((line = socketReader.readLine()) != null) {
                                if (commandLine.hasOption("v")) { // 1. 옵션이 있는 경우
                                    System.out.println(line);
                                } else { // 2. 옵션이 없는 경우.
                                    if (line.isEmpty()) {
                                        isBody = true; // 첫 번째 빈 줄을 만나면, 이후의 내용은 응답 본문임
                                        continue; // 본문의 첫 줄부터 출력하기 위해 현재의 빈 줄은 건너뜀
                                    }
                                    if (isBody) {
                                        System.out.println(line); // 응답 본문만 출력
                                    }
                                }
                            }
                        } catch (SocketException e) { // BufferedReader에서의 Exception
                            System.out.println("소켓이 닫혔습니다.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    receiver.start();
                    receiver.join();
                } catch (IOException e) { // Socket에서의 Exception
                    e.printStackTrace();
                }
            } else {
                System.err.println("URL이 필요합니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}