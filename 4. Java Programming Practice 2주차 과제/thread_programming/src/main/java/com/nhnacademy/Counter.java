package com.nhnacademy;

import java.time.LocalTime;

// 그냥 쓰레드를 쓴 예시임
public class Counter {
    String name;
    int count;
    int maxCount;

    public Counter(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    public void run() {
        while (this.count < this.maxCount) {
            try {
                Thread.sleep(1000);
                // InterruptedException은 스레드가 대기(waiting), 수면(sleeping), 또는 작업 중(blocked)일 때, 다른
                // 스레드가 현재 스레드를 중단(interrupt)하려고 시도할 때 발생하는 예외
            } catch (InterruptedException e) { // checked Exception -> 컴파일할 때 문제가 생길 수 있음.
                Thread.currentThread().interrupt(); // 현재 돌아가고 있는 쓰레드를 -> interrupt하라.
            }
            this.count += 1;
            System.out.println(this.name + " : " + this.count);
        }
    }

    public static void main(String[] args) {
        Counter counter1 = new Counter("counter1", 10);
        Counter counter2 = new Counter("counter2", 10);

        System.out.println("start : " + LocalTime.now());
        counter1.run(); // counter1을 다 쓰고
        counter2.run(); // 그 다음에 counter2가 나옴.
        System.out.println("end : " + LocalTime.now());
    }
}