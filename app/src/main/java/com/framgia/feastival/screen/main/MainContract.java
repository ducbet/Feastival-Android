package com.framgia.feastival.screen.main;

import com.framgia.feastival.data.source.model.CategoriesResponse;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void setMapFragment(SupportMapFragment mapFragment);
        void onGetRestaurantsSuccess(Marker viewPoint, RestaurantsResponse restaurantsResponse);
        void onGetCategoriesSuccess(CategoriesResponse categoriesResponse);
        void onClickExistGroup(Group group);
        void onGetGroupDetailSuccess(GroupDetailResponse groupDetailResponse);
        void onGetFailed(Throwable e);
        void onGetNewGroupLocalSuccess(Group group);
        void onGetNewGroupLocalFailed(String e);
        void onPinNewViewPoint();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getRestaurants(Marker viewPoint, double radius);
        void getCategories();
        double getDistance(LatLng latLngA, LatLng latLngB);
        void getGroupDetail(int groupId);
    }
}
