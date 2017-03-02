package com.actualmx.actualmxnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.activities.HomeMainActivity;
import com.actualmx.actualmxnew.activities.MainActivity;
import com.actualmx.actualmxnew.models.HomeModelClass;

import java.util.ArrayList;

/**
 * Created by ENGS_CPU_33 on 9/22/2016.
 */
public class CustomGrid extends RecyclerView.Adapter<CustomGrid.ViewHolder>{
    private final LayoutInflater inflater;
    private Context mContext;
    ArrayList<HomeMainActivity.MyPojoCategoty> moreNews;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.hor_txt);
               /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = HomeModelClass.TYPE_GRID_VALUE;
        return type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=inflater.inflate(R.layout.orizentalitem, null);//.inflate(R.layout.orizentalitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.itemTitle.setText(moreNews.get(i).getName());
        holder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(mContext, MainActivity.class);
                i1.putExtra("index",i);
                mContext.startActivity(i1);

            }
        });

    }


    @Override
    public int getItemCount() {
        return moreNews.size();
    }

    public CustomGrid(Context c, ArrayList<HomeMainActivity.MyPojoCategoty> moreNews ) {
        //super(c,0,moreNews);
        mContext = c;
       this.moreNews =moreNews;
        inflater = LayoutInflater.from(mContext);
    }
   /* @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return moreNews.size();
    }

    @Override
    public Object getItem(int position) {
        return moreNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.orizentalitem, null);
            TextView textView = (TextView) grid.findViewById(R.id.hor_txt);
            textView.setText(moreNews.get(position).getName());
            grid.findViewById(R.id.hor_par).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, MainActivity.class);
                    i.putExtra("index",position);
                    mContext.startActivity(i);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
   */ }
