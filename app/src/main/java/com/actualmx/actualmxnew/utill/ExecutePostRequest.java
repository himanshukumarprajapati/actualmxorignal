package com.actualmx.actualmxnew.utill;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.RequestBody;


/**
 * Created by Office on 20-10-2015.
 */
public class ExecutePostRequest extends AsyncTask<String, Void, String> {

    Context context;
    int method;
    String key;
    String value;
    String data = "";
    RequestBody requestBody;
    PostDataExecute postDataExecute;
    private String JsonBody = "";

    public ExecutePostRequest(Context context, int method, String key, String value) {

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
        this.key = key;
        this.value = value;

        postDataExecute = (PostDataExecute) context;

    }

    public ExecutePostRequest(Context context, int method, RequestBody requestBody,PostDataExecute p,String Json) {

        try {
            Boolean isInternet = ConnectionManager.isNetworkOnline(context.getApplicationContext());
            if (isInternet == true) {
            } else {
                final Toast tag = Toast.makeText(context.getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
                tag.show();
                new CountDownTimer(9000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        tag.show();
                    }

                    public void onFinish() {
                        tag.show();
                    }
                }.start();
            }
            try {
                this.context = context;
                this.method = method;
                this.requestBody = requestBody;
                this.JsonBody = Json;
                postDataExecute = p/*(PostDataExecute) context*/;
            }
            catch(Exception e1){
            }
        }
        catch (Exception e)
        {
            Log.e("ExecutePostRequest ", "-> " + e.getMessage());
        }

    }

    @Override
    protected String doInBackground(String... params) {

        try {

            data = ExecuteHttpRequest.executeHttpPost(params[0] + "/", JsonBody, requestBody);

            Log.e("request url", params[0] + "/");
            Log.e("requestBody", requestBody.toString());

        } catch (Exception e) {
            Log.e("Executng Post", "-> " + e.getMessage());
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            if (s.length() > 0) {
                postDataExecute.onPostRequestSuccess(method, s);
            } else {
                postDataExecute.onPostRequestFailed(method, s);
            }
        } catch (Exception e) {
            postDataExecute.onPostRequestFailed(method, "Failed");
        }
    }

}
