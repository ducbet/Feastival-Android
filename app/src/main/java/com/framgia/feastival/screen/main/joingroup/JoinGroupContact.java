package com.framgia.feastival.screen.main.joingroup;

import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class JoinGroupContact {
    /**
     *
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onJoinGroup();
        void setGroupResponse(GroupDetailResponse groupDetailResponse);
    }

    /**
     *
     */
    public interface Presenter extends BasePresenter {
    }
}
