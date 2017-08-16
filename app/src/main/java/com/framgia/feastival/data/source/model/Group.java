package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 01/08/2017.
 */
public class Group {
    @SerializedName("id")
    private int mId;
    @SerializedName("category_id")
    private int mCategoryId;
    @SerializedName("restaurant_id")
    private int mRestaurantId;
    @SerializedName("creator_id")
    private int mCreatorId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("time")
    private String mTime;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("latitude")
    private float mLatitude;
    @SerializedName("longitude")
    private float mLongtitude;
    @SerializedName("size")
    private int mSize;
    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("completed")
    private boolean mCompleted;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdateAt;

    public int getId() {
        return mId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public int getRestaurantId() {
        return mRestaurantId;
    }

    public int getCreatorId() {
        return mCreatorId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTime() {
        return mTime;
    }

    public String getAddress() {
        return mAddress;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public float getLongtitude() {
        return mLongtitude;
    }

    public int getSize() {
        return mSize;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public void setRestaurantId(int restaurantId) {
        mRestaurantId = restaurantId;
    }

    public void setCreatorId(int creatorId) {
        mCreatorId = creatorId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setLatitude(float latitude) {
        mLatitude = latitude;
    }

    public void setLongtitude(float longtitude) {
        mLongtitude = longtitude;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public void setUpdateAt(String updateAt) {
        mUpdateAt = updateAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (mLatitude == ((Group) obj).getLatitude()
            && mLongtitude == ((Group) obj).getLongtitude()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
