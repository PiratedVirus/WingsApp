package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.avinash_dharmadhikari_card,R.drawable.varun_agarwal_card,R.drawable.sankarshan_karhade_card};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0){
                    //Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                    Intent gTwo = new Intent(v.getContext(), GuestTwo.class);
                    v.getContext().startActivity(gTwo);
                } else if(position == 1){
                    Intent readMore = new Intent(v.getContext(), GuestOne.class);
                    v.getContext().startActivity(readMore);
                    //Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else if(position == 2) {
                    Intent gThree = new Intent(v.getContext(), GuestThree.class);
                    v.getContext().startActivity(gThree);
                }

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}


