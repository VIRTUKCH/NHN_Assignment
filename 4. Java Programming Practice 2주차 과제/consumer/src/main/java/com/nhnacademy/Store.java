/*
1. 매장은 물건을 납품 받아서 판매한다. - buy()
2. 매장에는 최대 10개의 물건만 전시할 수 있다. - buy()
3. 매장은 최대 5명까지만 동시 입장 가능하다. - enter()
4. 매장에서 물건 구매는 동시에 1명만 가능하다. - sell()
5. 매장에서 물건 판매 후 빈 공간에 생기면 생산자에게 알려 준다. - sell()
6. 매장에서 물건 납품은 동시에 1명만 가능하다. - buy()
7. 매장에서 물건이 들어오면 소비자에게 알려 준다.
*/
package com.nhnacademy;

import java.util.LinkedList;
import java.util.Queue;

public class Store {
    private static final int MAX_PRODUCTS = 10;
    private static final int MAX_CUSTOMER = 5;
    private int customer;
    private Queue<Product> productList;

    public int getProductListSize() {
        return this.productList.size();
    }

    public Store() {
        productList = new LinkedList<>();
        for (int i = 0; i < MAX_PRODUCTS; i++) {
            productList.add(new Product()); // 일단 10개 미리 박아두기
        }
    }

    // 최대 5명까지 '동시' 입장 가능하다
    public void enter() {
        if(customer > MAX_CUSTOMER) {
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            customer++;
        }
    }

    // 고객이 나감
    public void exit() {
        customer--;
    }

    // 납품 받는 메서드
    public synchronized void buy(Product product) {
        // 1. 물건 납품은 동시에 1명만 가능하다.
        // 2. 최대 10개의 물건만 전시할 수 있다.
        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            // 3. 매장에서 물건이 들어오면 소비자에게 알린다.
            System.out.println("새 물건이 생겼습니다.");
        }
    }

    // 물건 파는 메서드
    public synchronized void sell() {
        if (!productList.isEmpty()) {
            // 1. 물건 구매는 동시에 1명만 가능하다.
            Product product = productList.poll();
            System.out.println("Product sold: " + product);
            // 2. 물건 판매 후에 빈 공간이 생기면 생산자에게 알려준다.
        } else {
            System.out.println("물건이 없습니다.");
        }
    }       
}