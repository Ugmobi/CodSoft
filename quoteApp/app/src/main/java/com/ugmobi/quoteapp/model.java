package com.ugmobi.quoteapp;

import androidx.annotation.Keep;

@Keep
public class model {
    String bio,position;
    boolean fav,bookmark,showad;

    public model() {
    }

    public model(String bio, boolean fav, boolean bookmark,boolean showad,String position) {
        this.bio = bio;
        this.fav = fav;
        this.bookmark = bookmark;
        this.showad = showad;
        this.position = position;
    }

    public boolean isShowad() {
        return showad;
    }

    public void setShowad(boolean showad) {
        this.showad = showad;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
