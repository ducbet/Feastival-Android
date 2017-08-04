package com.framgia.feastival.screen.main.restaurantdetail;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.ImageView;

import com.framgia.feastival.BR;
import com.framgia.feastival.R;
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

    public void changeStateBottomSheet(View view) {
        BottomSheetBehavior<View> bottomSheetBehavior =
            ((MainViewModel) mBaseViewModel).getBottomSheetBehavior();
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            ((ImageView) view).setImageResource(R.drawable.ic_keyboard_arrow_up_white_24px);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            ((ImageView) view).setImageResource(R.drawable.ic_keyboard_arrow_down_white_24px);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return;
        }
    }
}
