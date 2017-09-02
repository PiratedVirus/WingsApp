package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Developer extends AppCompatActivity {


    TextView title_text, name_saurabh, email_saurabh, phone_saurabh;
    TextView name_ashwin, email_ashwin, phone_ashwin, website_ashwin;
    TextView name_rugved, email_rugved, phone_rugved, title_text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        title_text = (TextView)findViewById(R.id.title);
        name_saurabh = (TextView) findViewById(R.id.name_s);
        email_saurabh = (TextView) findViewById(R.id.email_s);
        phone_saurabh = (TextView) findViewById(R.id.phone_s);
        name_ashwin = (TextView) findViewById(R.id.name_a);
        email_ashwin = (TextView)findViewById(R.id.email_a);
        phone_ashwin = (TextView)findViewById(R.id.phone_a);
        website_ashwin =(TextView) findViewById(R.id.website_a);
        name_rugved = (TextView) findViewById(R.id.name_r);
        email_rugved = (TextView)findViewById(R.id.email_r);
        phone_rugved = (TextView)findViewById(R.id.phone_r);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/mont.ttf");
        title_text.setTypeface(face);
        name_saurabh.setTypeface(face);
        email_saurabh.setTypeface(face);
        phone_saurabh.setTypeface(face);
        name_ashwin.setTypeface(face);
        email_ashwin.setTypeface(face);
        phone_ashwin.setTypeface(face);
        website_ashwin.setTypeface(face);
        name_rugved.setTypeface(face);
        email_rugved.setTypeface(face);
        phone_rugved.setTypeface(face);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Developer.this, Team.class);
        startActivity(i);
        finish();
    }
}