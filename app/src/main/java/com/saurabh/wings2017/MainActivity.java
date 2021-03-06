package com.saurabh.wings2017;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public LinearLayout sliderDotspanel;
    public Button CivilBtn;
    public Button BrainBtn;
    public Button viewCartBtn;
    private int dotscount;
    private ImageView[] dots;


    //  Printing Details
    TextView fireName, fireMail;
    ImageView fireImage;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername, mPhotoUrl, mUsermail;


 //   Method for checking auth state
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

//    Method for opening Civil activity
    public void CivilIntent(){
        CivilBtn = (Button) findViewById(R.id.civilBtn);
        CivilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent civilIntent = new Intent(MainActivity.this,CivilHome.class);
                startActivity(civilIntent);
                finish();
            }
        });

    }



//   Method for opening Brain


//    Method for  SignOut
    public void LogOutNew() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(MainActivity.this, signIn.class));
                    finish();
                }
            }
        };
    }

//    Printing Details
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
            //Toast.makeText(MainActivity.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(MainActivity.this).load(mPhotoUrl).into(fireImage);

        }

    }

//    Count Dots
    private void countDots(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);





//        Dots count start
        dotscount = viewPagerAdapter.getCount();

        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(6, 0, 6, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));



//        Pages changes
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    View Cart
    private void viewCart(){
        viewCartBtn = (Button) findViewById(R.id.ViewCart);
        viewCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                Intent cartIntent = new Intent(MainActivity.this,Cart.class);
                cartIntent.putExtra("userName",mFirebaseUser.getDisplayName());
                cartIntent.putExtra("userMail",mFirebaseUser.getEmail());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(cartIntent);
                finish();

            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





//    Transition Gradients
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl)
                .setTransitionDuration(4000)
                .start();


        if(SaveSharedPreferences.getUserPhone(getApplicationContext()).isEmpty())
        {
            Intent TicketIntent = new Intent(MainActivity.this,Details.class);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(TicketIntent);
            finish();

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        printUserDetails();
        countDots();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS, android.Manifest.permission.CALL_PHONE};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        float scale = getResources().getDisplayMetrics().density;

        int top = (int)(55*scale + 0.5f);
        int bottom = (int)(10*scale + 0.5f);
        int left = (int)(55*scale + 0.5f);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(top , bottom, left, 0);
        viewPager.setPageMargin(90);
        viewPager.setCurrentItem(0);

        CivilIntent();

        LogOutNew();
        viewCart();







    }

    public class NotificationService extends FirebaseMessagingService {
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSmallIcon(R.drawable.notif_icon)
                    .setSound(alarmSound)
                    .build();



            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(123, notification);
        }
    }




    public void openCart(View v) {


        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();
        } else {

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            Intent cartIntent = new Intent(MainActivity.this,Cart.class);
            cartIntent.putExtra("userName",mFirebaseUser.getDisplayName());
            cartIntent.putExtra("userMail",mFirebaseUser.getEmail());
           // Toast.makeText(this, "Long press on event to delete!", Toast.LENGTH_LONG).show();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(cartIntent);
            finish();
        }


    }

    public void viewTickets(View v){

        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();
        } else {


            Intent TicketIntent = new Intent(MainActivity.this,tickets.class);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(TicketIntent);
            Toast.makeText(this, "Swipe down to Refresh", Toast.LENGTH_SHORT).show();
            finish();
        }



    }

    public void viewProfile(View v){

        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();
        } else {


            Intent viewProIntnet = new Intent(MainActivity.this, Profile.class);
            startActivity(viewProIntnet);
            finish();
        }
    }

    public void brain(View v) {
        Intent brainintent = new Intent(getApplicationContext(),Brain.class);
        startActivity(brainintent);
        finish();
    }

    public void code(View v) {
        Intent brainintent = new Intent(getApplicationContext(),Code.class);
        startActivity(brainintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
        finish();
        System.exit(0);

    }

    public void team(View v) {
        Intent intent = new Intent(MainActivity.this, Team.class);
        startActivity(intent);
        finish();
    }


    public void robot(View v) {
        Intent intent = new Intent(MainActivity.this, Robotics.class);
        startActivity(intent);
        finish();
    }

    public void talent(View v) {
        Intent intent = new Intent(MainActivity.this, Talent.class);
        startActivity(intent);
        finish();
    }

    public void mini(View v) {
        Intent intent = new Intent(MainActivity.this, Mini.class);
        startActivity(intent);
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        Log.e("PV", "sendRegistrationToServer: " + token);
    }



    public void viewSchedule(View v){
        Intent iSchedule = new Intent(MainActivity.this,BinarySchedule.class);
        startActivity(iSchedule);
        finish();
    }

    public void elegance(View v){
        Intent iSchedule = new Intent(MainActivity.this,Elegance.class);
        startActivity(iSchedule);
        finish();
    }

    public void faq(View v){
        Intent iSchedule = new Intent(MainActivity.this,Faq.class);
        startActivity(iSchedule);
        finish();
    }

    public void volt(View v){
        Intent iSchedule = new Intent(MainActivity.this,Volt.class);
        startActivity(iSchedule);
        finish();
    }

    public void mechanico(View v){
        Intent iSchedule = new Intent(MainActivity.this,Mechanico.class);
        startActivity(iSchedule);
        finish();
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



}


