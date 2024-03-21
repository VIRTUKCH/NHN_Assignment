package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    int index;
    private BufferedReader br;
    private BufferedWriter bw;

    public ClientHandler(Socket clientSocket, int index) {
        this.index = index;
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 클라이언트와의 통신 처리
        try {
            String msgOfClient;
            while ((msgOfClient = br.readLine()) != null) {
                // 클라이언트로부터 메시지를 받아서 처리하는 로직을 구현
                System.out.println("클라이언트 " + index + "의 메세지: " + msgOfClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        try {
            bw.write(msg);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}