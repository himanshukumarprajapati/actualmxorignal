package com.actualmx.actualmxnew.utill;

/**
 * Created by Office on 07-09-2015.
 */
public interface PostDataExecute {

    void onPostRequestSuccess(int method, String resp);

    void onPostRequestFailed(int method, String resp);
}
