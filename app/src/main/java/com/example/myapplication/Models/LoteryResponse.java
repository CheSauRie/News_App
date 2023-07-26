package com.example.myapplication.Models;

public class LoteryResponse {
    private int countNumbers;
    private String time;
    private LoteryResult results;

    public int getCountNumbers() {
        return countNumbers;
    }

    public void setCountNumbers(int countNumbers) {
        this.countNumbers = countNumbers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LoteryResult getResults() {
        return results;
    }

    public void setResults(LoteryResult results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "LoteryResponse{" +
                "countNumbers=" + countNumbers +
                ", time='" + time + '\'' +
                ", results=" + results +
                '}';
    }
}
