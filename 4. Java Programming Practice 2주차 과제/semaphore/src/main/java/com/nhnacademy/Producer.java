/*
 * <생산자>
 * M명의 생산자는 N개의 품목을 납품할 수 있다. // 일단 다섯 개 동시에 납품하기로 타협. => 랜덤으로 납품하기??
 * 생산자는 1종 이상의 품목 납품이 가능하다. // 일단 다섯 개 동시에 납품하기로 타협. => 선택하기는 너무 어려운 일인 것 같은데... 랜덤으로 타협??
 * 생산자가 품목을 납품하기 전까지는 어떤 품목인지 알 수 없다. // 일단 다섯 개 동시에 납품하기로 타협. => 랜덤으로 납품하기??
 * 납품시 해당 품목이 최대 갯수만큼 있을 경우, 일정시간 대기 후에도 납품이 어려운 경우 포기한다. // 일단 다섯 개 동시에 납품하기로 타협. => 랜덤으로 납품하기...?
 */

// 일단은 공급자도 쓰레드를 가지고 있게 하자.
package com.nhnacademy;

import java.util.concurrent.ThreadLocalRandom;

// <로그 구현>
// 생산자의 동작(납품, 포기 등)과 소비자의 동작(입장, 구매, 포기, 퇴장 등)을 로거를 이용해 출력하라.
// 포기는 WARN 레벨로 출력하라.
public class Producer implements Runnable {
    Store store;
    int randomSecond;
    Thread thread;

    public Producer(Store store) {
        this.store = store;
        thread = new Thread(this);
    }

    void start() {
        this.thread.start();
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