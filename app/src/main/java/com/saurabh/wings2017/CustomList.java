package com.saurabh.wings2017;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {


    private final Activity context;
    private final ArrayList userName_list;
    private final ArrayList eventName_list;
    private final ArrayList eventID_list;
    private final ArrayList eventPrice_list;
    private final ArrayList uniqueID_list;


    public CustomList(Activity context,
                      ArrayList userName_list, ArrayList eventName_list, ArrayList eventID_list, ArrayList eventPrice_list, ArrayList uniqueID_list) {
        super(context, R.layout.cart_list, userName_list);
        this.context = context;
        this.userName_list = userName_list;
        this.eventName_list = eventName_list;
        this.eventID_list = eventID_list;
        this.eventPrice_list = eventPrice_list;
        this.uniqueID_list = uniqueID_list;
        Log.e("PV","bochya"+userName_list+eventName_list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.e("PV","xyz");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.cart_list, null, true);
        rowView.setMinimumHeight(80);
        rowView.setBackgroundColor(Color.WHITE);



        TextView CartEventName = (TextView) rowView.findViewById(R.id.CartEventName);
        TextView CartEventInfo = (TextView) rowView.findViewById(R.id.CartEventInfo);
        TextView CartEventLocation = (TextView) rowView.findViewById(R.id.CartEventLocation);
        TextView pricetag = (TextView) rowView.findViewById(R.id.PriceTag);
        TextView uniqueID = (TextView) rowView.findViewById(R.id.uniqueID);




        rowView.setBackgroundColor(Color.WHITE);
        CartEventInfo.setText((CharSequence)eventID_list.get(position));
        CartEventName.setText((CharSequence)eventName_list.get(position));
        CartEventLocation.setText((CharSequence)eventID_list.get(position));
        pricetag.setText((CharSequence)eventPrice_list.get(position));
        uniqueID.setText((CharSequence)uniqueID_list.get(position));

        Log.e("PV", "yes"+userName_list.get(position)+eventName_list.get(position));
        return rowView;
    }

}