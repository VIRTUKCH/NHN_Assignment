/*
 * <마트>
 * N개의 품목 매장이 있다. // 구현 완료
 * 품목마다 최대 갯수가 지정되어 있다. // 구현 완료
 * 폼목별로 세마포어를 이용해 관리한다. // 리팩토링 필요.
 * 생산자와 소비자 모두 thread pool을 이용해 동시 입장 수를 관리하라. // 구현 완료
 * 5분간만 오픈하라. // 구현 완료
 * 
 * <로그 구현>
 * 생산자의 동작(납품, 포기 등)과 소비자의 동작(입장, 구매, 포기, 퇴장 등)을 로거를 이용해 출력하라. // 완료
 * 포기는 WARN 레벨로 출력하라. // 완료
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// <로그 구현>
// 생산자의 동작(납품, 포기 등)과 소비자의 동작(입장, 구매, 포기, 퇴장 등)을 로거를 이용해 출력하라.
// 포기는 WARN 레벨로 출력하라.
public class Store {
    private int currentCustomers;

    private List<Queue<Product>> productLists; // 품목을 모아서 관리하는 리스트
    private List<Integer> maxProductsList;

    private static final Semaphore semaphore = new Semaphore(1);
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    // ------------------------------멤버변수------------------------------

    public Store(int numberOfCategory) {
        // 0. 기본 초기화
        this.maxProductsList = new ArrayList<>(); // 품목 별 최대 개수를 관리하는 리스트
        productLists = new ArrayList<>();

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

    public void enter() throws InterruptedException {
        currentCustomers++;
        logger.info("고객 입장, 현재 고객 수: " + currentCustomers);
    }

    public void exit() {
        currentCustomers--;
        logger.info("고객 퇴장, 현재 고객 수: " + currentCustomers);
    }

    // 고객한테 팔기
    public void buy() throws InterruptedException {
        semaphore.acquire(); // 혼자서 확인하자
        // 1. 품목 별로 하나도 없다면 -> 기다려 보고 -> 성과가 없으면 나가세요
        while (!isExistOneItemPerCategory()) {
            if (!semaphore.tryAcquire(5, TimeUnit.SECONDS)) { // 2. 5초 wait() 시키고도 release가 안 떨어지면
                semaphore.release();
                logger.warn("고객이 주문을 포기하였습니다.");
                return; // 3. 포기하세요.
            }
        }
        semaphore.release();

        // 2. 품목 별로 하나씩 사세요. TODO : 실제로 세마포어가 품목 별로 들어갈까?
        for (Queue<Product> queue : productLists) {
            semaphore.acquire();
            queue.poll();
            semaphore.release();
        }
    }

    // 납품 받기
    public void sell() throws InterruptedException {

        semaphore.acquire(); // 혼자서 확인하자
        // 1. 종목이 하나라도 꽉 찬 게 있다면 -> 기다려 보시고 -> 성과가 없으면 나가세요
        while (isExistsFullyOccupied()) {
            if (!semaphore.tryAcquire(5, TimeUnit.SECONDS)) { // 2. 5초 wait() 시키고도 release가 안 떨어지면
                semaphore.release();
                logger.warn("생산자가 납품을 포기하였습니다.");
                return; // 3. 포기하세요.
            }
        }
        semaphore.release();

        // 2. 품목 별로 하나씩 재고 채워 주세요.
        semaphore.acquire();
        for (int i = 0; i < productLists.size(); i++) {
            productLists.get(i).add(new Product());
            logger.info(i + "번 품목의 재고가 들어왔습니다.");
            logger.info(i + "번 품목의 물건 수는 : " + productLists.get(i).size() + "입니다.");
        }

        // 3. 주변 사람에게 알리기
        semaphore.release();
    }

    private boolean isExistsFullyOccupied() {
        for (int i = 0; i < productLists.size(); i++) {
            if (productLists.get(i).size() == maxProductsList.get(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistOneItemPerCategory() { // 아무런 아이템도 없습니까?
        for (Queue<Product> queue : productLists) {
            if (queue.isEmpty()) { // 비어 있는 품목이 단 하나라도 존재한다면
                return false;
            }
        }
        return true; // 모든 품목이 하나씩이라도 있다면
    }
}