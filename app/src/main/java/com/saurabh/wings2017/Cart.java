package com.saurabh.wings2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

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

public class Cart extends AppCompatActivity {
    private static final String TAG = "PV";
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private ListView lvEvent;
    private Context context;

    public static final String PHP_GET_CART = "https://scouncilgeca.com/WingsApp/getCartData.php";

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
    JSONObject jsonobj1;
    ProgressDialog dialog;
    JSONArray stu = null;
    String serverUrl = "http://cseapp.16mb.com/notification.php";
    String result;
    JSONObject jso;
    JSONArray cart_user_list;
    String 	userName, eventName, eventID, eventPrice;
    ArrayList<String> userName_list = new ArrayList<String>();
    ArrayList<String> eventName_list = new ArrayList<String>();
    ArrayList<String> eventID_list = new ArrayList<String>();
    ArrayList<String> eventPrice_list = new ArrayList<String>();

    //    Getting Details
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


    public void fetchData(){

        getUserDetails();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Wait a moment, Fetching messages...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
            Thread t = new Thread(new Runnable() {
                public void run() {

                    try {
                        httpclient = new DefaultHttpClient();
                        httppost = new HttpPost(PHP_GET_CART); // make sure the url is correct.
                        //add your data
                        nameValuePairs = new ArrayList<NameValuePair>(1);
                        // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                        nameValuePairs.add(new BasicNameValuePair("fuserMail", mUsermail));

                        // $Edittext_value = $_POST['Edittext_value'];
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        Log.d("andro", "1" + mUsermail);
                        //Execute HTTP Post Requ
                        response = httpclient.execute(httppost);
                        Log.d("andro", "2");
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
                                dialog.dismiss();
                            }
                        });

                        if (!(result.startsWith("F"))) {
                            Log.i("andro", result);
                            try {
                                jso = new JSONObject(result);
                                cart_user_list = jso.getJSONArray("result");
                                for (int i = 0; i < cart_user_list.length(); i++) {
                                    JSONObject c = cart_user_list.getJSONObject(i);
                                    userName = c.getString("userName");
                                    eventName = c.getString("eventName");
                                    eventID = c.getString("eventID");
                                    eventPrice = c.getString("eventPrice");
                                    Log.e("result",userName+eventName+eventID+eventPrice);
                                    userName_list.add(userName);
                                    eventName_list.add(eventName);
                                    eventID_list.add(eventID);
                                    eventPrice_list.add(eventPrice);
                                }

                                Cart.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CustomList ad = new CustomList(Cart.this, userName_list, eventName_list, eventID_list, eventPrice_list);
                                        ListView cart = (ListView)findViewById(R.id.cart_list_show);
                                        cart.setAdapter(ad);
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
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fetchData();
    }
}
