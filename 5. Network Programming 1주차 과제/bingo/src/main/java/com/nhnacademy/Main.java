package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        Option serverOption = new Option("s", false, "ServerOption"); // -l 옵션은 뒤에 값이 이어서 온다.
        options.addOption(serverOption);

        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption(serverOption.getOpt())) {
                Server server = new Server();
                Thread serverThread = new Thread(server);
                serverThread.start();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 1. 서버 역할로 들어가려면 -s 옵션 붙이고

        // 2. 클라이언트 역할로 들어가려면 -c 옵션 붙이라고 해야겠다.

    }
}
