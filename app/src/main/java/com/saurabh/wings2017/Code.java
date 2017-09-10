package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import me.anwarshahriar.calligrapher.Calligrapher;

public class Code extends AppCompatActivity  {

    private String fromConst;

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;

    FabSpeedDial fab;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;



    ArrayList<String>  eventName_list = new ArrayList<>();
    ArrayList<String>  eventDetails_list = new ArrayList<>();
    ArrayList<String>  eventLocation = new ArrayList<>();
    ArrayList<String>  eventContactPerson_list = new ArrayList<>();
    ArrayList<String>  eventContactNum_list = new ArrayList<>();
    ArrayList<String>  eventDate = new ArrayList<>();
    ArrayList<String> eventprice = new ArrayList<>();
    ArrayList<String> group_limit = new ArrayList<>();



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
            Toast.makeText(Code.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Code.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_code);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


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
                Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.e("PV", "itemID: " + itemId);

                switch(textView.getText().toString()){
                    case "Tickets":
                        Intent iticket = new Intent(getApplicationContext(),tickets.class);
                        startActivity(iticket);
                        finish();
                        break;
                    case "Cart":
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


        eventName_list.add("Bridge");
        eventName_list.add("Paper Bridge");
        eventName_list.add("Building");
        eventName_list.add("Paper Bridge");
        eventName_list.add("Paper Bridge");


        eventDetails_list.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt");
        eventDetails_list.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt");
        eventDetails_list.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt");
        eventDetails_list.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt");
        eventDetails_list.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sunt");

        eventLocation.add("Classroom Complex");
        eventLocation.add("Classroom Complex");
        eventLocation.add("Classroom Complex");
        eventLocation.add("Classroom Complex");
        eventLocation.add("Classroom Complex");

        eventContactPerson_list.add("Ashwin Kulkarni");
        eventContactPerson_list.add("Ashwin Kulkarni");
        eventContactPerson_list.add("Ashwin Kulkarni");
        eventContactPerson_list.add("Ashwin Kulkarni");
        eventContactPerson_list.add("Ashwin Kulkarni");

        eventContactNum_list.add("7798080437");
        eventContactNum_list.add("7798080437");
        eventContactNum_list.add("7798080437");
        eventContactNum_list.add("7798080437");
        eventContactNum_list.add("7798080437");

        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");

        eventprice.add("35");
        eventprice.add("45");
        eventprice.add("50");
        eventprice.add("25");
        eventprice.add("50");

        group_limit.add("0");
        group_limit.add("4");
        group_limit.add("2");
        group_limit.add("8");
        group_limit.add("2");




        CodeAdapter ad = new CodeAdapter(Code.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit);
        ListView civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Code.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
