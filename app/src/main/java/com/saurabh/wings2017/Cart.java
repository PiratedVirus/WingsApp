package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        getUserDetails();

//        Getting UNIQUE Key, this value to be passed in PHP and retrive results according to it.
        final String reqno = getIntent().getStringExtra("userMail");
        Log.e(TAG, "fetchData: " + reqno);


//        Using Volley Lib.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, PHP_GET_CART, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray requests = response.getJSONArray("result");

                    for(int i = 0; i < requests.length(); i++){
                        JSONObject request = requests.getJSONObject(i);
                        String no1 = request.getString("userMail");
                        String desc1 = request.getString("userName");
                        String date1 = request.getString("eventName");
                        String status1 = request.getString("eventID");

                        Log.e(TAG, "onResponse: EventName" +  date1 + "EventID" + status1 );
                        Toast.makeText(Cart.this, "Display all: " +no1+desc1+date1+status1, Toast.LENGTH_SHORT).show();



                    }


                } catch (JSONException e) {
                    Toast.makeText(Cart.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"Json error: " + e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cart.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Response error: "+error.toString() );

            }
        })
        {
//            passing reqno:the unique key through this method.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("fuserMail", reqno);
                Log.e(TAG, "getParams: " + reqno );
                return parameters;
            }
        };


        requestQueue.add(request);

        Toast.makeText(Cart.this, "It's working", Toast.LENGTH_SHORT).show();


    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fetchData();
    }
}
