package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 04/08/2017.
 */
public class Category {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }
}
