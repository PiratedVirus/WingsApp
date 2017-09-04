package com.saurabh.wings2017;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public LinearLayout sliderDotspanel;
    public Button CivilBtn;
    public Button BrainBtn;
    public ImageView signOutBtn;
    public Button viewCartBtn;
    private int dotscount;
    private ImageView[] dots;

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;

    public static final String URL_DATA = "https://api.myjson.com/bins/cxrbn";



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
            }
        });

    }



//   Method for opening Brain
    public void BrainIntent(){
        BrainBtn = (Button) findViewById(R.id.brainBtn);
        BrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BrainIntent = new Intent(MainActivity.this,BrainHome.class);
                startActivity(BrainIntent);
            }
        });
    }
//
////    Method for  SignOut
    public void LogOutNew() {
//        signOutBtn = (ImageView) findViewById(R.id.logout);
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
            Toast.makeText(MainActivity.this,mUsername,Toast.LENGTH_SHORT).show();

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
                startActivity(cartIntent);

            }
        });

    }

//    Timer class
    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // relativeLayout = (RelativeLayout)findViewById(R.id.activity_main);

//        animationDrawable = (AnimationDrawable) relativeLayout.getRootView()
//        .getBackground();
//        animationDrawable.setEnterFadeDuration(5000);
//        animationDrawable.setExitFadeDuration(2000);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl)
                .setTransitionDuration(4000)
                .start();
//        View xyz = getWindow().getDecorView();
//        BackgroundPainter backgroundPainter = new BackgroundPainter();


        int color1 = ContextCompat.getColor(getApplicationContext(), R.color.color1);
        int color2 = ContextCompat.getColor(getApplicationContext(), R.color.color2);
        int color3 = ContextCompat.getColor(getApplicationContext(), R.color.color3);
        int color31 = ContextCompat.getColor(getApplicationContext(), R.color.color31);
        int color4 = ContextCompat.getColor(getApplicationContext(), R.color.color4);
        int color5 = ContextCompat.getColor(getApplicationContext(), R.color.color5);
        int color6 = ContextCompat.getColor(getApplicationContext(), R.color.color6);
        int color7 = ContextCompat.getColor(getApplicationContext(), R.color.color7);
        int color8 = ContextCompat.getColor(getApplicationContext(), R.color.color8);
        int color9 = ContextCompat.getColor(getApplicationContext(), R.color.color9);
        int color10 = ContextCompat.getColor(getApplicationContext(), R.color.color10);
        int color11 = ContextCompat.getColor(getApplicationContext(), R.color.color11);
        int color12 = ContextCompat.getColor(getApplicationContext(), R.color.color12);
        int color13 = ContextCompat.getColor(getApplicationContext(), R.color.color13);
        int color14 = ContextCompat.getColor(getApplicationContext(), R.color.color14);
        int color15 = ContextCompat.getColor(getApplicationContext(), R.color.color15);
        int color16 = ContextCompat.getColor(getApplicationContext(), R.color.color16);
        int color17 = ContextCompat.getColor(getApplicationContext(), R.color.color17);
        int color18 = ContextCompat.getColor(getApplicationContext(), R.color.color18);
        int color19 = ContextCompat.getColor(getApplicationContext(), R.color.color19);
        int color20 = ContextCompat.getColor(getApplicationContext(), R.color.color20);
        int color21 = ContextCompat.getColor(getApplicationContext(), R.color.color21);
        int color22 = ContextCompat.getColor(getApplicationContext(), R.color.color22);
        int color23 = ContextCompat.getColor(getApplicationContext(), R.color.color23);
        int color24 = ContextCompat.getColor(getApplicationContext(), R.color.color24);
        int color25 = ContextCompat.getColor(getApplicationContext(), R.color.color25);
        int color26 = ContextCompat.getColor(getApplicationContext(), R.color.color26);
        int color27 = ContextCompat.getColor(getApplicationContext(), R.color.color27);

//        backgroundPainter.animate(xyz, color20, color21, color22, color23, color24, color31, color6, color7, color10, color11, color12, color13, color14, color16, color18, color20);



        if(SaveSharedPreferences.getUserPhone(getApplicationContext()).isEmpty())
        {
            Intent TicketIntent = new Intent(MainActivity.this,Details.class);
            startActivity(TicketIntent);
            finish();

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            // finally change the color
//           // window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.setStatusBarColor(Color.TRANSPARENT);

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        printUserDetails();
        countDots();

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(170, 60, 170, 0);
        viewPager.setPageMargin(90);
        viewPager.setCurrentItem(0);

        CivilIntent();
        BrainIntent();
        LogOutNew();
        viewCart();

    }

    public void openCart(View v) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Intent cartIntent = new Intent(MainActivity.this,Cart.class);
        cartIntent.putExtra("userName",mFirebaseUser.getDisplayName());
        cartIntent.putExtra("userMail",mFirebaseUser.getEmail());
        Toast.makeText(this, "Long press on event to delete!", Toast.LENGTH_LONG).show();
        startActivity(cartIntent);
        finish();

    }



    public void viewTickets(View v){
        Intent TicketIntent = new Intent(MainActivity.this,tickets.class);
        startActivity(TicketIntent);
        finish();

    }

    public void viewProfile(View v){
        Intent viewProIntnet = new Intent(MainActivity.this,Profile.class);
        startActivity(viewProIntnet);
        finish();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);

    }

    public void team(View v)
    {
        Intent intent = new Intent(MainActivity.this, Team.class);
        startActivity(intent);
        finish();
    }


    public static class BackgroundPainter {

        private static final int MIN = 20000;
        private static final int MAX = 73000;

        private final Random random;

        public BackgroundPainter() {
            random = new Random();
        }

        public void animate(@NonNull final View target, @ColorInt final int color1,
                            @ColorInt final int color2, @ColorInt final int color3,@ColorInt final int color4,@ColorInt final int color5,@ColorInt final int color6,@ColorInt final int color7,@ColorInt final int color8,@ColorInt final int color9,@ColorInt final int color10,@ColorInt final int color11,@ColorInt final int color12,@ColorInt final int color13,
                            @ColorInt final int color14,@ColorInt final int color15,@ColorInt final int color16) {
//            ,@ColorInt final int color18,@ColorInt final int color19,@ColorInt final int color20,@ColorInt final int color21,@ColorInt final int color22,@ColorInt final int color23,@ColorInt final int color24,@ColorInt final int color25,@ColorInt final int color26,@ColorInt final int color27,@ColorInt final int color28
            final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2,color3,color4,color5,color6,color7,color8,color9,color10,color11,color12,color13,color14,color15,color16);
//            ,color18,color19,color20,color21,color22,color23,color24,color25,color26,color27,color28
            valueAnimator.setDuration(randInt(MIN, MAX));

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator animation) {
                    target.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    //reverse animation
                    animate(target, color1, color2, color3,color4,color5,color6,color7,color8,color9,color10,color11,color12,color13,color14,color15,color16);
                }
            });

            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.start();
        }

        private int randInt(int min, int max) {
            return random.nextInt((max - min) + 1) + min;
        }
    }
//

}


