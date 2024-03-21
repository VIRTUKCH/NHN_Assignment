package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class User implements Runnable {
    Socket socket;
    int[][] bingoBoard;
    Thread sendThread;
    Thread receiveThread;
    int index;

    BufferedReader serverMessageReader;
    BufferedWriter serverMessageWriter;
    BufferedReader clientMessageReader;
    BufferedWriter clientMessageWrtier;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public User() {
        try {
            socket = new Socket("localhost", 1234);
            System.out.println("서버에 접속하였습니다.");
        } catch (UnknownHostException e) {
            System.out.println("UnknownHost");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendThread = new Thread(this);
        receiveThread = new Thread(this);
        sendThread.start();
        receiveThread.start();
    }

    public void start() {
        try {
            serverMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverMessageWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientMessageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientMessageWrtier = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sendThread.run();
        this.receiveThread.run();
    }

    public void initBingoBoard() {

    }

    @Override
    public void run() {

    }
}