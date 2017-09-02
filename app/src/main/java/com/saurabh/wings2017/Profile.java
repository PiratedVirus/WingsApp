package com.saurabh.wings2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Profile extends AppCompatActivity {

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;
    public ImageView signOutBtn;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;


    //    Printing Details
    public void printUserDetails(){

        //        Fetching Details

        fireName = (TextView) findViewById(R.id.userName);
        fireMail = (TextView) findViewById(R.id.mailAddress);
        fireImage = (ImageView) findViewById(R.id.profileImg);


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
            Log.e("PV", "printUserDetails: " + mUsername + mUsermail);

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Profile.this).load(mPhotoUrl).into(fireImage);

        }

    }

    //   Method for  SignOut
    public void LogOut() {
        signOutBtn = (ImageView) findViewById(R.id.logout);
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
                        .setContentText("You are about to log out from the app")
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
                            }
                        }).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        printUserDetails();
    }

    public void logout(View v){
        LogOut();
    }
}
