package com.framgia.feastival.util;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
    public static void setBottomSheetState(CoordinatorLayout rootCoordinate, MainViewModel
        mainViewModel) {
        View mView;
        rootCoordinate.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(mainViewModel.getContext());
        View bottomSheet = mainViewModel.getBottomSheet();
        switch (mainViewModel.getState()) {
            case STATE_SHOW_RESTAURANT_DETAIL:
                FrameRestaurantDetailBinding restaurantDetailBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout
                        .frame_restaurant_detail, rootCoordinate, false);
                restaurantDetailBinding.setViewModel(mainViewModel.getRestaurantDetailViewModel());
                mView = restaurantDetailBinding.getRoot();
                bottomSheet.getLayoutParams().height = mainViewModel.getContext().getResources()
                    .getInteger(R.integer.bottom_sheet_expand_restaurant_detail);
                bottomSheet.requestLayout();
                mainViewModel.getBottomSheetBehavior().onLayoutChild(rootCoordinate, bottomSheet,
                    ViewCompat.LAYOUT_DIRECTION_LTR);
                rootCoordinate.addView(mView, mParams);
                break;
            case STATE_CREATE_GROUP:
                FrameGroupCreateBinding createGroupBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout
                        .frame_group_create, rootCoordinate, false);
                createGroupBinding.setViewModel(mainViewModel.getCreateGroupViewModel());
                mView = createGroupBinding.getRoot();
                bottomSheet.getLayoutParams().height = mainViewModel.getContext().getResources()
                    .getInteger(R.integer.bottom_sheet_expand_create_group);
                bottomSheet.requestLayout();
                mainViewModel.getBottomSheetBehavior().onLayoutChild(rootCoordinate, bottomSheet,
                    ViewCompat.LAYOUT_DIRECTION_LTR);
                rootCoordinate.addView(mView, mParams);
                break;
            case STATE_SHOW_GROUP_DETAIL:
                // TODO: 02/08/2017
                break;
        }
    }

    @BindingAdapter("spinnerAdapter")
    public static void setSourceSpinner(Spinner spinner, ArrayAdapter<String> spinnerAdapter) {
        spinner.setAdapter(spinnerAdapter);
    }

    @BindingAdapter("src")
    public static void setImageStateBottomSheet(ImageView imageView, int state) {
        switch (state) {
            case BottomSheetBehavior.STATE_COLLAPSED:
                imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24px);
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24px);
                break;
            default:
                break;
        }
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void spinnerItemSelected(Spinner spinner, int selectedValue,
                                           final InverseBindingListener changeSelectedValue) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeSelectedValue.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (changeSelectedValue != null) {
            spinner.setSelection(selectedValue, true);
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static int captureSelectedValue(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }
}
