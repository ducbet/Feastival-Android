package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tmd on 28/07/2017.
 */
public class RestaurantsResponse {
    @SerializedName("restaurants")
    private List<Restaurant> mRestaurants;
    @SerializedName("groups")
    private List<Group> mGroups;
    @SerializedName("users")
    private List<UserModel>  mUsers;

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public List<Group> getGroups() {
        return mGroups;
    }
}
