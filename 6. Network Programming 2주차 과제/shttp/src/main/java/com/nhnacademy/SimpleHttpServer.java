package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class SimpleHttpServer extends Thread {
    Logger log;
    int port;

    public SimpleHttpServer(int port) {
        log = LogManager.getLogger(this.getClass().getSimpleName());
        this.port = port;
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shutting down ...");
                    SimpleHttpServer.this.interrupt();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        log.trace("Start server : {}", port);
        while (!Thread.currentThread().isInterrupted()) {
            try (ServerSocket socket = new ServerSocket(port)) {
                ServiceHandler handler = new ServiceHandler(socket.accept());

                handler.start();

            } catch (IOException ignore) {
                //
            }
        }
        log.trace("Stopped server : {}", port);
    }

    public static void main(String[] args) throws InterruptedException {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 80;
        }
        SimpleHttpServer server = new SimpleHttpServer(port);
        server.start();
        server.join();
    }
}
