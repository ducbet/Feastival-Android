package com.framgia.feastival.util;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.framgia.feastival.R;

/**
 * Created by tmd on 01/08/2017.
 */
public class BindingUtil {
    @BindingAdapter({"recyclerAdapter"})
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
            .load(url)
            .into(imageView);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
                                        LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
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
