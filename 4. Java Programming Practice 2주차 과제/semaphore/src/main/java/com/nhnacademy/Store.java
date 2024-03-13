/*
 * <마트>
 * N개의 품목 매장이 있다. // 구현 완료
 * 품목마다 최대 갯수가 지정되어 있다. // 구현 완료
 * 폼목별로 세마포어를 이용해 관리한다. // 대강 구현 완료
 * 생산자와 소비자 모두 thread pool을 이용해 동시 입장 수를 관리하라.
 * 5분간만 오픈하라.
 */

/*
 * 품목 별로 세마포어를 이용해 관리하라는 말은
 * 1. 한 품목은 한 명만 살 수 있게 만들어야 한다.
 * 2. 여러 품목을 한 개 씩 살 수도 있다. (여러 개 사는 건 구현에 안 나옴)
 * 3. 그렇다면, 한 품목을 구경할 때마다 세마포어를 구현해 주어야 하는 게 아닐까??
 */
package com.nhnacademy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Store {
    private static final int MAX_PRODUCTS = 10;
    private static final int MAX_CUSTOMER = 5;
    private int currentCustomers;

    private int numberOfCategory; // 품목의 개수
    private List<Queue<Product>> productLists; // 품목을 모아서 관리하는 리스트
    private List<Integer> maxProductsList;

    private static final Semaphore semaphore = new Semaphore(1);

    public Store(int numberOfCategory) {
        this.maxProductsList = new ArrayList<>(); // 품목 별 최대 개수를 관리하는 리스트
        this.numberOfCategory = numberOfCategory; // 품목의 개수

        // 1. 품목 별로 링크드 리스트 만들고
        for (int i = 0; i < numberOfCategory; i++) {
            productLists.add(new LinkedList<Product>());
        }

        // 2. 품목 별로 최대 갯수를 정한다. - 5개 ~ 10개
        for (int i = 0; i < numberOfCategory; i++) {
            int random = ((int) Math.random() * 6) + 5;
            maxProductsList.add(random);
        }
    }

    // TODO : 고객 메서드에서 세마포어로 구현하기.
    public void enter() throws InterruptedException {
        while (currentCustomers >= MAX_CUSTOMER) {
            wait();
        }
        currentCustomers++;
        System.out.println("고객 입장, 현재 고객 수: " + currentCustomers);
    }

    // TODO : 고객 메서드에서 세마포어로 구현하기.
    public void exit() {
        currentCustomers--;
        System.out.println("고객 퇴장, 현재 고객 수: " + currentCustomers);
        notifyAll();
    }

    // 고객한테 팔기
    public void buy() throws InterruptedException {
        semaphore.acquire(); // 혼자서 확인하자
        // 1. 품목 별로 하나도 없다면 -> 기다려 보고 -> 성과가 없으면 나가세요
        while (!isExistOneItemPerCategory()) {
            if(!semaphore.tryAcquire(5, TimeUnit.SECONDS)) { // 2. 5초 wait() 시키고도 release가 안 떨어지면
                semaphore.release();
                return; // 3. 포기하세요.
            }
        }
        semaphore.release();

        // 2. 품목 별로 하나씩 사세요. / TODO: 품목 별로 세마포어가 들어갈 수 있도록 수정 요망. -> 지금은 블럭하고 다를게 없음.
        semaphore.acquire();
        for (int i = 0; i < productLists.size(); i++) {
            productLists.get(i).poll();
            
            System.out.println(i + "번 품목을 구매하셨습니다.");
            System.out.println(i + "번 품목의 남은 물건 수는 : " + productLists.get(i).size() + "입니다.");
        }

        // 3. 주변 사람에게 알려주기
        semaphore.release();
    }

    // 납품 받기
    public void sell() throws InterruptedException {

        semaphore.acquire(); // 혼자서 확인하자
        // 1. 종목이 하나라도 꽉 찬 게 있다면 -> 기다려 보시고 -> 성과가 없으면 나가세요
        while(isExistsFullyOccupied()) {
            if(!semaphore.tryAcquire(5, TimeUnit.SECONDS)) { // 2. 5초 wait() 시키고도 release가 안 떨어지면
                semaphore.release();
                return; // 3. 포기하세요.
            }
        }
        semaphore.release();

        // 2. 품목 별로 하나씩 재고 채워 주세요.
        semaphore.acquire();
        for (int i = 0; i < productLists.size(); i++) {
            productLists.get(i).add(new Product());

            System.out.println(i + "번 품목의 재고가 들어왔습니다.");
            System.out.println(i + "번 품목의 물건 수는 : " + productLists.get(i).size() + "입니다.");
        }

        // 3. 주변 사람에게 알리기
        semaphore.release();
    }

    private boolean isExistsFullyOccupied() {
        for (int i = 0; i < productLists.size(); i++) {
            if(productLists.get(i).size() == maxProductsList.get(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistOneItemPerCategory() { // 아무런 아이템도 없습니까?
        for (Queue queue : productLists) {
            if (queue.isEmpty()) { // 비어 있는 품목이 단 하나라도 존재한다면
                return false;
            }
        }
        return true; // 모든 품목이 하나씩이라도 있다면
    }
}