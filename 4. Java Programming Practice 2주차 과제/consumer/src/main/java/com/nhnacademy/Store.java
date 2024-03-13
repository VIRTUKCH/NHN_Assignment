/* 
 * N개의 품목 매장이 있다.
 * 품목마다 최대 갯수가 지정되어 있다.
 * 폼목별로 세마포어를 이용해 관리한다.
 * 생산자와 소비자 모두 thread pool을 이용해 동시 입장 수를 관리하라.
 * 5분간만 오픈하라.
 */

package com.nhnacademy;

import java.util.LinkedList;
import java.util.Queue;

public class Store {
    private static final int MAX_PRODUCTS = 10;
    private static final int MAX_CUSTOMER = 5;
    private int currentCustomers;
    private Queue<Product> productList;

    public Store() {
        productList = new LinkedList<>();
    }

    public synchronized void enter() throws InterruptedException {
        while(currentCustomers >= MAX_CUSTOMER) {
            wait();
        }
        currentCustomers++;
        System.out.println("고객 입장, 현재 고객 수: " + currentCustomers);
    }

    // 사고 나서 나가세요
    public synchronized void exit() {
        currentCustomers--;
        System.out.println("고객 퇴장, 현재 고객 수: " + currentCustomers);
        notifyAll();
    }

    // 고객한테 팔기 : 한 명만 가능 + 
    public synchronized void buy() throws InterruptedException {
        while(productList.isEmpty()) { // 1. 살 거 없으면 사지 말고 기다리세요
            wait();
        }
        productList.poll(); // 2. 사세요
        System.out.println("물건 구매, 남은 물건 수: " + productList.size());
        notifyAll(); // 3. 샀으니까 -> 나간다는 걸 의미 -> 고객한테 알리기 -> notifyAll()
    }

    // 납품 받기 : 한 명만 가능 + 빈 공간 생산자 알림 + 물건 들어오면 소비자 알림 + 10개 이상 납품 불가능
    public synchronized void sell() throws InterruptedException {
        while(productList.size() == MAX_PRODUCTS) { // 납품자 기다리게 하기
            wait();
        }
        productList.add(new Product());
        System.out.println("물건 납품, 남은 물건 수: " + productList.size());
        notifyAll();
    }
}