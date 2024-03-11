package com.nhnacademy;

import java.time.LocalTime;

// Runnable 인터페이스는 run() 메서드 만을 구현하게 강제한다.
public class RunnableCounter implements Runnable {
    Thread thread;
    String name;
    int count;
    int maxCount;

    public RunnableCounter(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
        count = 0;
        thread = new Thread(this); // 자기 자신이 Runnable이니까 가능한 이야기
    }

    // 내가 필드로 가지고 있는 쓰레드를 돌아가게 만들기. => 필드로 쓰레드를 가지고 있기때문에 가능한 이야기.
    public void start() {
        this.thread.start();
    }

    // 내가 필드로 가지고 있는 쓰레드를 멈추게 만들기. => 필드로 쓰레드를 가지고 있기 때문에 가능한 이야기.
    public void stop() {
        // this.thread.interrupt(); // 이건 RunnableCounter의 쓰레드에게 interrupt를 준 것.
        Thread.currentThread().interrupt(); // 이건 main의 쓰레드에게 interrupt를 준 것.
    }

    public Thread getThread() {
        return this.thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    /*
     * InteruptedException -> sleep()을 할 때 무조건 써야 함.
     * Thread가 자고 있으면 Interrupt를 받을 수 없기 때문임. 그래서 catch문으로 깨워서 -> 굳이굳이 다시 깨워서 interrupt를 걸어 주는 것.
     */
    public void run() {
        while (!this.getThread().isInterrupted() && this.count < this.maxCount) {
            try {
                Thread.sleep(1000);
                // InterruptedException은 스레드가 대기(waiting), 수면(sleeping), 또는 작업 중(blocked)일 때, 다른
                // 스레드가 현재 스레드를 중단(interrupt)하려고 시도할 때 발생하는 예외
            } catch (InterruptedException e) { // checked Exception -> 컴파일할 때 문제가 생길 수 있음.
                Thread.currentThread().interrupt(); // 현재 돌아가고 있는 쓰레드를 -> interrupt하라.
            }
            this.count += 1;
            System.out.println(this.name + " : " + this.count);

            if(this.getCount() > 5) {
                this.stop();
            }
        }
    }

    // 함수의 call 관계와 쓰레드의 주인은 다르다........................
    public static void main(String[] args) {
        RunnableCounter[] counters = new RunnableCounter[10]; // counters는 RunnableCounter 타입의 배열일 뿐.
        for (int i = 0; i < 10; i++) {
            counters[i] = new RunnableCounter("counter" + (i), 10); // counters는 각각의 쓰레드를 가진다.
            counters[i].getThread().start(); // 각각의 쓰레드를 작동시킨다.
        }

        boolean isAllStopped = false;
        while (!isAllStopped) {
            for (int i = 0; i < counters.length; i++) {
                if(!counters[i].getThread().isAlive()) {
                    isAllStopped = true;
                }
            }
        }

        System.out.println("end : " + LocalTime.now());

        /*
         * Runnable로도 쓰레드의 구현이 가능하지만, 별개의 작업을 수행하지는 못한다.
         * 
         * Runnable 인터페이스는 붙이나 안 붙이나 똑같다.
         * 당연히 그런 게, Runnable은 Run 가능한 타입이라는 거지, Thread가 적용된다는 거다.
         * 그러므로, 쓰레드를 따로 만들어 주고, 그 안에 만들어 줘야 의미가 생기게 된다.
         * 
         * Runnable한 객체는 쓰레드의 생성자에 매개변수로 들어간다.
         * Runnable하다는 표현을 좀 더 생각해 보면 되겠다.
         */
    }
}