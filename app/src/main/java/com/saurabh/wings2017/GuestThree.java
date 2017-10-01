package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class GuestThree extends AppCompatActivity {

    public static final String PHP_BOOK_GThree = "https://scouncilgeca.com/wingsapp/guestthree.php";


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername,mUsermail;
    SweetAlertDialog pDialog;
    Button bt;
    TextView txt, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_three);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        bt = (Button) findViewById(R.id.bookSeat);   //button for booking
        txt = (TextView) findViewById (R.id.regIDText);  //textview of booking number
        info = (TextView) findViewById(R.id.genEventInfo);
        info.setText("Sankarshan Karhade also known as 'Shanky', is a famous Marathi Film Actor. He is co-host of famous TV programme 'Aamhi Saare Khavayye'.  \n");

        if(!(SaveSharedPreferences.getGuestThree(GuestThree.this).isEmpty())){
            txt.setText("RegID : " + SaveSharedPreferences.getGuestThree(GuestThree.this));
            bt.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
        }

    }

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
                PHP_BOOK_GThree,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GuestThree.this, "Your registration id is : "+ response, Toast.LENGTH_LONG).show();
                        SaveSharedPreferences.setGuestThree(GuestThree.this,response);
                        txt.setText("RegID : " +response);
                        bt.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuestThree.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fuserName", SaveSharedPreferences.getUserName(GuestThree.this));
                params.put("fuserMail",SaveSharedPreferences.getUserEmail(GuestThree.this));
                params.put("fuserMobile",SaveSharedPreferences.getUserPhone(GuestThree.this));
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
        BookSeat();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestThree.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }
}
