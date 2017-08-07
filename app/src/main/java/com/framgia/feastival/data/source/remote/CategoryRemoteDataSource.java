package com.framgia.feastival.data.source.remote;

import com.framgia.feastival.data.service.FeastivalService;
import com.framgia.feastival.data.service.ServiceGenerator;
import com.framgia.feastival.data.source.CategoryDataSource;
import com.framgia.feastival.data.source.model.CategoriesResponse;

import io.reactivex.Observable;

/**
 * Created by tmd on 04/08/2017.
 */
public class CategoryRemoteDataSource implements CategoryDataSource {
    private FeastivalService mService;

    public CategoryRemoteDataSource() {
        mService = ServiceGenerator.createService(FeastivalService.class);
    }

    @Override
    public Observable<CategoriesResponse> getCategories() {
        return mService.getCategories();
    }
}
