package com.nhnacademy;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        Option serverOption = Option.builder("p")
                .argName("portNumber")
                .hasArg() // 이 옵션은 인자(값)을 가질 수 있음
                .optionalArg(true) // 옵션의 인자(값)은 선택적임
                .desc("This is an option that may or may not have a value.")
                .build();
        Option clientOption = new Option("c", false, "Run as client");

        options.addOption(serverOption);
        options.addOption(clientOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("p")) {
                String portNumber = commandLine.getOptionValue("p");
                if(portNumber == null) {
                    Server server = new Server(1234);
                    server.runServer();
                } else {
                    int integerPortNumber = Integer.parseInt(portNumber);
                    Server server = new Server(integerPortNumber);
                    server.runServer();
                }
            }

            if (commandLine.hasOption("c")) {
                Client client = new Client();
                client.runClient();
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }
    }
}