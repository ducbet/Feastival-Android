package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 04/08/2017.
 */
public class GroupDetailResponse {
    @SerializedName("message")
    private String mMessage;
    @SerializedName("group")
    private Group mGroup;
    @SerializedName("group_user")
    private GroupUser mGroupUser;

    public String getMessage() {
        return mMessage;
    }

    public Group getListGroups() {
        return mGroup;
    }

    public GroupUser getGroupUser() {
        return mGroupUser;
    }
}
