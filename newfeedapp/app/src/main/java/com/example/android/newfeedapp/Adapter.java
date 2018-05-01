package com.example.android.newfeedapp;

import java.util.ArrayList;


public class Adapter {
    public String heading;
    public String relevantinfo;
    public String details;
    public String URL;
    public String Auhtor;

    public Adapter(String mheading, String mrelevantinfo,String mdetails,String murl,String mAuthor) {
        heading = mheading;
        relevantinfo = mrelevantinfo;
        details = mdetails;
        URL = murl;
        Auhtor=mAuthor;
    }

    public String getHeading() {
        return heading;
    }

    public String getRelevantinfo() {
        return relevantinfo;
    }

    public String getDetails() {
        return details;
    }

    public String getURL() {
        return URL;
    }
    public String getAuthor()
    {
        return Auhtor;
    }
}
