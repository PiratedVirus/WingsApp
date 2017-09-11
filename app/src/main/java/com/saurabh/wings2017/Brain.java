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


        eventName_list.add("Virtual Campus");
        eventName_list.add("Aqualaunchia");
        eventName_list.add("Spell UR Brain");
        eventName_list.add("Murder Mystery");




        eventDetails_list.add(
                "Explore your skills with aptitude, discussion as well as interview to challenge the potential within.\n" +
                "\n\n" +
                "Rules: \n\n" +
                "One person can take part for a single entry.\t\t\t\t\n" +
                "All rounds will be taken as per the campus rules,some rules may change .\n" +
                "Time pattern is there for each round which is expected to be followed.\n" +
                "Participant are expected to give their contact details correctly, so as to informed them event and event results.\n" +
                "For each round word of judges will be final.\n" +
                "\n" +
                "Structure:\n\n" +
                "Round 1: Aptitude \n" +
                "                 On basic reasoning, logical thinking, multiple choice question will be on general studies and current events,  There will be 30-40 question for 30 to 45 minutes.\n" +
                "\n\n" +
                "Round 2: Group Discussion\n" +
                "               You will be given one topic at the instant and you have to speak on it and make good points. Only DISCUSSION no DEBATE.\n" +
                "\n\n" +
                "Round 3: Personal Interview\n" +
                "             PI will be conducted by judges and general questions will be asked.Your skills will be judged.\n");

        eventDetails_list.add("Utilize the knowledge of Engineering Mechanics and Hydrodynamics. \n" +
                "\n" +
                "Rules: \n\n" +
                "1. Team should consist of maximum three members. \n" +
                "2. Each member can participate in only team. \n" +
                "3. Team has to come with their own rocket, only fins will be provided at the event site. \n" +
                "4. Final instructions will be given by the organizers at the beginning of each round. \n" +
                "5. The decision of organizers will be final. \n" +
                "\n" +
                "Rounds: \n\n" +
                "Round 1: Water rocket must be launched for maximum range. \n" +
                "\n" +
                "Round 2: Water rocket is to be launched for particular decided range.\n" +
                " \n" +
                "Round 3: Surprise round. \n" +
                "\n" +
                "Note: \n" +
                "Only one attempt is allowed. \n");


        eventDetails_list.add("Spell UR Brain is a non-technical event which will test participant’s general knowledge and presence of mind. It is a three round team event. Each team will have 2 members per team.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should arrive at the venue at least 15 minutes before the start of the event.\n" +
                "Each and every team member must be present during the event.\n" +
                "Team: 2 members per team.\n" +
                "Use of Internet or other means of communication is strictly prohibited.\n" +
                "Once a team is registered, same team members should be carried till the end.\n" +
                "\n" +
                "Event Details:\n\n" +
                "Spell UR Brain will be a three round event. First round will be based on grammar. Second round is related to GK. Third and final is surprise round. Three teams will be rewarded 1st, 2nd and 3rd prize.\n");

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


        eventLocation.add("Classroom Complex");
        eventLocation.add("Main playground, GECA.");
        eventLocation.add("IT Dept");
        eventLocation.add("Front of CC");


        eventContactPerson_list.add("Vyankatesh Kulkarni");
        eventContactPerson_list.add("Apoorva Rahatkar");
        eventContactPerson_list.add("Siddhi Sharma");
        eventContactPerson_list.add("Madhura Tayade");


        eventContactNum_list.add("9527500921");
        eventContactNum_list.add("9049313795");
        eventContactNum_list.add("8600042536");
        eventContactNum_list.add("9975491679");


        eventDate.add("4 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("4 OCT 2017");
        eventDate.add("6 OCT 2017");


        eventprice.add("50");
        eventprice.add("100");
        eventprice.add("50");
        eventprice.add("50");


        group_limit.add("0");
        group_limit.add("3");
        group_limit.add("2");
        group_limit.add("0");




        BrainAdapter ad = new BrainAdapter(Brain.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Brain.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
