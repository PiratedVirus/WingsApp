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
           //     Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
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


        eventName_list.add("Code Junkie 4.0");
        eventName_list.add("Codesprint 3.0");
        eventName_list.add("Game of Codes");
        eventName_list.add("C-Ladder");
        eventName_list.add("Web Sutra");
        eventName_list.add("Hi-Tech Fair");


        eventDetails_list.add("30/- Single\n"  +
                "50/- Team of two\nThis is an event where the participants participate in team of 2 or as individual and face the different Aptitude questions related to programming languages, logical round and coding round. Anyone who clears all the rounds with highest points will be the winner.\n" +
                "\n" +
                "Rules:\n\n" +
                "There are 3 rounds conducted across the span of 2 days.\n" +
                "Each group will contain 2 members.\n" +
                "No language barrier.\n" +
                "Certain tasks will be given to each group, they have to complete them to be eligible for next round.\n" +
                "Organizers reserves right to modify, change or suspend rules and regulations at any time, if required\n" +
                "Decision of the organizers will be finals.\n" +
                "Any misbehaver with other participants, coordinators or organizers will not be entertained and will lead to immediate disqualification.\n\n" +
                "Rounds:\n\n" +
                "Round 1: Aptitude.\n" +
                "The round will be of 45-55 questions (Pen and paper round). There will be separate question paper for 1st year students.\n" +
                "\n" +
                "Round 2: Logical rounds\n" +
                "       There will be 3 separate stages.\n" +
                "Buzzer round: There will be series of question which will be answered by team which will press the buzzer first.\n" +
                "Code Hunt: A fun game based on coding and logical skills of participant.\n" +
                "White board Coding: Live coding on white board similar to which takes place in companies like Google, etc.\n" +
                "\n" +
                "Round 3: Surprise round\n" +
                "         This will be surprise for participants but based on pure competitive coding.\n");

        eventDetails_list.add("30/- Single\n"  +
                "50/- Team of two\nThe objective of this event is to test the students’ knowledge in basic C Programming. This event is for beginners and intermediates, and will try to cover general aspects of the basic programming languages.\n" +
                "\n" +
                "Rules:\n\n The event consist of 3 rounds .\n" +
                "Only one paper will be provided for team. \n" +
                "For wrong answer there will be a negative score. \n" +
                "Co-coordinators decision will be final. \n" +
                "The decision of judges will be final and binding to all. \n" +
                "Rules may be changed without prior intimation. Participants are requested to check the website for updates In case any assistance is needed during the event, the teams should approach only the organizer.\n" +
                "Time slots will be given and participants are expected to follow it strictly.\n" +
                "All the queries regarding any syntax will be resolved according to C standards. \n" +
                "Participants found discussing with other teams will be disqualified. \n" +
                "\n" +
                "Structure: \n\n" +
                "Round I:  Aptitude test.\n" +
                "It consists of questions related to every technical aspect of C language.\n" +
                "Round 1 will be held in IT department.\n" +
                "Negative marking will be used.\n" +
                "Time may vary according to the number of questions.\n" +
                "\n" +
                "Round II: Intermediate sprint round \n" +
                "In this round your teamwork and speed will be tested.\n" +
                "8-10 small problem statements will be given and you need to create a code on machine and execute it. \n" +
                "Marking System will be declared at time round is conducted. \n" +
                "Compiler: Codeblocks/turboc Programming language: C,C++\n" +
                "Time allotment for round will be around 1-1.25 hour\t\n" +
                "Round III: Swap your mind \n" +
                "Let’s check how you perform and analyze partner’s brain. 2 medium-hard level problem statements will be given and you need to write code on machine and execute it. Marking System will be declared at time round is conducted. \n" +
                "Compiler: Codeblocks/turboc Programming language: C ,C++\n" +
                "Time allotment for round will be around 1-1.25 hrs.\n");


        eventDetails_list.add("30/- Single\n"  +
                "50/- Team of two\nTest Your Programming Skill, Its a Debugging Contest, Let’s Experience the world of Competitors in Designing The Best Algorithm.\n" +
                "\n" +
                "Rules:\n\n" +
                "Team should be consists of maximum 2 members.\t\t\t\t\n" +
                "In the event if  there is online misuse of Internet,it will lead to Disqualification.\n" +
                "Time slots will be allotted  and participant are expected to follow them strictly.\n" +
                "Participant are expected to give their contact details correctly, so as to informed them event and event results.\n" +
                "Rules may be changed without prior intimation. \n" +
                "\n" +
                "Structure:\n\n" +
                "Round 1: Quiz.\n" +
                "This round will have a quiz based on basic programming, logical thinking, multiple choicequestions on error finding, language will be in C, C++, Java. There will be 30-40 questions.\n" +
                "Rounds 2: Debugging. \n" +
                "You will be given problem statements with program but the code contents error. Find out error and submit the correct code. Language will be in C,C++,and java .\n");

        eventDetails_list.add("To test and improve computer knowledge and programming skills.\n" +
                "\n" +
                "Event Details:\n\n" +
                "\tIt is a traditional c ladder game with one to roll a dice and one to play a game with more fun and tricks. Life line there will be to safe guard.\n" +
                "\n" +
                "Description:\n\n" +
                "Round 1:\n" +
                "It consists of 4 teams of 2 candidate per team, one to roll dice and one to play the game. At every successful move according to place he/she have to face the task. Life-line will be there if needed. Winners will be shortlisted for the next semifinal round of c ladder.\n" +
                "\n" +
                "Round 2: \n" +
                "In this round the shortlisted teams will plays the same game and the winners will be shortlisted. At the end of day only 4 teams will be selected for final.\n" +
                "\n" +
                "Round 3:\n" +
                "The 3rd round is a final round of this game in which the 4 finalist play C Ladder the winner will be the final winner of the game\n" +
                "\n" +
                "Rules:\n\n" +
                "Every round will have teams of 2 candidates.\n" +
                "Double entries are not allowed.\n" +
                "If you miss any round then you will not be allowed for next round.\n" +
                "Entry Fees once paid will not be refundable. \n" +
                "All rights are reserved for authority/ management team.\n" +
                "Any candidate or team cannot use any electronic device throughout the game.\n" +
                "Team can swap partner only 1 time.\n" +
                "Judge’s decision will be the final decision.\n" +
                "\n" +
                "Judging Criteria:\n" +
                "\tAt every round performance of every group will be considered qualification criteria should be passed.\n");

        eventDetails_list.add("This is an event which tests participants Entrepreneurial, Management and Web-Development skills. The event will have 2 rounds. Participation will be individual but team of 2 will be formed on the spot. Each participant will be given an abstract idea based on which team will be formed. \n" +
                "\n" +
                "Rules:\n\n" +
                "Round 1: Each team will have to present an idea based on the abstract topic.\n" +
                "Round 2: Each team will have to build a website based on the idea.\n" +
                "\n" +
                "Rules for participants:\n\n" +
                "Participant must be present at the venue 15 min prior of the event.\n" +
                "Precooked code/ Templates not allowed.\n" +
                "Once teams formed can’t be changed.\n" +
                "Organizer reserves the right to modify, change or suspend rules and regulations at any time if required. \n" +
                "Decisions of the organizers will be final.\n" +
                "Any misbehaviour with the other participants, coordinators or organizers will not be entertained and will lead to immediate disqualification.\n" +
                "Judging criteria:\n" +
                "Creativity and idea \n" +
                "Coding skills\n" +
                "Design\n");

        eventDetails_list.add("Hi tech fair is the web programming contest for participants. A small web page is to create in given time. You can use the HTML, CSS in it. \n" +
                "\n" +
                "Description:  It has 3 rounds. \n\n" +
                "1) Web Puzzle\n" +
                "2) Web Page Design\n" +
                "3) Surprise round\n\n" +
                "Round 1:\n" +
                "After solving the puzzle candidates will be eligible for 2nd round.\n" +
                "Round 2:\n" +
                "In this round participants will design web page.\n" +
                "After judging these web pages panelist will select candidates for next round.\n" +
                "Round 3:\n" +
                "In this round we will ask some questions to candidates.  And if candidate knows the   answer of the questions then he/she has blown the buzzer and answer the question.\n" +
                "            Note:-1 Negative marking will be applied. \n\n" +
                "Specifications:\n" +
                "1. Individual entry is mandatory.\n" +
                "2. Taking the help form another and use of any electronic device are restricted.\n" +
                "3. Anyone found who will be doing cheating will be immediately disqualified and registration will be canceled.\n" +
                "4. All rights are reserved for authority/ management.\n" +
                "5. Fees once paid will not refundable \n" +
                "\n" +
                "Testing Conditions:\n\n" +
                "Logical thinking, web designing skills, presence of mind, presentational skill.\n" +
                "\n" +
                "Judging Criteria:\n\n" +
                "Participants will be judged purely on the amount of time taken to complete given task.\n" +
                "Also, in case of failure or partial success of all the participants, the portion of the task completed and time it required to do so will be taken into account. \n" +
                "c.    Judging panel reserve all the rights to make a decision and that decision will be final.\n" +
                "d.   The scope and construct of competition is dynamic and organizers reserve the rights to change both at any time without prior notice to participants.\n");

        eventLocation.add("Classroom 12 CSE Dept");
        eventLocation.add("IT Dept");
        eventLocation.add("Project Lab, IT Dept");
        eventLocation.add("Near Coffee Shop");
        eventLocation.add("Computer Science Dept");
        eventLocation.add("MCA Lab");

        eventContactPerson_list.add("Aniket Kulkarni");
        eventContactPerson_list.add("Pawan Kadadi");
        eventContactPerson_list.add("Anand Ingle");
        eventContactPerson_list.add("Rajkumar Andhale");
        eventContactPerson_list.add("Abhishek Raut");
        eventContactPerson_list.add("Sachin Jadhav");

        eventContactNum_list.add("9881477360");
        eventContactNum_list.add("8421348474");
        eventContactNum_list.add("8668723196");
        eventContactNum_list.add("9011159596");
        eventContactNum_list.add("9096795298");
        eventContactNum_list.add("7798968772");

        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");

        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("50");

        group_limit.add("2");
        group_limit.add("2");
        group_limit.add("2");
        group_limit.add("2");
        group_limit.add("0");
        group_limit.add("0");

        time_list.add("5 OCT, 2017\n 4 pm onwards");
        time_list.add("5 OCT, 2017\n 1 pm onwards");
        time_list.add("5 OCT, 2017\n 1.30 pm onwards");
        time_list.add("5 OCT, 2017\n 2 pm onwards");
        time_list.add("5 OCT, 2017\n 4 pm onwards");
        time_list.add("5 OCT, 2017\n 1.30 pm onwards");






        CodeAdapter ad = new CodeAdapter(Code.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        ListView civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Code.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}
