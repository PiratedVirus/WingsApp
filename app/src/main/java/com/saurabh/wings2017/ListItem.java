package com.saurabh.wings2017;

/**
 * Created by saurabh on 16/07/17.
 */


// Method for Declaring elements to be displayed in scrollable list view
public class ListItem {

    private String head;
    private String desc;
    private String imageUrl;

    // Defining elements
    public ListItem(String head, String desc, String imageUrl) {
        this.head = head;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }
    //Returning elements
    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}