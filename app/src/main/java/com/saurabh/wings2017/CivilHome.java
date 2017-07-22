package com.saurabh.wings2017;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CivilHome extends AppCompatActivity {




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


        civilRecylerHome = (RecyclerView) findViewById(R.id.CivilRecyler);
        civilLayoutManager = new LinearLayoutManager(this);
        civilRecylerHome.setLayoutManager(civilLayoutManager);
        civilRecylerHome.setHasFixedSize(true);
        civilAdapter = new CivilEventAdapter(list, this);
        civilRecylerHome.setAdapter(civilAdapter);

    }




}
