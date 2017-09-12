package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public void dayOne(View v){
        Intent one = new Intent(Schedule.this,DayOne.class);
        startActivity(one);
        finish();
    }

    public void dayTwo(View v){
        Intent one = new Intent(Schedule.this,DayTwo.class);
        startActivity(one);
        finish();
    }

    public void dayThree(View v){
        Intent one = new Intent(Schedule.this,DayThree.class);
        startActivity(one);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Schedule.this,BinarySchedule.class);
        startActivity(i);
        finish();

    }
}
