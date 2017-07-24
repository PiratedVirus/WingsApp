package com.saurabh.wings2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CivilHome extends AppCompatActivity  {

    private String fromConst;

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;


    public void printUserDetails(){

        //        Fetching Details

        fireName = (TextView) findViewById(R.id.displayName);
        fireMail = (TextView) findViewById(R.id.displayMail);
        fireImage = (ImageView) findViewById(R.id.displayImage);


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
            Toast.makeText(CivilHome.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(CivilHome.this).load(mPhotoUrl).into(fireImage);

        }

    }



    RecyclerView civilRecylerHome;
    RecyclerView.Adapter civilAdapter;
    RecyclerView.LayoutManager civilLayoutManager;

    ArrayList<CivilEventList> list = new ArrayList<CivilEventList>();

    String[] name,excerpt,location,rules,criteria,price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civil_home);


        name = getResources().getStringArray(R.array.CivilEventName);
        excerpt = getResources().getStringArray(R.array.CivilEventExcerpt);
        location = getResources().getStringArray(R.array.CivilEventLocation);
        rules = getResources().getStringArray(R.array.CivilEventRules);
        criteria = getResources().getStringArray(R.array.CivilEventCriteria);
        price = getResources().getStringArray(R.array.CivilEventPrice);

        int count = 0;

        for(String Name : name)
        {
            CivilEventList EventList = new CivilEventList(Name, excerpt[count],location[count],rules[count],criteria[count],price[count]);
            count++;
            list.add(EventList);

        }

        printUserDetails();

        civilRecylerHome = (RecyclerView) findViewById(R.id.CivilRecyler);
        civilLayoutManager = new LinearLayoutManager(this);
        civilRecylerHome.setLayoutManager(civilLayoutManager);
        civilRecylerHome.setHasFixedSize(true);
        civilAdapter = new CivilEventAdapter(list, this);
        civilRecylerHome.setAdapter(civilAdapter);

    }




}
