package com.angus.stocker;

/**
 * Created by Angus on 11/01/2018.
 */

public class StockData {
    String date;
    float close;
    float volume;
    float open;
    float high;
    float low;

    public StockData(String date, float close, float volume, float open, float high, float low) {
        this.date = date;
        this.close = close;
        this.volume = volume;
        this.open = open;
        this.high = high;
        this.low = low;
    }

    @Override
    public String toString(){
        return "Date: " + date + ", " +
                "Close: " + close + ", " +
                "Volumne: " + volume + ", " +
                "Open: " + open + ", " +
                "High: " + high + ", " +
                "Low: " + low;
    }
}
