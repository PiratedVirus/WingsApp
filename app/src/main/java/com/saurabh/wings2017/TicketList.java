package com.saurabh.wings2017;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saurabh on 29/08/17
 */
public class TicketList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList userName_ticket;
    private final ArrayList eventName_ticket;
    private final ArrayList eventID_ticket;
    private final ArrayList eventPrice_ticket;
    private final ArrayList played;
    private final ArrayList paid;
    private final ArrayList eventLocation_ticket;


    public TicketList(Activity context,
                      ArrayList userName_ticket, ArrayList eventName_ticket, ArrayList eventID_ticket, ArrayList eventPrice_ticket, ArrayList played, ArrayList paid, ArrayList eventLocation_ticket) {
        super(context, R.layout.ticket_list, userName_ticket);
        this.context = context;
        this.userName_ticket = userName_ticket;
        this.eventName_ticket = eventName_ticket;
        this.eventID_ticket = eventID_ticket;
        this.eventPrice_ticket = eventPrice_ticket;
       // this.uniqueID_ticket = uniqueID_ticket;
        this.played = played;
        this.paid = paid;
        this.eventLocation_ticket = eventLocation_ticket;

        Log.e("PV","bochya"+userName_ticket+eventName_ticket);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {



        Log.e("PV","TicketList");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.ticket_list, null, true);
        // ithe barobar dile ahe me tari pan ti cart walach ghetoy e
        rowView.setMinimumHeight(80);
        rowView.setBackgroundColor(Color.WHITE);
        ImageView unpaid =(ImageView) rowView.findViewById(R.id.unpaid);
        ImageView unplayed = (ImageView) rowView.findViewById(R.id.unplayed);


        TextView CartEventName = (TextView) rowView.findViewById(R.id.CartEventName);
        TextView CartEventInfo = (TextView) rowView.findViewById(R.id.CartEventInfo);
        TextView CartEventLocation = (TextView) rowView.findViewById(R.id.CartEventLocation);
        TextView pricetag = (TextView) rowView.findViewById(R.id.PriceTag);

        Typeface mont = Typeface.createFromAsset(getContext().getAssets(),  "fonts/mont.ttf");
        CartEventInfo.setTypeface(mont);
        CartEventName.setTypeface(mont);
        CartEventLocation.setTypeface(mont);
        pricetag.setTypeface(mont);
//        remove.setTypeface(mont);


        Log.e("PV", "paid bochesh = "+paid.get(position));

        if(paid.get(position).equals("0")) {
            unpaid.setImageResource(R.drawable.unpaid);

        }
        else
            unpaid.setImageResource(R.drawable.paid);

        if(played.get(position).equals("0")) {
            unplayed.setImageResource(R.drawable.unplayed);
        }
        else
            unplayed.setImageResource(R.drawable.played);




        rowView.setBackgroundColor(Color.WHITE);
        CartEventInfo.setText((CharSequence)eventID_ticket.get(position));
        CartEventName.setText((CharSequence)eventName_ticket.get(position));
        CartEventLocation.setText((CharSequence)eventLocation_ticket.get(position));
        pricetag.setText((CharSequence)eventPrice_ticket.get(position));


        Log.e("PV", "yes"+userName_ticket.get(position)+eventName_ticket.get(position));
        return rowView;
    }


}
