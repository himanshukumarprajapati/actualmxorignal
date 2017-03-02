package com.actualmx.actualmxnew.models;

/**
 * Created by Office on 12-09-2015.
 */
public class HomeModelClass {

    public static final String TYPE_HEADER = "video";
    public static final int TYPE_HEADER_VALUE = 111;
    public static final String TYPE_GRID = "video";
    public static final int TYPE_GRID_VALUE = 222;
    public static final String TYPE_VIDEO = "video";
    public static final int TYPE_VIDEO_VALUE = 333;
    public static final String TYPE_MORE = "video";
    public static final int TYPE_MORE_VALUE = 444;
    public static final String TYPE_GALLERY = "video";
    public static final int TYPE_GALLERY_VALUE = 555;

    String listType;
    int listTypeValue;
    int position;

    public HomeModelClass(String type, int value) {
        this.listType = type;
        this.listTypeValue = value;
    }

    public HomeModelClass(String type, int value, int position) {
        this.listType = type;
        this.listTypeValue = value;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public int getListTypeValue() {
        return listTypeValue;
    }

    public void setListTypeValue(int listTypeValue) {
        this.listTypeValue = listTypeValue;
    }


}
