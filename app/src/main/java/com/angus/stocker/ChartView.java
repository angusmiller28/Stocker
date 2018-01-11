package com.angus.stocker;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Angus on 11/01/2018.
 */

public class ChartView extends View {
    List<StockData> data;
    List<StockData> subset;
    float maxPrice, minPrice;
    float width, height;
    Paint mPaint = new Paint();

    public ChartView(Context context, int resId) {
        super(context);
        InputStream inputStream = getResources().openRawResource(resId);
        data = CSVParser.read(inputStream);
        showLast();
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float rectWidth = width/subset.size();
        float left = 0;

        for (int i = data.size() - 1; i >= 0; i--){
            StockData stockData = subset.get(i);
            canvas.drawRect(left, getYPosition(stockData.high), left + rectWidth, getYPosition(stockData.low), mPaint);
            left += rectWidth;
        }
    }

    private float getYPosition(float price){
        float scaleFactoryY = (price - minPrice)/(maxPrice - minPrice);
        return height - height * scaleFactoryY;
    }

    public void showLast(int n) {
        subset = data.subList(0, n);
        invalidate();
    }

    private void updateMaxAndMin(){
        maxPrice = -1f;
        minPrice = 99999f;
        for (StockData stockData : subset){
            if (stockData.high > maxPrice){
                maxPrice = stockData.high;
            }
            if (stockData.low < minPrice){
                minPrice = stockData.low;
            }
        }
    }

    public void showLast() {
        subset = data.subList(0, data.size());
    }
}
