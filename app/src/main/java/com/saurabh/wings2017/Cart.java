package com.saurabh.wings2017;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;


public class Cart extends AppCompatActivity {
//    private static final String TAG = "PV";
//    private RecyclerView recyclerView;
//    private  RecyclerView.Adapter adapter;
//    private List<ListItem> listItems;
//    private ListView lvEvent;
//    private Context context;

    public static final String PHP_GET_CART = "https://scouncilgeca.com/WingsApp/getCartData.php";
    public static final String PHP_DELETE_CART = "https://scouncilgeca.com/WingsApp/deleteEventCart.php";

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername, mPhotoUrl, mUsermail, uniqueID, EventNum;
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
    String serverUrl = "http://cseapp.16mb.com/notification.php";
    String result;
    JSONObject jso;
    ListView cart;
    int total_cart_sum;
    CustomList ad;
    int positionlist,cart_sum;
    TextView total;
    SweetAlertDialog pDialog;
    ImageView emptyCart,exploreBtn,checkout;
    TextView cartText,secText;
    JSONArray cart_user_list;
    FabSpeedDial fab;
    String 	userName, eventName, eventID, eventPrice;
    ArrayList<String> userName_list = new ArrayList<String>();
    ArrayList<String> eventName_list = new ArrayList<String>();
    ArrayList<String> eventID_list = new ArrayList<String>();
    ArrayList<String> eventPrice_list = new ArrayList<String>();
    ArrayList<String> uniqueID_list = new ArrayList<String>();

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

    public void DeleteEvent(final String uniqueID){

        Log.e("PV","BOcha is " +uniqueID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_DELETE_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cart.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("uniqueId", EventNum);

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void fetchData(){

        getUserDetails();
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Wait a moment, Fetching your events...");
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.show();

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(false);
        pDialog.show();

        emptyCart = (ImageView) findViewById(R.id.emptyCart);
        cartText = (TextView) findViewById(R.id.cart_text);
        exploreBtn = (ImageView) findViewById(R.id.exploreBtn);
        secText = (TextView) findViewById(R.id.cart_sec_text);
        checkout = (ImageView) findViewById(R.id.chkOutBtn);


        //total.setVisibility(View.VISIBLE);
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
//                                dialog.dismiss();
                                pDialog.dismissWithAnimation();

                            }
                        });

                        if (!(result.startsWith("F"))) {
                            Log.i("andro", result);
                            try {
                                jso = new JSONObject(result);
                                cart_user_list = jso.getJSONArray("result");
                                for (int i = 0; i < cart_user_list.length(); i++) {
                                    JSONObject c = cart_user_list.getJSONObject(i);
                                    uniqueID = c.getString("uniqueID");
                                    userName = c.getString("userName");
                                    eventName = c.getString("eventName");
                                    eventID = c.getString("eventID");
                                    eventPrice = c.getString("eventPrice");
                                    Log.e("result",userName+eventName+eventID+eventPrice);
                                    userName_list.add(userName);
                                    eventName_list.add(eventName);
                                    eventID_list.add(eventID);
                                    eventPrice_list.add(eventPrice);
                                    uniqueID_list.add(uniqueID);
                                }

                                Cart.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ad = new CustomList(Cart.this, userName_list, eventName_list, eventID_list, eventPrice_list, uniqueID_list);
                                        cart = (ListView)findViewById(R.id.cart_list_show);
                                        cart.setAdapter(ad);
                                        total = (TextView) findViewById(R.id.total);
                                        for(int i=0;i<eventPrice_list.size();i++)
                                        {
                                            Log.e("PV","sum="+eventPrice_list.get(i));
                                            cart_sum+=Integer.parseInt(eventPrice_list.get(i));
                                        }
                                        Log.e("PV","sum="+cart_sum);
                                        total.setText("Amount   ₹"+String.valueOf(cart_sum));
                                        if(cart_sum==0){
                                            emptyCart.setVisibility(View.VISIBLE);
                                            cartText.setVisibility(View.VISIBLE);
                                            exploreBtn.setVisibility(View.VISIBLE);
                                            secText.setVisibility(View.VISIBLE);
                                            checkout.setVisibility(View.GONE);
                                            total.setVisibility(View.GONE);
                                            Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
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

            try {
                cart = (ListView)findViewById(R.id.cart_list_show);
                cart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        positionlist = position;
                        Log.e("PV","Dabla maza jorat"+positionlist);

                        for(int i=0;i<uniqueID_list.size();i++)
                        {
                            Log.e("PV","gdsjh="+uniqueID_list.get(i));
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                        builder.setTitle("Cancel Event!");
                        builder.setMessage("Do your really want to cancel this event?")
                                .setCancelable(false)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       try {
                                           eventName_list.remove(positionlist);
                                           eventID_list.remove(positionlist);
                                           eventPrice_list.remove(positionlist);
                                           userName_list.remove(positionlist);

                                           // uniqueID_list.remove(positionlist);
                                           unique = (TextView) cart.getChildAt(positionlist).findViewById(R.id.uniqueID);

                                           ad.notifyDataSetChanged();

                                           EventNum = (String) unique.getText();
                                           Toast.makeText(Cart.this, "deleted" + EventNum, Toast.LENGTH_SHORT).show();

//                                            cart_sum = Integer.parseInt((String)total.getText());
                                           DeleteEvent(EventNum);
                                           TextView temp_price = (TextView)cart.getChildAt(positionlist).findViewById(R.id.PriceTag);
                                           Log.e("PV",(String)temp_price.getText());
                                           cart_sum = cart_sum - Integer.parseInt((String)temp_price.getText());
                                           total.setText("Amount   ₹"+String.valueOf(cart_sum));
                                           Toast.makeText(Cart.this,"Cart_sum = " + cart_sum,Toast.LENGTH_LONG).show();

                                           if(cart_sum==0){
                                               emptyCart.setVisibility(View.VISIBLE);
                                               cartText.setVisibility(View.VISIBLE);
                                               exploreBtn.setVisibility(View.VISIBLE);
                                               secText.setVisibility(View.VISIBLE);
                                               total.setVisibility(View.GONE);
                                               checkout.setVisibility(View.GONE);
                                               Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                       catch (IndexOutOfBoundsException r)
                                       {
                                           if(positionlist!=0)
                                           {
                                               cart_sum = Integer.parseInt((String)total.getText());
                                               total.setVisibility(View.VISIBLE);
                                               TextView temp_price = (TextView)cart.getChildAt(positionlist).findViewById(R.id.PriceTag);
                                               cart_sum = cart_sum-Integer.parseInt((String)temp_price.getText());
                                               total.setText("Amount   ₹"+String.valueOf(cart_sum));

                                           }
                                           else{
                                           total.setVisibility(View.GONE);

                                           Toast.makeText(Cart.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                                           r.printStackTrace();}
                                       }
                                    }
                                });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();


                        return true;

                    }
                });
            }
            catch(NullPointerException e)
            {
                e.printStackTrace();
            }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Window window = Cart.this.getWindow();
        //  mAuth = FirebaseAuth.getInstance();
//        mAuth.addAuthStateListener(mAuthListener);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }

        fetchData();

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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Cart.this,MainActivity.class);
        startActivity(i);
        finish();

    }

    public boolean hasNavBar (Resources resources)
    {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }


}
