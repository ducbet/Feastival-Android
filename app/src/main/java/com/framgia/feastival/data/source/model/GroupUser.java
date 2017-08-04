package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 04/08/2017.
 */
public class GroupUser {
    @SerializedName("id")
    private int mId;
    @SerializedName("group_id")
    private int mGroupId;
    @SerializedName("user_id")
    private int mUserId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("status")
    private String mStatus;

    public int getId() {
        return mId;
    }

    public int isGroupId() {
        return mGroupId;
    }

    public int isUserId() {
        return mUserId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getStatus() {
        return mStatus;
    }
}
