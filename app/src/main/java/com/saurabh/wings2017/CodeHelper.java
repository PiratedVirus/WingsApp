package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CodeHelper extends AppCompatActivity {

    TextView gen_Name, gen_Location, gen_Info, gen_Date, gen_person_name, gen_Price, gen_person_num ;
    Button add_to_cart;
    public static final String PHP_URL = "https://scouncilgeca.com/wingsapp/sendEventData.php";

    // Firebase instance variables

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    SweetAlertDialog pDialog;
    String member;
    FabSpeedDial fab;

    // Firebase Detail holders
    String mUsername, mobNum;
    String mPhotoUrl;
    String mUsermail;

    String eventprice;





    //    Printing Details
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getUserDetails();
                mobNum = SaveSharedPreferences.getUserPhone(CodeHelper.this);
                eventprice = getIntent().getStringExtra("EventCriteria");
                Log.d(TAG, "price: " + eventprice);
                Map<String, String> params = new HashMap<>();
                params.put("fuserName", SaveSharedPreferences.getUserName(CodeHelper.this));
                params.put("fuserMail", mUsermail);
                params.put("fuserMob", mobNum);
                params.put("eventName", getIntent().getStringExtra("name"));
                params.put("eventID", getIntent().getStringExtra("date"));
                params.put("eventPrice", getIntent().getStringExtra("price"));
                params.put("eventLocation", getIntent().getStringExtra("location"));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_helper);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        gen_Name = (TextView) findViewById(R.id.genEventName);
        gen_Location = (TextView) findViewById(R.id.genEventLocation);
        gen_Info = (TextView) findViewById(R.id.genEventInfo);
        gen_Price = (TextView) findViewById(R.id.genEventPrice);
        gen_person_name = (TextView) findViewById(R.id.genEventContactPerson);
//        gen_person_num = (TextView) findViewById(R.id.genEventContactMob) ;
        gen_Date = (TextView)findViewById(R.id.EventDate);
        add_to_cart = (Button) findViewById(R.id.addToCart);

//        Getting data from Intent of respective activities
        String contactDetails = getIntent().getStringExtra("person_name") +"   "+ getIntent().getStringExtra("person_num");
        gen_Name.setText(getIntent().getStringExtra("name"));
        gen_Location.setText(getIntent().getStringExtra("location"));
        gen_Info.setText(getIntent().getStringExtra("desc"));
        gen_Price.setText("₹" + getIntent().getStringExtra("price"));
        gen_person_name.setText(contactDetails);
//        gen_person_num.setText(getIntent().getStringExtra("person_num"));
        gen_Date.setText(getIntent().getStringExtra("date"));
        member = getIntent().getStringExtra("members");

        String time = getIntent().getStringExtra("time");
        TextView gen_time = (TextView)findViewById(R.id.dateTime);

        gen_time.setText(time);


//        OnClick Listner. Adding data to Database.
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkAvailable()) {
                    Log.e("PV", "not connected");


                    new SweetAlertDialog(CodeHelper.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("No Internet")
                            .setContentText("Let's fix the satellites !")
                            .setCustomImage(R.drawable.no_internet)
                            .setConfirmText("FIX")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    final Context ctx = getApplicationContext();
                                    Intent i = new Intent(Settings.ACTION_SETTINGS);
                                    // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    ctx.startActivity(i);
                                }
                            })
                            .show();

                } else {

                    if (Integer.parseInt(member) != 0) {

                        new SweetAlertDialog(CodeHelper.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setTitleText("Add Group Members")
                                .setContentText("This event contains at max "+member+" members!")                                 .setCustomImage(R.drawable.team_group)
                                .setConfirmText("Add Group")
                                .setCancelText("Continue Single")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent i = new Intent(getApplicationContext(), CodeGroup.class);
                                        i.putExtra("member", member);
                                        i.putExtra("fuserName",SaveSharedPreferences.getUserName(CodeHelper.this));
                                        i.putExtra("fuserMail", mUsermail);
                                        i.putExtra("fuserMob", mobNum);
                                        i.putExtra("eventName", getIntent().getStringExtra("name"));
                                        i.putExtra("eventID", getIntent().getStringExtra("date"));
                                        i.putExtra("eventPrice", getIntent().getStringExtra("price"));
                                        i.putExtra("eventLocation", getIntent().getStringExtra("location"));
                                        startActivity(i);
                                        finish();
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
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
                    else if(Integer.parseInt(member)==0) {


                        new SweetAlertDialog(CodeHelper.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                                                    }
                                                })
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }


                                })
                                .show();
                    }
                }
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CodeHelper.this, Code.class);
        startActivity(i);
        finish();
    }
}
