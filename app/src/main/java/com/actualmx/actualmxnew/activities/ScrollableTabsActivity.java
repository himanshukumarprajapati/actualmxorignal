package com.actualmx.actualmxnew.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.fragments.OtherNewsFragment;
import com.actualmx.actualmxnew.fragments.OtherNewsFragmentListType;
import com.actualmx.actualmxnew.utill.ArraysContainer;

import java.util.ArrayList;
import java.util.List;


public class ScrollableTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
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
}
