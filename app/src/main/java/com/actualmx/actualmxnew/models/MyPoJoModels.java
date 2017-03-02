package com.actualmx.actualmxnew.models;

import java.util.ArrayList;

/**
 * Created by ENGS_CPU_33 on 9/14/2016.
 */
public class MyPoJoModels {
    private ArrayList<NewsModel> news;

    private String status;

    public ArrayList<NewsModel> getNews ()
    {
        return news;
    }

    public void setNews (ArrayList<NewsModel> news)
    {
        this.news = news;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

}


