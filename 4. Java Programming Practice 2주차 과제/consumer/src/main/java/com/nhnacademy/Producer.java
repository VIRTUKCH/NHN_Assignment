package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

/*
 * M명의 생산자는 N개의 품목을 납품할 수 있다.
 * 생산자는 1종 이상의 품목 납품이 가능하다.
 * 생산자가 품목을 납품하기 전까지는 어떤 품목인지 알 수 없다.
 * 납품시 해당 품목이 최대 갯수만큼 있을 경우, 일정시간 대기 후에도 납품이 어려운 경우 포기한다.
 */

public class Producer implements Runnable {
    Store store;
    int randomSecond;

    public Producer(Store store) {
        this.store = store;
    }

    // 1초에서 10초 간격으로 물건을 채운다.
    @Override
    public void run() {
        while (true) {
            try {
                store.sell();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}