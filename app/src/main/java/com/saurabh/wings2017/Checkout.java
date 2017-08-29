package com.saurabh.wings2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {

    TextView FinalChkSum,discountPrice,netPrice;
    int totalCnt;
    int Discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        FinalChkSum = (TextView) findViewById(R.id.TotalPrice);
        discountPrice = (TextView) findViewById(R.id.discountPrice);
        netPrice = (TextView) findViewById(R.id.netPrice);

        FinalChkSum.setText("₹"+getIntent().getStringExtra("TotalSum"));
        totalCnt = Integer.parseInt(getIntent().getStringExtra("TotalSum"));

        Discount = totalCnt - ((totalCnt*5)/100);
        discountPrice.setText("₹ "+((totalCnt*5)/100));
        netPrice.setText("₹" + Discount);



    }

    public void goBack(View v){
        Intent goBack = new Intent(Checkout.this,Cart.class);
        startActivity(goBack);
    }

    public void confirmCart(View v){
        Intent confirmCartIntent = new Intent(Checkout.this,confirmOrder.class);
        startActivity(confirmCartIntent);
    }
}
