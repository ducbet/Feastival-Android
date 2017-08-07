package com.framgia.feastival.screen.main.creategroup;

import com.framgia.feastival.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by tmd on 04/08/2017.
 */
public class CreateGroupPresenter implements CreateGroupContract.Presenter {
    private CreateGroupContract.ViewModel mViewModel;
    private static final String TIME_FORMAT = "HH:mm dd/MM/yyyy";

    public CreateGroupPresenter(CreateGroupContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public static String getTimeFormat() {
        return TIME_FORMAT;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void checkValid(String name, String address, String time, String size, String category) {
        if (name == null || name.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_name_empty);
            return;
        }
        if (address == null || address.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_address_empty);
            return;
        }
        if (time == null || time.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_time_empty);
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(time);
        } catch (ParseException e) {
            mViewModel.onHaveFieldInvalid(R.string.field_time_not_match);
            return;
        }
        if (size == null || size.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_size_empty);
            return;
        }
        if (Integer.parseInt(size) < 0) {
            mViewModel.onHaveFieldInvalid(R.string.field_size_cant_negative);
            return;
        }
        if (category == null || category.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_category_empty);
            return;
        }
        mViewModel.onValidateSuccess();
    }
}
