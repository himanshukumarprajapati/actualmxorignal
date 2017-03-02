package com.actualmx.actualmxnew.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.adapter.CustomGrid;
import com.actualmx.actualmxnew.adapter.GridAdapter;
import com.actualmx.actualmxnew.utill.ArraysContainer;

import java.util.ArrayList;

public class MyGridActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView otherRecyclerView;
    private GridLayoutManager manager;
    private GridAdapter otherRecycleViewAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageView bookmark;
    private ImageView home;
    private ImageView ab_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grid);
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.i1);
        images.add(R.drawable.i2);
        images.add(R.drawable.i3);
        images.add(R.drawable.i4);
        images.add(R.drawable.i5);
        images.add(R.drawable.i6);
        images.add(R.drawable.i7);
        images.add(R.drawable.i8);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        bookmark = (ImageView)findViewById(R.id.star);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MyGridActivity.this,BookMarksActivity.class);
                startActivity(ii);
            }
        });
        home = (ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MyGridActivity.this,MainActivity.class);
                ii.putExtra("index",0);
                startActivity(ii);
            }
        });
                ab_search = (ImageView)findViewById(R.id.ab_search);
        ab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MyGridActivity.this,SerchActivity.class);
                ii.putExtra("index",0);
                startActivity(ii);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.horizentalList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CustomGrid ap = new CustomGrid(this, ArraysContainer.TableData);
        recyclerView.setAdapter(ap);
        otherRecyclerView = (RecyclerView) findViewById(R.id.otherGridRecucleView);
        otherRecyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(this, 2);
        otherRecyclerView.setLayoutManager(manager);
        try {
            otherRecycleViewAdapter = new GridAdapter(this, ArraysContainer.TableData, images
            );
            otherRecyclerView.setAdapter(otherRecycleViewAdapter);

        } catch (Exception e1) {
            Log.e("error" + "  -- Adapter Exc", e1.getMessage());
        }
    }
}
