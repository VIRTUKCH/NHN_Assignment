/*
 * <소비자>
 * 품목별로 매장을 이용할 수 있다. 즉, 여러 사람이 각기 다른 품목을 구매할 경우 동시 구매가 가능하다.
 * 소비자는 구매할 1종 이상의 품목을 구매할 수 있다.
 * 물건이 부족할 경우 일정시간 기다리고, 기다리는 시간내에 물건이 입고되지 않으면 포기한다.
 */

package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    String name;
    Store store;
    Thread thread;

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
                System.out.println(this.name + "번 고객 입장");

                // 2. 물건 사자
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10001));
                store.buy();

                // 3. 나가자
                store.exit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}