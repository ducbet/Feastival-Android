package com.framgia.feastival.screen.main.creategroup;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.framgia.feastival.BR;
import com.framgia.feastival.data.source.model.Category;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.screen.BaseViewModel;
import com.framgia.feastival.screen.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanh.tv on 8/2/2017.
 */
public class CreateGroupViewModel extends BaseObservable implements CreateGroupContract.ViewModel {
    private BaseViewModel mBaseViewModel;
    private CreateGroupContract.Presenter mPresenter;
    private Restaurant mSelectedRestaurant;
    private int mState;
    private String mName;
    private String mAddress;
    private String mTime;
    private String mSize;
    private List<Category> mListCategories;
    private ArrayAdapter<String> mSpinnerAdapter;

    public CreateGroupViewModel(MainViewModel mainViewModel) {
        mBaseViewModel = mainViewModel;
        mListCategories = new ArrayList<>();
        setSpinnerAdapter();
    }

    @Bindable
    public ArrayAdapter<String> getSpinnerAdapter() {
        return mSpinnerAdapter;
    }

    public void setSpinnerAdapter() {
        List<String> categoriesName = new ArrayList<>();
        for (Category category : mListCategories) {
            categoriesName.add(category.getName());
        }
        mSpinnerAdapter = new ArrayAdapter<String>(
            ((MainViewModel) mBaseViewModel).getContext(),
            android.R.layout.simple_spinner_item,
            categoriesName);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyPropertyChanged(BR.spinnerAdapter);
    }

    @Bindable
    public int getState() {
        return mState;
    }

    @Bindable
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
    public String getTime() {
        return mTime;
    }

    @Bindable
    public String getSize() {
        return mSize;
    }

    public void setBaseViewModel(BaseViewModel baseViewModel) {
        mBaseViewModel = baseViewModel;
        notifyChange();
    }

    public void setState(int state) {
        mState = state;
        notifyPropertyChanged(BR.state);
    }

    public void setSelectedRestaurant(Restaurant selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
        setAddress(mSelectedRestaurant.getAddress());
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    public void setAddress(String address) {
        mAddress = address;
        notifyPropertyChanged(BR.address);
    }

    public void setTime(String time) {
        mTime = time;
        notifyPropertyChanged(BR.time);
    }

    public void setSize(String size) {
        mSize = size;
        notifyPropertyChanged(BR.size);
    }

    @Bindable
    public List<Category> getListCategories() {
        return mListCategories;
    }

    public void setListCategories(List<Category> listCategories) {
        mListCategories = listCategories;
        notifyPropertyChanged(BR.listCategories);
        setSpinnerAdapter();
    }

    @Override
    public void onCreateGroup() {
        mPresenter.checkValid(mName, mAddress, mTime, mSize);
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
        newGroup.setTime(mTime);
        newGroup.setAddress(mSelectedRestaurant.getAddress());
        newGroup.setLatitude(mSelectedRestaurant.getLatitude());
        newGroup.setLongtitude(mSelectedRestaurant.getLongtitude());
        newGroup.setSize(Integer.parseInt(mSize));
        ((MainViewModel) mBaseViewModel).onGetNewGroupLocalSuccess(newGroup);
    }

    @Override
    public void onHaveFieldInvalid(int errorCode) {
        ((MainViewModel) mBaseViewModel).onGetNewGroupLocalFailed(
            ((MainViewModel) mBaseViewModel).getContext().getString(errorCode));
    }

    @Override
    public void changeStateBottomSheet(View view) {
        ((MainViewModel) mBaseViewModel).onChangeStateBottomSheet();
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
