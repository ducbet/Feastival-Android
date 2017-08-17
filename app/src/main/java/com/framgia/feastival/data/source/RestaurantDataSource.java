package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;

/**
 * Created by tmd on 07/07/2017.
 */
public interface RestaurantDataSource {
    Observable<RestaurantsResponse> getRestaurants();
    Observable<RestaurantsResponse> getRestaurants(LatLng location, double radius);
    Observable<GroupDetailResponse> getGroupDetail(int id);
    Observable<Group> createNewGroup(Group newGroup);
}
