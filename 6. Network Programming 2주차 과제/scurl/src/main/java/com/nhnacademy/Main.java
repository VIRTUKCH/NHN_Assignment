package com.nhnacademy;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    static final int HTTP_PORT = 80;

    public static void main(String[] args) {
        Options options = new Options();
        String version = "HTTP/1.1";
        String location = "/get";

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
                    System.out.println("GET Option");
                } else if (x_OptionValue.equals("POST")) {
                    System.out.println("POST Option");
                } else if (x_OptionValue.equals("PUT")) {
                    System.out.println("PUT Option");
                } else if (x_OptionValue.equals("DELETE")) {
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
                String purePath = url.getPath();
                String pureHost = url.getHost();

                Socket socket = new Socket(pureHost, HTTP_PORT); // host(주소), port
                System.out.println("pureHost : " + pureHost);
                System.out.println("purePath : " + purePath);
                System.out.println("Inet Address : " + socket.getInetAddress());
                System.out.println("Host Name : " + socket.getInetAddress().getHostName());
                System.out.println("Address : " + Arrays.toString(socket.getInetAddress().getAddress())); // 마이너스 형태의 배열
                System.out.println("Port Number : " + socket.getPort());

                Thread receiver = new Thread(() -> {

                });

                PrintStream writer = new PrintStream(socket.getOutputStream());
                writer.printf("%s %s %s\r\n", commandLine, location, version);

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