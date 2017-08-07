package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tmd on 04/08/2017.
 */
public class CategoriesResponse {
    @SerializedName("categories")
    private List<Category> mCategories;

    public List<Category> getCategories() {
        return mCategories;
    }
}
