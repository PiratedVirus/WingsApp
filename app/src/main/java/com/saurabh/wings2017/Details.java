package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class Details extends AppCompatActivity {

    public static final String PHP_SAVE_USER = "https://scouncilgeca.com/WingsApp/saveDetails.php";

    //  Printing Details
    TextView fireName;
    TextView mobileNum,userNameInput;
    public ImageView updateInfo;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders

    String mPhotoUrl, mUsername;
    String mUsermail;






    //    Printing Details
    public void printUserDetails(){

        //        Fetching Details

        fireName = (TextView) findViewById(R.id.nameInput);
        mobileNum = (TextView) findViewById(R.id.mobileInput);
        updateInfo = (ImageView) findViewById(R.id.updateInfo);



        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, signIn.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mUsermail = mFirebaseUser.getEmail();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            Log.e("PV", "printUserDetails: " + mUsername + mUsermail);

            fireName.setText(mUsername);


        }

    }

    public void fetchData(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_SAVE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Details.this,"Data Saved",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Details.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseUser = mFirebaseAuth.getCurrentUser();

                mUsermail = mFirebaseUser.getEmail();
                mobileNum = (TextView) findViewById(R.id.mobileInput);
                userNameInput = (TextView) findViewById(R.id.nameInput);
                String userName = userNameInput.getText().toString();
                String mobileNumber = mobileNum.getText().toString();



                Map<String,String> params = new HashMap<>();
                params.put("fuserName", userName);
                params.put("fuserMail", mUsermail);
                params.put("fuserMob", String.valueOf(mobileNumber));

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        printUserDetails();


    }

    public void updateInfo(View v){
        fetchData();
        Intent updateInfoIntent  = new Intent(Details.this,MainActivity.class);
        startActivity(updateInfoIntent);
        finish();

    }
}
