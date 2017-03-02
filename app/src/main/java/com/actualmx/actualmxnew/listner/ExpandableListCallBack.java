// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.actualmx.actualmxnew.listner;


import android.widget.ExpandableListView;

public interface ExpandableListCallBack {

    public abstract void collapseItem(int i);

    public abstract void collapseItem(ExpandableListView listView);

    public abstract void expandItem(int i);

    public abstract void setCategoryFollowed(String s, String s1, int i, boolean flag);
}
