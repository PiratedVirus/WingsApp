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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ilyagh.TypewriterRefreshLayout;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

import static com.saurabh.wings2017.R.id.pullToRefresh;

public class tickets extends AppCompatActivity {

    public static final String PHP_GET_TICKETS = "https://scouncilgeca.com/wingsapp/getTickets.php";

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
    TypewriterRefreshLayout tr;
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



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_GET_TICKETS,
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
                        Toast.makeText(tickets.this, error.getMessage(), Toast.LENGTH_LONG).show();

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




    }

    public void json(String response) {
        try {
            jso = new JSONObject(response);
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
                Log.e("result", userName + eventName + eventID + eventPrice + "paid_php" + paid);

                userName_ticket.add(userName);
                eventName_ticket.add(eventName);
                eventID_ticket.add(eventID);
                eventPrice_ticket.add(eventPrice);
                paid_ticket.add(paid);
                played_ticket.add(played);
                eventLocation_ticket.add(eventLocation);
            }

        {
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
            Log.e("PV","ticketsum="+cart_sum);
            if(cart_sum==0){
                noTickets.setVisibility(View.VISIBLE);
                Toast.makeText(tickets.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
            }
            }
        }catch (JSONException e)
        {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);
        setContentView(R.layout.activity_tickets);

        tr = (TypewriterRefreshLayout)findViewById(pullToRefresh);

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


        tr.setOnRefreshListener(new TypewriterRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tr.setRefreshing(true);


                tr.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tr.setRefreshing(false);
                        Intent i = new Intent(tickets.this, tickets.class);
                        startActivity(i);
                        finish();
                    }
                }, 4000);


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
        Intent i = new Intent(tickets.this,MainActivity.class);
        startActivity(i);
        finish();

    }
}
