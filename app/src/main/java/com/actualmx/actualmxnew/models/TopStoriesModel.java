package com.actualmx.actualmxnew.models;

import android.util.Log;

/**
 * Created by Office on 24-08-2015.
 */
public class TopStoriesModel {

    String id;
    String title;
    String summary;
    String url;
    String updated;
    int timestamp;
    String author;
    String desc;
    String newsUrl;
    String imageurl;
    String imagedesc;
    String imagetitle;
    String thumburl;
    String thumbdesc;
    String thumbtitle;
    String videourl;
    String videodesc;
    String videotitle;
    // Moere News
    String mid;
    String mtitle;
    String msummary;
    String murl;
    String mupdated;
    int mtimestamp;
    String mauthor;
    String mdesc;
    String mnewsUrl;
    String mimageurl;
    String mimagedesc;
    String mimagetitle;
    String mthumburl;
    String mthumbdesc;
    String mthumbtitle;
    String mvideourl;
    String mvideodesc;
    String mvideotitle;
    // video
    String vid;
    String vtitle;
    String vDescription;
    String youtubeCode;
    String vurl;
    String vNewsurl;
    // Programs
    String progid;
    String progTitle;
    String progDesc;
    String progYoutubeCode;
    String progUpdated;
    String progcatId;
    String progUrl;
    String newsType;
    String videoDetailPage;
    String newsBaseUrl;


    public TopStoriesModel() {

    }

    public TopStoriesModel(String vid, String vtitle, String vDescription, String youtubeCode,
                           String url, String news_url, int temp) {

        this.vid = vid;
        this.vtitle = vtitle;
        this.vDescription = vDescription;
        this.youtubeCode = youtubeCode;
        this.vNewsurl = news_url;
        this.vurl = url;

    }

    // Top Stories
    public TopStoriesModel(String id, String title, String summary, String url, String updated,
                           int timestamp, String author, String desc, String newsUrl,
                           String imageurl, String imagedesc, String imagetitle, String thumburl,
                           String thumbdesc, String thumbtitle, String videourl, String videodesc,
                           String videotitle) {

        Log.e("Top Stories", "Started");

        this.id = id;
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.updated = updated;
        this.timestamp = timestamp;
        this.author = author;
        this.desc = desc;
        this.newsUrl = newsUrl;
        this.imageurl = imageurl;
        this.imagedesc = imagedesc;
        this.imagetitle = imagetitle;
        this.thumburl = thumburl;
        this.thumbdesc = thumbdesc;
        this.thumbtitle = thumbtitle;
        this.videourl = videourl;
        this.videodesc = videodesc;
        this.videotitle = videotitle;

    }

    // Just in
    public TopStoriesModel(String id, String title, String summary, String url, String updated,
                           String author, String desc, String newsUrl, String imageurl,
                           String imagedesc, String imagetitle, String thumburl, String thumbdesc,
                           String thumbtitle, String videourl, String videodesc, String videotitle) {

        Log.e("Just in", "Started");

        this.mid = id;
        this.mtitle = title;
        this.msummary = summary;
        this.murl = url;
        this.mupdated = updated;
//        this.timestamp = timestamp;
        this.mauthor = author;
        this.mdesc = desc;
        this.mnewsUrl = newsUrl;
        this.mimageurl = imageurl;
        this.mimagedesc = imagedesc;
        this.mimagetitle = imagetitle;
        this.mthumburl = thumburl;
        this.mthumbdesc = thumbdesc;
        this.mthumbtitle = thumbtitle;
        this.mvideourl = videourl;
        this.mvideodesc = videodesc;
        this.mvideotitle = videotitle;

    }

    public TopStoriesModel(String id, String title, String url, String update, String desc,
                           String newsUrl, String imageurl, String imagedesc, String imagetitle,
                           String thumburl, String thumbdesc, String thumbtitle, String videourl,
                           String videodesc, String videotitle) {

        this.mid = id;
        this.mtitle = title;
//        this.msummary = summary;
        this.murl = url;
        this.mupdated = update;
//        this.timestamp = timestamp;
//        this.mauthor = author;
        this.mdesc = desc;
        this.mnewsUrl = newsUrl;
        this.mimageurl = imageurl;
        this.mimagedesc = imagedesc;
        this.mimagetitle = imagetitle;
        this.mthumburl = thumburl;
        this.mthumbdesc = thumbdesc;
        this.mthumbtitle = thumbtitle;
        this.mvideourl = videourl;
        this.mvideodesc = videodesc;
        this.mvideotitle = videotitle;

    }

    public TopStoriesModel(String progid, String progTitle, String progDesc, String progYoutubeCode,
                           String progUpdated, String progcatId) {

        this.progid = progid;
        this.progTitle = progTitle;
        this.progDesc = progDesc;
        this.progYoutubeCode = progYoutubeCode;
        this.progUpdated = progUpdated;
        this.progcatId = progcatId;

    }

    // bookmark model
    public TopStoriesModel(String newsId, String newsTitle, String newsThumb, String newsImage,
                           String newsDesc, String newsUrl, String newsHtmlUrl, String newsType,
                           String youtubeCode, String videoDetaiPage, String newsBaseUrl) {
        this.id = newsId;
        this.title = newsTitle;
        this.thumburl = newsThumb;
        this.imageurl = newsImage;
        this.desc = newsDesc;
        this.url = newsUrl;
        this.newsUrl = newsHtmlUrl;
        this.newsType = newsType;
        this.progYoutubeCode = youtubeCode;
        this.videoDetailPage = videoDetaiPage;
        this.newsBaseUrl = newsBaseUrl;

    }

    public String getVurl() {
        return vurl;
    }

//    public TopStoriesModel(String progid, String progTitle, String progDesc, String progYoutubeCode,
//                           String progUpdated, String progcatId, String news_url, String url) {
//
//        this.progid = progid;
//        this.progTitle = progTitle;
//        this.progDesc = progDesc;
//        this.progYoutubeCode = progYoutubeCode;
//        this.progUpdated = progUpdated;
//        this.progcatId = progcatId;
//        this.newsUrl = news_url;
//        this.progUrl = url;
//
//    }


    public String getNewsBBaseURL() {
        return newsBaseUrl;
    }


    public String getvNewsurl() {
        return vNewsurl;
    }

    public String getProgUrl() {
        return progUrl;
    }

    public String getVideoDetailPage() {
        return videoDetailPage;
    }

    public String getNewsType() {
        return newsType;
    }

    public String getProgid() {
        return progid;
    }

    public String getProgTitle() {
        return progTitle;
    }

    public String getProgDesc() {
        return progDesc;
    }

    public String getProgYoutubeCode() {
        return progYoutubeCode;
    }

    public String getProgUpdated() {
        return progUpdated;
    }

    public String getProgcatId() {
        return progcatId;
    }

    public String getMid() {
        return mid;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getMsummary() {
        return msummary;
    }

    public String getMurl() {
        return murl;
    }

    public String getMupdated() {
        return mupdated;
    }

    public int getMtimestamp() {
        return mtimestamp;
    }

    public String getMauthor() {
        return mauthor;
    }

    public String getMdesc() {
        return mdesc;
    }

    public String getMnewsUrl() {
        return mnewsUrl;
    }

    public String getMimageurl() {
        return mimageurl;
    }

    public String getMimagedesc() {
        return mimagedesc;
    }

    public String getMimagetitle() {
        return mimagetitle;
    }

    public String getMthumburl() {
        return mthumburl;
    }

    public String getMthumbdesc() {
        return mthumbdesc;
    }

    public String getMthumbtitle() {
        return mthumbtitle;
    }

    public String getMvideourl() {
        return mvideourl;
    }

    public String getMvideodesc() {
        return mvideodesc;
    }

    public String getMvideotitle() {
        return mvideotitle;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImagedesc() {
        return imagedesc;
    }

    public void setImagedesc(String imagedesc) {
        this.imagedesc = imagedesc;
    }

    public String getImagetitle() {
        return imagetitle;
    }

    public void setImagetitle(String imagetitle) {
        this.imagetitle = imagetitle;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getThumbdesc() {
        return thumbdesc;
    }

    public void setThumbdesc(String thumbdesc) {
        this.thumbdesc = thumbdesc;
    }

    public String getThumbtitle() {
        return thumbtitle;
    }

    public void setThumbtitle(String thumbtitle) {
        this.thumbtitle = thumbtitle;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideodesc() {
        return videodesc;
    }

    public void setVideodesc(String videodesc) {
        this.videodesc = videodesc;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Video Model

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getvDescription() {
        return vDescription;
    }

    public void setvDescription(String vDescription) {
        this.vDescription = vDescription;
    }

    public String getYoutubeCode() {
        return youtubeCode;
    }

    public void setYoutubeCode(String youtubeCode) {
        this.youtubeCode = youtubeCode;
    }
}
