package com.actualmx.actualmxnew.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.activities.NewsDetailPage;
import com.actualmx.actualmxnew.models.HomeModelClass;
import com.actualmx.actualmxnew.models.NewsModel;
import com.actualmx.actualmxnew.utill.CharecterEncode;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.actualmx.actualmxnew.utill.ShareData;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Office on 11-09-2015.
 */
public class OtherRecycleViewAdapterNew extends RecyclerView.Adapter<OtherRecycleViewAdapterNew.ViewHolder>
        implements PostDataExecute {

    ArrayList<NewsModel> moreNews;

    Context context;
    LayoutInflater inflater;

    String actionBarName, baseurl;

    ViewHolder viewHolder;

    public OtherRecycleViewAdapterNew(Context context, ArrayList<NewsModel> moreNews, String name,
                                      String baseUrl) {
        super();

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.actionBarName = name;
        this.baseurl = baseUrl;
        this.moreNews = moreNews;

    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {

        int type = HomeModelClass.TYPE_GRID_VALUE;
        if (position == 0) {
            type = HomeModelClass.TYPE_HEADER_VALUE;
        }
        return type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int getType) {

        View view;

        if (getType == HomeModelClass.TYPE_HEADER_VALUE) {
            view = inflater.inflate(R.layout.other_layout_header, null);
            viewHolder = new ViewHolder(view, getType);
        } else {
            view = inflater.inflate(R.layout.other_layout_main_list_item_new, null);
            viewHolder = new ViewHolder(view, getType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int type = getItemViewType(position);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        switch (type) {

            case HomeModelClass.TYPE_HEADER_VALUE:

                final NewsModel model = moreNews.get(position);

                try {

                    holder.textheaderRecent.setText("5 hr");
                } catch (Exception e) {
                    Log.e("Other home Parse  1 ", "-->  " + e.getMessage());
                    e.printStackTrace();
                }

                try {
                    holder.textHeader.setText(CharecterEncode.decodeChar(model.getPost_title()));
                    Picasso.with(context).load(model.getUrl()).into(holder.imageHeader);
                } catch (Exception ee) {
                    Log.e("Other image  1 ", "-->  " + ee.getMessage());
                }

                holder.headerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent detailIntent = new Intent(context, NewsDetailPage.class);
                       detailIntent.putExtra("header", actionBarName);
                       detailIntent.putExtra("news_id", model.getID());
                      detailIntent.putExtra("news_title", CharecterEncode.decodeChar(model.getPost_title()));
                      detailIntent.putExtra("news_desc", CharecterEncode.decodeChar(model.getContents()));
                        detailIntent.putExtra("img_url", model.getUrl());
                      detailIntent.putExtra("base_url", baseurl);
                        detailIntent.putExtra("post_date", model.getPost_date());
                       context.startActivity(detailIntent);
                        ((Activity) context).overridePendingTransition(0, 0);

                    }
                });

                break;

            case HomeModelClass.TYPE_GRID_VALUE:

                final NewsModel model2 = moreNews.get(position);

                try {

                   holder.txtRecent.setText(model2.getPost_date());

                } catch (Exception e) {
                    Log.e("Other home Parse  2 ", "-->  " + e.getMessage());
                    e.printStackTrace();
                }

                try {
                    holder.textGrid.setText(CharecterEncode.decodeChar(model2.getPost_title()));

                    Picasso.with(context).load(model2.getUrl())
                            .placeholder(R.drawable.placeholder).into(holder.imageGrid);
                } catch (Exception ee) {
                    Log.e("image other", "-- " + ee.getMessage());
                }

                holder.childGridLeftLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailIntent = new Intent(context, NewsDetailPage.class);
                        detailIntent.putExtra("header", actionBarName);
                        detailIntent.putExtra("news_id", model2.getID());
                        detailIntent.putExtra("news_title", CharecterEncode.decodeChar(model2.getPost_title()));
                        detailIntent.putExtra("news_desc", CharecterEncode.decodeChar(model2.getContents()));
                        detailIntent.putExtra("img_url", model2.getUrl());
                        detailIntent.putExtra("base_url", baseurl);
                        context.startActivity(detailIntent);
                        ((Activity) context).overridePendingTransition(0, 0);

                    }
                });

                holder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        ShareData shareData = new ShareData(context, CharecterEncode.decodeChar(
//                                model1.getMtitle()) + "\n\n" + model1.getMnewsUrl());
//
//                        sharePost(model1.getMurl());
                        Log.e("share", "3");
                    }
                });

                holder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                ShareData shareData = new ShareData(context, "Welcome to ActualMX");

//                        sharePost(model1.getMurl());
                                Log.e("share", "3");
//                        Intent detailIntent = new Intent(context, CommentActivity.class);
//                        detailIntent.putExtra("header", actionBarName);
//                        detailIntent.putExtra("news_id", model1.getMid());
//                        detailIntent.putExtra("news_url", model1.getMurl());
//                        detailIntent.putExtra("news_title", CharecterEncode.decodeChar(model1.getMtitle()));
//                        context.startActivity(detailIntent);
//                        ((Activity) context).overridePendingTransition(0, 0);

                    }
                });

                break;

        }

    }


    @Override
    public int getItemCount() {
        return moreNews.size();
    }



    @Override
    public void onPostRequestSuccess(int method, String resp) {
        try {
            JSONObject jsonObject = new JSONObject(resp);
            if (method == 11 && jsonObject.getBoolean("success")) {
                //   Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show();
            } else if (method == 2 && jsonObject.getBoolean("success")) {
                //    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ee) {
            Log.e("Home Post Success", "-  " + ee.getMessage());
        }
    }

    @Override
    public void onPostRequestFailed(int method, String resp) {
        //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textHeader, textheaderRecent, textGrid, txtRecent;
        ImageView imageHeader, imageGrid;

        RelativeLayout headerLayout;
        LinearLayout childGridLeftLayout;

        ImageButton comment, share;

        public ViewHolder(View v, int type) {
            super(v);

            switch (type) {

                case HomeModelClass.TYPE_HEADER_VALUE:

                    headerLayout = (RelativeLayout) v.findViewById(R.id.headerMainListItemLayout);
                    textHeader = (TextView) v.findViewById(R.id.textHeaderTitle);
                    imageHeader = (ImageView) v.findViewById(R.id.imageHeaderFirst);
                    textheaderRecent = (TextView) v.findViewById(R.id.txtOtherRecentHeader);

                    break;

                case HomeModelClass.TYPE_GRID_VALUE:

                    childGridLeftLayout = (LinearLayout) v.findViewById(R.id.gridMoreLayout);

                    textGrid = (TextView) v.findViewById(R.id.textOtherListItem);
                    imageGrid = (ImageView) v.findViewById(R.id.imageOtherListItem);
                    share = (ImageButton) v.findViewById(R.id.btnShareOtherListItem);
                    txtRecent = (TextView) v.findViewById(R.id.btnRecentOtherListItem);
                    comment = (ImageButton) v.findViewById(R.id.btnCommentOtherListItem);

                    break;

            }

        }
    }
}
