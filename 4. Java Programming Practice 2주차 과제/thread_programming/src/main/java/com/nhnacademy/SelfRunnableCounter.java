package com.nhnacademy;

// Runnable 인터페이스는 run()만 구현하면 된다. 나머지는 강제 사항이 아니다.
public class SelfRunnableCounter implements Runnable {
    int count;
    int maxCount;
    Thread thread;
    boolean stopped = true;

    public SelfRunnableCounter(String name, int maxCount) {
        this.maxCount = maxCount;
        count = 0;
        thread = new Thread(this, name); // name을 실제로 넘겨주기도 한다. + 자신이 Runnable이니 Thread에게 넘겨 주는 게 가능.
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        System.out.println(Thread.currentThread().getName()); // 이 때는 스레드의 주인이 main 메서드다.
        System.out.println(thread.getName()); // 이 때는 스레드의 주인이 SelfRunnableCounter이다.
        thread.interrupt();
    }

    public Thread getThread() {
        return thread;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        stopped = false; // 필드로 초기화하는 것은 비추천. while문이 다 끝나고 다시 돌아와서야 stop하기 때문.
        while (!stopped && count < maxCount) {
            try {
                ++count;
                System.out.println(thread.getName() + " : " + count);
                Thread.sleep(1000);
            } catch (InterruptedException e) { // 혹시 쓰레드가 자고 있는데 인터럽트가 있으면 -> 깨워서 쓰레드 죽이자.
                System.out.println(Thread.currentThread().getName());
                System.out.println(thread.getName());
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SelfRunnableCounter[] counters = new SelfRunnableCounter[10];

        for (int i = 0; i < counters.length; i++) {
            counters[i] = new SelfRunnableCounter("counter" + (i + 1), 10);
            counters[i].getThread().start();
        }

        boolean allStopped = false;
        while (!allStopped) {
            if (counters[0].getCount() > 5) {
                for (SelfRunnableCounter counter : counters) {
                    counter.stop();
                }
            }

            for (SelfRunnableCounter counter : counters) {
                if (counter.getThread().isAlive()) {
                    allStopped = false;
                }
            }
        }
    }
}