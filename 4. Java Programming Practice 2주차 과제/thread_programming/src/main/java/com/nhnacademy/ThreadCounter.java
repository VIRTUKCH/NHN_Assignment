package com.nhnacademy;

import java.time.LocalTime;

public class ThreadCounter extends Thread {
    String name;
    int count;
    int maxCount;

    public ThreadCounter(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    @Override
    public void run() { // main()을 실행 하듯, run()을 만들면 됨.
        while (count < maxCount) {
            try {
                Thread.sleep(1000);
                count++;
                System.out.println(this.name + " : " + this.count);
            } catch (InterruptedException e) { // checked Exception -> 컴파일할 때 문제가 생길 수 있음.
                Thread.currentThread().interrupt(); // 현재 돌아가고 있는 쓰레드를 -> interrupt하라.
                // InterruptedException은 스레드가 대기(waiting), 수면(sleeping), 또는 작업 중(blocked)일 때, 다른
                // 스레드가 현재 스레드를 중단(interrupt)하려고 시도할 때 발생하는 예외
            }
        }
    }

    public static void main(String[] args) {
        ThreadCounter counter1 = new ThreadCounter("counter1", 10);
        ThreadCounter counter2 = new ThreadCounter("counter2", 10);

        System.out.println("start : " + LocalTime.now());
        counter1.start(); // 이렇게 하면 Thread main은 없어지고, Thread-0이 나타남.
        counter2.start(); // 알아서 run()을 호출함. + 두 쓰레드가 돌아가면서 숫자를 찍어 냄
        System.out.println("end : " + LocalTime.now());
        
        /*
         * [실행 결과]
         * start : 14:31:18.044728
         * end : 14:31:18.059370
         * main 쓰레드는 정지할 필요가 없다는 사실에 집중해 보자. 그저 counter1과 counter2를 호출하면 main 쓰레드가 할 일은 끝난 것이다.
         */
    }
}