package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;


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
    HttpPost httppost;
    SweetAlertDialog pDialog;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
//    Typeface face= Typeface.createFromAsset(getAssets(), "fonts/mont.ttf");


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

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

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

    public void  confirmCart(View v){
        new SweetAlertDialog(Checkout.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure ?")
                .setContentText("This can't be undone.")
                .setConfirmText("PURCHASE")
                .setCancelText("Go back")
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        printTicket();
                    }
                })
                .show();

    }

    public void printTicket(){

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(false);
        pDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(PHP_TRANSFER_CART); // make sure the url is correct.
                    //add your data
                    nameValuePairs = new ArrayList<NameValuePair>(1);
                    // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                    nameValuePairs.add(new BasicNameValuePair("fuserMail", mFirebaseUser.getEmail()));

                    // $Edittext_value = $_POST['Edittext_value'];
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("andro", "1" + mUsermail);
                    //Execute HTTP Post Requ
                    response = httpclient.execute(httppost);
                    Log.d("andro", "2");
                    httpentity = response.getEntity();
                    isr = httpentity.getContent();


                    runOnUiThread(new Runnable() {
                        public void run() {
                            // tv.setText("Response from PHP : " + response);
//                                dialog.dismiss();
                            pDialog.dismissWithAnimation();

                        }
                    });
                } catch (Exception e) {
                }

            }
        });
        t.start();
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
