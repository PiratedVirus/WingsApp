package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class confirmOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }

    }

    public void moreEvents(View v){
        Intent m = new Intent(confirmOrder.this,MainActivity.class);
        startActivity(m);
        finish();
    }

    public void showReceipt(View v){
        new SweetAlertDialog(confirmOrder.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Receipt")
                .setContentText("Visit PAYBOOTH in GECA to get final Receipt. ")
                .setConfirmText("Sounds nice !")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }

                })
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(confirmOrder.this,MainActivity.class);
        startActivity(i);
        finish();

    }
}
