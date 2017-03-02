package com.actualmx.actualmxnew.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.activities.CaptureImageActivity;
import com.actualmx.actualmxnew.activities.MainActivity;
import com.actualmx.actualmxnew.adapter.SideMenuExpandableAdapter;
import com.actualmx.actualmxnew.listner.ExpandableListCallBack;
import com.actualmx.actualmxnew.listner.NavigationDrawerCallbacks;
import com.actualmx.actualmxnew.utill.ArraysContainer;
import com.actualmx.actualmxnew.utill.CircleImageView;
import com.actualmx.actualmxnew.utill.ConnectionManager;
import com.actualmx.actualmxnew.utill.Pref;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Office on 14-09-2015.
 */
public class DrawerFragment extends Fragment implements NavigationDrawerCallbacks, ExpandableListCallBack, View.OnClickListener {


    ExpandableListView expandableListView;
    // Side panel Header
    RelativeLayout relativeLayout;
    TextView textRegister;
    ImageView circularImageView;
    // Side Panel Settings
    TextView textSettings;
    ImageView imageBtnSettings;
    RelativeLayout layoutSettings;
    // BookMarks
    RelativeLayout layoutBookmarks;
    TextView textBookmarks;
    ImageView imgBtnBookmarks;
    // Language
    LinearLayout layoutLanguage;
    TextView textLanguage;
    ToggleButton switchLanguage;
    ViewPager viewPager;
    int mCurrentSelectedPosition;
    View containerView;
    FragmentDrawerListener drawerListener;
    View mFragmentContainerView;
    Pref pref;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerCallbacks mCallbacks;
    private ImageView img_edit_profile;

    public DrawerFragment() {

    }

    @SuppressLint("ValidFragment")
    public DrawerFragment(Pref pref) {
        this.pref = pref;
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void collapseItem(int i) {
        expandableListView.collapseGroup(i);
    }

    @Override
    public void collapseItem(ExpandableListView listView) {

    }

    public void expandItem(int i) {
        expandableListView.expandGroup(i);
    }

    @Override
    public void setCategoryFollowed(String s, String s1, int i, boolean flag) {

    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mDrawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void onAttach(Activity activity1) {
        super.onAttach(activity1);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity1;
            return;
        }
        // Misplaced declaration of an exception variable
        catch (Exception e) {
            Log.e("Onattach", e.getMessage());
//            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        mDrawerToggle.onConfigurationChanged(configuration);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout;

        pref = new Pref(getActivity());


            layout = inflater.inflate(R.layout.en_fragment_navigation_drawer, container, false);


        mDrawerLayout = (DrawerLayout) layout.findViewById(R.id.drawer_layout);
        expandableListView = (ExpandableListView) layout.findViewById(R.id.lvExp);
      //  switchLanguage = (ToggleButton) layout.findViewById(R.id.img_language_switch);

        // Header
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.rl_menu_profile_info);
        circularImageView = (ImageView) layout.findViewById(R.id.img_menu_profile);
        textRegister = (TextView) layout.findViewById(R.id.txt_menu_profile);
        img_edit_profile= (ImageView)layout.findViewById(R.id.img_edit_profile);

        relativeLayout.setOnClickListener(this);
        circularImageView.setOnClickListener(this);
        textRegister.setOnClickListener(this);
        img_edit_profile.setOnClickListener(this);

        // Settings
      //  layoutSettings = (RelativeLayout) layout.findViewById(R.id.rl_settings_content);
      //  imageBtnSettings = (ImageView) layout.findViewById(R.id.img_settings);
       // textSettings = (TextView) layout.findViewById(R.id.txt_settings);

        // Bookmarks
       // layoutBookmarks = (RelativeLayout) layout.findViewById(R.id.rl_bookmarks_content);
      //  imgBtnBookmarks = (ImageView) layout.findViewById(R.id.img_bookmark);
      //  textBookmarks = (TextView) layout.findViewById(R.id.txt_bookmarks);


        // Language
       // layoutLanguage = (LinearLayout) layout.findViewById(R.id.rl_language_content);

        updateUserDetail(pref);
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar,
                      ViewPager viewPager) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        this.viewPager = viewPager;

        SideMenuExpandableAdapter sideMenuExpandableAdapter = new SideMenuExpandableAdapter
                (getActivity(), ArraysContainer.TableData,
                        expandableListView, viewPager, drawerLayout);

        expandableListView.setAdapter(sideMenuExpandableAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                updateUserDetail(pref);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    void selectItem(int i) {
        mCurrentSelectedPosition = i;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(i);
        }
    }

    public void updateUserDetail(Pref pref) {

        if (pref.isSessionOpen()) {

            try {
                textRegister.setText(pref.getStrData(Pref.userName));

                Picasso.with(getActivity()).load(pref.getStrData(Pref.userImage))
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile).into(circularImageView);

            } catch (Exception e) {
                Log.e("Profile Update", e.getMessage());
            }
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int i) {
Toast.makeText(getActivity(),"mgf"+i,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_edit_profile || v.getId() == R.id.txt_menu_profile) {

            if (pref.isSessionOpen()) {
                //Intent reg_intent = new Intent(getActivity(), ProfileActivity.class);
               // startActivity(reg_intent);

                getActivity().overridePendingTransition(0, 0);
            } else {
                //Intent reg_intent = new Intent(getActivity(), RegisterActivity.class);
                //startActivityForResult(reg_intent, 1111);
                //getActivity().overridePendingTransition(0, 0);
            }
            if(mDrawerLayout == null){
                return;
            }
            //mDrawerLayout.closeDrawers();
            Intent i = new Intent(getActivity(),CaptureImageActivity.class);
            startActivityForResult(i,111);

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111) {
            if (resultCode == 111){
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);
                    circularImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(resultCode==112){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                circularImageView.setImageBitmap(bitmap);
                /*attachment.setImageBitmap(bitmap);
                encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);*/
                // convert byte array to Bitmap
                // CaptureImageActivity.this.setResult(3,data);

            }
            // Bitmap bitmap = date.get
            { /*Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 1000, stream);
                byte[] byteArray = stream.toByteArray();

                encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // convert byte array to Bitmap
               // CaptureImageActivity.this.setResult(3,data);
                 Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); attachment.setImageBitmap(bitmap);*/
            }
        }
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
