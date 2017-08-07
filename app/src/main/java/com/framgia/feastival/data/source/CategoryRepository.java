package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.CategoriesResponse;

import io.reactivex.Observable;

/**
 * Created by tmd on 07/07/2017.
 */
public class CategoryRepository implements CategoryDataSource {
    private CategoryDataSource mRemoteDataSource;

    public CategoryRepository(
        CategoryDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<CategoriesResponse> getCategories() {
        return mRemoteDataSource.getCategories();
    }
}
