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

public class Elegance extends AppCompatActivity  {

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
            Toast.makeText(Elegance.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Elegance.this).load(mPhotoUrl).into(fireImage);

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
        setContentView(R.layout.activity_elegance);

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


        eventName_list.add("Rangmanch");
        eventName_list.add("Kaviya");
        eventName_list.add("Dhindhora");
        eventName_list.add("Lasya");
        eventName_list.add("Synchro");
        eventName_list.add("Aalap");
        eventName_list.add("Crescendo");
        eventName_list.add("T-Shirt Painting");
        eventName_list.add("Get. Set.. Paint...");
        eventName_list.add("Masquerade");

        eventDetails_list.add("Cause theatres were created to tell the truth about life! \n" +
                "\n RULES:\n\n" +
                "\uF071 Only solo performances are allowed.\n" +
                "\uF071 The art can be performed in any language of comfort.\n" +
                "\uF071 No offensive words are permissible. The performer will be disqualified if abusive content is found. \uF071 Performance will be judged purely on acting. Background music is not needed.\n" +
                "\uF071 The participants are allowed to bring their own properties . Only chair & table will be provided. \uF071 Participants have to report before 1 hour the competition to avoid disqualification.\n" +
                "\uF071 \n Registration Fees : ₹150\n\n" +
                "\uF071 \nPrizes : ₹ 2500 & ₹2000\n" +
                "\uF071 \n Time Duration : 6 mins (+1 minute stage set up & clearance \n)");
        eventDetails_list.add("Witness the world trapped in a poet being exploited with a hint of rhyme! \n" +
                "\n RULES:\n\n" +
                "\uF071This event consists of one round\n" +
                "\uF071 Participants should present a 2 self-prepared poems.\n" +
                "\uF071Language used can be Hindi or Marathi only.\n" +
                "\uF071The judging depends upon the content of the poem along with presentation.\n" +
                "\uF071The performance has to be presented on a stage with audience on one side.\n" +
                "\uF071 Participants have to report 1 hour before the competition or else that team faces disqualification. \uF071Registration fees: ₹150\n" +
                "\uF071 \nPrizes: ₹ 1500 & ₹1000\n" +
                "\uF071 \n Time Duration: 5 mins\n");
        eventDetails_list.add("Talent has always been recognized by the ones who never fail to showcase it. A stage is where an act is performed but a real act makes every place a stage itself! \n" +
                "\n RULES:\n\n" +
                "\uF071 This event consists of one round\n" +
                "\uF071 The essence of the play should be brought out in any language.\n" +
                "\uF071 The team size represents the number of people registered as a team. Only these shall be allowed to perform the street play. \uF071 Music accompanists are included in the team size stated above.\n" +
                "\uF071 The act has to be performed on a circular (8 meter in diameter) stage with audience on all the sides.\n" +
                "\uF071 The stage must be cleared by the participants to avoid problems for the next team or else heavy penalties will be imposed. \uF071 Teams have to report 1 hour before the competition or else that team faces disqualification.\n" +
                "\uF071 \nRegistration Fees : ₹700\n" +
                "\uF071 \nNo. of participants per team: 8-20.\n" +
                "\uF071 \nPrizes : ₹ 5000, ₹ 4000\n" +
                "\uF071 \n Time Duration: 15 minutes (including the entry and clearance time)\n");
        eventDetails_list.add("“One for all. All for one!” \n" +
                "\n RULES:\n\n" +
                "\uF071Only SOLO Performances are allowed.\n" +
                "\uF071Any dance style and song selection is allowed.\n" +
                "\uF071Vulgar songs are strictly prohibited.\n" +
                "\uF071Over-exposing costumes are not allowed.\n" +
                "\uF071Video auditions to be submitted from 28th Sept to 2nd Oct to the respective co-ordinators. \uF071Time limit for video auditions – 1 minute.\n" +
                "\uF071Only dances which clears auditions are allowed to perform.\n" +
                "\uF071Props are allowed.\n" +
                "\uF071Participant teams have to report before 1 hour of the competition to avoid disqualification. \uF071 Time Limit for performance -3 - 5 min (+1 preparation & stage clearance)\n" +
                "\uF071Registration Fee : ₹200\n" +
                "\uF071\nPrizes : ₹2500, ₹2000\n");
        eventDetails_list.add("Dance brings out the best in you & so does the team work. Then why not bring them together itself? Reveal your coordination in the most beautiful art form of your choice. Join us in SYNCHRO!\n" +
                "\n RULES:\n\n" +
                "\uF071 This event comprises of only one round of GROUP Dance.\n" +
                "\uF071 Any dance style and song selection is allowed.\n" +
                "\uF071Vulgar songs are strictly prohibited.\n" +
                "\uF071Over-exposing costumes are not allowed.\n" +
                "\uF071 Video auditions to be submitted from 28th Sept to 2nd Oct to the respective co-ordinators. \uF071 Time limit for video auditions – 1 minute.\n" +
                "\uF071Only dances which clears auditions are allowed to perform\n" +
                "\uF071Participant teams have to report before 1 hour of the competition to avoid disqualification. \uF071 Props are allowed.\n" +
                "\uF071The limit of performance – 3-5 minutes (+1 preparation & stage clearance)\n" +
                "\uF071Registration Fee : ₹700\n" +
                "\uF071No. of Participants : 7-12.\n" +
                "\uF071\nPrizes : ₹ 5000, ₹ 4000\n");
        eventDetails_list.add("“Sing your feelings.. Not just words!”\n" +
                "\n RULES:\n\n" +
                " \uF071This event comprises of 1 round.\n" +
                "\uF071Video auditions to be submitted from 28th Sept to 2nd Oct to the respective co-ordinators.\n" +
                "\uF071Time limit for video auditions – 1 minute.\n" +
                "\uF071Background music not compulsory.\n" +
                "\uF071You may also bring a karaoke track.\n" +
                "\uF071Judging is purely based on the singing.\n" +
                "\uF071 No RAPS allowed. Vulgarity/Obscenity is prohibited.\n" +
                "\uF071 Participants have to report 1 hour before the competition or else that team faces disqualification.\n" +
                "\uF071One instrumentalist may accompany the participant. The accompanist can either be a guitarist or a synth player who should\n" +
                "get his/her instrument.\n" +
                "\uF071 Registration fees: ₹150 \uF071 \n" +
                "\uF071\nPrizes: ₹2000, ₹1500, ₹1000\n");
        eventDetails_list.add("“Singing is an act of presenting your emotions wrapped in melody.”\n" +
                "\n RULES:\n\n" +
                "\uF071 The event consist of only one round.\n" +
                "\uF071 At least one song has to be performed and at least 3 instruments have to be used.\n" +
                "\uF071 The songs performed can be a cover or OC in either English, Hindi or Marathi or all three.\n" +
                "\uF071 All the instruments except the Drum kit should be brought by the Band itself.\n" +
                "\uF071 The Band will be provided with a basic drum kit, drum throne, 5 Amps (3 for base guitars, 2 for electric/acoustic guitars),\n" +
                "synth stand, mics and line-in jacks for guitars.\n" +
                "\uF071 Participants are welcome to bring their own equipments(Amps, drum pieces etc.)\n" +
                "\uF071 Vocals with lyrics are mandatory.\n" +
                "\uF071 A time slot of 2 hours before the event starts is allotted for the Bands to have a sound check and configure the levels.\n" +
                "\uF071 Bands who do not wish to have a sound check should report 1 hour before the competition or else that band faces\n" +
                "disqualification.\n" +
                "\uF071 Registration fees: ₹1000\n" +
                "No. of participants per team: 4-8\n" +
                "\n Time Duration: 10 minutes (+3 mins setup and music check time) \n" +
                "\uF071\nPrizes: ₹ 8000, ₹ 5000\n\n");
        eventDetails_list.add("Wear your imagination!\n" +
                "\n RULES:\n\n" +
                " \uF071This event consists of 1 round.\n" +
                "\uF071Participants will be provided with T shirts.\n" +
                "\uF071Colors, sponge and other decorative material will be provided. Brushes will not be provided. \uF071Participants can also use their own decorative material if allowed by volunteers.\n" +
                "\uF071Any copied or printed material is strictly prohibited. However, any source inspired idea is welcome. \uF071 Participants have to report 1 hr before the competition to avoid disqualification.\n" +
                "\uF071Registration Fee : ₹120/-\n" +
                "\uF071\n Time Duration : 60 minutes \n" +
                "\uF071\nPrizes : We are not gonna reveal this . 3 best painting with some proper cash prizes & gift hampers \n");

        eventDetails_list.add("This event consists of one round.\n" +
                "\n RULES:\n\n" +
                "All the necessary material will be provided (A2 Sheet, colours, sponge) except brushes Colours will be provided by volunteers as required not all at once.\n" +
                "Participants are not allowed to use personal materials or copy from any kind of objectionable source (mobiles, laptops, photographs etc.) during the course of the competition.\n" +
                "Participants will be judged on the basis of creativity, innovation, design & LEAST time taken to complete the art. Participants have to report 1 hour before the competition to avoid disqualification.\n" +
                "Registration Fees :₹100/-\n \nPrizes : ₹1500 & ₹ 1000\n");
        eventDetails_list.add("\n" +
                "\uF071 This event comprises of ONE Round of face painting.\n" +
                "\uF071 It is a team event . There can be only 2 participants in a team.\n" +
                "\uF071 Basic colours, sponge will be provided. Participants should bring their own brushes. \uF071 Topic will be given on the spot.\n" +
                "\uF071 Participants will be judged on the basis of creativity, innovation & design.\n" +
                "\uF071 Decision of the judges will be final & binding.\n" +
                "\uF071 Registration fees : ₹100/- per TEAM.\n" +
                "\uF071 Time limit : 60 minutes \n \nPrizes : We are not gonna reveal this . 3 best painting with some proper cash prizes & gift hampers\n");

        eventLocation.add("Electrical seminar hall");
        eventLocation.add("Electrical seminar hall");
        eventLocation.add("In front of Applied Mechanics");
        eventLocation.add("College Auditorium");
        eventLocation.add("College Auditorium");
        eventLocation.add("In front of Classroom Complex");
        eventLocation.add("Open Stage behind Civil Dept");
        eventLocation.add("Applied Mechanics");
        eventLocation.add("Applied Mechanics");
        eventLocation.add("Applied Mechanics");

        eventContactPerson_list.add("Priyanka Rajput");
        eventContactPerson_list.add("Dipam Chaudhari");
        eventContactPerson_list.add("Tejas Bagul");
        eventContactPerson_list.add("Payal Sarkate");
        eventContactPerson_list.add("Nikita Wadgaonkari");
        eventContactPerson_list.add("Chinmay Kulkarni");
        eventContactPerson_list.add("Ganesh Kasle");
        eventContactPerson_list.add("Pramita Shahare");
        eventContactPerson_list.add("Amruta Kulkarni");
        eventContactPerson_list.add("Amruta Kulkarni");

        eventContactNum_list.add("8805895988");
        eventContactNum_list.add("7798040995");
        eventContactNum_list.add("8275682235");
        eventContactNum_list.add("9850644422");
        eventContactNum_list.add("7040920114");
        eventContactNum_list.add("9881246601");
        eventContactNum_list.add("9423781980");
        eventContactNum_list.add("9975531168");
        eventContactNum_list.add("8087966802");
        eventContactNum_list.add("8087966802");

        eventDate.add("7 OCT 2017");
        eventDate.add("8 OCT 2017");
        eventDate.add("8 OCT 2017");
        eventDate.add("8 OCT 2017");
        eventDate.add("8 OCT 2017");
        eventDate.add("7 OCT 2017");
        eventDate.add("7 OCT 2017");
        eventDate.add("7 OCT 2017");
        eventDate.add("8 OCT 2017");
        eventDate.add("7 OCT 2017");


        eventprice.add("150");
        eventprice.add("150");
        eventprice.add("500");
        eventprice.add("200");
        eventprice.add("600");
        eventprice.add("150");
        eventprice.add("800");
        eventprice.add("150");
        eventprice.add("100");
        eventprice.add("100");

        group_limit.add("1");
        group_limit.add("4");
        group_limit.add("5");
        group_limit.add("6");
        group_limit.add("0");
        group_limit.add("0");
        group_limit.add("0");
        group_limit.add("0");
        group_limit.add("0");
        group_limit.add("0");






        EleganceAdapter ad = new EleganceAdapter(Elegance.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);


        try {

            civil.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // For Long Duration Toast
//                    https://stackoverflow.com/questions/6766625/listview-getchildat-returning-null-for-visible-children




                    try {

                        TextView name = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventName);
                        TextView location = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventLocation);
                        TextView desc = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventExcerpt);
                        TextView price = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventPrice);
                        TextView date = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventDate);
                        TextView person_name = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactPerson);
                        TextView person_num = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactNum);




                        Toast.makeText(getApplicationContext(), name.getText().toString(), Toast.LENGTH_LONG).show();

                        Intent eventi = new Intent(Elegance.this, GenericEventHome.class);
                        eventi.putExtra("name", name.getText().toString());
                        eventi.putExtra("location", location.getText().toString());
                        eventi.putExtra("desc", desc.getText().toString());
                        eventi.putExtra("price", price.getText().toString());
                        eventi.putExtra("date", date.getText().toString());
                        eventi.putExtra("person_name", person_name.getText().toString());
                        eventi.putExtra("person_num", person_num.getText().toString());

                        startActivity(eventi);
                        finish();
                        // For Long Short Toast
                        Log.e("PV","pachkan hagla + wanted child" +arg2);

                    }
                    catch (NullPointerException n)
                    {
                        Log.e("PV","pachkan hagla catch madhye "+arg2);
                        n.printStackTrace();
                    }

                }
            });

        } catch (NullPointerException n)
        {
            n.printStackTrace();
        }




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Elegance.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
