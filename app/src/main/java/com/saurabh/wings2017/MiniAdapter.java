package com.saurabh.wings2017;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MiniAdapter extends ArrayAdapter<String> {


    private final Activity context;

    private final ArrayList eventName_list;
    private final ArrayList eventDetails_list;
    private final ArrayList eventLocation;
    private final ArrayList eventContactPerson_list;
    private final ArrayList eventContactNum_list;
    private final ArrayList eventDate;
    private final ArrayList eventprice;




    public MiniAdapter(Activity context,
                       ArrayList eventName_list, ArrayList eventDetails_list, ArrayList eventLocation, ArrayList eventContactPerson_list, ArrayList eventContactNum_list, ArrayList eventDate, ArrayList eventprice) {
        super(context, R.layout.content_mini_single, eventName_list);
        this.context = context;
        this.eventName_list = eventName_list;
        this.eventDetails_list = eventDetails_list;
        this.eventLocation = eventLocation;
        this.eventContactPerson_list = eventContactPerson_list;
        this.eventContactNum_list = eventContactNum_list;
        this.eventDate = eventDate;
        this.eventprice = eventprice;


        Log.e("PV","bochya"+eventName_list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.e("PV","xyz");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.content_mini_single, null, true);
        rowView.setMinimumHeight(80);




        TextView EventName = (TextView) rowView.findViewById(R.id.CivilEventName);
        TextView EventInfo = (TextView) rowView.findViewById(R.id.CivilEventExcerpt);
        TextView EventLocation = (TextView) rowView.findViewById(R.id.CivilEventLocation);
        TextView EventDate = (TextView) rowView.findViewById(R.id.eventDate);
        TextView EventPerson = (TextView) rowView.findViewById(R.id.eventContactPerson);
        TextView Eventcontact = (TextView) rowView.findViewById(R.id.eventContactNum);
        TextView EventPrice = (TextView) rowView.findViewById(R.id.eventPrice);

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont((Activity) getContext(), "fonts/mont.ttf", true);


        rowView.setBackgroundResource(R.drawable.card_bg_5_rounded);
        EventInfo.setText((CharSequence)eventDetails_list.get(position));
        EventName.setText((CharSequence)eventName_list.get(position));
        EventLocation.setText((CharSequence)eventLocation.get(position));
        EventDate.setText((CharSequence)eventDate.get(position));
        EventPerson.setText((CharSequence)eventContactPerson_list.get(position));
        Eventcontact.setText((CharSequence)eventContactNum_list.get(position));
        EventPrice.setText((CharSequence)eventprice.get(position));

        Log.e("PV", "yes"+eventName_list.get(position));
        return rowView;
    }

}