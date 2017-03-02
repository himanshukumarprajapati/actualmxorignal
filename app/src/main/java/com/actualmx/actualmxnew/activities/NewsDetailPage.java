package com.actualmx.actualmxnew.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.models.TopStoriesModel;
import com.actualmx.actualmxnew.utill.DatabaseHandler;
import com.actualmx.actualmxnew.utill.Pref;
import com.actualmx.actualmxnew.utill.ShareData;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class NewsDetailPage extends AppCompatActivity implements View.OnClickListener, Html.ImageGetter {

    View headerView;
    String MimeType = "text/html";
    String Encoding = "UTF-8";
    ImageView imgBookmark, imgShare, imgBack;
    TextView txtHeaderTitle;
    //TextView txtTitle, txtNewsDescription, txtRecenttime;
    ImageView /*imgMainHeader*/ imgShareFaceBook, imgShareTwitter, imgShareWhatsApp;
    Bundle newsBundle;
    String strHeaderTitle, strNewsID, strTitle, strDescription, strThumbImgUrl, strImgUrl,
            strUpdated, timeStamp, strNewsUrl, strNewsHtmlUrl, strBaseUrl;
    //    Typeface typefaceArialBold, typefaceRoboto;
    WebView webViewDescription;
    ProgressBar webProgress;
    Pref pref;
    String content1;
    BufferedReader in = null;
    InputStream is = null;
    ArrayList<TopStoriesModel> alsoReadNews;
    RelativeLayout belowDetailpage;
    String url;
    boolean isBookmarked = false;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ScrollView scrollView;
    String likeCount = "", shareCount = "", commentCount = "";
    TextView txtLikeCount, txtShareCount, txtCommentCount, textAlso_read;
    private String scoure = "ActualMX Desk";
    private boolean loadingFinished;
    private boolean redirect;
    private Bundle bundle;
    private boolean isPose = false;
    private ImageView serch_btn;
    private DatabaseHandler dbHandler;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
            setContentView(R.layout.en_activity_news_detail_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        getIntentValues();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        alsoReadNews = new ArrayList<>();
        initObjects();
        dbHandler = new DatabaseHandler(NewsDetailPage.this);
        isBookmarked = dbHandler.isBookMarked(id);
        if (isBookmarked) {
            imgBookmark.setImageResource(R.drawable.star);
        }else {
            imgBookmark.setImageResource(R.drawable.star_unselected);
        }
        txtHeaderTitle.setText(strHeaderTitle);
        content1 = loadHtmlFromAsset();
        ParseNewsDetail();
        // Picasso.with(this).load(strImgUrl).into(imgMainHeader);
        imgBack.setOnClickListener(this);
        webProgress.setVisibility(View.VISIBLE);
        webViewDescription.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        imgShare.setOnClickListener(this);
        imgBookmark.setOnClickListener(this);
        imgShareWhatsApp.setOnClickListener(this);
        imgShareFaceBook.setOnClickListener(this);
        imgShareTwitter.setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();

    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* new ExecuteBackground(NewsDetailPage.this, 2).execute(ApiConfig.LikeUrlCall
                .replace("/", "").replace(Constants.baseUrl, ApiConfig.LikeUrl)
                .replace(Constants.APIKEY, ApiConfig.apiToken) + "/" + strNewsUrl);*/
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initObjects() {

//        typefaceArialBold = CustomFont.getArialBold(this);
//        typefaceRoboto = CustomFont.getRoboto(this);

        headerView = findViewById(R.id.include_DetailPage);
        ViewCompat.setElevation(headerView, 5);

        imgBookmark = (ImageView) headerView.findViewById(R.id.img_bookmarkHeader);;
        serch_btn = (ImageView) headerView.findViewById(R.id.img_serchHeader);
        imgShare = (ImageView) headerView.findViewById(R.id.img_shareDetailHeader);
        imgBack = (ImageView) headerView.findViewById(R.id.img_backButton);

        //txtRecenttime = (TextView) findViewById(R.id.txt_news_recentTime));


        txtLikeCount = (TextView) headerView.findViewById(R.id.txtLikeCount);
        txtShareCount = (TextView) headerView.findViewById(R.id.txtShareCount);

        belowDetailpage = (RelativeLayout) findViewById(R.id.layout_belowDetailPage);
        belowDetailpage.setVisibility(View.INVISIBLE);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        imgShareFaceBook = (ImageView) findViewById(R.id.img_FacebookShareNewsDetail);
        imgShareTwitter = (ImageView) findViewById(R.id.img_twitterShareNewsDetail);
        imgShareWhatsApp = (ImageView) findViewById(R.id.img_googleplusShareNewsDetail);

        if (isBookmarked) {
            imgBookmark.setImageResource(R.drawable.star);
        }

        txtHeaderTitle = (TextView) headerView.findViewById(R.id.txt_headerText);
        txtHeaderTitle.setOnClickListener(this);
        //txtTitle = (TextView) findViewById(R.id.txt_newsHeaderTitle);
//        txtTitle.setTypeface(typefaceArialBold);

        //txtNewsDescription = (TextView) findViewById(R.id.txt_newsDescription);
//        txtNewsDescription.setTypeface(typefaceRoboto);

        //imgMainHeader = (ImageView) findViewById(R.id.img_mainDetailImage);
        webProgress = (ProgressBar) findViewById(R.id.webprogressBar);
        webViewDescription = (WebView) findViewById(R.id.webviewDescription);
        WebSettings webSettings = webViewDescription.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewDescription.getSettings().setUseWideViewPort(false);

        webViewDescription.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webViewDescription.getSettings().setLoadWithOverviewMode(true);
        //  webviewDescription.setDrawingCacheEnabled(true);
        webViewDescription.setVerticalScrollBarEnabled(false);
        webViewDescription.setHorizontalScrollBarEnabled(false);
    }

    private void getIntentValues() {

        newsBundle = getIntent().getExtras();
        strHeaderTitle = newsBundle.getString("header");
        strTitle = newsBundle.getString("news_title");
        strDescription = newsBundle.getString("news_desc");
        strImgUrl = newsBundle.getString("img_url");
        id = newsBundle.getString("news_title");
        id = id.replace("-","");
        id =id.replace(" ","");
       // Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
       // strNewsID = newsBundle.getString("news_id");
       // strNewsUrl = newsBundle.getString("news_url");
       // Log.e("strNewsUrl", strNewsUrl);

    }

    private String loadHtmlFromAsset() {

        String content;
        try {
            StringBuilder buf = new StringBuilder();
                is = getAssets().open("display_english_news1.html");
                  in = new BufferedReader(new InputStreamReader(is));
            String str;
            boolean isFirst = true;

            while ((str = in.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    buf.append('\n');
                }
                buf.append(str);
            }

            content = buf.toString();
            content = new String(content.getBytes("ISO-8859-1"), "UTF-8");

            return content;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_backButton:
            case R.id.txt_headerText:

                finish();
                overridePendingTransition(0, 0);
                webViewDescription.stopLoading();

                break;

            case R.id.img_bookmarkHeader:

                if (isBookmarked) {

                    isBookmarked = dbHandler.removeBookmark(id);
                    if (isBookmarked) {
                        imgBookmark.setImageResource(R.drawable.star_unselected);
                    }
                    isBookmarked = false;
                } else {
                    newsBundle.getString("content");
                    isBookmarked = dbHandler.addBookmark("",
                            newsBundle.getString("news_desc"), "",
                            "",
                            newsBundle.getString("news_id"),
                            id,
                            newsBundle.getString("news_title"),
                            newsBundle.getString("img_url"),
                            newsBundle.getString("post_date")
                    );

                    if (isBookmarked) {
                        imgBookmark.setImageResource(R.drawable.star);
                    }
                }
                break;
            case R.id.img_shareDetailHeader:

                ShareData shareData = new ShareData(NewsDetailPage.this,getString(R.string.actualmax));

               // sharePost();

                break;

           /* case R.id.img_likeDetailHeader:

                likePost();

                break;
*/
          /*  case R.id.img_commentDetailHeader:
                cleverTap.event.push("Commented");

                Intent commentIntent = new Intent(NewsDetailPage.this, CommentActivity.class);
                commentIntent.putExtra("header", "Home");
                commentIntent.putExtra("news_id", strNewsID);
                commentIntent.putExtra("news_url", strNewsUrl);
                commentIntent.putExtra("news_title", CharecterEncode.decodeChar(strTitle));
                startActivity(commentIntent);
                overridePendingTransition(0, 0);

                break;*/

          /*  case R.id.img_whatsappShareNewsDetail:

                PackageManager pm = getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = strTitle + "\n\n" + strNewsHtmlUrl;

                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                    sharePost();

                } catch (PackageManager.NameNotFoundException e) {

                    Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

                break;*/
            case R.id.img_FacebookShareNewsDetail:

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    Toast.makeText(getApplicationContext(),"Please wait....",Toast.LENGTH_SHORT).show();

                  /*  ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(strTitle)
//                            .setContentDescription(strDescription)
                            .setContentUrl(Uri.parse("www.Actalmx.com"*//*strNewsHtmlUrl*//*))
                            .build();

                    shareDialog.show(linkContent);

                    sharePost();*/

                }

                break;

            case R.id.img_twitterShareNewsDetail:

                try {

                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                    intent.setType("text/plain");
                    String shareText = strTitle + "\n\n" + "www.Actalmx.com";//*strNewsHtmlUrl*//*;
                    intent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(intent);
                    sharePost();

                } catch (Exception e) {

                    Log.e("Twitter not found", e.getMessage());

                    Toast.makeText(this, "Twitter not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // restartActivity(this);
      /*  if(isPose) {
            restartActivity(this);
            isPose = false;
        }*/
    }

    @Override
    public Drawable getDrawable(String source) {

        try {

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(source).getContent());
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            LevelListDrawable d = new LevelListDrawable();
            d.addLevel(0, 0, drawable);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());

            Log.e("image ---", "Downoaded ---  " + source);

            return drawable;
        } catch (Exception e) {
            Log.e("image ---", "crashed ---  " + e.getMessage());
            return null;
        }

    }

//    JSONObject job1 = new JSONObject(resp);
//    if (job1.getBoolean("success")) {
//        likeCount = job1.getString("likes");
//        Log.e("likeCount",likeCount);
//        txtLikeCount.setText(likeCount);
//    }

    private void ParseNewsDetail() {

        try {

            //txtRecenttime.setText(timeStampCalculator.getDifferenceString());
            if (content1.contains("source_time")) {
                content1 = content1.replace("source_time","");
            }
            if (content1.contains("Loading_News_Title")) {
                content1 = content1.replace("Loading_News_Title", strTitle);
            }
            if (content1.contains("source_destination")) {
                content1 = content1.replace("source_destination", "Source: " + scoure);
            }
            if (content1.contains("MAIN_BANNER_IMAGE")) {
                content1 = content1.replace("MAIN_BANNER_IMAGE", strImgUrl);
            }
            if (content1.contains("Loading_Detail")) {
                strDescription = strDescription.replace("//platform.twitter.com", "https://platform.twitter.com");
                strDescription = strDescription.replace("//platform.instagram.com", "http://platform.instagram.com");
                content1 = content1.replace("Loading_Detail", strDescription);
            }
            webViewDescription.loadDataWithBaseURL(null, content1, MimeType, Encoding, null);
            webViewDescription.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!loadingFinished) {
                        redirect = true;
                    }

                    loadingFinished = false;
                    if (url != null && (url.startsWith("http://")
                            || url.startsWith("https://"))) {

                        Intent newsIntent = new Intent(NewsDetailPage.this, WebViewActivity.class);
                        newsIntent.putExtra("html_url", url);
                        startActivity(newsIntent);
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                    loadingFinished = false;
                    //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    webProgress.setVisibility(View.GONE);
                    webViewDescription.setVisibility(View.VISIBLE);
                    belowDetailpage.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    if (!redirect) {
                        loadingFinished = true;
                    }

                    if (loadingFinished && !redirect) {
                        // webProgress.setVisibility(View.GONE);
                        //HIDE LOADING IT HAS FINISHED
                        /*webProgress.setVisibility(View.GONE);
                        webViewDescription.setVisibility(View.VISIBLE);
                        belowDetailpage.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.VISIBLE);*/
                    } else {
                        redirect = false;
                    }


                    //imgMainHeader.requestFocus();
                    //txtTitle.requestFocus();
                    // webProgress.setVisibility(View.GONE);


                }
            });


        } catch (Exception ee) {
            Log.e("detail Exc", ee.getMessage());
        }

    }

    public void sharePost() {

      /*  cleverTap.event.push("Shared");
        Log.e("post Share Url", ApiConfig.ShareUrlCall
                .replace("/", "").replace(Constants.baseUrl, ApiConfig.ShareUrl)
                .replace(Constants.APIKEY, ApiConfig.apiToken));
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = preferences1.getString("user_id_", "");

        RequestBody formBody = new FormEncodingBuilder()
                .add("storyid", strNewsUrl)
                .add("user_id", user_id)

                .build();

        String ss = ApiConfig.ShareUrlCall
                .replace("/", "").replace(Constants.baseUrl, ApiConfig.ShareUrl)
                .replace(Constants.APIKEY, ApiConfig.apiToken);

        Log.e("sHARE PATH", ss);


        new ExecutePostRequest(NewsDetailPage.this, 11, formBody).execute(ss);*/

    }

    public void likePost() {
    /*    cleverTap.event.push("Liked");
        Log.e("post Like Url", ApiConfig.LikeUrlCall
                .replace("/", "").replace(Constants.baseUrl, ApiConfig.LikeUrl)
                .replace(Constants.APIKEY, ApiConfig.apiToken));

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = preferences1.getString("user_id_", "");

        RequestBody formBody = new FormEncodingBuilder()
                .add("storyid", strNewsUrl)
                .add("user_id", user_id)
                .build();


        String language = "";
        Boolean statusB = pref.getLanguage(Pref.languageKey);
        if (statusB.equals(false)) {
            language = "en";
        } else {
            language = "hi";
        }

        String ss = ApiConfig.LikeUrlCall
                .replace("/", "").replace(Constants.baseUrl, ApiConfig.LikeUrl)
                .replace(Constants.APIKEY, ApiConfig.apiToken);


        new ExecutePostRequest(NewsDetailPage.this, 22, formBody).execute(ss);
*/

    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(webViewDescription, (Object[]) null);

        } catch (Exception cnfe) {
        }
    }

   /* public static void restartActivity(Activity act){

        Intent intent=new Intent();
        if (new Pref(act).getLanguage(Pref.languageKey)) {
            intent.putExtra("header", act.getResources().getString(R.string.hi_top_stories));
        } else {
            intent.putExtra("header", "Top Stories");
        }
        Bundle newsBundle = act.getIntent().getExtras();
        intent.putExtra("news_id", newsBundle.getString("news_id"));
        intent.putExtra("news_title",newsBundle.getString("news_title"));
        intent.putExtra("news_desc",newsBundle.getString("news_desc"));
        intent.putExtra("img_url", newsBundle.getString("img_url"));
        intent.putExtra("news_url", newsBundle.getString("news_url"));
        intent.putExtra("base_url", newsBundle.getString("base_url"));
        intent.putExtra("thumb_img_url",  newsBundle.getString("thumb_img_url"));
        intent.putExtra("news_html_url", newsBundle.getString("news_html_url"));
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }*/
}
