package com.nhnacademy;

// Ball(Regionable) -> PaintalbeBall(Paintable) -> MovableBall(Movable) -> BoundedBall(Bounded)
public interface Bounded {
    void bounce(Regionable other);
}
