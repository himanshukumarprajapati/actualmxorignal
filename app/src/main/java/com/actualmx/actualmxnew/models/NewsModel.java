package com.actualmx.actualmxnew.models;

/**
 * Created by ENGS_CPU_33 on 9/14/2016.
 */

public class NewsModel
{
    private String content;

    private String contents;

    private String post_status;

    private String post_author;

    private String ID;

    private String post_name;

    private String post_title;

    private String url;

    private String post_date;

    public NewsModel(String content, String contents, String post_status, String post_author, String ID, String post_name, String post_title, String url, String post_date) {
        this.content = content;
        this.contents = contents;
        this.post_status = post_status;
        this.post_author = post_author;
        this.ID = ID;
        this.post_name = post_name;
        this.post_title = post_title;
        this.url = url;
        this.post_date = post_date;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getContents ()
    {
        return contents;
    }

    public void setContents (String contents)
    {
        this.contents = contents;
    }

    public String getPost_status ()
    {
        return post_status;
    }

    public void setPost_status (String post_status)
    {
        this.post_status = post_status;
    }

    public String getPost_author ()
    {
        return post_author;
    }

    public void setPost_author (String post_author)
    {
        this.post_author = post_author;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getPost_name ()
    {
        return post_name;
    }

    public void setPost_name (String post_name)
    {
        this.post_name = post_name;
    }

    public String getPost_title ()
    {
        return post_title;
    }

    public void setPost_title (String post_title)
    {
        this.post_title = post_title;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getPost_date ()
    {
        return post_date;
    }

    public void setPost_date (String post_date)
    {
        this.post_date = post_date;
    }

}