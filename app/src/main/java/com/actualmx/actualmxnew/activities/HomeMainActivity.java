package com.actualmx.actualmxnew.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.utill.ArraysContainer;
import com.actualmx.actualmxnew.utill.ExecuteBackground;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.actualmx.actualmxnew.utill.Pref;
import com.google.gson.Gson;

import java.util.ArrayList;


public class HomeMainActivity extends Activity implements PostDataExecute {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_main1);
       /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        new ExecuteBackground(HomeMainActivity.this, 1).execute("http://188.166.90.36/social/menuNew.json");

    }

    @Override
    public void onPostRequestSuccess(int method, String resp) {
        Gson gson = new Gson();
        MyPojoCategoty  myPojoCategoty[]= gson.fromJson(resp,MyPojoCategoty[].class);
        ArrayList<MyPojoCategoty> dd = new ArrayList<>();
        for(int i = 0;i<myPojoCategoty.length;i++){
            dd.add(myPojoCategoty[i]);
        }
        ArraysContainer.TableData.addAll(dd);
        if(new Pref(HomeMainActivity.this).getUserName("user","r").equalsIgnoreCase("r")) {
            startActivity(new Intent(HomeMainActivity.this, LoginActivity.class));
            finish();
        } else{
            startActivity(new Intent(HomeMainActivity.this, MyGridActivity.class));
            finish();
        }
    }

    @Override
    public void onPostRequestFailed(int method, String resp) {

    }

    public class MyPojoCategoty
    {
        private String id;

        private ArrayList<SubcatList> subcatList;

        private String index;

        private String subcategory;

        private String name;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public ArrayList<SubcatList> getSubcatList ()
        {
            return subcatList;
        }

        public void setSubcatList (ArrayList<SubcatList> subcatList)
        {
            this.subcatList = subcatList;
        }

        public String getIndex ()
        {
            return index;
        }

        public void setIndex (String index)
        {
            this.index = index;
        }

        public String getSubcategory ()
        {
            return subcategory;
        }

        public void setSubcategory (String subcategory)
        {
            this.subcategory = subcategory;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }
    }



    public class SubcatList
    {
        private String id;

        private String index;

        private String subcategory;

        private String name;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getIndex ()
        {
            return index;
        }

        public void setIndex (String index)
        {
            this.index = index;
        }

        public String getSubcategory ()
        {
            return subcategory;
        }

        public void setSubcategory (String subcategory)
        {
            this.subcategory = subcategory;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

    }
}
