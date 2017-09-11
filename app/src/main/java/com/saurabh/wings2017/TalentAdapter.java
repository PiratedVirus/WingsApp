package com.saurabh.wings2017;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TalentAdapter extends ArrayAdapter<String> {


    private final Activity context;

    private final ArrayList eventName_list;
    private final ArrayList eventDetails_list;
    private final ArrayList eventLocation;
    private final ArrayList eventContactPerson_list;
    private final ArrayList eventContactNum_list;
    private final ArrayList eventDate;
    private final ArrayList eventprice;
    private final ArrayList group_list;



    public TalentAdapter(Activity context,
                         ArrayList eventName_list, ArrayList eventDetails_list, ArrayList eventLocation, ArrayList eventContactPerson_list, ArrayList eventContactNum_list, ArrayList eventDate, ArrayList eventprice, ArrayList group_list) {
        super(context, R.layout.content_talent_single, eventName_list);
        this.context = context;
        this.eventName_list = eventName_list;
        this.eventDetails_list = eventDetails_list;
        this.eventLocation = eventLocation;
        this.eventContactPerson_list = eventContactPerson_list;
        this.eventContactNum_list = eventContactNum_list;
        this.eventDate = eventDate;
        this.eventprice = eventprice;
        this.group_list = group_list;


        Log.e("PV","bochya"+eventName_list);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Log.e("PV","xyz");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.content_talent_single, null, true);
        rowView.setMinimumHeight(80);


        RelativeLayout rel = (RelativeLayout) rowView.findViewById(R.id.talentRel);


        final TextView EventName = (TextView) rowView.findViewById(R.id.CivilEventName);
        final TextView EventInfo = (TextView) rowView.findViewById(R.id.CivilEventExcerpt);
        final TextView EventLocation = (TextView) rowView.findViewById(R.id.CivilEventLocation);
        final TextView EventDate = (TextView) rowView.findViewById(R.id.eventDate);
        final TextView EventPerson = (TextView) rowView.findViewById(R.id.eventContactPerson);
        final TextView Eventcontact = (TextView) rowView.findViewById(R.id.eventContactNum);
        final TextView EventPrice = (TextView)  rowView.findViewById(R.id.eventPrice);

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont((Activity) getContext(), "fonts/mont.ttf", true);

        final String fullInfo = eventDetails_list.get(position).toString();
        String smallInfo = fullInfo.substring(0, Math.min(fullInfo.length(), 75));


        rowView.setBackgroundResource(R.drawable.card_bg_5_rounded);
        EventInfo.setText(smallInfo);
        EventName.setText((CharSequence)eventName_list.get(position));
        EventLocation.setText((CharSequence)eventLocation.get(position));
        EventDate.setText((CharSequence)eventDate.get(position));
        EventPerson.setText((CharSequence)eventContactPerson_list.get(position));
        Eventcontact.setText((CharSequence)eventContactNum_list.get(position));
        EventPrice.setText((CharSequence)eventprice.get(position));


        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "AdapterClick"+EventName.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent eventi = new Intent(getContext(), TalentHelper.class);
                eventi.putExtra("name", EventName.getText().toString());
                eventi.putExtra("location", EventLocation.getText().toString());
                eventi.putExtra("desc", fullInfo);
                eventi.putExtra("price", EventPrice.getText().toString());
                eventi.putExtra("date", EventDate.getText().toString());
                eventi.putExtra("person_name", EventPerson.getText().toString());
                eventi.putExtra("person_num", Eventcontact.getText().toString());
                eventi.putExtra("members", (String)group_list.get(position));

                ((Activity)getContext()).startActivity(eventi);
                ((Activity) getContext()).finish();

            }
        });

        Log.e("PV", "yes"+eventName_list.get(position));
        return rowView;
    }

}