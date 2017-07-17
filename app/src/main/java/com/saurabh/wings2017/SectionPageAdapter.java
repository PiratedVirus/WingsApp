package com.saurabh.wings2017;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 17/07/17.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {


    private final List<Fragment> mfragList = new ArrayList<>();
    private final List<String> mFragTitleList = new ArrayList<>();


    public void addFragment(Fragment fragment, String title){
        mfragList.add(fragment);
        mFragTitleList.add(title);
    }


    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragList.get(position);
    }

    @Override
    public int getCount() {
        return mfragList.size();
    }
}
