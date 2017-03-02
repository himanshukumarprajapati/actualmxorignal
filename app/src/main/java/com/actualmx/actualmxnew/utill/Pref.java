package com.actualmx.actualmxnew.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Office on 17-09-2015.
 */
public class Pref {

    public static final String probingTimeStamp = "probing_timestamp";

    public static final String congigFileKey = "config";
    public static final String probingApiKey = "probing_api_key";
    public static final String homeFileKey = "home";
    public static final String languageKey = "language";
    public static final String isLanguageSet = "is_language_set";
    public static final String userName = "user_name";
    public static final String userEmail = "user_email";
    public static final String userImage = "user_image";
    public static final String Session = "session";
    public static final String isSession = "is_session";
    public static final String bookmarks = "bookmarks";
    public static final String ConfigCounter = "config_counter";

    // Feedback email
    public static final String feedbackMail = "feedback_mail";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Pref(Context context) {
        preferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
    }

    public int getConfigCouinter() {

        return preferences.getInt(ConfigCounter, 0);
    }

    public void setConfigCouinter(int timestamp) {
        editor = preferences.edit();
        editor.putInt(ConfigCounter, timestamp);
        editor.commit();
        Log.e("saved str", ConfigCounter + "  ---  " + timestamp);
    }

    public boolean isSessionOpen() {
        return preferences.getBoolean(isSession, false);
    }

    public void setSession(boolean bool) {
        editor = preferences.edit();
        editor.putBoolean(Session, bool);
        editor.putBoolean(isSession, true);
        editor.commit();
        Log.e("saved str", Session + "  ---  " + bool);
    }

    public void setTimeStamp(String key, int timestamp) {
        editor = preferences.edit();
        editor.putInt(key, timestamp);
        editor.commit();
        Log.e("saved str", key + "  ---  " + timestamp);
    }

    public int getTimeStamp(String key) {

        return preferences.getInt(key, 0);
    }

    public void setUserName(String key, String uNmae) {
        editor = preferences.edit();
        editor.putString(key, uNmae);
        editor.commit();
        Log.e("saved str", key + "  ---  " + uNmae);
    }

    public String getUserName(String key,String def) {

        return preferences.getString(key, def);
    }

    public boolean hasKey(String key) {

        return preferences.contains(key);
    }

    public void saveData(String key, String value) {

        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);

    }

    public String getStrData(String key) {

        return preferences.getString(key, "");
    }

    public void setbaseUrl(String key, String value) {

        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);

    }

    public String getBaseUrl(String key) {

        return preferences.getString(key, "");
    }

    public void setConTentNewsDetail(String key, String value) {

        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);

    }

    public String getConTentNewsDetail(String key) {

        return preferences.getString(key, "");
    }



    public void saveConfig(String value, boolean language) {

        editor = preferences.edit();
        editor.putString("main_config_string" + language, value);
        editor.commit();
        Log.e("saved str", "user_name" + "  ---  " + value);

    }

    public String getConfig(boolean language) {

        return preferences.getString("main_config_string" + language, "");
    }



    public void saveUserName(String value) {

        editor = preferences.edit();
        editor.putString("user_name", value);
        editor.commit();
        Log.e("saved str", "user_name" + "  ---  " + value);

    }

    public String getUserName() {

        return preferences.getString("user_name", "Anonymous");
    }

    public String getProbingApiKey(String key) {

        if (hasKey(key))
            return preferences.getString(key, "0");
        else
            return "0";
    }

    public boolean isLanguageSet() {

        return preferences.getBoolean(isLanguageSet, false);
    }

    public void setLanguage(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.putBoolean(isLanguageSet, true);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);
    }

    public boolean getLanguage(String key) {

        return preferences.getBoolean(key, false);
    }

    public void setNotifStatus(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.putBoolean(isLanguageSet, true);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);
    }

    public boolean getNotifStatus(String key) {

        return preferences.getBoolean(key, true);
    }


    public void setNotifSound(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        Log.e("saved str", key + "  ---  " + value);
    }

    public boolean getNotifSound(String key) {

        return preferences.getBoolean(key, false);
    }

}
