package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.anwarshahriar.calligrapher.Calligrapher;

public class BinarySchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_schedule);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public void eleganceSchedule(View v){
        Intent iElegance = new Intent(BinarySchedule.this,EleganceSchedule.class);
        startActivity(iElegance);
        finish();
    }

    public void wingsSchedule(View v){
        Intent iElegance = new Intent(BinarySchedule.this,Schedule.class);
        startActivity(iElegance);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BinarySchedule.this,MainActivity.class);
        startActivity(i);
        finish();

    }
}
