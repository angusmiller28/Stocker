package com.angus.stocker;

import android.content.Context;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Angus on 11/01/2018.
 */

public class ChartView extends View {
    List<StockData> data;
    List<StockData> subset;
    float width, height, maxPrice, minPrice;
    Paint paint = new Paint();
    Paint strokePaint = new Paint();
    Paint textPaint = new Paint();
    float textHeight;
    Rect textBounds = new Rect();

    public ChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributeSet, R.styleable.ChartView, 0, 0);
        int resId = typedArray.getResourceId(R.styleable.ChartView_data, 0);
        typedArray.recycle();
        InputStream inputStream = getResources().openRawResource(resId);
        data = CSVParser.read(inputStream);
        showLast();
        strokePaint.setColor(Color.WHITE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.getTextBounds("0", 0, 1, textBounds);
        textHeight = textBounds.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float chartWidth = width - textPaint.measureText("1000");
        float rectWidth = chartWidth / subset.size();
        strokePaint.setStrokeWidth(rectWidth / 8);
        float left = 0;
        float bottom, top;

        for (int i = subset.size() - 1; i >= 0; i--) {
            StockData stockData = subset.get(i);
            if (stockData.close >= stockData.open) {
                paint.setColor(Color.GREEN);
                top = stockData.close;
                bottom = stockData.open;
            } else {
                paint.setColor(Color.RED);
                top = stockData.open;
                bottom = stockData.close;
            }
            canvas.drawLine(left + rectWidth / 2, getYPosition(stockData.high),
                    left + rectWidth / 2, getYPosition(stockData.low), strokePaint);
            canvas.drawRect(left, getYPosition(top), left + rectWidth, getYPosition(bottom), paint);
            left += rectWidth;
        }

        for (int i = (int)minPrice; i < maxPrice; i++) {
            if (i % 20 == 0) {
                strokePaint.setStrokeWidth(1);
                canvas.drawLine(0, getYPosition(i), chartWidth, getYPosition(i), strokePaint);
                canvas.drawText(i + "", width, getYPosition(i) + textHeight / 2, textPaint);
            }
        }
    }

    private float getYPosition(float price) {
        float scaleFactorY = (price - minPrice)/(maxPrice - minPrice);
        return height - height * scaleFactorY;
    }

    public void showLast(int n) {
        subset = data.subList(0, n);
        updateMaxAndMin();
        invalidate();
    }

    private void updateMaxAndMin() {
        maxPrice = -1f;
        minPrice = 99999f;
        for (StockData stockData : subset) {
            if (stockData.high > maxPrice)
                maxPrice = stockData.high;
            if (stockData.low < minPrice)
                minPrice = stockData.low;
        }
    }

    public void showLast() {
        showLast(data.size());
    }
}
