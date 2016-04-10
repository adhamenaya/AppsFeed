package com.adhamenaya.grability.appsfeed.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adhamenaya on 4/7/2016.
 */
public class Category {

    public static class CategoryAttribute{

        @SerializedName("im:id")
        public String id;

        public String label;
    }

    public CategoryAttribute attributes = new CategoryAttribute();
}
