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

public class Mechanico extends AppCompatActivity  {

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
            Toast.makeText(Mechanico.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Mechanico.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_mechanico);

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
             //   Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
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


        eventName_list.add("Lathe War");
        eventName_list.add("3D-Structural");
        eventName_list.add("Cad-Ster (CATIA)");





        eventDetails_list.add("Unleash your machining skills, and let in to the groove of screeching rhythms of turning, facing, tapering and all you can do in a lathe.\n" +
                " \n" +
                "Structure: \n\n" +
                "Round 1: Quiz paper on basic of mechanical and manufacturing process \n" +
                "\n" +
                "Round 2: Pattern Job (Simple Job) \n" +
                "\n" +
                "Round 3: Making the given job on lathe.\n" +
                " \n" +
                "Rules and Regulations: \n\n" +
                "1. Participating team will be of 3 members. \n" +
                "2. All the participants should carry workshop aprons and shoes. \n" +
                "3. Experience of working with lathe machines is required. \n" +
                "4. Not more than 2 tools will be provided. \n" +
                "5. No extra work piece will be provided and carrying of extra material is banned. \n" +
                "6. All Participants are required to report 30 min before the event starts, to the reporting desk. \n" +
                "10. Any misbehavior of participant during event may lead to disqualification. \n" +
                "11. Participants must be present during the event at the specified time to avoid disqualification.\n" +
                "14. You are not allowed to use mobile internet simultaneously during the event. \n" +
                "15. In case of any disputes, final wording will remain with the organizers. Decision of the judges is final. \n" +
                "\n" +
                "Venue: Workshop Mechanical Dept. \n");

        eventDetails_list.add("Creativity of modeling of an engineer is tested. \n" +
                "\n" +
                "Structure: It consists of 3 Rounds: \n\n" +
                "Round 1: Aptitude test (engineering graphics and general skills). \n" +
                "\n" +
                "Round 2: This round will consist of 2 levels viz. \n" +
                "\n" +
                "        Level 1: Buzzer Round related to Engineering Graphics and reasoning. \n" +
                "        Level 2: To create 3D model using given Isometric views. \n" +
                "\n" +
                "Round 3: To create working mechanism within given specifications. \n" +
                "\n" +
                "Procedure: Every round is time bound and the selection depends on the accuracy of the model made as per given specification. \n" +
                "\n" +
                "Rules & Regulations: \n\n" +
                "Participant must bring receipt at time of competition. \n" +
                "Maximum 3 members in a group. \n" +
                "Participants should be acquainted with Engineering Graphics. \n" +
                "Participants should bring basic elements to construct model. \n" +
                "Event coordinators reserve the right of changing or cancellation of any above mentioned rules before or during the event. \n" +
                "The participants are expected to be present at the venue before the event commences. \n" +
                "Items needed for competition will be provided at the time of event. \n");


        eventDetails_list.add("The competition is to test how fast one interprets the given problem using CATIA.\n" +
                " \n" +
                "Objectives:\n\n" +
                "1. Identify core CATIA concepts \n" +
                "2. Navigate the main CATIA tools and features \n" +
                "3. Preview, open, and orient Design model \n" +
                "4. Manage CATIA memory and directories \n" +
                "\n" +
                "Structure:\n\n" +
                "1. Round 1: Quiz based on CATIA software & general skills. \n" +
                "\n" +
                "2. Round 2: Part Design \n" +
                "\n" +
                "3. Round 3: Assembly of component \n" +
                "\n" +
                "Rules:- \n\n" +
                "1. Participant must bring receipt at time of competition. \n" +
                "2. Judge’s decision will be final & binding on all. \n" +
                "3. Event coordinators reserve the right of changing or cancellation of any above mentioned rules before or during the event. \n" +
                "4. You are not allowed to use mobile or internet during event \n" +
                "5. The participants are expected to be present at the venue before the event commences.\n");


        eventLocation.add("Workshop Mechanical Dept");
        eventLocation.add("Mechanical Engineering Dept");
        eventLocation.add("CAME Lab (Mechanical Engg. Dept)");



        eventContactPerson_list.add("Totaram Murumkar");
        eventContactPerson_list.add("Sampada Ahale");
        eventContactPerson_list.add("Dhanshri Bansule");



        eventContactNum_list.add("8482930057 ");
        eventContactNum_list.add("9850954067");
        eventContactNum_list.add("9765614935");



        eventDate.add("4 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("4 OCT 2017");



        eventprice.add("100");
        eventprice.add("100");
        eventprice.add("50");



        group_limit.add("3");
        group_limit.add("0");
        group_limit.add("0");





        MechanicoAdapter ad = new MechanicoAdapter(Mechanico.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Mechanico.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
