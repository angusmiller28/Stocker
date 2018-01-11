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
    float width, height;
    Paint mPaint = new Paint();

    public ChartView(Context context, int resId) {
        super(context);
        InputStream inputStream = getResources().openRawResource(resId);
        data = CSVParser.read(inputStream);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float rectWidth = width/data.size();
        float left = 0;

        for (int i = data.size() - 1; i >= 0; i--){
            StockData stockData = data.get(i);
            canvas.drawRect(left, height - stockData.high, left + rectWidth, height - stockData.low, mPaint);
            left += rectWidth;
        }
    }
}
