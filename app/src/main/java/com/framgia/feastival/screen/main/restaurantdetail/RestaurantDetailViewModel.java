package com.framgia.feastival.screen.main.restaurantdetail;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

import com.framgia.feastival.BR;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.screen.BaseViewModel;
import com.framgia.feastival.screen.main.MainViewModel;

/**
 * Created by tmd on 01/08/2017.
 */
public class RestaurantDetailViewModel extends BaseObservable {
    private BaseViewModel mBaseViewModel;
    private Restaurant mSelectedRestaurant;
    private RestaurantsGroupsAdapter mRestaurantsGroupsAdapter;
    private int mState;

    @Bindable
    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
        notifyPropertyChanged(BR.state);
    }

    public RestaurantDetailViewModel(MainViewModel mainViewModel) {
        mBaseViewModel = mainViewModel;
        mRestaurantsGroupsAdapter = new RestaurantsGroupsAdapter(mainViewModel);
    }

    @Bindable
    public Restaurant getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    public void setSelectedRestaurant(Restaurant selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
        notifyPropertyChanged(BR.selectedRestaurant);
        mRestaurantsGroupsAdapter.updateData(selectedRestaurant.getGroups());
    }

    public RestaurantsGroupsAdapter getRestaurantsGroupsAdapter() {
        return mRestaurantsGroupsAdapter;
    }

    public void changeStateBottomSheet() {
        BottomSheetBehavior<View> bottomSheetBehavior =
            ((MainViewModel) mBaseViewModel).getBottomSheetBehavior();
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mState = BottomSheetBehavior.STATE_COLLAPSED;
            ((MainViewModel) mBaseViewModel).setBottomSheetState(mState);
            return;
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mState = BottomSheetBehavior.STATE_EXPANDED;
            ((MainViewModel) mBaseViewModel).setBottomSheetState(mState);
            return;
        }
    }
}
