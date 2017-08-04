package com.framgia.feastival.screen.main.creategroup;

import android.view.View;

import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;

/**
 * Created by tmd on 04/08/2017.
 */
public class CreateGroupContract {
    /**
     * View.
     */
    public interface ViewModel extends BaseViewModel<CreateGroupContract.Presenter> {
        void onGetNewGroup();
        void onCreateGroup();
        void onCancelClick();
        void onValidateSuccess();
        void onHaveFieldInvalid(int errorCode);
        void changeStateBottomSheet(View view);
    }

    /**
     * Presenter.
     */
    public interface Presenter extends BasePresenter {
        void checkValid(String name, String address, String time, String size);
    }
}
