package com.saurabh.wings2017;

import android.app.ProgressDialog;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
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
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import me.anwarshahriar.calligrapher.Calligrapher;


public class Cart extends AppCompatActivity {


    public static final String PHP_GET_CART = "https://scouncilgeca.com/wingsapp/getcartdata.php";
    public static final String PHP_DELETE_CART = "https://scouncilgeca.com/wingsapp/deleteEventCart.php";

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername, mPhotoUrl, mUsermail, uniqueID, EventNum;
    TextView unique;
    HttpPost httppost;
    HttpResponse response;
    final InputStream[] is = {null};
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
    int flag=0;
    JSONObject jsonobj1;
    ProgressDialog dialog;
    JSONArray stu = null;
    String serverUrl = "http://cseapp.16mb.com/notification.php";
    String result;
    JSONObject jso;
    ListView cart;
    TextView cart_single;
    int total_cart_sum;
    CustomList ad;
    int positionlist,cart_sum;
    TextView total;
    SweetAlertDialog pDialog;
    ImageView emptyCart,exploreBtn,checkout;
    TextView cartText,secText,removeText;
    JSONArray cart_user_list;
    FabSpeedDial fab;
    String 	userName, eventName, eventID, eventPrice,eventLocationString;
    ArrayList<String> userName_list = new ArrayList<String>();
    ArrayList<String> eventName_list = new ArrayList<String>();
    ArrayList<String> eventID_list = new ArrayList<String>();
    ArrayList<String> eventPrice_list = new ArrayList<String>();
    ArrayList<String> uniqueID_list = new ArrayList<String>();
    ArrayList<String> eventLocation = new ArrayList<String>();


    //    Getting Details
    public void getUserDetails(){

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

            mUsername = mFirebaseUser.getDisplayName();
            mUsermail = mFirebaseUser.getEmail();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();

        }






    public void fetchData(){

        getUserDetails();


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(true);
        pDialog.show();


        emptyCart = (ImageView) findViewById(R.id.emptyCart);
        cartText = (TextView) findViewById(R.id.cart_text);
        exploreBtn = (ImageView) findViewById(R.id.exploreBtn);
        secText = (TextView) findViewById(R.id.cart_sec_text);
        checkout = (ImageView) findViewById(R.id.chkOutBtn);
        removeText = (TextView) findViewById(R.id.removeBtn);



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_GET_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismissWithAnimation();
                        json(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cart.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getUserDetails();

                Map<String, String> params = new HashMap<>();

                params.put("fuserMail", mUsermail);
                Log.e("PVT", "Location hagla = "+getIntent().getStringExtra("location"));
                return params;
            }


        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {

            }
        });









//
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//
//
//                    try {
//                        httpclient = new DefaultHttpClient();
//                        httppost = new HttpPost(PHP_GET_CART); // make sure the url is correct.
//                        //add your data
//                        nameValuePairs = new ArrayList<NameValuePair>(1);
//                        // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
//                        nameValuePairs.add(new BasicNameValuePair("year", "fe"));
//
//                        // $Edittext_value = $_POST['Edittext_value'];
//                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                        Log.e("andro", "1" + mUsermail);
//                        //Execute HTTP Post Request
//                        response = httpclient.execute(httppost);
//
//                        Log.e("andro", "2");
//                        httpentity = response.getEntity();
//                        is[0] = httpentity.getContent();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(is[0], "UTF-8"), 8);
//                        StringBuilder sb = new StringBuilder();
//                        String line = null;
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                        }
//                        result = sb.toString();
////            ResponseHandler<String> responseHandler = new BasicResponseHandler();
////            final String response = httpclient.execute(httppost, responseHandler);
////            System.out.println("Response : " + response);
//
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                // tv.setText("Response from PHP : " + response);
//                                pDialog.dismiss();
//                            }
//                        });
//                        Log.e("PV","3+"+result);
//
//                        if (!(result.startsWith("F"))) {
//                            Log.i("andro", result);
//                            try {
//                                jso = new JSONObject(result);
//                                cart_user_list = jso.getJSONArray("result");
//                                for (int i = 0; i < cart_user_list.length(); i++) {
//                                    JSONObject c = cart_user_list.getJSONObject(i);
//                                    uniqueID = c.getString("uniqueID");
//                                    userName = c.getString("userName");
//                                    eventName = c.getString("eventName");
//                                    eventID = c.getString("eventID");
//                                    eventPrice = c.getString("eventPrice");
//                                    eventLocationString = c.getString("eventLocation");
//                                    Log.e("result",userName+eventName+eventID+eventPrice+eventLocationString);
//                                    userName_list.add(userName);
//                                    eventName_list.add(eventName);
//                                    eventID_list.add(eventID);
//                                    eventPrice_list.add(eventPrice);
//                                    uniqueID_list.add(uniqueID);
//                                    eventLocation.add(eventLocationString);
//                                }
//
//                                Cart.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        total = (TextView) findViewById(R.id.total);
//                                        ad = new CustomList(Cart.this, userName_list, eventName_list, eventID_list, eventPrice_list, uniqueID_list,
//                                                eventLocation, total, emptyCart, exploreBtn, checkout, secText);
//                                        cart = (ListView)findViewById(R.id.cart_list_show);
//                                        cart.setAdapter(ad);
//
//                                        for(int i=0;i<eventPrice_list.size();i++)
//                                        {
//                                            Log.e("PV","sum="+eventPrice_list.get(i));
//                                            cart_sum+=Integer.parseInt(eventPrice_list.get(i));
//                                        }
//                                        Log.e("PV","sum="+cart_sum);
//                                        total.setText("Amount   ₹"+String.valueOf(cart_sum));
//                                        if(cart_sum==0){
//                                            emptyCart.setVisibility(View.VISIBLE);
//                                            cartText.setVisibility(View.VISIBLE);
//                                            exploreBtn.setVisibility(View.VISIBLE);
//                                            secText.setVisibility(View.VISIBLE);
//                                            checkout.setVisibility(View.GONE);
//                                            total.setVisibility(View.GONE);
//                                            Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Log.e("PV", "else_case_failed");
//                        }
//                    } catch (Exception e) {
//                    }
//
//                }
//            });
//            t.start();



    }

    public void json(String response) {
        pDialog.dismissWithAnimation();
        try {
            jso = new JSONObject(response);
            cart_user_list = jso.getJSONArray("result");
            for (int i = 0; i < cart_user_list.length(); i++) {
                JSONObject c = cart_user_list.getJSONObject(i);
                uniqueID = c.getString("uniqueID");
                userName = c.getString("userName");
                eventName = c.getString("eventName");
                eventID = c.getString("eventID");
                eventPrice = c.getString("eventPrice");
                eventLocationString = c.getString("eventLocation");
                Log.e("result", userName + eventName + eventID + eventPrice + eventLocationString);
                userName_list.add(userName);
                eventName_list.add(eventName);
                eventID_list.add(eventID);
                eventPrice_list.add(eventPrice);
                uniqueID_list.add(uniqueID);
                eventLocation.add(eventLocationString);
            }

            total = (TextView) findViewById(R.id.total);
            ad = new CustomList(Cart.this, userName_list, eventName_list, eventID_list, eventPrice_list, uniqueID_list,
                    eventLocation, total, emptyCart, exploreBtn, checkout, secText, removeText);
            cart = (ListView) findViewById(R.id.cart_list_show);
            cart.setAdapter(ad);

            for (int i = 0; i < eventPrice_list.size(); i++) {
                Log.e("PV", "sum=" + eventPrice_list.get(i));
                cart_sum += Integer.parseInt(eventPrice_list.get(i));
            }
            Log.e("PV", "sum=" + cart_sum);
            total.setText("Amount   ₹" + String.valueOf(cart_sum));
            if (cart_sum == 0) {
                emptyCart.setVisibility(View.VISIBLE);
                cartText.setVisibility(View.VISIBLE);
                exploreBtn.setVisibility(View.VISIBLE);
                secText.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.GONE);
                total.setVisibility(View.GONE);
                //Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
            }


//
        } catch (JSONException e) {
                e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Window window = Cart.this.getWindow();


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }

        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(Cart.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();



        } else {


            fetchData();
        }
    }

    public void explore(View v){
        Intent exploreIntent = new Intent(Cart.this,MainActivity.class);
        startActivity(exploreIntent);
        finish();
    }

    public void checkOut(View v){
        Intent chkOutIntent = new Intent(Cart.this,Checkout.class);
        StringBuilder sb = new StringBuilder(total.getText());
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        chkOutIntent.putExtra("TotalSum",sb.toString());
        startActivity(chkOutIntent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Cart.this,MainActivity.class);
        startActivity(i);
        finish();

    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
