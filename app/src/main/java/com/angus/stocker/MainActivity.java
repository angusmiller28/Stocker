package com.angus.stocker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String PAST_WEEK = "Past Week";
    static final String PAST_MONTH = "Past Month";
    static final String PAST_YEAR = "Past Year";
    static final String ALL_DATA = "All Data";
    private ChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartView = new ChartView(this, R.raw.goog);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.addView(chartView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle()){

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(PAST_WEEK);
        menu.add(PAST_MONTH);
        menu.add(PAST_YEAR);
        menu.add(ALL_DATA);
        return true;
    }
}