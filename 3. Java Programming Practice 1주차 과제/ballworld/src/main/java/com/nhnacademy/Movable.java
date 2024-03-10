package com.nhnacademy;

// Ball(Regionable) -> PaintalbeBall(Paintable) -> MovableBall(Movable) -> BoundedBall(Bounded)
public interface Movable {
    /** 
     * object에 단위 시간만큼 이동을 지시합니다.
    */
    public void move();  
}
