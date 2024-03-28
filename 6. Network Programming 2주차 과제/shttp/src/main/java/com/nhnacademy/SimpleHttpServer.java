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
    int port = 8080; // 기술적으로 80과 동일한 기능을 수행. 주로 추가적인 웹 서비스를 할 때 사용.

    public SimpleHttpServer() {
        log = LogManager.getLogger(this.getClass().getSimpleName());
    }

    @Override
    public void run() {
        // 프로그램 종료 전에 수행할 작업 정의
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

        // 시작
        log.trace("Start server : {}", port);
        while (!Thread.currentThread().isInterrupted()) {
            try (ServerSocket socket = new ServerSocket(port)) {
                ServiceHandler handler = new ServiceHandler(socket.accept()); // 클라이언트의 연결 요청을 받아들임
                handler.start(); // 받았으면 -> start() 메서드를 호출.
            } catch (IOException ignore) {
                //
            }
        }
        log.trace("Stopped server : {}", port);
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleHttpServer server = new SimpleHttpServer();
        server.start();
        server.join(); // 메인 쓰레드는 server 쓰레드가 끝날 때까지 기다린다.
    }
}
