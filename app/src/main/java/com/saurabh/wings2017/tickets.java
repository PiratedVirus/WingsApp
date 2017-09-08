package com.saurabh.wings2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class tickets extends AppCompatActivity {

    public static final String PHP_GET_TICKETS = "https://scouncilgeca.com/WingsApp/getTickets.php";

    String mUsername, mPhotoUrl, mUsermail, uniqueID, EventNum, paid, played;
    TextView unique;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
    JSONObject jsonobj1;
    ProgressDialog dialog;
    JSONArray stu = null;
    SweetAlertDialog pDialog;

    String result;
    JSONObject jso;
    ListView ticket;
    TicketList ad;
    int positionlist,cart_sum;
    Button total;
    ImageView noTickets;
    TextView cartText,secText;
    JSONArray cart_user_ticket;
    String 	userName, eventName, eventID, eventPrice, eventLocation;
    ArrayList<String> userName_ticket = new ArrayList<String>();
    ArrayList<String> eventName_ticket = new ArrayList<String>();
    ArrayList<String> eventID_ticket = new ArrayList<String>();
    ArrayList<String> eventPrice_ticket = new ArrayList<String>();
    ArrayList<String> uniqueID_ticket = new ArrayList<String>();
    ArrayList<String> paid_ticket = new ArrayList<String>();
    ArrayList<String> played_ticket = new ArrayList<String>();
    ArrayList<String> eventLocation_ticket = new ArrayList<String>();

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    public void getUserDetails() {

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

    public void printTickets(){

        getUserDetails();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("Waiting for more ink to print your Tickets !");
        pDialog.setCancelable(false);
        pDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(PHP_GET_TICKETS); // make sure the url is correct.
                    //add your data
                    nameValuePairs = new ArrayList<NameValuePair>(1);
                    // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                    nameValuePairs.add(new BasicNameValuePair("fuserMail", mUsermail));

                    // $Edittext_value = $_POST['Edittext_value'];
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.e("PV", "1" + mUsermail);
                    //Execute HTTP Post Requ
                    response = httpclient.execute(httppost);
                    Log.e("PV", "2");
                    httpentity = response.getEntity();
                    isr = httpentity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();


                    runOnUiThread(new Runnable() {
                        public void run() {
                            // tv.setText("Response from PHP : " + response);
                           // dialog.dismiss();
                            pDialog.dismissWithAnimation();
                        }
                    });

                    if (!(result.startsWith("F"))) {
                        Log.i("andro", result);
                        try {
                            jso = new JSONObject(result);
                            cart_user_ticket = jso.getJSONArray("result");
                            for (int i = 0; i < cart_user_ticket.length(); i++) {
                                JSONObject c = cart_user_ticket.getJSONObject(i);
                               // uniqueID = c.getString("uniqueID");
                                userName = c.getString("userName");
                                eventName = c.getString("eventName");
                                eventID = c.getString("eventID");
                                eventPrice = c.getString("eventPrice");
                                paid = c.getString("paid");
                                played = c.getString("played");
                                eventLocation = c.getString("eventLocation");
                                Log.e("result",userName+eventName+eventID+eventPrice+"paid_php"+paid);

                                userName_ticket.add(userName);
                                eventName_ticket.add(eventName);
                                eventID_ticket.add(eventID);
                                eventPrice_ticket.add(eventPrice);
                                paid_ticket.add(paid);
                                played_ticket.add(played);
                                eventLocation_ticket.add(eventLocation);
                            }

                            tickets.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ad = new TicketList(tickets.this, userName_ticket, eventName_ticket, eventID_ticket, eventPrice_ticket,  played_ticket, paid_ticket, eventLocation_ticket);
                                    ticket = (ListView)findViewById(R.id.ticket_list_show);
                                    ticket.setAdapter(ad);
                                    total = (Button) findViewById(R.id.total);
                                    Log.e("PV", "bocha tanla");
                                    for(int i=0;i<eventPrice_ticket.size();i++)
                                    {
                                        Log.e("PV","sum="+eventPrice_ticket.get(i));
                                        cart_sum+=Integer.parseInt(eventPrice_ticket.get(i));
                                    }
                                    Log.e("PV","sum="+cart_sum);
                                    total.setText(String.valueOf(cart_sum));
                                    noTickets = (ImageView) findViewById(R.id.noTicket);
                                    if(cart_sum==0){
                                        noTickets.setVisibility(View.VISIBLE);
                                        Toast.makeText(tickets.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("PV", "else_case_failed");
                    }
                } catch (Exception e) {
                }

            }
        });
        t.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);
        setContentView(R.layout.activity_tickets);


        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(tickets.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
            printTickets();
        }
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
        Intent i = new Intent(tickets.this,MainActivity.class);
        startActivity(i);
        finish();

    }
}
