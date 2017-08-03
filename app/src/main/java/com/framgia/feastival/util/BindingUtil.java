package com.framgia.feastival.util;

import android.databinding.BindingAdapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framgia.feastival.R;
import com.framgia.feastival.databinding.FrameGroupCreateBinding;
import com.framgia.feastival.databinding.FrameRestaurantDetailBinding;
import com.framgia.feastival.screen.main.MainViewModel;

import static com.framgia.feastival.screen.main.MainViewModel.STATE_CREATE_GROUP;
import static com.framgia.feastival.screen.main.MainViewModel.STATE_SHOW_GROUP_DETAIL;
import static com.framgia.feastival.screen.main.MainViewModel.STATE_SHOW_RESTAURANT_DETAIL;

/**
 * Created by tmd on 01/08/2017.
 */
public class BindingUtil {
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
                                        LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    private static LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);

    @BindingAdapter("state")
    public static void setBottomSheetState(LinearLayout rootLiear, MainViewModel
        mainViewModel) {
        View mView;
        rootLiear.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(mainViewModel.getContext());
        View bottomSheet = mainViewModel.getBottomSheet();
        switch (mainViewModel.getState()) {
            case STATE_SHOW_RESTAURANT_DETAIL:
                FrameRestaurantDetailBinding restaurantDetailBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout
                        .frame_restaurant_detail, rootLiear, false);
                restaurantDetailBinding.setViewModel(mainViewModel.getRestaurantDetailViewModel());
                mView = restaurantDetailBinding.getRoot();
                rootLiear.addView(mView, mParams);
                break;
            case STATE_CREATE_GROUP:
                FrameGroupCreateBinding createGroupBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout
                        .frame_group_create, rootLiear, false);
                createGroupBinding.setViewModel(mainViewModel.getCreateGroupViewModel());
                mView = createGroupBinding.getRoot();
                rootLiear.addView(mView, mParams);
                break;
            case STATE_SHOW_GROUP_DETAIL:
                // TODO: 02/08/2017
                break;
        }
    }
}
