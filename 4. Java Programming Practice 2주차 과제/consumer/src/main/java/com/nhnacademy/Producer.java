package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

/*
 * 1. 생산자는 매장에 물건이 부족하지 않도록 채워둔다.
 * 2. 물건은 1~10초 간격으로 채운다.
 * 3. Thread내에서 난수 생성을 위해서는 ThreadLocalRandom.current().nextInt()를 사용하면 된다.
 */

// 물건이 부족하지 않도록 채워 둔다.
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