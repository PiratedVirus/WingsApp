package com.saurabh.wings2017;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SaveSharedPreferences {

    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PHONE= "phone";
    static final String PREF_USER_EMAIL= "email";


    static final String BOOK_ID_GL1= "GL1";
    static final String BOOK_ID_GL2= "GL2";
    static final String BOOK_ID_GL3= "GL3";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setUserPhone(Context ctx, String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PHONE, phone);
        editor.commit();
    }
    public static void setUserEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.commit();
    }

    public static void setGuestOne(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(BOOK_ID_GL1, id);
        editor.commit();
    }

    public static void setGuestTwo(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(BOOK_ID_GL2, id);
        editor.commit();
    }
    public static void setGuestThree(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(BOOK_ID_GL3, id);
        editor.commit();
    }
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserPhone(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PHONE, "");

    }
    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static String getGuestOne(Context ctx) {
        return getSharedPreferences(ctx).getString(BOOK_ID_GL1, "");
    }

    public static String getGuestTwo(Context ctx) {
        return getSharedPreferences(ctx).getString(BOOK_ID_GL2, "");
    }

    public static String getGuestThree(Context ctx) {
        return getSharedPreferences(ctx).getString(BOOK_ID_GL3, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

}

