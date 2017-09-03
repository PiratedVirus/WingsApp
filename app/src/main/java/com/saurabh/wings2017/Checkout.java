package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;



public class Checkout extends AppCompatActivity {

    public static final String PHP_TRANSFER_CART = "https://scouncilgeca.com/WingsApp/transferCartData.php";
    public static final String PHP_SEND_MAIL = "https://scouncilgeca.com/WingsApp/sendMail.php";
    TextView FinalChkSum,discountPrice,netPrice;
    int totalCnt;
    int Discount;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername;
    String mUsermail;


    public void sendMail(){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();
        mUsermail = mFirebaseUser.getEmail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_SEND_MAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("fuserName", mUsername);
                params.put("fuserMail", mUsermail);
                params.put("cartSum",getIntent().getStringExtra("TotalSum"));

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Window window = Checkout.this.getWindow();
        //  mAuth = FirebaseAuth.getInstance();
//        mAuth.addAuthStateListener(mAuthListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }

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
        finish();
    }

    public void confirmCart(View v){

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_TRANSFER_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Checkout.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("fuserMail", mFirebaseUser.getEmail());
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        sendMail();




        Intent confirmCartIntent = new Intent(Checkout.this,confirmOrder.class);
        startActivity(confirmCartIntent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Checkout.this,Cart.class);
        startActivity(i);
        finish();

    }
}
