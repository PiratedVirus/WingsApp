package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by saurabh on 16/07/17.
 */

public class CivilEventAdapter extends RecyclerView.Adapter<CivilEventAdapter.CivilEventListViewHolder> {

//  in above CivilEventListViewHolder is generic viewholder class

// Just Declaring new constructor/instance. Defining in onBindViewHolder method

    ArrayList<CivilEventList> civilList = new ArrayList<CivilEventList>();
    Context CVCtx;


    public CivilEventAdapter(ArrayList<CivilEventList> civilList, Context CVCtx){
        this.civilList = civilList;
        this.CVCtx = CVCtx;
    }

    @Override
    public CivilEventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View CVView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_civil_home,parent,false);

        CivilEventListViewHolder civil_event_list_viewHolder = new CivilEventListViewHolder(CVView,CVCtx,civilList);
        return civil_event_list_viewHolder;


//        Returning object of CivilEventListViewHolder class.
    }

    @Override
    public void onBindViewHolder(CivilEventListViewHolder holder, int position) {
//        Fetching values from Array defined in string.xml Defining  the constructor

        CivilEventList CVLIST = civilList.get(position);

        holder.CVEventName.setText(CVLIST.getlCivilEventName());
        holder.CVEventExcerpt.setText(CVLIST.getlCivilEventExcerpt());
        holder.CVEventLocation.setText(CVLIST.getlCivilEventLocation());


    }

    @Override
    public int getItemCount() {
        return civilList.size() ;
    }



    public static class CivilEventListViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        TextView CVEventName, CVEventExcerpt, CVEventLocation;
        ArrayList<CivilEventList> civilList = new ArrayList<CivilEventList>();
        Context CVCtx;

//        pass these 3 arguments to line 37(
        public CivilEventListViewHolder(View civilView, Context CVCtx,ArrayList<CivilEventList> civilList){
            super(civilView);

//            Initiating context var(following context is declared before this constructor.

            this.CVCtx = CVCtx;

//            Initiating arraylist from constructor
            this.civilList = civilList;


//            Registering each card view so upon clicking can invoke onCLick(View v) method
            civilView.setOnClickListener(this);




            CVEventName = (TextView) civilView.findViewById(R.id.CivilEventName);
            CVEventExcerpt = (TextView) civilView.findViewById(R.id.CivilEventExcerpt);
            CVEventLocation = (TextView) civilView.findViewById(R.id.CivilEventLocation);

        }

        @Override
        public void onClick(View v) {


            int position = getAdapterPosition();

            CivilEventList civilClickObject = this.civilList.get(position);

            Intent civilEventIntent = new Intent(this.CVCtx,GenericEventHome.class);

            civilEventIntent.putExtra("EventName",civilClickObject.getlCivilEventName());
            civilEventIntent.putExtra("EventLocation",civilClickObject.getlCivilEventLocation());
            civilEventIntent.putExtra("EventPrice",civilClickObject.getlCivilEventPrice());
            civilEventIntent.putExtra("EventInfo",civilClickObject.getlCivilEventExcerpt());
            civilEventIntent.putExtra("EventRules",civilClickObject.getlCivilEventRules());
            civilEventIntent.putExtra("EventCriteria",civilClickObject.getlCivilEventCriteria());

            this.CVCtx.startActivity(civilEventIntent);

        }
    }
}
