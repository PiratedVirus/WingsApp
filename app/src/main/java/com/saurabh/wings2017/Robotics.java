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

public class Robotics extends AppCompatActivity  {

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
    ArrayList<String> eventtime = new ArrayList<>();



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
            Toast.makeText(Robotics.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Robotics.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_robotics);

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
            //    Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
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


        eventName_list.add(" Pick N Place");
        eventName_list.add("Robo Race");



        eventDetails_list.add("Pick n Place is a technical event which will test participant’s robotic skill in the electronics field.\n" +
                "It is a three round team event. Each team will have minimum 2 and maximum of 4 members per team. \n" +
                "\n" +
                "Procedure:\n\n" +
                "In Pick n Place, The bot is required to pick the object and place, at their respective places and the team will get the points accordingly. Points depends on the type of the object picked and it will have its specific place to be placed. There will be 2 types of objects with points 5.10. Small objects will carry 5 points each which will be awarded when picked up and dropped in respective barrels. After each barrel is filled, the bot can approach the object having 10 points. Dropping the bigger object into the final hoop will award 10 points and finish the turn.\n" +
                "Problem Statement:\n" +
                "2 spaceships from two planets AP and BP respectively got lost while on their space research. So to get their astronauts back, they have to approach another planet C to rescue them.\n" +
                "You belong to Planet C and you have to launch your spaceship to rescue them.\n" +
                "No. of Rounds: 2\n" +
                "\n" +
                "Duration: All Day.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should arrive at the venue at least 15 minutes before the start of the event.\n" +
                "Each and every team member must be present during the event.\n" +
                "Team: Minimum 2 and maximum 4 members per team.\n" +
                "Use of Internet or other means of communication is strictly prohibited.\n" +
                "Once a team is registered, same team members should be carried till the end.\n");


        eventDetails_list.add("Provide a platform for students to showcase their creative and innovative skills in problem solving. \n" +
                "\n" +
                "Rules & Regulations: \n\n" +
                "Task: \n" +
                "1. The robots build by given dimension and specification will be allowed for game. 2. The event will conduct in three rounds. 3. Rounds will declare on the spot. 4. The robot which gains maximum points will be the winner. 5. Track will be declared before 10 days of the event. \n" +
                "\n" +
                "Rules: \n\n" +
                "1. The team should not consist of more than 4 members. \n" +
                "2. Each member from same college is not mandatory. \n" +
                "3. The robot should follow the robot specifications provided. Any deviation from the mentioned specifications will lead to disqualification. \n" +
                "4. Once the race begins, three hand touches are allowed, if you are using hand touch, you will have to start from last check point. \n" +
                "5. No test practice will be allowed on the main arena. \n" +
                "6. Terminals for charging the battery will not be provided in the college. \n" +
                "7. The arena may subject to change before the commencement of any round. \n" +
                "8. If the Robot crosses a checkpoint, and moves off track, then the Robot would be placed back on the previous checkpoint crossed. \n" +
                "9. The decision of the judges will be final and abiding. Argument with judges in any form will lead to the disqualification of the team. \n" +
                "\n" +
                "Robot specifications:\n\n" +
                "1. The L X B X H should not exceed 25 X 25 X 25. \n" +
                "2. The net weight should not exceed 3 kgs. \n" +
                "3. However a tolerance of 5% is acceptable. \n");


        eventLocation.add("Front of E&TC Dept");
        eventLocation.add("Basketball Court");


        eventContactPerson_list.add("Yashodeep Kacholiya");
        eventContactPerson_list.add("Prabhat Shahare");


        eventContactNum_list.add("8275861835");
        eventContactNum_list.add("9405248469");


        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");


        eventprice.add("200");
        eventprice.add("250");


        group_limit.add("4");
        group_limit.add("4");

        eventtime.add("5th Oct, 2017\n 2 pm onwards");
        eventtime.add("5th Oct, 2017\n 1 pm onwards");





        RoboticsAdapter ad = new RoboticsAdapter(Robotics.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, eventtime);
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

                Intent eventi = new Intent(Robotics.this, GenericEventHome.class);
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
        Intent i = new Intent(Robotics.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}