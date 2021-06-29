package com.android.nmnewsagency.model;

import android.content.Intent;

/**
 * Created by apple on 11/7/16.
 */
public class Child {

    String name;


    public Integer getImg(int childPosition) {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    Integer img;

    public Child(String name,Integer img) {
        this.name = name;
        this.img=img;
    }

    public String getName() {
        return name;
    }
}