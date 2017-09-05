package com.saurabh.wings2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by saurabh on 22/07/17.
 */

public class signIn extends AppCompatActivity {

    private static final String TAG = "Firebase";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    private Button button;
    private final static int RC_SIGN_IN = 2;

    private GoogleApiClient mGoogleApiClient;


    SweetAlertDialog pDialog;



    private ProgressDialog mConnectionProgressDialog;

    public static String fUserName;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        Window window = signIn.this.getWindow();

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        ImageView wings = (ImageView)findViewById(R.id.wings_logo);
        button = (Button) findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Animation zoom = AnimationUtils.loadAnimation(signIn.this, R.anim.fadein);
        wings.setAnimation(zoom);

        Animation zoom1 = AnimationUtils.loadAnimation(signIn.this, R.anim.zoomin);
        button.setAnimation(zoom1);

//        YoYo.with(Techniques.ZoomInUp)
//                .duration(1000)
//                .playOn(findViewById(R.id.wings_logo));

//        YoYo.with(Techniques.ZoomIn)
//                .duration(1000)
//                .playOn(findViewById(R.id.text_login));


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_layout);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl)
                .setTransitionDuration(4000)
                .start();




            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isNetworkAvailable()) {
                        Log.e("PV", "not connected");


                        new SweetAlertDialog(signIn.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                                        finish();

                                    }
                                })
                                .show();

                    } else {
                        pDialog.show();
                        signIn();
                    }

                }
            });


            mAuthListner = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        Intent fireBaseIntent = new Intent(signIn.this, MainActivity.class);
                        startActivity(fireBaseIntent);
                        finish();
                    }
                }
            };

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();


            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(signIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            mConnectionProgressDialog = new ProgressDialog(this);
            mConnectionProgressDialog.setMessage("Signing in...");

            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please Wait! ");
            pDialog.setContentText("Tell Google to build faster Computers!");
            pDialog.setCancelable(false);


    }


    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //finish();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e(TAG, "onActivityResult: " + String.valueOf(result.isSuccess()) );
//            mConnectionProgressDialog.dismiss();
            pDialog.dismissWithAnimation();
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(signIn.this,"Auth Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            fUserName = account.getDisplayName();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(signIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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

    public void skip(View v){
        Intent skipI = new Intent(signIn.this,MainActivity.class);
        startActivity(skipI);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
