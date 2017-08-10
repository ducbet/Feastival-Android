package com.framgia.feastival.screen.main.joingroup;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.framgia.feastival.data.source.model.UserModel;
import com.framgia.feastival.databinding.ItemMemberBinding;
import com.framgia.feastival.screen.main.MainContract;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> {
    private MainContract.ViewModel mViewModel;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public GroupMemberAdapter(MainContract.ViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMemberBinding mItemMemberBinding;

        public ViewHolder(ItemMemberBinding itemMemberBinding) {
            super(itemMemberBinding.getRoot());
            mItemMemberBinding = itemMemberBinding;
        }

        public void setBinding(UserModel.UserInfo user) {
            mItemMemberBinding.setUserInfo(user);
            mItemMemberBinding.executePendingBindings();
        }
    }
}
