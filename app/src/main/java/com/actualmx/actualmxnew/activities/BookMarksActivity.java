package com.actualmx.actualmxnew.activities;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.fragments.BookMarkFragment;

public class BookMarksActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView text_header;
    private ImageView serch_header;
    private ImageView share_header;
    private ImageView back_header;
    private ImageView bookmark_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView_1,new BookMarkFragment()).commit();
        init();
        text_header.setText("Back");
        serch_header.setVisibility(View.GONE);
        bookmark_header.setVisibility(View.GONE);
        share_header.setVisibility(View.GONE);
        back_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void init(){
        text_header = (TextView)findViewById(R.id.txt_headerText);
        serch_header = (ImageView)findViewById(R.id.img_serchHeader);
        share_header = (ImageView)findViewById(R.id.img_shareDetailHeader);
        back_header = (ImageView)findViewById(R.id.img_backButton);
        bookmark_header = (ImageView)findViewById(R.id.img_bookmarkHeader);
    }
}
