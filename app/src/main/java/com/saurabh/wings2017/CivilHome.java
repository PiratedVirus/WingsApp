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

public class CivilHome extends AppCompatActivity  {

    private String fromConst;

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;

    FabSpeedDial fab;

    // Firebase instance variables

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


       eventName_list.add("Plan It!");
        eventName_list.add("Deckstro");
        eventName_list.add("Routage Intelligent");
        eventName_list.add("Deflexion");



        eventDetails_list.add("Planning and Drafting is an aesthetic work of a Civil Engineer. Challenge your skill with speed and accuracy.\n" +
                "\n\n" +
                "Structure:\n\n" +
                "Round 1: Teams will be provided with problem statement. Participants have to prepare 2-D plan and elevation in stipulated time using AUTO CADD 2010.\n" +
                "Round 2: 2-D plan has to be converted into 3-D plan using AUTO CADD 2010.\n" +
                "\n\n" +
                "Rules & regulation: \n\n" +
                "The team should not consist of more than 2 members. \n" +
                "AUTO CADD 2010 version will be provided at the time of event, if team wants to use higher version of AUTO CADD then they will have to bring their own facility.\n" +
                "Level 1 will be judged on the basis of principles of planning and time consumed by the team.\n" +
                "Level 2 will be judged on the basis of elegance of structure and time consumed by team. Both levels carry equal scores.\n" +
                "8. Any misbehavior of participant during event may lead to disqualification.\n" +
                "Participants must be present during the event at reporting time to avoid disqualification.\n" +
                "Use of mobile internet is strictly prohibited during the event.\n" +
                "In case of any disputes, final wording will remain with the organizers. Decision of the judges is final.\n");


        eventDetails_list.add(
                "Apply your structural engineering skills and knowledge to build bridge from pop sticks. Motto of this event is experiencing structural laws on model for technical enhancement of the participants.\n\n" +
                "Structure:\n\n" +
                "Round 1: Qualifying Round: Bridge must fulfill dimensions criteria as given. \n" +
                "Round 2: Dynamic Load Testing: A moving load of 15 kg will be pass through bridge, any crack during testing will be considered as failure of structure.\n" +
                "Round 3: Static Load Testing: Bridge will tested with gradually increasing static load through C.G. of structure until it fails.\n\n" +
                "Dimensions of Bridge:\n\n" +
                "Height above deck level = 15cm\n" +
                "Thickness of deck           = 3cm\n" +
                "Inner width of deck        = 15cm\n" +
                "Outer width if deck        =  18 cm\n" +
                "Span of bridge                =  60cm\n" +
                "Abutment size                = 5 cm \n\n"+
                    "Rules & regulation: \n\n" +
                "\n" +
                "1. The team should not consist of more than 3members. \n" +
                "2. Dimensions of the bridge should be same as given in above schedule.\n" +
                "3. Material used for bridge making should be wooden popsicles (ice-cream sticks) and fevicol only.\n" +
                "4.Use of any other adhesive material and bridge component leads to disqualification of team.\n" +
                "5. Judging criteria will be strength to self-weight of bridge ratio.\n" +
                "6. Bridge will be tested with dynamic load of 15kg and static loading at C.G of bridge upto appearance of first crack in bridge.\n" +
                "7. Bridge not meeting qualifying criteria will be disqualified.\n" +
                "8. Every participant should have his college ID card or at least one proof of identity.\n" +
                "9. All the teams are required to bring the registration receipt with them.\n" +
                "10. In case of any inconvenience, participants are required to seek one of volunteers present.\n" +
                "11. Any misbehavior of participant during event/rounds may lead to disqualification.\n" +
                "12. Participants must be present during the event at the specified time. Absent participants will be automatically disqualified.\n");


        eventDetails_list.add(
                "Solid waste management is important feature of a smart city. In Life cycle cost analysis of solid waste management system, “Collection of Resources” plays very important role. Hence for efficient and effective SWM system, designers as well as ULB (Urban Local Body) administration must aware of these aspect. In this event participant are provided with necessary data for design of collection system and vehicle routing.\n" +
                "\n\n" +
                "Structure:\n\n" +
                "Round 1: Design of collection system.\n" +
                "Round 2: Vehicle routing for given locality.\n\n Rules and Regulations:\n\n" +
                "Participating team will be of 2 members.\n" +
                "All Participants are required to report 30 min before the event to the reporting desk.\n" +
                "Winner will be decided by aggregate score of Round 1 and Round 2; both level carry equal marks.\n" +
                "Optimum number of trucks and minimum routing time will be the judging criteria.\n" +
                "Vehicle routing should be done according to EPA published 11 Rules for Heuristic routing.\n" +
                "Every participant should have his college ID card or at least one proof of identity.\n" +
                "Any misbehavior of participant during event/rounds may lead to disqualification.\n" +
                "Participants must be present during the event at the specified time. Absent participants will be automatically disqualified.\n" +
                "You are not allowed to use mobile internet simultaneously during the event.\n" +
                "In case of any disputes, final wording will remain with the organizers. Decision of the judges is final.\n");


        eventDetails_list.add(
                "Anchor your analyzing skills by analyzing real structures.\n" +
                "Structure:\n\n" +
                "Round 1: Participants have to analyze problem statement related to actual structure in order to determine Shear Force Diagram (S.F.D.), Bending Moment Diagram (B.M.D.), and Elastic Deflection Curve (E.D.C.)\n" +
                "Round 2: (Before commencement of round 2, a 4 hours workshop on STADD pro will be conducted for all the participants) Team has to model the same structure which was given in round 1 on STADD pro and analyze it.  \n\n" +
                "Rules and Regulations:\n\n" +
                "Participating team will be of 2 members.\n" +
                "All Participants are required to report 30 min before the event to the reporting desk.\n" +
                "Evaluation will be done on results obtained by manual calculations and STADD pro analysis.\n" +
                "Performance will be judged on the basis of time and accuracy.\n" +
                "STADD pro facility will be provided by organizers.\n" +
                "Teams participating to this event will be eligible for the both levels.\n" +
                "All the teams are required to bring the registration receipt with them.\n" +
                "Participants must be present for both the rounds. Absent participants will be disqualified.\n" +
                "Use of mobile internet is strictly prohibited during the event.\n");


        eventLocation.add("Front of Civil Dept");
        eventLocation.add("Behind Classroom Comlex");
        eventLocation.add("DH-1 Civil Dept");
        eventLocation.add("Civil Dept");


        eventContactPerson_list.add("Dhruti Bawaskar");
        eventContactPerson_list.add("Prakash Taksal");
        eventContactPerson_list.add("Pushpraj Patil");
        eventContactPerson_list.add("Sohail Shaikh");


        eventContactNum_list.add("9923147750");
        eventContactNum_list.add("770921434");
        eventContactNum_list.add("9890116736");
        eventContactNum_list.add("959549598");


        eventDate.add("5 OCT 2017");
        eventDate.add("6 OCT 2017");
        eventDate.add("6 OCT 2017");
        eventDate.add("6 OCT 2017");


        eventprice.add("50");
        eventprice.add("150");
        eventprice.add("100");
        eventprice.add("100");


        group_limit.add("2");
        group_limit.add("3");
        group_limit.add("2");
        group_limit.add("2");


        time_list.add("6th Oct, 2017\n 3 pm onwards");
        time_list.add("6th Oct, 2017\n 1 pm onwards");
        time_list.add("6th Oct, 2017\n 9 am onwards");
        time_list.add("6th Oct, 2017\n 1 pm onwards");


        CivilEventAdapter ad = new CivilEventAdapter(CivilHome.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        ListView civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CivilHome.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }



}
