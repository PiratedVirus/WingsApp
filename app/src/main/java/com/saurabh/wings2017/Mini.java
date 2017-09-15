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
import android.widget.AdapterView;
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

public class Mini extends AppCompatActivity  {

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
            Toast.makeText(Mini.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Mini.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_mini);

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
         //       Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
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


        eventName_list.add("Treasure Hunt");
        eventName_list.add("FunMania");
        eventName_list.add("Lan Gaming (COD)");
        eventName_list.add("Lan Gaming (NFS)");
        eventName_list.add("Lan Gaming (CS)");
        eventName_list.add("GFAM Quiz");
        eventName_list.add("Beg, Borrow & Steal");



        eventDetails_list.add("This is a group event in which the job of individual in any group is to complete all the tasks so that the group will be eligible for the next round and in turn will win the game.\n" +
                "\n" +
                "Rules:\n\n" +
                "There are five round conducted across the span of two days.\n" +
                "Each participant will be assigned to a group of other participants.\n" +
                "Each group will change 10 participants.\n" +
                "Certain tasks will be given to each group and they have to complete them to be eligible to the next round.\n" +
                "Groups will be formed by organizers/coordinators.\n" +
                "There will be one coordinator assigned to each group.\n" +
                "Each group has to report to their respective coordinator.\n" +
                "The duration of the event will be two days 4 hours each.\n" +
                "Event will be conducted in the free space behind Civil Department.\n\n" +
                "Motto:\n To develop the camaraderie among the participants.\n\n" +
                "Rules for participants:\n\n" +
                "Fees will not be refunded.\n" +
                "Any misbehavior with other participants, organizers, coordinators or any other person will not be entertained and will lead to disqualification of the individual participant.\n" +
                "Participants will have to present before the given time otherwise they will be disqualified.\n" +
                "We may change or modify the rules and regulations at any time and will be made available, the terms indicate that you agree to such changes. Organizers reserve the right to modify, suspend, and keep the rules, tasks.\n");


        eventDetails_list.add("Here is the opportunity to show your knowledge and coding skill together. Come to event have fun with gaming and coding.\n" +
                "\n" +
                "Rounds:\n\n" +
                "Round 1 (Lan Gaming):\n" +
                "Candidate will play bike racing, they will race to finishing line. The candidates which will come at position 1st,2nd& 3rd will qualify for second round that is Picture Perfect.\n" +
                "\n" +
                "Round 2 (Mobile Photography):\n" +
                "Candidate will have use their Camera Phones to click pictures from our college campus, pictures can be Abstract, Nature, etc. Candidate should bring their own Mobile Phone with atleast 5MP Camera.\n" +
                "\n" +
                "Round 3 (TechQuiz):\n" +
                "Candidate will compete in a classic quiz competition. Question will be based on C,C++,Java, HTML, CSS,etc.\n" +
                "\n" +
                "Judging Criteria:\n\n" +
                "Participants will be judged purely on the amount of time they require to perform a given task at hand. \n" +
                "Also, in case of failure or partial success of all the participants, the portion of the task completed and time it required to do so will be taken into account. \n" +
                "Organizers reserve all the rights to make a decision and that decision will be final.\n" +
                "The scope and construct of competition is dynamic and organizers reserve the rights to change both at any time without prior notice to participants.\n");

        eventDetails_list.add("200/-per team (Team of 4)\n" +
                "50/- (single)\nRounds:  Call of Duty Modern Welfare\n" +
                "The game consists of three rounds.\n" +
                "First two rounds will be free for all\n" +
                "Third round is survival round.\n\n" +
                "Rules:\n\n" +
                "Double entries are allowed.\n" +
                "If you miss any round then you will not be allowed for next round.\n" +
                "Fees once paid will not be refundable. \n" +
                "All rights are reserved for authority/ management.\n");

        eventDetails_list.add("\nThe popular computer game NFS is here for you.\n" +
                "Need for Speed: Most Wanted is an open world racing game.\n\n" +
                "Rules for participants: \n\n" +
                "Fees will not be refunded once paid.\n" +
                "Any misbehaviour with other participants, organizers, coordinators or any other person will not be entertained and will lead to disqualification of the individual participant.\n" +
                "Participants will have to present before the given time otherwise they will be disqualified.\n" +
                "We may change or modify the rules and regulations at any time and will be made available, the terms indicate that you agree to such changes. Organizers reserve the right to modify, suspend, and keep the rules, tasks.\n");


        eventDetails_list.add("250/- 5 vs 5 (Team of 5)\n" +
                "50/- 1 vs 1 (Single)\nThis is an event where participants (Team of 5) will have to eliminate other team and vice versa in order to be eligible for the next rounds and in turn will win the competition.\n" +
                "\n" +
                "Rules & Regulations:\n\n" +
                "Game Version: Counter Strike Global Offensive\n" +
                "Players can bring their own mouse, keyboards, headphones and other peripherals to stimulate their own best playing environment (Optional).\n" +
                "\n" +
                "5 vs 5 (Team Play, 5 players per team)\n\n" +
                "Winning a round:\n\n" +
                "Team that kills all the members of the opposite team wins the round.\n" +
                "If the Terrorists successfully bomb a base, then they will win the round.\n" +
                "If the bomb is successfully diffused, then the Counter Terrorist will win the round.\n" +
                "A limited amount of time will be given to configure the system as per your wish.\n" +
                "A Pistol round will decide which map to play first by the winning team and the map in which the Pistol round will be played is randomly selected by the coordinator.\n" +
                "The losing team will decide which team gets to play Counter Terrorist/Terrorist first.\n" +
                "Team switch sides at the halfway mark (i.e. Terrorists become Counter Terrorists and Counter Terrorist become Terrorists). This information will be announced at the start of each match.\n" +
                "Boosting (stepping on top on own team player) is permitted.\n" +
                "De dust and De dust 2 are banned.\n" +
                "Hyper Sniper is banned (GSSG11 and SCAR-20).\n" +
                "In case of a tie after the regulation round ends, 6 extra rounds will be played as a tie breaker (3 rounds as Terrorist and 3 rounds as Counter Terrorist). The first team to win 4 rounds wins the game.\n" +
                "\n" +
                "1 vs 1 (1 player per team)\n\n" +
                "Player that kills the opponent wins the round.\n" +
                "A limited amount of time will be given to configure the system as per your wish.\n" +
                "A Pistol round will decide which map to play first by the winning team.\n" +
                "Hyper Sniper is banned (GSSG11 and SCAR-20).\n" +
                "\n" +
                "Rounds:\n\n" +
                "1) 5 vs 5 (Team Play, 5 players per team)\n" +
                "Total 20 rounds (Max Rounds): 10 rounds as Terrorists and 10 rounds as Counter Terrorists per team (If a team scores 11 rounds first, the match is ended immediately).\n" +
                "Victory Condition: The first team to win 11 rounds.\n" +
                "Round Time: 2 minutes.\n" +
                "In the case of a tie after regulation rounds end, 6 extra rounds will be played (3 rounds as Terrorists/ 3 rounds as Counter Terrorists).\n" +
                "Friendly Fire is On.\n" +
                "2) 1 vs 1 (1 Player per team)\n" +
                "Total 10 rounds (Max Rounds): If a player scores 6 first, the match is ended immediately.\n" +
                "Victory Condition: The first player to win 6 rounds.\n\n" +
                "Round Time: 1 minute 30 seconds.\n" +
                "In the case of a tie after regulation rounds end, 3 extra rounds will be played.\n\n" +
                "General Settings:\n\n" +
                "Start Money: $1000/-\n" +
                "Spectate is off, fade to block is on.\n" +
                "C4 Timer: 45 seconds\n" +
                "Freeze Time: 6 seconds\n" +
                "Buy Time: 25 seconds\n" +
                "Friendly Fire: On\n" +
                "\n" +
                "Disconnections:\n\n" +
                "Any disconnection of the connection between match players due to System, Network, PC and/or Power problem issues.\n" +
                "Intentional: Upon judgement by the referee, any offending player will be charged with a loss by forfeit.\n" +
                "Unintentional: If the disconnection is to be unintentional by the referee, the match will be restarted. If any player does not agree to a match restart, the player will lose by default.\n\n" +
                "Note:\n\n" +
                "As the rounds held are as per knockout matches, therefore the losing team will not be allowed to re-enter in the event.\n");


        eventDetails_list.add("GFAM = Game of Thrones. Friends, Anime, Movies \n" +
                "It will test your knowledge on the most watched TV series through various rounds including aptitude tests and duels. Participants have to choose their own series out of the allotted ones and multiple selections are allowed however each round will be having separate payments.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should arrive at the venue at least 15 minutes before the start of the event.\n" +
                "Each and every team member must be present during the event.\n" +
                "Team: Minimum 1 and maximum 2 members per team.\n" +
                "Use of Internet or other means of communication is strictly prohibited.\n" +
                "Once a team is registered, same team members should be carried till the end.\n");

        eventDetails_list.add("50/- (per participant)\n" +
                "100/-(for team of 3)\n Fun event.\n" +
                "Rules:\n" +
                "Each member can participate individually .\n" +
                "If Team is participating, then it  should consist of maximum 3 members.\n" +
                "Final Introduction and instruction will be provided by organizing team on time.\n" +
                "The decision of organizers will be final. \n" +
                "Participant or team cannot seek help of others, if found in such case team will be debarred.\n\n" +
                "Round 1:\n\n" +
                "Task 1 –\n" +
                "Participants will be given list of items, participants have to collect the given items in the given time.\n" +
                "\n" +
                "Task – 2 \n\n" +
                "Participants Presence of mind will be judged on the basis of given condition, to see how JUGAD you can do \n" +
                "Round 2: Surprise round.\n");

        eventLocation.add("Civil Ground");
        eventLocation.add("MCA Dept, TR");
        eventLocation.add("MCA Dept, Lab 2");
        eventLocation.add("Computer Science Dept (CC Lab)");
        eventLocation.add("Programming Fundamentals Lab, IT Dept");
        eventLocation.add("Classroom Complex");
        eventLocation.add("Electrical Department");

        eventContactPerson_list.add("Darshan Chobarkar");
        eventContactPerson_list.add("Karamjeet Singh");
        eventContactPerson_list.add("Vivek Yeljale");
        eventContactPerson_list.add("Kshitij Choudhari");
        eventContactPerson_list.add("Abhinay Koreti");
        eventContactPerson_list.add("Vaibhav Thorat");
        eventContactPerson_list.add("Kaustubh kangale");

        eventContactNum_list.add("9404168827");
        eventContactNum_list.add("8087141667");
        eventContactNum_list.add("7385424799");
        eventContactNum_list.add("9765256110");
        eventContactNum_list.add("9422568063");
        eventContactNum_list.add("8087735739");
        eventContactNum_list.add("8857838327");

        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");

        eventprice.add("50");
        eventprice.add("30");
        eventprice.add("200");
        eventprice.add("40");
        eventprice.add("250");
        eventprice.add("50");
        eventprice.add("100");

        group_limit.add("0");
        group_limit.add("0");
        group_limit.add("4");
        group_limit.add("0");
        group_limit.add("5");
        group_limit.add("2");
        group_limit.add("3");

        time_list.add("5 OCT, 2017 \n 2 pm onwards");
        time_list.add("5 OCT, 2017 \n 2 pm onwards");
        time_list.add("5 OCT, 2017 \n 1 pm onwards");
        time_list.add("5 OCT, 2017 \n 2 pm onwards");
        time_list.add("5 OCT, 2017 \n 1 pm onwards");
        time_list.add("5 OCT, 2017 \n 1.30 pm onwards");
        time_list.add("5 OCT, 2017 \n 2.30 pm onwards");




        MiniAdapter ad = new MiniAdapter(Mini.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);

        civil.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // For Long Duration Toast
                TextView  name = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventName);
                TextView location = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventLocation);
                TextView desc = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventExcerpt);
                TextView price = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventPrice);
                TextView date = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventDate);
                TextView person_name = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactPerson);
                TextView person_num = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactNum);


                Toast.makeText(getApplicationContext(), name.getText().toString() , Toast.LENGTH_LONG).show();

                Intent eventi = new Intent(Mini.this, GenericEventHome.class);
                eventi.putExtra("name", name.getText().toString());
                eventi.putExtra("location",location.getText().toString());
                eventi.putExtra("desc",desc.getText().toString());
                eventi.putExtra("price",price.getText().toString());
                eventi.putExtra("date",date.getText().toString());
                eventi.putExtra("person_name",person_name.getText().toString());
                eventi.putExtra("person_num",person_num.getText().toString());

                startActivity(eventi);
                finish();
                // For Long Short Toast


            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Mini.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}
