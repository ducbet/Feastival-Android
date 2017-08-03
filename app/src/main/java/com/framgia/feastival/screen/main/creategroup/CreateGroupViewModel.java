package com.framgia.feastival.screen.main.creategroup;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.framgia.feastival.BR;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.screen.BaseViewModel;
import com.framgia.feastival.screen.main.MainViewModel;

/**
 * Created by thanh.tv on 8/2/2017.
 */
public class CreateGroupViewModel extends BaseObservable implements CreateGroupContract.ViewModel {
    private BaseViewModel mBaseViewModel;
    private CreateGroupContract.Presenter mPresenter;
    private Restaurant mSelectedRestaurant;
    private String mName;
    private String mAddress;
    private String mSize;
    private String mCategory;

    public CreateGroupViewModel(MainViewModel mainViewModel) {
        mBaseViewModel = mainViewModel;
    }

    public Restaurant getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    @Bindable
    public String getAddress() {
        return mAddress;
    }

    @Bindable
    public String getSize() {
        return mSize;
    }

    @Bindable
    public String getCategory() {
        return mCategory;
    }

    public void setBaseViewModel(BaseViewModel baseViewModel) {
        mBaseViewModel = baseViewModel;
        notifyChange();
    }

    public void setSelectedRestaurant(Restaurant selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
        mAddress = mSelectedRestaurant.getAddress();
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    public void setAddress(String address) {
        mAddress = address;
        notifyPropertyChanged(BR.address);
    }

    public void setSize(String size) {
        mSize = size;
        notifyPropertyChanged(BR.size);
    }

    public void setCategory(String category) {
        mCategory = category;
        notifyPropertyChanged(BR.category);
    }

    @Override
    public void onGetNewGroup() {
        mPresenter.checkValid(mName, mAddress, mSize, mCategory);
    }

    @Override
    public void onCreateGroup() {
        ((MainViewModel) mBaseViewModel).onGetNewGroup();
    }

    @Override
    public void onCancelClick() {
        ((MainViewModel) mBaseViewModel).setState(MainViewModel.STATE_SHOW_RESTAURANT_DETAIL);
    }

    @Override
    public void onValidateSuccess() {
        Group newGroup = new Group();
        newGroup.setRestaurantId(mSelectedRestaurant.getId());
        newGroup.setTitle(mName);
        newGroup.setAddress(mSelectedRestaurant.getAddress());
        newGroup.setLatitude(mSelectedRestaurant.getLatitude());
        newGroup.setLongtitude(mSelectedRestaurant.getLongtitude());
        newGroup.setSize(Integer.parseInt(mSize));
        ((MainViewModel) mBaseViewModel).onGetNewGroupSuccess(newGroup);
    }

    @Override
    public void onHaveFieldInvalid(int errorCode) {
        ((MainViewModel) mBaseViewModel).onGetNewGroupFailed(
            ((MainViewModel) mBaseViewModel).getContext().getString(errorCode));
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setPresenter(CreateGroupContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
