package com.actualmx.actualmxnew.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.adapter.OtherRecycleViewAdapter;
import com.actualmx.actualmxnew.adapter.SideMenuExpandableAdapter;
import com.actualmx.actualmxnew.listner.ForceListner;
import com.actualmx.actualmxnew.listner.MyFragments;
import com.actualmx.actualmxnew.listner.RecyclerViewScrollListener;
import com.actualmx.actualmxnew.models.MyPoJoModels;
import com.actualmx.actualmxnew.models.NewsModel;
import com.actualmx.actualmxnew.utill.ArraysContainer;
import com.actualmx.actualmxnew.utill.ConnectionManager;
import com.actualmx.actualmxnew.utill.ExecutePostRequest;
import com.actualmx.actualmxnew.utill.Global;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;


public class OtherNewsFragment extends Fragment implements PostDataExecute ,ForceListner,MyFragments {
private View rootView;
    private String name;
    private String index;
    private String category;
    private String id;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView otherRecyclerView;
    private GridLayoutManager manager;
    private View loadMoreFooter;
    private int page =1;
    private MyPoJoModels myPoJoModels;
    private OtherRecycleViewAdapter otherRecycleViewAdapter;
    private ArrayList<NewsModel> datanewList= new ArrayList<>();

    public OtherNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.other_news_feagment, container, false);
        name =this.getArguments().getString("name");
        SideMenuExpandableAdapter.registremydemo(this);
        id =this.getArguments().getString("id");
        category =this.getArguments().getString("category");
        index = this.getArguments().getString("index");
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressOther);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.otherSwipeLayout);
        otherRecyclerView = (RecyclerView) rootView.findViewById(R.id.otherGridRecucleView);
        otherRecyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(getActivity(), 2);
        otherRecyclerView.setLayoutManager(manager);
        loadMoreFooter = rootView.findViewById(R.id.include_load_more_footer);
        Boolean isInternet =  ConnectionManager.isNetworkOnline(getActivity());
        try {
            otherRecycleViewAdapter = new OtherRecycleViewAdapter(getActivity(),datanewList,
                    ArraysContainer.TableData.get(Integer.parseInt(index)).getName(),"http://www.actualmx.com/backup/articlelist.php" );
            otherRecyclerView.setAdapter(otherRecycleViewAdapter);

            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return otherRecycleViewAdapter.isHeader(position) ? manager.getSpanCount() : 1;
                }
            });

        } catch (Exception e1) {
            Log.e("error" + "  -- Adapter Exc", e1.getMessage());
        }
        if (isInternet){
            progressBar.setVisibility(View.VISIBLE);

            RequestBody formBody = new FormEncodingBuilder()
                    .add("category_name", "noticias")
                    .add("page", String.valueOf(1))
                    .build();
            Gson gson = new Gson();
            if(Global.childPagerPos!=-1) {
                name = ArraysContainer.TableData.get(Global.parentPagerPosnrw).getSubcatList().get(Global.childPagerPos).getName();
                Global.parentPagerPosnrw = -1;
                Global.childPagerPos = -1;
            }
            Log.e("myname",name);
                String JsonBody = gson.toJson(new Body(name, page + ""));
                new ExecutePostRequest(getActivity(), 1, formBody, this, JsonBody)
                        .execute("http://www.actualmx.com/backup/articlelist.php");


        }else {
            final Toast tag = Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(9000, 1000)
            {
                public void onTick(long millisUntilFinished) {tag.show();}
                public void onFinish() {tag.show();}
            }.start();
        }
        otherRecyclerView.addOnScrollListener(new RecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page) {

                page = page + 1;
                Log.e("Scroll page - nextPos", "" + page);
                Gson gson = new Gson();
                String JsonBody = gson.toJson(new Body(name,page+""));
                new ExecutePostRequest(getActivity(), 2, null,OtherNewsFragment.this,JsonBody)
                        .execute("http://www.actualmx.com/backup/articlelist.php");
                loadMoreFooter.setVisibility(View.VISIBLE);

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Gson gson = new Gson();
                String JsonBody = gson.toJson(new Body(name,page+""));
              /*  new ExecutePostRequest(getActivity(), 1, null,OtherNewsFragment.this,JsonBody)
                        .execute("http://www.actualmx.com/backup/articlelist.php");*/
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return rootView;
    }

    @Override
    public void onPostRequestSuccess(int method, String resp) {
        if (method == 1) {

            Log.e("HomeSubFragment", "executed");

                try {
                    Gson gson = new Gson();
                    myPoJoModels = gson.fromJson(resp,MyPoJoModels.class);

                  //  Toast.makeText(getActivity(),resp,Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    datanewList.clear();
                    datanewList.addAll(myPoJoModels.getNews());
                    otherRecycleViewAdapter.notifyDataSetChanged();

                } catch (Exception e1) {
                   Log.e("error" + "  -- Adapter Exc", e1.getMessage());
                }

                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

        } else if (method == 2) {

            try {
                Gson gson = new Gson();
                myPoJoModels = gson.fromJson(resp,MyPoJoModels.class);
                datanewList.addAll(myPoJoModels.getNews());
                otherRecycleViewAdapter.notifyDataSetChanged();
                Log.e("load more worked", "SuccessFully");
                swipeRefreshLayout.setRefreshing(false);
                loadMoreFooter.setVisibility(View.GONE);
            } catch (Exception ee) {
                Log.e("LOad More options", ee.getMessage());
            }
        } else if (method == 3) {

            try {
                progressBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                myPoJoModels = gson.fromJson(resp,MyPoJoModels.class);
                datanewList.addAll(myPoJoModels.getNews());
                otherRecycleViewAdapter.notifyDataSetChanged();
                Log.e("load more worked", "SuccessFully");
                swipeRefreshLayout.setRefreshing(false);
                loadMoreFooter.setVisibility(View.GONE);
            } catch (Exception ee) {
                Log.e("LOad More options", ee.getMessage());
            }
        }

    }

    @Override
    public void onPostRequestFailed(int method, String resp) {

    }

    @Override
    public void forceLoding(String name) {
        OtherNewsFragment.this.name = name;
        progressBar.setVisibility(View.VISIBLE);
        RequestBody formBody = new FormEncodingBuilder()
                .add("category_name", "noticias")
                .add("page", String.valueOf(1))
                .build();
        Gson gson =new Gson();
        String JsonBody = gson.toJson(new Body(name, 1 + ""));
        new ExecutePostRequest(getActivity(), 3, formBody, this, JsonBody)
                .execute("http://www.actualmx.com/backup/articlelist.php");
    }

    public class Body{
        public String category_name;
        public String page;

        public Body(String category_name, String page) {
            this.category_name = category_name;
            this.page = page;
        }
    }
}

/*


package fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.indiatv.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.OtherRecycleViewAdapter;
import asynctasks.ExecuteBackground;
import interfaces.PostDataExecute;
import models.TopStoriesModel;
import ui.RecyclerViewScrollListener;
import utils.ApiConfig;
import utils.ConnectionManager;
import utils.Constants;
import utils.ExecuteHttpRequest;
import utils.GoogleAnalyticsApp;
import utils.Pref;

public class OtherSubFragments extends Fragment implements PostDataExecute {

    // Shared Preference Class
    public Pref pref;
    ArrayList<TopStoriesModel> moreNews;
    ArrayList<TopStoriesModel> moreNewsTemp;
    TopStoriesModel model3;

    ProgressBar progressBar;

    String tabKey = "", tabName;
    String url, baseUrl, latestStoriesUrl;
    boolean isApiKeyEnable = false;
    boolean isTimeStampEnable = false;
    int timeStamp = 0;


    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView otherRecyclerView;
    OtherRecycleViewAdapter otherRecycleViewAdapter;
    GridLayoutManager manager;

    int lastPos = 0, nextPos = 0;

    View loadMoreFooter;
    LayoutInflater inflater1;
    boolean isRefreshing = false, isRefreshed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View otherView = inflater.inflate(R.layout.fragment_other_sub, container, false);

        inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pref = new Pref(getActivity());

        Log.e("sub Fragment", "Started");

//        lastPos = 0;
        nextPos = 0;

        try {
            Bundle bundle = getArguments();
            tabKey = bundle.getString("tab_key");

            isTimeStampEnable = bundle.getBoolean("is_time_stamp");
            isApiKeyEnable = bundle.getBoolean("is_api_key");

            Log.e("tabkey", tabKey);
        } catch (Exception ee) {
            Log.e("error tab", ee.getMessage());
        }

        moreNews = new ArrayList<>();
        moreNewsTemp = new ArrayList<>();

        progressBar = (ProgressBar) otherView.findViewById(R.id.progressOther);
        swipeRefreshLayout = (SwipeRefreshLayout) otherView.findViewById(R.id.otherSwipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.main_tab_bg_color);

        otherRecyclerView = (RecyclerView) otherView.findViewById(R.id.otherGridRecucleView);
        otherRecyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(getActivity(), 2);
        otherRecyclerView.setLayoutManager(manager);

        loadMoreFooter = otherView.findViewById(R.id.include_load_more_footer);

        url = getUrl();

        Boolean isInternet =  ConnectionManager.isNetworkOnline(getActivity());
        if (isInternet==true){
            progressBar.setVisibility(View.VISIBLE);

            new ExecuteBackground(OtherSubFragments.this, 1).execute(url);

        }else {
            final Toast tag = Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(9000, 1000)
            {
                public void onTick(long millisUntilFinished) {tag.show();}
                public void onFinish() {tag.show();}
            }.start();
        }


//        try {
//            Tracker tracker = ((GoogleAnalyticsApp) getActivity().getApplication()).getTracker(
//                    GoogleAnalyticsApp.TrackerName.GLOBAL_TRACKER);
////            tracker.enableAutoActivityTracking(true);
//            tracker.setScreenName("Other Page Screen");
//            tracker.send(new HitBuilders.ScreenViewBuilder().build());
//        } catch (Exception e) {
//            Log.e("Other analytics", "" + e.getMessage());
//        }

//        new ExecuteBackground(OtherSubFragments.this, 1).execute(url);

        otherRecyclerView.addOnScrollListener(new RecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page) {

                nextPos = nextPos + 1;
                Log.e("Scroll page - nextPos", "" + nextPos);

                url = getUrl();

                new ExecuteBackground(OtherSubFragments.this, 2).execute(url);
                loadMoreFooter.setVisibility(View.VISIBLE);

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                nextPos = 0;
                isRefreshing = true;
                url = getUrl();

//                trendingList.clear();
                moreNewsTemp.clear();
                new ExecuteBackground(OtherSubFragments.this, 1).execute(url);

            }
        });

        return otherView;
    }

    public String getUrl() {

        Bundle bundle = getArguments();
        baseUrl = bundle.getString("base_api_url");
        latestStoriesUrl = bundle.getString("detail_url");
        Log.e("Other base Url", baseUrl);
        Log.e("Other latest Url", latestStoriesUrl);

        if (isApiKeyEnable) {
            baseUrl = baseUrl + ApiConfig.apiToken;
        }

        try {
//            if (isTimeStampEnable) {
//            latestStoriesUrl = latestStoriesUrl.replace(Constants.TimeStamp, "" + timeStamp);
//            }
            if (isRefreshing) {
                latestStoriesUrl = latestStoriesUrl.replace(Constants.TimeStamp, "" + timeStamp);
            } else {
                latestStoriesUrl = latestStoriesUrl.replace(Constants.TimeStamp, "0");
            }

            latestStoriesUrl = latestStoriesUrl.replace(Constants.NextPrevious,
                    (isRefreshed ? Constants.Previous : Constants.Next));

            String url = baseUrl + latestStoriesUrl.replace(Constants.PagingStr, nextPos + "");

            return url;

        } catch (Exception e) {
            Log.e("Url Creation Error", e.getMessage());
        }

        return "";
    }

    public void parseHome(String data) {

        try {

            JSONObject job1 = new JSONObject(data);
            JSONArray morearr = job1.getJSONArray("stories");

            if (nextPos == 0 && job1.has("storytimestamp")) {
                pref.setTimeStamp(tabKey, job1.getInt("storytimestamp"));
            }

            Log.e("more Json", "" + morearr.length());

            for (int i = 0; i < morearr.length(); i = i + 1) {
                parseAtPosition(i, morearr);
            }

            Log.e("more news list  ===  11", moreNews.size() + "");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("post error  1111", e.getMessage());
        }
    }

    private void parseAtPosition(int i, JSONArray morearr) {

        try {

            JSONObject moreobj = morearr.getJSONObject(i);

            //----For Images-----
            String imgurl = "", imgdesc = "", imgtitle = "";

            if (moreobj.has("image")) {
                JSONObject imgbj = moreobj.getJSONObject("image");

                if (imgbj.has("url")) {
                    imgurl = imgbj.getString("url");
                }
                if (imgbj.has("title")) {
                    imgtitle = imgbj.getString("title");
                }
                if (imgbj.has("description")) {
                    imgdesc = imgbj.getString("description");
                }
            }

            // -----For Thumb-----
            String thmurl = "", thmdesc = "", thmtitle = "";

            if (moreobj.has("thumb")) {
                JSONObject thmbbj = moreobj.getJSONObject("thumb");

                if (thmbbj.has("url")) {
                    thmurl = thmbbj.getString("url");
                }
                if (thmbbj.has("title")) {
                    thmtitle = thmbbj.getString("title");
                }
                if (thmbbj.has("description")) {
                    thmdesc = thmbbj.getString("description");
                }
            }

            // -----------------
            String vdeourl = "", vdeodesc = "", vdeotitle = "";

            if (moreobj.has("video")) {
                JSONObject vdeobj = moreobj.getJSONObject("video");

                if (vdeobj.has("url")) {
                    vdeourl = vdeobj.getString("youtube_code");
                }
                if (vdeobj.has("title")) {
                    vdeotitle = vdeobj.getString("title");
                }
                if (vdeobj.has("description")) {
                    vdeodesc = vdeobj.getString("description");
                }
            }

            Log.e(tabKey + " title -- " + i + "  --", moreobj.getString("title"));

            model3 = new TopStoriesModel(moreobj.getString("id"),
                    moreobj.getString("title"), moreobj.getString("url"),
                    moreobj.getString("updated"), moreobj.getString("description"),
                    moreobj.getString("news_url"), imgurl, imgdesc, imgtitle, thmurl,
                    thmdesc, thmtitle, vdeourl, vdeodesc, vdeotitle);

            moreNews.add(model3);

        } catch (Exception ee) {
            ee.printStackTrace();
            Log.e(tabKey + "  --  Error--111", ee.getMessage());
        }

    }

    @Override
    public void onPostRequestSuccess(int method, String resp) {
        if (method == 1) {

            Log.e("HomeSubFragment", "executed");

            try {

                parseHome(resp);

                try {

                    otherRecycleViewAdapter = new OtherRecycleViewAdapter(getActivity(), moreNews,
                            tabKey.equals("") ? tabName.substring(0, 1).toUpperCase()
                                    + tabName.substring(1) : tabKey.equals("all") ?
                                    tabName.substring(0, 1).toUpperCase() + tabName.substring(1)
                                    : tabKey.substring(0, 1).toUpperCase() + tabKey.substring(1), baseUrl);
                    otherRecyclerView.setAdapter(otherRecycleViewAdapter);

                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return otherRecycleViewAdapter.isHeader(position) ? manager.getSpanCount() : 1;
                        }
                    });

                } catch (Exception e1) {
                    Log.e(tabKey + "  -- Adapter Exc", e1.getMessage());
                }

                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

            } catch (Exception ee) {
                ee.printStackTrace();
                Log.e("post error", ee.getMessage());
            }

        } else if (method == 2) {

            try {

                parseHome(resp);

                otherRecycleViewAdapter.notifyDataSetChanged();
                Log.e("load more worked", "SuccessFully");
                swipeRefreshLayout.setRefreshing(false);
                loadMoreFooter.setVisibility(View.GONE);

            } catch (Exception ee) {
                Log.e("LOad More options", ee.getMessage());
            }
        }
    }

    @Override
    public void onPostRequestFailed(int method, String resp) {
        Log.e("async error", resp);
        // Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
//        otherLayout.setVisibility(View.GONE);

    }

    public class ExecuteBackgroundTask extends AsyncTask<String, Void, String> {

        int method = -1;
        Context context;
        String url;
        String assetFileName;

        PostDataExecute postDataExecute;

        String data;

        public ExecuteBackgroundTask(Context context, int method, String url, String fileName) {

            this.context = context;
            this.method = method;
            this.url = url;
            this.assetFileName = fileName;

            postDataExecute = (PostDataExecute) context;

        }

        public ExecuteBackgroundTask(android.support.v4.app.Fragment context, int method, String url, String fileName) {

            this.context = context.getActivity();
            this.method = method;
            this.url = url;
            this.assetFileName = fileName;

            postDataExecute = (PostDataExecute) context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (pref.hasKey(Pref.congigFileKey)) {
//                    data = AssetAccess.getFileFromAsset(context, assetFileName);
//                } else {
                data = ExecuteHttpRequest.executeHttp(params[0], "");
//                }

                Log.e("data api", "" + data);


                return data;
            } catch (Exception e1) {


                Log.e("async error", e1.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            if (aVoid.length() > 0) {

                postDataExecute.onPostRequestSuccess(method, aVoid);

            } else {

                postDataExecute.onPostRequestFailed(method, aVoid);
            }

        }
    }
}

* */
