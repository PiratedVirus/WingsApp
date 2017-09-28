package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GuestOne extends AppCompatActivity {


    public static final String PHP_BOOK_GOne = "https://scouncilgeca.com/wingsapp/guestone.php";


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername,mUsermail;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_one);
    }


    //    Getting Details
    public void getUserDetails(){

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mUsername = mFirebaseUser.getDisplayName();
        mUsermail = mFirebaseUser.getEmail();


    }

    public void BookSeat(){


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_BOOK_GOne,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuestOne.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getUserDetails();

//                eventprice = getIntent().getStringExtra("EventCriteria");

                Map<String, String> params = new HashMap<>();
                params.put("fuserName", SaveSharedPreferences.getUserName(GuestOne.this));
                params.put("fuserMail",SaveSharedPreferences.getUserEmail(GuestOne.this));
                params.put("fuserMobile",SaveSharedPreferences.getUserPhone(GuestOne.this));
                return params;
            }


        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                pDialog.dismissWithAnimation();
            }
        });

    }



    public void BookTicket(View v){

        getUserDetails();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestOne.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }
}
