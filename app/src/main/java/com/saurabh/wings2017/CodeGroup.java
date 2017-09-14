package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CodeGroup extends AppCompatActivity {
    public static final String PHP_URL = "https://scouncilgeca.com/wingsapp/sendEventData.php";
    String mUsermail;
    SweetAlertDialog pDialog;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername, mobNum;
    String mPhotoUrl;
    EditText groupname;
    String xyz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_group);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        xyz = getIntent().getStringExtra("member");
        groupname = (EditText)findViewById(R.id.grouptext);
        groupname.setText(SaveSharedPreferences.getUserName(CodeGroup.this)+", ");
        int position = groupname.length();
        Editable etext = groupname.getText();
        Selection.setSelection(etext, position);

    }

    public void getUserDetails(){

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

        }

    }

    public void fetchData() {

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CodeGroup.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getUserDetails();
                mobNum = SaveSharedPreferences.getUserPhone(CodeGroup.this);
                groupname = (EditText)findViewById(R.id.grouptext);
                String temp = groupname.getText().toString().trim();
                Map<String, String> params = new HashMap<>();
                params.put("fuserName", SaveSharedPreferences.getUserName(CodeGroup.this));
                params.put("fuserMail", mUsermail);
                params.put("fuserMob", mobNum);
                params.put("eventName", getIntent().getStringExtra("eventName"));
                params.put("eventID", getIntent().getStringExtra("eventID"));
                params.put("eventPrice", getIntent().getStringExtra("eventPrice"));
                params.put("eventLocation", getIntent().getStringExtra("eventLocation"));
                params.put("teamMember",temp);
                Log.e("PVT", "Location hagla = "+getIntent().getStringExtra("location"));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CodeGroup.this, Code.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }

    public void addcart(View v)
    {

        groupname = (EditText)findViewById(R.id.grouptext);
        String temp = groupname.getText().toString().trim();

        String name[] = temp.split(",");

        if(name.length > Integer.parseInt(xyz))
        {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(groupname);
            Toast.makeText(this, "Oops! You've exceeded group member limit! Maximum BrainGroup Limit : "+xyz, Toast.LENGTH_SHORT).show();
        }

        else {
            new SweetAlertDialog(CodeGroup.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Are you sure to add event?")
//                        .setContentText("Events later can be changed through cart.")
                    .setConfirmText("Yeah")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setCustomImage(R.drawable.dialoge_cart)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            fetchData();
                            sDialog
                                    .setTitleText("Success!")
                                    .setContentText("Event added to Cart!")
                                    .setConfirmText("OK")
                                    .setCancelText("View Cart")
                                    .setConfirmClickListener(null)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            Intent i = new Intent(getApplicationContext(), Cart.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }


                    })
                    .show();
        }
    }
}
