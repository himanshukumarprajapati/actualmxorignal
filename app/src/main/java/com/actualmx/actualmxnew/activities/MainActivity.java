package com.actualmx.actualmxnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.fragments.DrawerFragment;
import com.actualmx.actualmxnew.fragments.OtherNewsFragment;
import com.actualmx.actualmxnew.fragments.OtherNewsFragmentListType;
import com.actualmx.actualmxnew.utill.ArraysContainer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements com.actualmx.actualmxnew.fragments.DrawerFragment.FragmentDrawerListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int index =0;

    public ActionBarDrawerToggle actionBarDrawerToggle;
    public DrawerLayout leftDrawerLayout;
    private ViewPagerAdapter adapter;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ImageView bookmark;
    private ImageView search_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        index =this.getIntent().getIntExtra("index",index);
        toolbar.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
             /*   mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView,new MyGridActivity()).commit();
                tabLayout.setVisibility(View.GONE);
                findViewById(R.id.containerView).setVisibility(View.VISIBLE);*/
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(index);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white_color));
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        bookmark = (ImageView)findViewById(R.id.star);
        search_btn = (ImageView) findViewById(R.id.ab_search);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*tabLayout.setVisibility(View.VISIBLE);
                findViewById(R.id.containerView).setVisibility(View.GONE);*/
                Intent ii = new Intent(MainActivity.this,BookMarksActivity.class);
                startActivity(ii);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                leftDrawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
               // invalidateOptionsMenu();
            }


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
              //  invalidateOptionsMenu();
            }
        };

        leftDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, leftDrawerLayout, toolbar, viewPager);
        drawerFragment.setDrawerListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ArrayList<HomeMainActivity.MyPojoCategoty> dd = ArraysContainer.TableData;
        for(int i=0;i<dd.size();i++) {
            if(i==0) {
                OtherNewsFragment ff = new OtherNewsFragment();
                Bundle bb = new Bundle();
                bb.putString("index", dd.get(i).getIndex());
                bb.putString("id", dd.get(i).getId());
                bb.putString("name", dd.get(i).getName());
                bb.putString("category", dd.get(i).getSubcategory());
                ff.setArguments(bb);
                adapter.addFrag(ff, dd.get(i).getName());
            } else{
                OtherNewsFragmentListType ff = new OtherNewsFragmentListType();
                Bundle bb = new Bundle();
                bb.putString("index", dd.get(i).getIndex());
                bb.putString("id", dd.get(i).getId());
                bb.putString("name", dd.get(i).getName());
                bb.putString("category", dd.get(i).getSubcategory());
                ff.setArguments(bb);
                adapter.addFrag(ff, dd.get(i).getName());
            }
        }
        viewPager.setAdapter(adapter);
    }

      public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        //return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        view.setVisibility(View.VISIBLE);
    }

   

    //  @SuppressWarnings("StatementWithEmptyBody")
   /* @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}
