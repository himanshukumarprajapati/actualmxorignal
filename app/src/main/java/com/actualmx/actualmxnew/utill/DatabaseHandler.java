package com.actualmx.actualmxnew.utill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.actualmx.actualmxnew.models.NewsModel;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by R on 07-10-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "actualmx";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_BOOKMARK = "bookmark";
    public static final String KEY_ID = "id";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_CONTENTS = "contents";
    public static final String KEY_POST_STATUS = "post_status";
    public static final String KEY_POST_AUTHOR = "post_author";
    public static final String KEY_POST_ID = "post_id";
    public static final String KEY_POST_NAME = "post_name";
    public static final String KEY_POST_TITLE = "post_title";
    public static final String KEY_URL = "url";
    public static final String KEY_POST_DATE = "post_date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "create table " + TABLE_BOOKMARK + "(" + KEY_ID + " Integer Primary Key, "
                + KEY_CONTENT + " TEXT, " + KEY_CONTENTS + " TEXT, " + KEY_POST_STATUS + " TEXT, "
                + KEY_POST_AUTHOR + " TEXT, " + KEY_POST_ID + " TEXT, " + KEY_POST_NAME + " TEXT, "
                + KEY_POST_TITLE + " TEXT, " + KEY_URL + " TEXT, " + KEY_POST_DATE + " TEXT )";

        Log.e("create query", createQuery);

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
        onCreate(db);

    }

    public boolean addBookmark(String content, String contents, String post_status, String post_author, String ID,
                               String post_name, String post_title, String url, String post_date) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_CONTENT, content);
            contentValues.put(KEY_CONTENTS, contents);
            contentValues.put(KEY_POST_STATUS, post_status);
            contentValues.put(KEY_POST_AUTHOR, post_author);
            contentValues.put(KEY_POST_ID, ID);
            contentValues.put(KEY_POST_NAME, post_name);
            contentValues.put(KEY_POST_TITLE, post_title);
            contentValues.put(KEY_URL, url);
            contentValues.put(KEY_POST_DATE, post_date);

            db.insert(TABLE_BOOKMARK, null, contentValues);

            Log.e("record", "Added");

            return true;
        } catch (Exception e) {
            Log.e("DB Insert Error", "-->  " + e.getMessage());
            return false;
        }
    }

    public boolean removeBookmark(String id) {

        try {
            Log.e("goint to delete", "news id - " + id);

//            String querry2 = ("DELETE FROM " + TABLE_BOOKMARK + " WHERE "
//                    + KEY_NEWS_ID + "=" + id);

            SQLiteDatabase db = this.getWritableDatabase();
            int affected = db.delete(TABLE_BOOKMARK,
                    KEY_POST_NAME + " = ? ",
                    new String[]{id});

//            Cursor cursor = db.rawQuery("delete from " + TABLE_BOOKMARK + " where " + KEY_NEWS_ID
//                    + " = '" + id + "'", null);

            if (affected > 0) {
                Log.e("deleted", "news id - " + id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            Log.e("bookmarkException", e.toString());
            return false;
        }
    }

    public boolean isBookMarked(String id) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor res = db.rawQuery("select * from " + TABLE_BOOKMARK + " where " + KEY_POST_NAME + "= '" + id+"'",
                    null);

//            Cursor res1 = db.rawQuery(TABLE_BOOKMARK, new String[]{KEY_ID, KEY_NEWS_TITLE
//            }, KEY_NEWS_ID + "=?", new String[]{id}, null, null, null, null);
            res.moveToFirst();

            Log.e("curser length", res.getCount() + "");

            boolean alreadyExist = false;

            if (res.getCount() == 0) {
                alreadyExist = false;
            } else {
                alreadyExist = true;
            }

            return alreadyExist;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<NewsModel> getBookmarks() {
        ArrayList<NewsModel> bookmarks = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_BOOKMARK, null);

        Log.e("total bookmarks", res.getCount() + "");
        res.moveToFirst();

        if (res.getCount() > 0) {
            Log.e("added", "" + "Adding");
            for (int i = 0; i < res.getCount(); i++) {
//            while (res.moveToNext()) {
                res.moveToPosition(i);
                //String news_url = res.getString(res.getColumnIndex("news_url"));
                //boolean blnFound = news_url.contains(Global.app_language);

              //  if (blnFound){
                NewsModel model = new NewsModel(res.getString(1), res.getString(2),
                            res.getString(4), res.getString(5), res.getString(3), res.getString(6),
                            res.getString(7), res.getString(8), res.getString(9));
                    bookmarks.add(model);
            //    }

                Log.e("bookmark id", "-->  " + res.getString(1));
                Log.e("img arr", "" + res.getString(9));

            }

            Collections.reverse(bookmarks);

            return bookmarks;
        }

        return bookmarks;

    }

    public boolean clearBookmarks() {

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_BOOKMARK, "", null);

            Cursor cur = db.rawQuery("select * from " + TABLE_BOOKMARK, null);

            Log.e("clear db", cur.getCount() + "");

            if (cur.getCount() == 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.e("clear all", e.getMessage());
        }

        return false;
    }

}


/*

                    String news_url =res.getString(res.getColumnIndex("news_url"));

                    boolean blnFound = news_url.contains("en");

                if (blnFound){
                    TopStoriesModel model = new TopStoriesModel(res.getString(1), res.getString(2),
                            res.getString(4), res.getString(5), res.getString(3), res.getString(6),
                            res.getString(7), res.getString(8), res.getString(9), res.getString(10));
                    bookmarks.add(model);
                }

 */