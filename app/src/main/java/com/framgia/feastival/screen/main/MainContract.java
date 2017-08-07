package com.framgia.feastival.screen.main;

import com.framgia.feastival.data.source.model.CategoriesResponse;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void setMapFragment(SupportMapFragment mapFragment);
        void onGetRestaurantsSuccess(RestaurantsResponse restaurantsResponse);
        void onGetCategoriesSuccess(CategoriesResponse categoriesResponse);
        void onGetFailed(Throwable e);
        void onGetNewGroupLocalSuccess(Group group);
        void onGetNewGroupLocalFailed(String e);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getRestaurants();
        void getRestaurants(LatLng location, double radius);
        void getCategories();
        double getDistance(LatLng latLngA, LatLng latLngB);
    }
}
