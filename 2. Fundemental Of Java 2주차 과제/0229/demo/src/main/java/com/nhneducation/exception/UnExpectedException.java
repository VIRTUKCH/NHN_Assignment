package com.nhneducation.exception;

public class UnExpectedException extends Exception {
    // 프로그램을 진행할 수 없는 심각한 오류입니다. 개발자에게 신고 부탁드립니다.
    public UnExpectedException(String message) { 
        super(message);
    }
}
