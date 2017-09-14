package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class Profile extends AppCompatActivity {

    //  Printing Details
    TextView fireName;
    TextView fireMail,mobileNumber;
    ImageView fireImage,alterImg;
    public ImageView signOutBtn;
    Bitmap image;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername, mUsermail, mUserMobileNum, mPhotoUrl, mPhotoUrlLarge;


    //    Printing Details
    public void printUserDetails(){

        //        Fetching Details

        fireName = (TextView) findViewById(R.id.userName);
        fireMail = (TextView) findViewById(R.id.userMail);
        mobileNumber = (TextView) findViewById(R.id.userMobile);
        fireImage = (ImageView) findViewById(R.id.userImage);



        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, signIn.class));
            finish();
            return;
        } else {
            mUsername = SaveSharedPreferences.getUserName(getApplicationContext());
            mUsermail = SaveSharedPreferences.getUserEmail(getApplicationContext());
            mUserMobileNum = SaveSharedPreferences.getUserPhone(getApplicationContext());
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            mPhotoUrlLarge = mPhotoUrl.replace("/s96-c/","/s800-c/");
            mPhotoUrlLarge = mPhotoUrlLarge.toString();
            Log.e("PV", "mPhotoURL " + mPhotoUrlLarge );
            Log.e("PV", "printUserDetails: " + mUsername + mUsermail);
            Picasso.with(getApplicationContext()).load(mPhotoUrlLarge).into(fireImage);
            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            mobileNumber.setText(mUserMobileNum);
//            Fix this issue, big images not being dislayed either converted into circular shape.
            Picasso.with(Profile.this).
                    load(mPhotoUrlLarge).
                    into( new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                /* Save the bitmap or do something with it here */

                            image = getRoundedShape(bitmap);
                            fireImage.setImageBitmap(image);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {}

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {}
                    });



        }

    }

    //   Method for  SignOut
    public void LogOut() {
        signOutBtn = (ImageView) findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(Profile.this, signIn.class));
                    finish();
                }
            }
        };


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(Profile.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("We know it's hard to say Goodbye!")
                        .setCancelText("No")
                        .showCancelButton(true)
                        .setConfirmText("Logout")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                mAuth.getInstance().signOut();
                                Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_SHORT).show();
                                SaveSharedPreferences.clearUserName(getApplicationContext());
                                finish();
                            }
                        }).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.RED);
        }
        printUserDetails();

    }

    public void logout(View v){
        LogOut();
    }
    public void home(View v){
        Intent iHome = new Intent(Profile.this, MainActivity.class);
        startActivity(iHome);
        finish();
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 700;
        int targetHeight = 550;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Profile.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
