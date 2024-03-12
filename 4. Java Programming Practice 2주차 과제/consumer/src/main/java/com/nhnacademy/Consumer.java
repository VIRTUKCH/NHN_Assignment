package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

/*
 * 1. 소비자는 매장에 입장 후 물건을 구매할 수 있다.
 * 2. 매장에는 입장 인원 제한이 있으므로, 인원 초과시 기다린다.
 * 3. 매장에 입장하면 물건을 구매하고, 퇴장한다.
 * 4. 1~10초 간격으로 구매한다.
 */

public class Consumer implements Runnable {
    String name;
    Store store;
    Thread thread;

    int randomSecond;

    public Consumer(String name, Store store) {
        this.name = name;
        this.store = store;
        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. 입장하자 - 입장 컨트롤은 스토어에서 알아서 해주길 기대.
                store.enter();
                System.out.println("고객 입장");

                // 2. 물건 사자
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10001));
                store.buy();

                // 3. 나가자
                store.exit();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }
}