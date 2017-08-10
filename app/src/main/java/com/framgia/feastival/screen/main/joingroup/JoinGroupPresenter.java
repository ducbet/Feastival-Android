package com.framgia.feastival.screen.main.joingroup;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class JoinGroupPresenter implements JoinGroupContact.Presenter {
    private JoinGroupContact.ViewModel mViewModel;

    public JoinGroupPresenter(JoinGroupContact.ViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
