package com.nhnacademy;

public class SelfRunnableCounter implements Runnable {
    int count;
    int maxCount;
    Thread thread;
    boolean stopped = true;

    public SelfRunnableCounter(String name, int maxCount) {
        this.maxCount = maxCount;
        count = 0;
        thread = new Thread(this, name);
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
        stopped = false;
        while (!stopped && count < maxCount) {
            try {
                ++count;

                System.out.println(thread.getName() + " : " + count);
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(thread.getName());
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SelfRunnableCounter[] counters = new SelfRunnableCounter[10];

        for (int i = 0; i < counters.length; i++) {
            counters[i] = new SelfRunnableCounter("counter" +(i+1), 10);
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