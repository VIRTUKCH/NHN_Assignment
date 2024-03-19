package com.nhnacademy;

public class Record {
    int totalCount;
    int winCount;

    public Record(int totalCount, int winCount) {
        this.totalCount = totalCount;
        this.winCount = winCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
}