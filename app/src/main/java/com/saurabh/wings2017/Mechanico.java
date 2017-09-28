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
        eventName_list.add("3D Structial");
        eventName_list.add("Cad-Ster (CATIA)");
        eventName_list.add("Aqualaunchia");
        eventName_list.add("Nitro Racers");





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

        eventDetails_list.add("Objective:\n" +
                "This event involves the fabrication, testing and racing of a wireless\n" +
                "Remote controlled, 4 wheeled, IC engine powered vehicle.\n" +
                "\n" +
                "Structure:\n\n" +
                "Round 1: Qualification Round:\n" +
                "This round will consist of time trials. Two laps are to be completed one-by-one.\n" +
                "The best lap time will be considered and relative ranking will be done accordingly.\n" +
                "Round 2: Elimination Round:\n" +
                "The teams which are qualified for second round will have to race with each other on modified track.\n" +
                "Round 3: Final Round:\n" +
                "The teams which are shortlisted from 2nd round will have to race with each other on modified track.\n" +
                "\n" +
                "Rules:\n" +
                "1. Each team can comprise of a minimum of two members and a maximum of five members.\n" +
                "2. Each member must be present on specified date of inspection to ensure that the machine has been made as per given specifications.\n" +
                "3. The eligible teams will be given their practice slots. During this time, the teams can practice on the track; however, this permission is given on a first come first serve basis. So, it is advisable to secure your slot well before time.\n" +
                "4. Participants must use remote with frequency of band spectrum 2.4 GHz only. No power supply will be given on the track.\n" +
                "5. Team members are not permitted to touch either their machines or those of their opponents once the race begins (unless there is a need to lift the machine). The penalty for doing so on invalid grounds is disqualification.\n" +
                "6. Teams are prohibited from purposefully damaging the machine of the opponent team. The concerned team will be disqualified, if found guilty. \n" +
                "7. If the machine is found unsafe to run on the track, that team will be disqualified from the race. The organizers’ decision will be final and binding to all in this respect. No damage to the track will be tolerated and if found guilty, the responsible team will be disqualified.\n" +
                "8. The vehicles are not allowed to leave any loose parts or any extra part on any part of the arena.\n" +
                "9. Any vehicle disintegrating during the race will be disqualified. The vehicle must remain intact throughout the race. Once the race gets started, the participants must complete the race.\n" +
                "10. The track will have checkpoints at regular intervals. If the machine halts, tumbles or goes off the arena at any point, one of the team members is allowed to lift it up and place it at the nearest completed checkpoint. However, the timer will still be running, in the meantime.\n" +
                "11. Persons from different institutes can be a part of the same team. However, one person cannot be a part of multiple teams for the same event. Only undergraduate students may participate.\n" +
                "12. The judges' and organizers' decisions shall be treated as final and binding to all.\n" +
                "The organizers reserve the rights to change any/or all of the above rules as they deem fit.\n" +
                "\n" +
                "ARENA Details:\n" +
                "The race track will be a combination of on road and off-road tracks with many hurdles.\n" +
                "Participants are advised to use proper suspension and tires to endure the bumps.\n" +
                "Check points will be provided on track where the machine can restart, in case of a disturbance.\n" +
                "\n" +
                "Specifications:\n" +
                "1. ENGINE:\n" +
                "Maximum piston displacement allowed is 4.6 cc. The car should be powered by only one IC engine. Any machine which uses DC Motors for propulsion will be disqualified. However, DC motors and servos can be used for steering mechanisms or any other control mechanisms, apart from propulsion.\n" +
                "2. FUEL:\n" +
                "The percentage of nitro methane should not exceed 20% by volume in the fuel. Readymade fuel can be used.\n" +
                "3. VEHICLE DIMENSIONS:\n" +
                "Machine should fit in a box of dimensions 650 mm x 500 mm x 400 mm (lxbxh) at any time during the race. The external device which is used to control the machine is not included in the size constraint. Antennae are exempt from the height restriction.\n" +
                "4. PARTS THAT ARE TO BE FABRICATED BY THE PARTICIPANTS: The chassis, Suspensions system, Steering mechanism and Brakes.\n" +
                "5. The machine must not be made from Lego parts, or any ready-made assembly kits other than the parts mentioned above. Readily available chassis layouts are not allowed. Any machine found having a ready-made chassis will be disqualified.\n" +
                "6. Parts that can be directly procured from the market:\n" +
                "Gear box assembly, Differentials (if used)\n" +
                "Suspension springs, Shock absorbers\n" +
                "Tires and wheels (You are advised to use tires of good width for better performance on dirt tracks).\n" +
                "Clutch System, Brake System, Engine, Carburetor, Servo Motors, Wheel hub.\n" +
                "7. POWER SUPPLY:\n" +
                "The machine must have an on-board power supply to provide power to any mechanism requiring electric power not exceeding 12 V.\n" +
                "8. RADIO CONTROLLERS:\n" +
                "The machine must be necessarily controlled by a wireless remote-control system.\n" +
                "Note: You may use clutch mechanism between the engine and the wheel, cooling mechanism to prevent overheating of the engine and air filters as dirt might cause serious problems to the engine for better performance.\n" +
                "9. STEERING MECHANISM:\n" +
                "The entire steering mechanism must be fabricated by the participants i.e. any part connected to the steering part rigidly must be fabricated by the participants; failing to do so the team will be disqualified. However, the heim joint, knuckle arm, studs and wheel hub can be ready-made.\n" +
                "10. SUSPENSION MECHANISM:\n" +
                "The suspension mechanism must be fabricated by the participants except the shock absorbers. The suspension tower and the suspension arms must be fabricated by the participants.\n" +
                "11. BRAKES:\n" +
                "The brake mechanism must be disc brakes only. The disc pads used must be made by participants.\n");

        eventLocation.add("Workshop Mechanical Dept");
        eventLocation.add("Classroom M3, Mechanical Engg. Dept");
        eventLocation.add("Classroom M2, Mechanical Engg. Dept");
        eventLocation.add("Main playground, GECA.");
        eventLocation.add("GECA");

        eventContactPerson_list.add("Totaram Murumkar");
        eventContactPerson_list.add("Sampada Ahale");
        eventContactPerson_list.add("Dhanshri Bansule");
        eventContactPerson_list.add("Apoorva Rahatkar");
        eventContactPerson_list.add("Akash Mendhe");



        eventContactNum_list.add("8482930057");
        eventContactNum_list.add("9850954067");
        eventContactNum_list.add("9765614935");
        eventContactNum_list.add("9049313795");
        eventContactNum_list.add("9766373835");



        eventDate.add("4 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("4 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");



        eventprice.add("100");
        eventprice.add("100");
        eventprice.add("50");
        eventprice.add("100");
        eventprice.add("500");



        group_limit.add("3");
        group_limit.add("3");
        group_limit.add("0");
        group_limit.add("3");
        group_limit.add("5");

        time_list.add("5 OCT, 2017\n1pm onwards");
        time_list.add("5 OCT, 2017\n3pm onwards");
        time_list.add("5 OCT, 2017\n3pm onwards");
        time_list.add("5 OCT, 2017\n1 pm onwards");
        time_list.add("5 OCT, 2017\n4 pm onwards");






        MechanicoAdapter ad = new MechanicoAdapter(Mechanico.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Mechanico.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }




}
