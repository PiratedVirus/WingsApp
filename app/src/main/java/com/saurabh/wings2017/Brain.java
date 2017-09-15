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

public class Brain extends AppCompatActivity  {

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
    ListView civil;



    ArrayList<String>  eventName_list = new ArrayList<>();
    ArrayList<String>  eventDetails_list = new ArrayList<>();
    ArrayList<String>  eventLocation = new ArrayList<>();
    ArrayList<String>  eventContactPerson_list = new ArrayList<>();
    ArrayList<String>  eventContactNum_list = new ArrayList<>();
    ArrayList<String>  eventDate = new ArrayList<>();
    ArrayList<String>  eventprice = new ArrayList<>();
    ArrayList<String> group_limit = new ArrayList<>();
    ArrayList<String> time_list = new ArrayList<>();




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
            Toast.makeText(Brain.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Brain.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_brain);

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
              //  Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
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



        eventName_list.add("Spell UR Brain");
        eventName_list.add("Murder Mystery");
        eventName_list.add("Web Quest 4.0");






        eventDetails_list.add("It is a team event, consists of three rounds.\n A team will have 2 members.\n\n"+
        "Rules\n\n" +
                "1. Each team should arrive at the venue at least 15 mins before the start of the event.\n" +
                "2. Both members of the team must be present during the event.\n" +
                "3. Use of cellphones, accessing internet through any means is strictly prohibited.\n" +
                "4. Once a team is registered, same team members should attend all the rounds.\n\n" +
                "Details\n\n" +
                "There are 3 rounds in the event.\n" +
                "First round will include logo guessing, dictation and visual clues.\n" +
                "Further rounds will be based on simple english and general knowledge.\n" +
                "Final round will be surprise round.\n");

        eventDetails_list.add("To solve a mysterious murder case & find the murderer(s) with the help of given police investigation report and other details.\n" +
                "\n" +
                "Rules: \n\n" +
                "Each team will be given a booklet of police investigation report\n" +
                "The team which will answer accurately & in min. time will be\n" +
                "the winners.\n" +
                "Reportin time for participants will be 10:30am.\n" +
                "End limit for submitting your answer sheet will be 5:30pm.\n\n" +
                "Event Structure: \n\n" +
                "All teams will be given a booklet consisting of murder details & police investigation report fron the desk. The team which solves given question(s) accurately & reports back to desk within min. time will be winners.\n");

        eventDetails_list.add("WebQuest 4.0 is an event, which consists of an individual participant to decode the given web based application in a given time limit. This contest will consist of individual participant. Each competing to decode the ultimate web quest.\n" +
                "\n" +
                "Rules:\n\n" +
                "The participant completing all the hacks within shortest time will qualify for the next round.\n" +
                "For the in campus challenge rules will be same as above with slight different of time limit.\n" +
                "For in campus challenge any technical glitches with the participants' system will fetch an extra time of two minutes.\n" +
                "Organizers reserve right to modify, change or suspend rules and regulations at any time if required. Decisions of the organizers will be final.\n" +
                "Any misbehavior with other participants, coordinators or organizers will not be entertained and will lead to immediate disqualification. \n");




        eventLocation.add("IT Dept");
        eventLocation.add("Front of CC");
        eventLocation.add("Computer Science Dept");



        eventContactPerson_list.add("Siddhi Sharma");
        eventContactPerson_list.add("Madhura Tayade");
        eventContactPerson_list.add("Ashutosh Gajre");


        eventContactNum_list.add("8600042536");
        eventContactNum_list.add("9975491679");
        eventContactNum_list.add("9561002599");



        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");




        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("50");



        group_limit.add("2");
        group_limit.add("0");
        group_limit.add("0");



        time_list.add("5th Oct, 2017\n 1.30 pm onwards");
        time_list.add("5th Oct, 2017\n 1 pm onwards");
        time_list.add("5 OCT, 2017\n 4 pm onwards");



        BrainAdapter ad = new BrainAdapter(Brain.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Brain.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}
