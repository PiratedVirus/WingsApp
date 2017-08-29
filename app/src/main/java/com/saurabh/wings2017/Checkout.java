package com.saurabh.wings2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {

    TextView FinalChkSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        FinalChkSum = (TextView) findViewById(R.id.FinalChkSum);
        FinalChkSum.setText(getIntent().getStringExtra("TotalSum"));

    }
}
