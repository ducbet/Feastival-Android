package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.CategoriesResponse;

import io.reactivex.Observable;

/**
 * Created by tmd on 04/08/2017.
 */
public interface CategoryDataSource {
    Observable<CategoriesResponse> getCategories();
}
