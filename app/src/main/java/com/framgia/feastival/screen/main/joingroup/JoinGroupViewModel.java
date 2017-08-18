package com.framgia.feastival.screen.main.joingroup;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.screen.BaseViewModel;
import com.framgia.feastival.screen.main.MainViewModel;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class JoinGroupViewModel extends BaseObservable implements JoinGroupContact.ViewModel {
    private BaseViewModel mBaseViewModel;
    private JoinGroupContact.Presenter mPresenter;
    private GroupDetailResponse mGroupDetailResponse;
    private GroupMemberAdapter mGroupMemberAdapter;

    @Bindable
    public GroupMemberAdapter getGroupMemberAdapter() {
        return mGroupMemberAdapter;
    }

    public JoinGroupViewModel(MainViewModel mainViewModel) {
        mBaseViewModel = mainViewModel;
        mGroupMemberAdapter = new GroupMemberAdapter(mainViewModel);
    }

    @Override
    public void onJoinGroup() {
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(JoinGroupContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setGroupResponse(GroupDetailResponse groupDetailResponse) {
        mGroupDetailResponse = groupDetailResponse;
    }
}
