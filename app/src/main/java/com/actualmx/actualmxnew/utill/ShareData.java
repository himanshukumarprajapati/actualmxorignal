package com.actualmx.actualmxnew.utill;

import android.content.Context;
import android.content.Intent;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

public class ShareData {

    Context context;

    public ShareData(Context context, String title) {

        this.context = context;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, title);
        context.startActivity(Intent.createChooser(shareIntent, "Share via"));

    }


    public void sharePost(String strNewsUrl) {


        RequestBody formBody = new FormEncodingBuilder()
                .add("storyid", strNewsUrl)
                .build();

        String ss ="";

        //new ExecutePostRequest(context, 11, formBody).execute(ss);

    }


    public void likePost(String strNewsUrl) {


        RequestBody formBody = new FormEncodingBuilder()
                .add("storyid", strNewsUrl)
                .build();

        String ss = "";

       // new ExecutePostRequest(context, 22, formBody).execute(ss);

    }

    // Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
    // shareIntent.setType("text/plain");
    // shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
    // "Stupid Scanner tricks - http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html");
    // v.getContext().startActivity(Intent.createChooser(shareIntent,
    // "Share via"));

}
