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
import com.actualmx.actualmxnew.activities.HomeMainActivity;
import com.actualmx.actualmxnew.activities.MainActivity;
import com.actualmx.actualmxnew.activities.NewsDetailPage;
import com.actualmx.actualmxnew.models.HomeModelClass;
import com.actualmx.actualmxnew.models.NewsModel;
import com.actualmx.actualmxnew.utill.CharecterEncode;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Office on 11-09-2015.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>
        implements PostDataExecute {

    ArrayList<HomeMainActivity.MyPojoCategoty> moreNews;
    ArrayList<Integer> images;

    Context context;
    LayoutInflater inflater;

    String actionBarName;

    ViewHolder viewHolder;

    public GridAdapter(Context context, ArrayList<HomeMainActivity.MyPojoCategoty> moreNews,ArrayList<Integer> images
                       ) {
        super();

        this.context = context;
        inflater = LayoutInflater.from(context);
       this.images =images;
        this.moreNews = moreNews;

    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = HomeModelClass.TYPE_GRID_VALUE;
        return type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int getType) {

        View view;
            view = inflater.inflate(R.layout.grig_single, null);
            viewHolder = new ViewHolder(view, getType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position==moreNews.size()){
            return;
        }
        int type = getItemViewType(position);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        holder.imageGrid.setImageResource(images.get(position));
        holder.textGrid.setText(moreNews.get(position).getName());
        holder.imageGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("index",position);
                context.startActivity(i);
            }
        });
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

        TextView textGrid;
        ImageView imageGrid;

        public ViewHolder(View v, int type) {
            super(v);
                    textGrid = (TextView) v.findViewById(R.id.txt);
                    imageGrid = (ImageView) v.findViewById(R.id.image);
        }
    }
}
