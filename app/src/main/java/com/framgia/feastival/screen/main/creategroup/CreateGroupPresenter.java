package com.framgia.feastival.screen.main.creategroup;

import com.framgia.feastival.R;

/**
 * Created by tmd on 04/08/2017.
 */
public class CreateGroupPresenter implements CreateGroupContract.Presenter {
    private CreateGroupContract.ViewModel mViewModel;

    public CreateGroupPresenter(CreateGroupContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void checkValid(String name, String address, String size, String category) {
        if (name == null || name.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_name_empty);
            return;
        }
        if (address == null || address.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_address_empty);
            return;
        }
        if (size == null || size.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_size_empty);
            return;
        }
        if (category == null || category.trim().isEmpty()) {
            mViewModel.onHaveFieldInvalid(R.string.field_category_empty);
            return;
        }
        mViewModel.onValidateSuccess();
    }
}
