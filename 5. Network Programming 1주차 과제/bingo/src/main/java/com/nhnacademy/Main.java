package com.nhnacademy;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        Option serverOption = new Option("s", false, "Run as server");
        Option clientOption = new Option("c", false, "Run as client");

        options.addOption(serverOption);
        options.addOption(clientOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("s")) {
                Server server = new Server();
            } else if (commandLine.hasOption("c")) {
                Client client = new Client();
                client.communicate();
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
        }
    }
}