package com.saurabh.wings2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class confirmOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
    }

    public void moreEvents(View v){
        Intent m = new Intent(confirmOrder.this,MainActivity.class);
        startActivity(m);
    }
}
