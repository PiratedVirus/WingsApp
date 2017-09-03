package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
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

public class GenericEventHome extends AppCompatActivity {

    TextView gen_Name, gen_Location, gen_Info, gen_Rules, gen_Criteria, gen_Price;
    Button add_to_cart;
    public static final String PHP_URL = "https://scouncilgeca.com/WingsApp/sendEventData.php";

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    FabSpeedDial fab;

    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;
    int mobNum;
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


    public void fetchData(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GenericEventHome.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getUserDetails();
                mobNum = 878654352;
                eventprice = getIntent().getStringExtra("EventCriteria");
                Log.d(TAG, "price: " + eventprice);
                Map<String,String> params = new HashMap<>();
                params.put("fuserName", mUsername);
                params.put("fuserMail", mUsermail);
                params.put("fuserMob", String.valueOf(mobNum));
                params.put("eventName", getIntent().getStringExtra("EventName"));
                params.put("eventID", "CSG101");
                params.put("eventPrice",eventprice);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_event_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }


        gen_Name = (TextView) findViewById(R.id.genEventName);
        gen_Location = (TextView) findViewById(R.id.genEventLocation);
        gen_Info = (TextView) findViewById(R.id.genEventInfo);
        gen_Price = (TextView) findViewById(R.id.genEventPrice);
        add_to_cart = (Button) findViewById(R.id.addToCart);

//        Getting data from Intent of respective activities
        gen_Name.setText(getIntent().getStringExtra("EventName"));
        gen_Location.setText(getIntent().getStringExtra("EventLocation"));
        gen_Info.setText(getIntent().getStringExtra("EventInfo"));
        gen_Price.setText("₹" + getIntent().getStringExtra("EventCriteria"));


        fab  = (FabSpeedDial) findViewById(R.id.fab);

        fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                // do something
            }
        });

        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
                Toast.makeText(GenericEventHome.this, "Itemid = "+itemId, Toast.LENGTH_SHORT).show();
                Log.e("PV", "itemID: " + itemId);

                switch(itemId){
                    case 2131624251:
                        Intent iticket = new Intent(getApplicationContext(),tickets.class);
                        startActivity(iticket);
                        finish();
                        break;
                    case 2131624250:
                        Intent iCart = new Intent(getApplicationContext(),Cart.class);
                        startActivity(iCart);
                        finish();
                        break;
                    default:
                        break;
                }


            }
        });fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {

            }
        });





//        OnClick Listner. Adding data to Database.
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkAvailable()) {
                    Log.e("PV", "not connected");


                    new SweetAlertDialog(GenericEventHome.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("You are not connected to Internet")
                            .setContentText("You are about to log out from the app")
                            .setConfirmText("Go to Settings")
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


                    new SweetAlertDialog(GenericEventHome.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                sDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
                                }


                            })
                            .show();


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
}
