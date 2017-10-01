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

public class GuestOne extends AppCompatActivity {

    public static final String PHP_BOOK_GOne = "https://scouncilgeca.com/wingsapp/guestone.php";


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername,mUsermail;
    SweetAlertDialog pDialog;
    Button bt;
    TextView txt, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_one);

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
        info.setText("“Most ideas fizzle out because everyone spends hours just thinking about them and not doing anything.”\n" +
                "\n" +
                "Varun Agarwal is 29 years old, and has three companies to his credit. He’s now turned into an author with his first " +
                "book “How I Braved Anu Aunty and Co-founded a Million Dollar Company“.\n" +
                "\n" +
                "Q&A about Varun\n" +
                "\n" +
                "So whats your age and qualification?\n" +
                "\n" +
                "I’m 29 years old and a BE in Telecommunication. I’ve done my schooling from Bishop Cottons.\n" +
                "\n" +
                "And what exactly do you do??\n" +
                "\n" +
                "I’m a filmmaker and I have 3 startups.\n" +
                "And they are??\n" +
                "1. Alma Mater : We make merchandise for students of schools and colleges. www.almamaterstore.in \n" +
                "\n" +
                "2. Reticular : A social media marketing company www.reticular.in \n" +
                "\n" +
                "3. Last minute Films. A film production house. www.lastminutefilms.in (you can see some of films on the site)\n" +
                "\n" +
                "How did filmmaking happen?\n" +
                "\n" +
                "Filmmaking happened while still at school. My mum had given me a handycam and I used to make short films and music videos using the cam. I haven’t delved into my filmmaking life in my book but my next book will have a lot of that.\n" +
                "\n" +
                "You’ve directed Preity Zinta and AR Rahman when you were 21 years old???\n" +
                "\n" +
                "Yeah its true. So after graduating from Engineering I went to work for this production company called Phat Phish productions. And it there I got a chance to work with these stars.\n");

        if(!(SaveSharedPreferences.getGuestOne(GuestOne.this).isEmpty())){
         txt.setText("RegID : " + SaveSharedPreferences.getGuestOne(GuestOne.this));
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
                PHP_BOOK_GOne,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GuestOne.this, "Your registration id is : "+ response, Toast.LENGTH_LONG).show();
                        SaveSharedPreferences.setGuestOne(GuestOne.this,response);
                        txt.setText("RegID : " +response);
                        bt.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuestOne.this,"the error is" +error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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
        BookSeat();
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
