package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;

/**
 * Created by tmd on 07/07/2017.
 */
public class RestaurantRepository implements RestaurantDataSource {
    private RestaurantDataSource mRemoteDataSource;

    public RestaurantRepository(
        RestaurantDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<RestaurantsResponse> getRestaurants() {
        return mRemoteDataSource.getRestaurants();
    }

    @Override
    public Observable<RestaurantsResponse> getRestaurants(LatLng location, double radius) {
        return mRemoteDataSource.getRestaurants(location, radius);
    }

    @Override
    public Observable<GroupDetailResponse> getGroupDetail(int id) {
        return mRemoteDataSource.getGroupDetail(id);
    }

    @Override
    public Observable<Group> createNewGroup(Group newGroup) {
        return mRemoteDataSource.createNewGroup(newGroup);
    }
}
