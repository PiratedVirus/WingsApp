package com.saurabh.wings2017;

import android.support.v7.widget.RecyclerView;
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


    public CivilEventAdapter(ArrayList<CivilEventList> civilList){
        this.civilList = civilList;
    }

    @Override
    public CivilEventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View CVView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_civil_home,parent,false);
//        content_civil_home may cause problem. IF so make seprate layout for cardview
        CivilEventListViewHolder civil_event_list_viewHolder = new CivilEventListViewHolder(CVView);
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

    public static class CivilEventListViewHolder extends  RecyclerView.ViewHolder{

        TextView CVEventName, CVEventExcerpt, CVEventLocation;


        public CivilEventListViewHolder(View civilView){
            super(civilView);

            CVEventName = (TextView) civilView.findViewById(R.id.CivilEventName);
            CVEventExcerpt = (TextView) civilView.findViewById(R.id.CivilEventExcerpt);
            CVEventLocation = (TextView) civilView.findViewById(R.id.CivilEventLocation);

        }
    }
}
