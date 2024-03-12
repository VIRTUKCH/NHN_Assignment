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

    @Override
    public void run() {
        try {
            // 1. 입장하자 - 입장 컨트롤은 스토어에서 알아서 해주세요
            store.enter();
            
            // 2. 물건 사자
            randomSecond = ThreadLocalRandom.current().nextInt(1, 11);
            store.sell();
    
            // 3. 나가자
            store.exit();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}