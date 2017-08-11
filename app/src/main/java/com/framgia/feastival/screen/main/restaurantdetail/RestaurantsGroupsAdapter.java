package com.framgia.feastival.screen.main.restaurantdetail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.databinding.ItemGroupBinding;
import com.framgia.feastival.screen.main.MainContract;
import com.framgia.feastival.screen.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmd on 01/08/2017.
 */
public class RestaurantsGroupsAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_NEW_GROUP = 0;
    private static final int ITEM_EXIST_GROUP = 1;
    private List mList = new ArrayList<>();
    private MainContract.ViewModel mViewModel;

    public RestaurantsGroupsAdapter(MainContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void updateData(List list) {
        mList.clear();
        mList.add(new Object());
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof Group) {
            return ITEM_EXIST_GROUP;
        }
        return ITEM_NEW_GROUP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == ITEM_NEW_GROUP) {
            v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_group, parent, false);
            return new ViewHolderNewGroup(v);
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGroupBinding binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_group, parent, false);
        return new ViewHolderExistGroups(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == ITEM_NEW_GROUP) {
            Object newGroup = mList.get(0);
            ((ViewHolderNewGroup) holder).bindData();
        } else {
            Group groupDetailViewModel = (Group) mList.get(position);
            ((ViewHolderExistGroups) holder).bindData(groupDetailViewModel);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     *
     */
    private class ViewHolderNewGroup extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        ViewHolderNewGroup(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        public void bindData() {
        }

        @Override
        public void onClick(View view) {
            mViewModel.onClickCreateNewGroup();
        }
    }

    /**
     *
     */
    private class ViewHolderExistGroups extends RecyclerView.ViewHolder {
        private ItemGroupBinding mItemGroupBinding;

        ViewHolderExistGroups(ItemGroupBinding itemGroupBinding) {
            super(itemGroupBinding.getRoot());
            mItemGroupBinding = itemGroupBinding;
        }

        public void bindData(Group group) {
            mItemGroupBinding.setViewModel((MainViewModel) mViewModel);
            mItemGroupBinding.setGroup(group);
            mItemGroupBinding.executePendingBindings();
        }
    }
}
