/*
 * <소비자>
 * 품목별로 매장을 이용할 수 있다. 즉, 여러 사람이 각기 다른 품목을 구매할 경우 동시 구매가 가능하다. // 일단 다섯 개 동시에 막기로 구현했음.
 * 소비자는 구매할 1종 이상의 품목을 구매할 수 있다. // 랜덤으로 구매할까?
 * 물건이 부족할 경우 일정시간 기다리고, 기다리는 시간내에 물건이 입고되지 않으면 포기한다. // 랜덤으로 구매할까?
 */

package com.nhnacademy;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

// <로그 구현>
// 생산자의 동작(납품, 포기 등)과 소비자의 동작(입장, 구매, 포기, 퇴장 등)을 로거를 이용해 출력하라.
// 포기는 WARN 레벨로 출력하라.
public class Consumer implements Runnable {
    String name;
    Store store;
    Thread thread;
    private static final Semaphore semaphore = new Semaphore(5);

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
                semaphore.acquire(); // 다섯 명만 들어와
                store.enter();
                semaphore.release();
                System.out.println("Sytem.out.println() : " + this.name + "번 고객 입장");

                // 2. 물건 사자
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10001));
                store.buy();

                // 3. 나가자
                store.exit();
                System.out.println("Sytem.out.println() : " + this.name + "번 고객 퇴장");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}