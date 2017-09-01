package com.saurabh.wings2017;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class confirmOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Window window = confirmOrder.this.getWindow();
        //  mAuth = FirebaseAuth.getInstance();
//        mAuth.addAuthStateListener(mAuthListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(R.color.upper);
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
