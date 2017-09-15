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

public class Volt extends AppCompatActivity  {

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
            Toast.makeText(Volt.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Volt.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_volt);

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


        eventName_list.add("Technobuzz");
        eventName_list.add("Electroma");
        eventName_list.add("Tantradyana");
        eventName_list.add("Battle of Microcontroller");



        eventDetails_list.add("Technobuzz is a technical event which will test participant’s regular study skill in the electronics field. It is a three round team event taken in two groups: junior group and senior group.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should arrive at the venue at least 15 minutes before the start of the event.\n" +
                "Each and every team member must be present during the event.\n" +
                "Team: Minimum 1 and maximum 2 members per team.\n\n" +
                "Use of Internet or other means of communication is strictly prohibited.\n" +
                "Once a team is registered, same team members should be carried till the end.\n");

        eventDetails_list.add("To increase the knowledge of circuit designing.\n" +
                "Rules:\n\n" +
                "Each member can participate individually.\n" +
                "Participating team should consist of maximum 2 members.\n" +
                "All required components for the 2nd round will be provided by organizing team on time.\n" +
                "The decision of organizers will be final. \n" +
                "The event is only for polytechnic, 1st& 2nd year engineering students.\n" +
                "Use of calculator is allowed.\n" +
                "\n" +
                "DAY ONE:\n\n" +
                "ROUND 1-MCQ TEST\n" +
                "Test comprising of 50 questions(25 general aptitude+25 technical)\n" +
                "DURATION-45 MIN.\n" +
                "Organizing committee will convey message to the shortlisted teams.\n" +
                "\n" +
                "DAY TWO:\n\n" +
                "ROUND 2-CIRCUIT FIXER\n" +
                "The task will be given to each team either designing circuit or fault finding.\n" +
                "Teams will be evaluated on the basis of output of the circuit accuracy and time.\n" +
                "Result of round 2 will be declared within an hour.\n\n" +
                "\n" +
                "ROUND 3-RAPID FIRE QUESTIONS (1 minute)\n");


        eventDetails_list.add(" Hunt for the most innovative and creative ideas\n" +
                "  \n" +
                "Rules:\n\n" +
                "Team should consist of max 3 members.\n" +
                "Each member can participate in only 1 team.\n" +
                "Models must be purely core based on electrical n electronics.\n" +
                "No robo’s allowed.\n" +
                "Models based on renewable energy technology such as Arduino, automation and any innovative ideas are highly encouraged.\n" +
                "Maximum time limit for the power point presentation is 10 minutes.\n" +
                "Chemicals will not be allowed. Safety is top priority while performing the models.\n" +
                "Final instructions will be given by the organizers at the beginning of each round.\n" +
                "The decision of judges will be final.\n\n" +
                "Rounds:\n" +
                "Round 1: power point presentation on your respective models\n" +
                "Round 2: demonstration of your working model.\n" +
                "\n" +
                "Note:\n\n" +
                "Separate time will be provided for model setup before the start of demonstration.\n");


        eventDetails_list.add("Battle of Microcontroller based projects will be kept in exhibition where professors will judge and rate the projects.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should arrive at the venue at least 15 minutes before the start of the event.\n" +
                "Each and every team member must be present during the event.\n\n" +
                "Team: Minimum 2 and maximum 4 members per team.\n\n" +
                "Once a team is registered, same team members should be carried till the end.\n" +
                "Project should be feasible, innovative and application based.\n" +
                "Projects will be limited to only Microcontroller family(8051, Raspberry pi etc.)\n");

        eventLocation.add("Computer Lab, E&TC Dept");
        eventLocation.add("Electrical Dept");
        eventLocation.add("Electrical Dept");
        eventLocation.add("Computer Lab, E&TC Dept");




        eventContactPerson_list.add("Shubham Pardeshi");
        eventContactPerson_list.add("Rushabh Patil");
        eventContactPerson_list.add("Sarang Suryawanshi");
        eventContactPerson_list.add("Trupti Hagawane");


        eventContactNum_list.add("9552894182");
        eventContactNum_list.add("9421573616");
        eventContactNum_list.add("9764635669");
        eventContactNum_list.add("9890749405");



        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("6 OCT 2017");



        eventprice.add("50");
        eventprice.add("60");
        eventprice.add("150");
        eventprice.add("50");



        group_limit.add("2");
        group_limit.add("2");
        group_limit.add("3");
        group_limit.add("4");

        time_list.add("5 OCT 2017, 4 pm onwards");
        time_list.add("5 OCT 2017, 1.30 pm onwards");
        time_list.add("5 OCT 2017, 4.25 pm onwards");
        time_list.add("6 OCT 2017, 2 pm onwards");





        VoltAdapter ad = new VoltAdapter(Volt.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Volt.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}
