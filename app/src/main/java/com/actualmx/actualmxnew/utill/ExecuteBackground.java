package com.actualmx.actualmxnew.utill;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Office on 07-09-2015.
 */
public class ExecuteBackground extends AsyncTask<String, Void, String> {

    int method = -1;
    Context context;
    String url;
    String assetFileName;

    PostDataExecute postDataExecute;

    String data;

    public ExecuteBackground(Context context, int method) {

        Boolean isInternet =  ConnectionManager.isNetworkOnline(context.getApplicationContext());
        if (isInternet==true){
        }else {
            final Toast tag = Toast.makeText(context.getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(9000, 1000)
            {
                public void onTick(long millisUntilFinished) {tag.show();}
                public void onFinish() {tag.show();}
            }.start();
        }

        this.context = context;
        this.method = method;
        postDataExecute = (PostDataExecute) context;
    }

    public ExecuteBackground(Context context, int method, String url, String fileName) {

        Boolean isInternet =  ConnectionManager.isNetworkOnline(context.getApplicationContext());
        if (isInternet==true){
        }else {
            final Toast tag = Toast.makeText(context.getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(9000, 1000)
            {
                public void onTick(long millisUntilFinished) {tag.show();}
                public void onFinish() {tag.show();}
            }.start();
        }

        this.context = context;
        this.method = method;
        this.url = url;
        this.assetFileName = fileName;

        postDataExecute = (PostDataExecute) context;

    }

    public ExecuteBackground(Fragment context, int method, String url, String fileName) {

        isInternet(context);

        this.context = context.getActivity();
        this.method = method;
        this.url = url;
        this.assetFileName = fileName;

        postDataExecute = (PostDataExecute) context;

    }

    public ExecuteBackground(Fragment context, int method) {

        this.context = context.getActivity();
        this.method = method;
//        this.url = url;
//        this.assetFileName = fileName;

        postDataExecute = (PostDataExecute) context;

    }

    public void  isInternet(Fragment context){

        Boolean isInternet =  ConnectionManager.isNetworkOnline(context.getActivity());
        if (isInternet==true){
        }else {
            final Toast tag = Toast.makeText(context.getActivity(), "No Internet Connection", Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(9000, 1000)
            {
                public void onTick(long millisUntilFinished) {tag.show();}
                public void onFinish() {tag.show();}
            }.start();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            Log.e("===API Executed===", params[0]);

//            data = AssetAccess.getFileFromAsset(context, assetFileName);
            data = ExecuteHttpRequest.executeHttp(params[0], "");

            Log.e("response", data);

            return data;
        } catch (Exception e1 ) {
            Log.e("async error", e1.getMessage());
        }

//        catch (NoClassDefFoundError e3){
//            Log.e("async error", e3.getMessage());
//            e3.printStackTrace();
//        }

        return data;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        try {

            if (aVoid.length() > 0) {

                postDataExecute.onPostRequestSuccess(method, aVoid);

            } else {

                postDataExecute.onPostRequestFailed(method, aVoid);
            }
        } catch (Exception ee) {
            Log.e("Execution error ==", "-- " + ee.getMessage());
            postDataExecute.onPostRequestFailed(method, "Error");
        }

    }
}
