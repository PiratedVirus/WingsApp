package com.saurabh.wings2017;

/**
 * Created by saurabh on 16/07/17.
 */

//This class is behind the sence for CivilEventAdapter class

public class CivilEventList {

    //    Declare constructor to get values
    public CivilEventList(String CVName,String CVExcerpt,String CVLocation, String CVPrice, String CVRules, String CVCriteria){

//        Getting values
        this.setlCivilEventName(CVName);
        this.setlCivilEventExcerpt(CVExcerpt);
        this.setlCivilEventLocation(CVLocation);
        this.setlCivilEventPrice(CVPrice);
        this.setlCivilEventRules(CVRules);
        this.setlCivilEventCriteria(CVCriteria);

    }

    private String lCivilEventName,lCivilEventExcerpt,lCivilEventLocation,lCivilEventPrice,lCivilEventRules,lCivilEventCriteria;


    public String getlCivilEventPrice() {
        return lCivilEventPrice;
    }

    public void setlCivilEventPrice(String lCivilEventPrice) {
        this.lCivilEventPrice = lCivilEventPrice;
    }

    public String getlCivilEventRules() {
        return lCivilEventRules;
    }

    public void setlCivilEventRules(String lCivilEventRules) {
        this.lCivilEventRules = lCivilEventRules;
    }

    public String getlCivilEventCriteria() {
        return lCivilEventCriteria;
    }

    public void setlCivilEventCriteria(String lCivilEventCriteria) {
        this.lCivilEventCriteria = lCivilEventCriteria;
    }

    //    Getter and Setter methods for vars
    public String getlCivilEventName() {

        return lCivilEventName;
    }

    public void setlCivilEventName(String lCivilEventName) {
        this.lCivilEventName = lCivilEventName;
    }

    public String getlCivilEventExcerpt() {
        return lCivilEventExcerpt;
    }

    public void setlCivilEventExcerpt(String lCivilEventExcerpt) {
        this.lCivilEventExcerpt = lCivilEventExcerpt;
    }

    public String getlCivilEventLocation() {
        return lCivilEventLocation;
    }

    public void setlCivilEventLocation(String lCivilEventLocation) {
        this.lCivilEventLocation = lCivilEventLocation;
    }


}
