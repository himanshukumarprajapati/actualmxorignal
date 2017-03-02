package com.actualmx.actualmxnew.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.activities.HomeMainActivity;
import com.actualmx.actualmxnew.activities.MainActivity;
import com.actualmx.actualmxnew.fragments.OtherNewsFragment;
import com.actualmx.actualmxnew.fragments.OtherNewsFragmentListType;
import com.actualmx.actualmxnew.listner.ExpandableListCallBack;
import com.actualmx.actualmxnew.listner.MyFragments;
import com.actualmx.actualmxnew.utill.ArraysContainer;
import com.actualmx.actualmxnew.utill.Global;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Office on 09-09-2015.
 */
public class SideMenuExpandableAdapter extends BaseExpandableListAdapter implements ExpandableListCallBack {

    Context context;
    ArrayList<HomeMainActivity.MyPojoCategoty> grp;
    HashMap<String, ArrayList<HomeMainActivity.SubcatList>> child;

    LayoutInflater inflater;

    ViewHolderGr viewHolderGr;
    ViewHolderCh viewHolderCh;
    private boolean isclicked = true;

    ExpandableListView expandableListView;

    ArrayList<HomeMainActivity.SubcatList> sub;

    int lastGroupOpenedPos = -1;

//    Typeface typefaceBold;

    ViewPager mViewPager;
    DrawerLayout mDrawerLayout;
     private static MyFragments mMyFragments;

    int selected_position = -1;

    public SideMenuExpandableAdapter(Context context, ArrayList<HomeMainActivity.MyPojoCategoty> grp,
                                     ExpandableListView listView, ViewPager viewPager,
                                     DrawerLayout drawerLayout) {

        this.context = context;
        this.grp = grp;
        HashMap<String, ArrayList<HomeMainActivity.SubcatList>> child = new HashMap<>();
        for(int i =0;i<grp.size();i++){
            child.put(grp.get(i).getName(),grp.get(i).getSubcatList());
        }
        this.child = child;
        this.expandableListView = listView;

        mViewPager = viewPager;
        mDrawerLayout = drawerLayout;

//        typefaceBold = CustomFont.getArialBold(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getGroupCount() {
        // Log.e("group count", "" + grp.size());
        return grp.size();
    }

    @Override
    public int getChildrenCount(int i) {
        // Log.e("child count", "" + child.getRoboto(grp.getRoboto(i)).size());
        return grp.get(i).getSubcatList().size();
    }

    @Override
    public Object getGroup(int i) {
        // Log.e("gorup", "" + grp.getRoboto(i));
        return grp.get(i).getName();
    }

    private void setupViewPager(ViewPager viewPager,int index,int position) {
        MainActivity.ViewPagerAdapter adapter = (MainActivity.ViewPagerAdapter) viewPager.getAdapter();
        ArrayList<HomeMainActivity.MyPojoCategoty> dd = ArraysContainer.TableData;
        if (index == 0) {
            OtherNewsFragment ff = new OtherNewsFragment();
            Bundle bb = new Bundle();
            bb.putString("index", dd.get(index).getSubcatList().get(position).getIndex());
            bb.putString("id", dd.get(index).getSubcatList().get(position).getId());
            bb.putString("name", dd.get(index).getSubcatList().get(position).getName());
            bb.putString("category", dd.get(index).getSubcatList().get(position).getSubcategory());
            ff.setArguments(bb);
            adapter.addFrag(ff, dd.get(position).getName());
        } else {
            OtherNewsFragmentListType ff = new OtherNewsFragmentListType();
            Bundle bb = new Bundle();
            bb.putString("index", dd.get(index).getSubcatList().get(position).getIndex());
            bb.putString("id", dd.get(index).getSubcatList().get(position).getId());
            bb.putString("name", dd.get(index).getSubcatList().get(position).getName());
            bb.putString("category", dd.get(index).getSubcatList().get(position).getSubcategory());
            ff.setArguments(bb);
            adapter.addFrag(ff, dd.get(position).getName());
        }
        viewPager.setAdapter(adapter);
    }


    @Override
    public Object getChild(int i, int i1) {
        // Log.e("child = ", child.getRoboto(grp.getRoboto(i)).getRoboto(i1));
        return child.get(i);
    }

    @Override
    public long getGroupId(int i) {
        // Log.e("group id", "" + i);
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        // Log.e("child id", "" + i1);
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPos, final boolean isExpanded, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.side_menu_group_layout, null);
            viewHolderGr = new ViewHolderGr(view);
            view.setTag(viewHolderGr);
        } else {
            viewHolderGr = (ViewHolderGr) view.getTag();
        }

        viewHolderGr.textView.setText(grp.get(groupPos).getName());
//        viewHolderGr.textView.setTypeface(typefaceBold);

       /* if (getChildrenCount(groupPos) > 0) {
            viewHolderGr.btnExpand.setVisibility(View.VISIBLE);
        } else {
            viewHolderGr.btnExpand.setVisibility(View.GONE);
        }

        try {

            if (ArraysContainer.TableData != null && ArraysContainer.TableData.get(groupPos).getSubcatList().size()>0) {
                viewHolderGr.btnExpand.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.childindicator));

                viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
            } else {
                viewHolderGr.btnExpand.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.img_drop_down));
                viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("##005395"));
            }
        } catch (Exception ee) {
            Log.e("Crash", ee.getMessage());
            viewHolderGr.btnExpand.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.childindicator));
            viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
        }*/

        viewHolderGr.iconCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (true){
                    viewHolderGr.btnExpand.setImageDrawable(context.getResources()
                            .getDrawable(R.drawable.img_drop_down));
                    viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
                }*/

                if (lastGroupOpenedPos != -1 && groupPos != lastGroupOpenedPos) {
                    expandableListView.collapseGroup(lastGroupOpenedPos);
                    viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
                  //  ArraysContainer.TableData.set(lastGroupOpenedPos, "gfh");
                }

                lastGroupOpenedPos = groupPos;

                if (expandableListView.isGroupExpanded(groupPos)) {
                    expandableListView.collapseGroup(groupPos);
                    viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
                } else {
                    expandableListView.expandGroup(groupPos);
                    viewHolderGr.gprLayout.setBackgroundColor(Color.parseColor("#005395"));
                }

                if (isExpanded) {
                 //  ArraysContainer.TableData.set(groupPos, false);
                } else {
                   // ArraysContainer.TableData.set(groupPos, true);
                }

                notifyDataSetChanged();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.childPagerPos = -1;
                mViewPager.setCurrentItem(groupPos);
                mDrawerLayout.closeDrawers();
                notifyDataSetChanged();

              // mViewPager.setCurrentItem(ArraysContainer.TableData.get(groupPos).getSubcatList().get(0).getIndex()).g;
              //  mDrawerLayout.closeDrawers();

            }
        });

        return view;
    }

    @Override
    public View getChildView(final int groupPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.side_menu_child_layout, null);
            viewHolderCh = new ViewHolderCh(view);
            view.setTag(viewHolderCh);
        } else {
            viewHolderCh = (ViewHolderCh) view.getTag();
        }

        sub = new ArrayList<>();
        sub = child.get(getGroup(groupPos));

        viewHolderCh.textView.setText(sub.get(childPos).getName());
//        viewHolderCh.textView.setTypeface(typefaceBold);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.childPagerPos = childPos;
                Global.parentPagerPosnrw =groupPos;
                mMyFragments.forceLoding(sub.get(childPos).getName());
                mViewPager.getAdapter().notifyDataSetChanged();
                //setupViewPager(mViewPager,groupPos,childPos);
                mViewPager.setCurrentItem(groupPos);
                mDrawerLayout.closeDrawers();
                notifyDataSetChanged();


               /* SubCategoryChildSelection childSelection = (SubCategoryChildSelection)
                        ArraysContainer.tabFragment.get(groupPos);
                childSelection.selectChild(childPos);*/
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void collapseItem(int i) {

    }

    @Override
    public void collapseItem(ExpandableListView listView) {

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            listView.collapseGroup(i);
        }

    }

    @Override
    public void expandItem(int i) {
        expandableListView.expandGroup(i);
    }

    @Override
    public void setCategoryFollowed(String s, String s1, int i, boolean flag) {

    }

    public class ViewHolderGr {

        TextView textView;
        //ImageButton btnExpand;
        CheckBox iconCheck;
        LinearLayout gprLayout;

        public ViewHolderGr(View view) {

            textView = (TextView) view.findViewById(R.id.textGrouptitle);
           // btnExpand = (ImageButton) view.findViewById(R.id.iconGroupExpander);
            iconCheck = (CheckBox) view.findViewById(R.id.iconGroupExpanderCB);
            gprLayout = (LinearLayout) view.findViewById(R.id.grp_layout);

        }

    }

    public class ViewHolderCh {

        TextView textView;
        ImageView icon;

        public ViewHolderCh(View view) {

            textView = (TextView) view.findViewById(R.id.textChildtitle);

        }

    }

    public static void registremydemo(MyFragments mMyFragments0){
        mMyFragments =mMyFragments0;
    }
}
