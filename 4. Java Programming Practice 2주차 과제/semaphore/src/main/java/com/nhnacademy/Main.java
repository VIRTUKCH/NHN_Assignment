// 생산자와 소비자 모두 thread pool을 이용해 동시 입장 수를 관리하라.
package com.nhnacademy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    final static int MAX_CONSUMER_ALLOWED = 5;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("품목의 개수를 정수로 입력하십시오 : ");
        int numberOfCategory = Integer.parseInt(br.readLine());

        // 1. Store 객체 생성
        Store store = new Store(numberOfCategory);

        //------------------------------------------------------------

        // 2. 생산자 쓰레드 풀 생성 및 객체 생성

        // 2-1) 생산자 쓰레드 풀 생성 -> 생산자는 다 들어가자.
        System.out.print("생산자는 총 몇 명인가요? : ");
        int M = Integer.parseInt(br.readLine());
        ExecutorService producerThreadPool = Executors.newFixedThreadPool(M); // 생산자 쓰레드 풀

        // 2-2) 생산자 쓰레드 풀에 생산자 제출하기. => 쓰레드 풀이 알아서 작업을 진행해 주니, 따로 start()를 호출하지는 않아도 됨.
        for (int i = 0; i < M; i++) {
            producerThreadPool.submit(new Producer(store));
        }

        //------------------------------------------------------------
        
        // 3. 소비자 쓰레드 풀 생성 및 객체 생성

        // 3-1) 소비자 쓰레드 풀 생성 -> 소비자는 5명만 들어가자.
        System.out.println("소비자는 총 100명으로 가정하겠습니다.");
        ExecutorService consumerThreadPool = Executors.newFixedThreadPool(MAX_CONSUMER_ALLOWED); // 소비자 쓰레드 풀

        // 3-2) 소비자 쓰레드 풀에 소비자 제출하기. => 쓰레드 풀이 알아서 작업을 진행해 주니, 따로 start()를 호출하지는 않아도 됨.
        for (int i = 0; i < 100; i++) {
            consumerThreadPool.submit(new Consumer(String.valueOf(i), store));
        }

        //------------------------------------------------------------
        
        // 4. 5분 뒤 종료하기
        Thread.sleep(300000);
        producerThreadPool.shutdown();
        consumerThreadPool.shutdown();
    }
}