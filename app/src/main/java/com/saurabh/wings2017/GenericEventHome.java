package com.saurabh.wings2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GenericEventHome extends AppCompatActivity {

    TextView gen_Name, gen_Location, gen_Info, gen_Rules, gen_Criteria, gen_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_event_home);

        gen_Name = (TextView) findViewById(R.id.genEventName);
        gen_Location = (TextView) findViewById(R.id.genEventLocation);
        gen_Info = (TextView) findViewById(R.id.genEventInfo);
        gen_Rules = (TextView) findViewById(R.id.genEventRules);
        gen_Criteria = (TextView) findViewById(R.id.genEventCriteria);
        gen_Price = (TextView) findViewById(R.id.genEventPrice);

//        Getting data from Intent of recspective activities

        gen_Name.setText(getIntent().getStringExtra("EventName"));
        gen_Location.setText(getIntent().getStringExtra("EventLocation"));
        gen_Info.setText(getIntent().getStringExtra("EventInfo"));
        gen_Rules.setText(getIntent().getStringExtra("EventRules"));
        gen_Criteria.setText(getIntent().getStringExtra("EventPrice"));
        gen_Price.setText(getIntent().getStringExtra("EventCriteria"));

//        EventCriteria = Price
//        EventPrice = Rules
//        EventInfo = Info -- All others are same



    }
}
