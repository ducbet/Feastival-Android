package com.framgia.feastival.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        switch (mainViewModel.getState().get()) {
            case STATE_SHOW_RESTAURANT_DETAIL:
                // TODO: 02/08/2017
                break;
            case STATE_CREATE_GROUP:
                // TODO: 02/08/2017
                break;
            case STATE_SHOW_GROUP_DETAIL:
                // TODO: 02/08/2017
                break;
        }
    }
}
