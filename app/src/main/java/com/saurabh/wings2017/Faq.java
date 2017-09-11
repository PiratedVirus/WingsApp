package com.saurabh.wings2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Faq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Faq.this,MainActivity.class);
        startActivity(i);
        finish();

    }
}
