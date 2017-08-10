package com.framgia.feastival.screen.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.databinding.BaseObservable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.CategoriesResponse;
import com.framgia.feastival.data.source.model.Category;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.data.source.model.GroupDetailResponse;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.framgia.feastival.screen.BaseActivity;
import com.framgia.feastival.screen.main.creategroup.CreateGroupContract;
import com.framgia.feastival.screen.main.creategroup.CreateGroupPresenter;
import com.framgia.feastival.screen.main.creategroup.CreateGroupViewModel;
import com.framgia.feastival.screen.main.restaurantdetail.RestaurantDetailViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Main screen.
 */
public class MainViewModel extends BaseObservable
    implements MainContract.ViewModel, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private static final String TAG = MainViewModel.class.getName();
    private static final String MARKER_VIEW_POINT = "MARKER_VIEW_POINT";
    private static final String MARKER_RESIZE = "MARKER_RESIZE";
    private static final String MARKER_RESTAURANT = "MARKER_RESTAURANT";
    private static final String MARKER_GROUP = "MARKER_GROUP";
    public static final String STATE_SHOW_RESTAURANT_DETAIL = "STATE_SHOW_RESTAURANT_DETAIL";
    public static final String STATE_SHOW_GROUP_DETAIL = "STATE_SHOW_GROUP_DETAIL";
    public static final String STATE_CREATE_GROUP = "STATE_CREATE_GROUP";
    private String mState;
    private static final double RADIUS = 2000;
    private Context mContext;
    private MainContract.Presenter mPresenter;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private List<Marker> mRestaurantsMarker;
    private List<Marker> mGroupsMarker;
    private List<Marker> mViewPointMarker;
    private LatLng mMyLocation;
    private Marker mMarkerMyLocation;
    private boolean isNeedInMyLocation;
    private ProgressDialog mProgressDialog;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private View mBottomSheet;
    private GoogleMap.OnMyLocationChangeListener mMyLocationChangeListener =
        new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                mMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                zoomInMyPositonAutomaticly();
            }
        };
    private RestaurantDetailViewModel mRestaurantDetailViewModel =
        new RestaurantDetailViewModel(this);
    private CreateGroupViewModel mCreateGroupViewModel;
    private CreateGroupContract.Presenter mCreateGroupPresenter;
    private List<Category> mListCategories;

    public RestaurantDetailViewModel getRestaurantDetailViewModel() {
        return mRestaurantDetailViewModel;
    }

    public CreateGroupViewModel getCreateGroupViewModel() {
        return mCreateGroupViewModel;
    }

    public MainViewModel(Context context) {
        mContext = context;
        mRestaurantsMarker = new ArrayList<>();
        mGroupsMarker = new ArrayList<>();
        mViewPointMarker = new ArrayList<>();
        setState(STATE_SHOW_RESTAURANT_DETAIL);
        mCreateGroupViewModel = new CreateGroupViewModel(this);
        mCreateGroupPresenter = new CreateGroupPresenter(mCreateGroupViewModel);
        mCreateGroupViewModel.setPresenter(mCreateGroupPresenter);
        mListCategories = new ArrayList<>();
    }

    public Context getContext() {
        return mContext;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        switch (state) {
            case STATE_SHOW_RESTAURANT_DETAIL:
                break;
            case STATE_SHOW_GROUP_DETAIL:
                break;
            case STATE_CREATE_GROUP:
                mCreateGroupViewModel
                    .setSelectedRestaurant(mRestaurantDetailViewModel.getSelectedRestaurant());
                break;
            default:
                break;
        }
        mState = state;
        notifyChange();
    }

    public BottomSheetBehavior<View> getBottomSheetBehavior() {
        return mBottomSheetBehavior;
    }

    public View getBottomSheet() {
        return mBottomSheet;
    }

    private void zoomInMyPositonAutomaticly() {
        if (mMap != null && isNeedInMyLocation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, 14));
            markMyLocation(mMyLocation);
            isNeedInMyLocation = false;
        }
    }

    public List<Category> getListCategories() {
        return mListCategories;
    }

    private void markMyLocation(LatLng location) {
        if (mMap != null) {
            if (mMarkerMyLocation != null) {
                removeViewPoint(mMarkerMyLocation);
                mViewPointMarker.remove(mMarkerMyLocation);
            }
            mMarkerMyLocation = addMarkerViewPoint(location);
        }
    }

    private void removeViewPoint(Marker viewPoint) {
        if (mMap == null) return;
        Circle circle = (Circle) viewPoint.getTag();
        Marker markerResize = (Marker) circle.getTag();
        markerResize.remove();
        circle.remove();
        mViewPointMarker.remove(viewPoint);
        viewPoint.remove();
    }

    private void updateCircle(Marker viewPoint, double newRadious) {
        Circle circle = (Circle) viewPoint.getTag();
        Marker makerResize = (Marker) circle.getTag();
        circle.setRadius(newRadious);
    }

    private void updateCircle(Marker viewPoint) {
        Circle circle = (Circle) viewPoint.getTag();
        Marker makerResize = (Marker) circle.getTag();
        circle.setCenter(viewPoint.getPosition());
        makerResize.setPosition(
            SphericalUtil.computeOffset(viewPoint.getPosition(), circle.getRadius(), 90));
    }

    private Marker addMarkerViewPoint(LatLng location) {
        Marker marker = mMap.addMarker(new MarkerOptions()
            .position(location)
            .snippet(MARKER_VIEW_POINT)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        marker.setTag(drawCircle(marker));
        marker.setDraggable(true);
        mViewPointMarker.add(marker);
        return marker;
    }

    private Circle drawCircle(Marker viewPoint) {
        if (mMap == null) return null;
        Circle circle = (Circle) viewPoint.getTag();
        double radius = RADIUS;
        if (circle != null) {
            radius = circle.getRadius();
            circle.remove();
        }
        circle = mMap.addCircle(new CircleOptions()
            .center(viewPoint.getPosition())
            .fillColor(mContext.getResources().getColor(R.color.color_pink_50))
            .radius(radius)
            .strokeWidth(2)
            .strokeColor(mContext.getResources().getColor(R.color.color_red_accent_200)));
        mPresenter.getRestaurants(viewPoint.getPosition(), radius);
        circle.setTag(drawResizeMarker(viewPoint, circle));
        return circle;
    }

    private Marker drawResizeMarker(Marker viewPoint, Circle circle) {
        if (mMap == null) return null;
        Marker marker = (Marker) circle.getTag();
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
            .position(SphericalUtil.computeOffset(circle.getCenter(), circle.getRadius(), 90))
            .snippet(MARKER_RESIZE)
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_resize_circle)));
        marker.setTag(viewPoint);
        marker.setDraggable(true);
        return marker;
    }

    private void showNotifyLoading() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.loading_map_dialog_title));
        mProgressDialog.setMessage(mContext.getString(R.string.loading_map_dialog_message));
        mProgressDialog.show();
    }

    public void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(mContext,
            Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).requestPermission();
            }
        } else {
            mMap.setMyLocationEnabled(true);
            isNeedInMyLocation = true;
            mMap.setOnMyLocationChangeListener(mMyLocationChangeListener);
        }
    }

    private void markNearbyRestaurants(RestaurantsResponse restaurantsResponse) {
        for (Marker marker : mRestaurantsMarker) {
            marker.remove();
        }
        mRestaurantsMarker.clear();
        for (Restaurant restaurant : restaurantsResponse.getRestaurants()) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongtitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_restaurant))
                .title(String.valueOf(restaurant.getId()))
                .snippet(MARKER_RESTAURANT + restaurant.getId()));
            marker.setTag(restaurant);
            mRestaurantsMarker.add(marker);
        }
    }

    private void markNearbyGroups(RestaurantsResponse restaurantsResponse) {
        for (Marker marker : mGroupsMarker) {
            marker.remove();
        }
        mGroupsMarker.clear();
        for (Group group : restaurantsResponse.getGroups()) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(group.getLatitude(), group.getLongtitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_group))
                .title(String.valueOf(group.getId()))
                .snippet(MARKER_GROUP + group.getId()));
            marker.setTag(group);
            mGroupsMarker.add(marker);
        }
    }

    private void createBottomSheet() {
        mBottomSheet = ((BaseActivity) mContext).findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);
        final TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
            new int[]{android.R.attr.actionBarSize});
        mBottomSheetBehavior.setPeekHeight((int) styledAttributes.getDimension(0, 0));
        styledAttributes.recycle();
        setBottomSheetState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    public void setBottomSheetState(int state) {
        mBottomSheetBehavior.setState(state);
        mRestaurantDetailViewModel.setState(state);
    }

    public void setSelectedRestaurant(Marker marker) {
        mRestaurantDetailViewModel.setSelectedRestaurant((Restaurant) marker.getTag());
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onStart() {
        mMapFragment.getMapAsync(this);
        mPresenter.onStart();
        mPresenter.getCategories();
        createBottomSheet();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showNotifyLoading();
        mMap.setOnMapLoadedCallback(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void setMapFragment(SupportMapFragment mapFragment) {
        mMapFragment = mapFragment;
    }

    @Override
    public void onGetRestaurantsSuccess(RestaurantsResponse restaurantsResponse) {
        markNearbyRestaurants(restaurantsResponse);
        markNearbyGroups(restaurantsResponse);
    }

    @Override
    public void onGetCategoriesSuccess(CategoriesResponse categoriesResponse) {
        mListCategories.clear();
        mListCategories.addAll(categoriesResponse.getCategories());
        mCreateGroupViewModel.setListCategories(mListCategories);
    }

    @Override
    public void onClickExistGroup(Group group) {
        // TODO: 06/08/2017
    }

    @Override
    public void onGetGroupDetailSuccess(
        GroupDetailResponse groupDetailResponse) { // TODO: 06/08/2017
    }

    @Override
    public void onGetFailed(Throwable e) {
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetNewGroupLocalSuccess(Group group) {
// TODO: 04/08/2017  
    }

    @Override
    public void onGetNewGroupLocalFailed(String e) {
        Toast.makeText(mContext, e, Toast.LENGTH_LONG).show();
    }

    public void onGetNewGroup() {
        if (mCreateGroupViewModel == null) {
            return;
        }
        mCreateGroupViewModel.onGetNewGroup();
    }

    @Override
    public void onMapLoaded() {
        mProgressDialog.dismiss();
        getMyLocation();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            marker
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.getSnippet().equals(MARKER_RESIZE)) {
            Marker viewPoint = (Marker) marker.getTag();
            double newRadius =
                mPresenter.getDistance(viewPoint.getPosition(), marker.getPosition());
            updateCircle(viewPoint, newRadius);
            return;
        }
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            updateCircle(marker);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(true);
            mPresenter.getRestaurants(marker.getPosition(), ((Circle) marker.getTag()).getRadius());
            return;
        }
        if (marker.getSnippet().equals(MARKER_RESIZE)) {
            Marker viewPoint = (Marker) marker.getTag();
            double radius = ((Circle) viewPoint.getTag()).getRadius();
            mPresenter.getRestaurants(viewPoint.getPosition(), radius);
            return;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        for (Marker marker : mViewPointMarker) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(false);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet().contains(MARKER_RESTAURANT)) {
            setSelectedRestaurant(marker);
            return true;
        }
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(true);
            marker
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            return true;
        }
        return false;
    }
}
