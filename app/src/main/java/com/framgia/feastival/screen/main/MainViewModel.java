package com.framgia.feastival.screen.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.databinding.BaseObservable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
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
import com.framgia.feastival.screen.main.joingroup.JoinGroupContact;
import com.framgia.feastival.screen.main.joingroup.JoinGroupPresenter;
import com.framgia.feastival.screen.main.joingroup.JoinGroupViewModel;
import com.framgia.feastival.screen.main.restaurantdetail.RestaurantDetailViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Exposes the data to be used in the Main screen.
 */
public class MainViewModel extends BaseObservable
    implements MainContract.ViewModel, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener,
    NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainViewModel.class.getName();
    private static final String MARKER_VIEW_POINT = "MARKER_VIEW_POINT";
    private static final String MARKER_RESIZE = "MARKER_RESIZE";
    private static final String MARKER_RESTAURANT = "MARKER_RESTAURANT";
    private static final String MARKER_GROUP = "MARKER_GROUP";
    private static final String MARKER_FLOATING = "MARKER_FLOATING";
    public static final String STATE_SHOW_RESTAURANT_DETAIL = "STATE_SHOW_RESTAURANT_DETAIL";
    public static final String STATE_SHOW_GROUP_DETAIL = "STATE_SHOW_GROUP_DETAIL";
    public static final String STATE_CREATE_GROUP = "STATE_CREATE_GROUP";
    public static final String STATE_JOIN_GROUP = "STATE_JOIN_GROUP";
    private static final double RADIUS = 2000;
    private BitmapDescriptor mIconMarkerViewPoint;
    private BitmapDescriptor mIconMarkerViewPointDraggable;
    private BitmapDescriptor mIconMarkerResize;
    private BitmapDescriptor mIconMarkerRestaurant;
    private BitmapDescriptor mIconMarkerGroup;
    private BitmapDescriptor mIconMarkerNewGroup;
    private BitmapDescriptor mIconMarkerFloating;
    private String mState;
    private Context mContext;
    private MainContract.Presenter mPresenter;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private Map<Restaurant, Marker> mRestaurantsMarker;
    private Map<Group, Marker> mGroupsMarker;
    private Map<Marker, HashSet> mViewPointsMarker;
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
    private Restaurant mSelectedRestaurant;
    private Marker mMarkerNewGroup;
    private Map<Group, Marker> mFloatingGroupsMarker;
    private Marker mMarkerFloating;
    private JoinGroupViewModel mJoinGroupViewModel;
    private JoinGroupContact.Presenter mJoinGroupPresenter;
    private List<Category> mListCategories;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    public RestaurantDetailViewModel getRestaurantDetailViewModel() {
        return mRestaurantDetailViewModel;
    }

    public CreateGroupViewModel getCreateGroupViewModel() {
        return mCreateGroupViewModel;
    }

    public JoinGroupViewModel getmJoinGroupViewModel() {
        return mJoinGroupViewModel;
    }

    public MainViewModel(Context context) {
        mContext = context;
        mRestaurantsMarker = new HashMap<>();
        mGroupsMarker = new HashMap<>();
        mViewPointsMarker = new HashMap<>();
        setState(STATE_SHOW_RESTAURANT_DETAIL);
        mCreateGroupViewModel = new CreateGroupViewModel(this);
        mCreateGroupPresenter = new CreateGroupPresenter(mCreateGroupViewModel);
        mCreateGroupViewModel.setPresenter(mCreateGroupPresenter);
        mJoinGroupViewModel = new JoinGroupViewModel(this);
        mJoinGroupPresenter = new JoinGroupPresenter(mJoinGroupViewModel);
        mJoinGroupViewModel.setPresenter(mJoinGroupPresenter);
        mListCategories = new ArrayList<>();
    }

    private void defineMarkerIcon() {
        mIconMarkerViewPoint =
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        mIconMarkerViewPointDraggable =
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        mIconMarkerResize = BitmapDescriptorFactory.fromResource(R.mipmap.ic_resize_circle);
        mIconMarkerRestaurant = BitmapDescriptorFactory.fromResource(R.mipmap.ic_restaurant);
        mIconMarkerGroup = BitmapDescriptorFactory.fromResource(R.mipmap.ic_group);
        mIconMarkerNewGroup = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_new_group);
        mIconMarkerFloating =
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
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
                setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED);
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

    public void setNavigationView(DrawerLayout drawerLayout, NavigationView navigationView) {
        mDrawerLayout = drawerLayout;
        mNavigationView = navigationView;
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
                mViewPointsMarker.remove(mMarkerMyLocation);
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
        mViewPointsMarker.remove(viewPoint);
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

    private Marker addFloatingMarker() {
        LatLng pointCenter = mMap.getCameraPosition().target;
        Marker marker = mMap.addMarker(new MarkerOptions()
            .position(pointCenter)
            .snippet(MARKER_FLOATING)
            .icon(mIconMarkerFloating));
        marker.setDraggable(true);
        marker.setVisible(false);
        return marker;
    }

    private Marker addMarkerViewPoint(LatLng location) {
        Marker marker = mMap.addMarker(new MarkerOptions()
            .position(location)
            .snippet(MARKER_VIEW_POINT)
            .icon(mIconMarkerViewPointDraggable));
        marker.setTag(drawCircle(marker));
        marker.setDraggable(true);
        mViewPointsMarker.put(marker, new HashSet());
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
        mPresenter.getRestaurants(viewPoint, radius);
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
            .icon(mIconMarkerResize));
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
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(mContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).requestPermission();
            }
        } else {
            mMap.setMyLocationEnabled(true);
            isNeedInMyLocation = true;
            mMap.setOnMyLocationChangeListener(mMyLocationChangeListener);
        }
    }

    private void markNearbyRestaurants(Marker viewPoint, RestaurantsResponse response) {
        Marker marker;
        HashSet<String> viewPointIds;
        for (Restaurant restaurant : response.getRestaurants()) {
            if (mRestaurantsMarker.containsKey(restaurant)) {
                viewPointIds =
                    (HashSet<String>) (mRestaurantsMarker.get(restaurant)).getTag();
                viewPointIds.add(viewPoint.getId());
                mViewPointsMarker.get(viewPoint).add(restaurant);
                continue;
            }
            marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongtitude()))
                .icon(mIconMarkerRestaurant)
                .title(String.valueOf(restaurant.getId()))
                .snippet(MARKER_RESTAURANT + restaurant.getId()));
            viewPointIds = new HashSet<>();
            viewPointIds.add(viewPoint.getId());
            marker.setTag(viewPointIds);
            mRestaurantsMarker.put(restaurant, marker);
            mViewPointsMarker.get(viewPoint).add(restaurant);
        }
        removeDuplicate(mRestaurantsMarker, response.getRestaurants(), viewPoint);
    }

    private void markNearbyGroups(Marker viewPoint, RestaurantsResponse response) {
        Marker marker;
        HashSet<String> viewPointIds;
        for (Group group : response.getGroups()) {
            if (mGroupsMarker.containsKey(group)) {
                viewPointIds =
                    (HashSet<String>) (mGroupsMarker.get(group)).getTag();
                viewPointIds.add(viewPoint.getId());
                mViewPointsMarker.get(viewPoint).add(group);
                continue;
            }
            marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(group.getLatitude(), group.getLongtitude()))
                .icon(mIconMarkerGroup)
                .title(String.valueOf(group.getId()))
                .snippet(MARKER_GROUP + group.getId()));
            viewPointIds = new HashSet<>();
            viewPointIds.add(viewPoint.getId());
            marker.setTag(viewPointIds);
            mGroupsMarker.put(group, marker);
            mViewPointsMarker.get(viewPoint).add(group);
        }
        removeDuplicate(mGroupsMarker, response.getGroups(), viewPoint);
    }

    private void removeDuplicate(Map hashMap, List newArea, Marker viewPoint) {
        Marker marker;
        HashSet viewPointIds;
        HashSet oldArea = mViewPointsMarker.get(viewPoint);
        List outSide = new ArrayList(oldArea);
        outSide.removeAll(newArea);
        for (Object o : outSide) {
            marker = (Marker) hashMap.get(o);
            if (marker == null) {
                continue;
            }
            viewPointIds = (HashSet) marker.getTag();
            viewPointIds.remove(viewPoint.getId());
            oldArea.remove(o);
            if (viewPointIds.isEmpty()) {
                marker.remove();
                hashMap.remove(o);
            }
        }
    }

    private Object getKeyFromValue(HashMap hashMap, Object value) {
        for (Object o : hashMap.keySet()) {
            if (hashMap.get(o).equals(value)) {
                return o;
            }
        }
        return null;
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
        mCreateGroupViewModel.setState(state);
    }

    public Restaurant getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    public void setSelectedRestaurant(Marker marker) {
        mRestaurantDetailViewModel.setSelectedRestaurant(
            (Restaurant) getKeyFromValue((HashMap) mRestaurantsMarker, marker));
        mSelectedRestaurant = (Restaurant) getKeyFromValue((HashMap) mRestaurantsMarker, marker);
        mRestaurantDetailViewModel.setSelectedRestaurant(mSelectedRestaurant);
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void makeAllViewPointMarkerUndraggable() {
        for (Marker viewPointMarker : mViewPointsMarker.keySet()) {
            Circle circle = (Circle) viewPointMarker.getTag();
            Marker resizeMarker = (Marker) circle.getTag();
            resizeMarker.setVisible(false);
            viewPointMarker.setIcon(mIconMarkerViewPoint);
        }
    }

    private void makeViewPointMarkerDraggable(Marker viewPointMarker) {
        makeAllViewPointMarkerUndraggable();
        Circle circle = (Circle) viewPointMarker.getTag();
        Marker resizeMarker = (Marker) circle.getTag();
        resizeMarker.setVisible(true);
        viewPointMarker.setIcon(mIconMarkerViewPointDraggable);
    }

    private void makeMarkerNomal(Marker marker) {
        if (marker.getSnippet().equals(MARKER_FLOATING)) {
            marker.setVisible(false);
            return;
        }
        if (marker.getSnippet().contains(MARKER_GROUP)) {
            marker.setIcon(mIconMarkerGroup);
            return;
        }
        if (marker.getSnippet().contains(MARKER_RESTAURANT)) {
            marker.setIcon(mIconMarkerRestaurant);
            return;
        }
    }

    private void passAddressIntoCreateGroupFrame(Marker marker) {
        if (marker.getSnippet().equals(MARKER_FLOATING)) {
            mCreateGroupViewModel.setAddress(getAddress(marker.getPosition()));
            return;
        }
        if (marker.getSnippet().contains(MARKER_RESTAURANT)) {
            mCreateGroupViewModel.setSelectedRestaurant(mSelectedRestaurant);
            return;
        }
    }

    private String getAddress(LatLng latLng) {
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
        }
        if (addresses == null || addresses.isEmpty()) {
            return "";
        }
        String address = "";
        for (int i = 0; addresses.get(0).getAddressLine(i) != null; i++) {
            address += ", " + addresses.get(0).getAddressLine(i);
        }
        return address.replaceFirst(", ", "");
    }

    @Override
    public void onStart() {
        mMapFragment.getMapAsync(this);
        mPresenter.onStart();
        mPresenter.getCategories();
        mNavigationView.setNavigationItemSelectedListener(this);
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
        defineMarkerIcon();
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
    public void onGetRestaurantsSuccess(Marker viewPoint, RestaurantsResponse response) {
        markNearbyRestaurants(viewPoint, response);
        markNearbyGroups(viewPoint, response);
    }

    @Override
    public void onGetCategoriesSuccess(CategoriesResponse categoriesResponse) {
        mListCategories.clear();
        mListCategories.addAll(categoriesResponse.getCategories());
        mCreateGroupViewModel.setListCategories(mListCategories);
    }

    @Override
    public void onCreateNewGroupSuccess(Group newGroup) {
        Marker marker = mMap.addMarker(new MarkerOptions()
            .position(new LatLng(newGroup.getLatitude(), newGroup.getLongtitude()))
            .snippet(MARKER_GROUP)
            .icon(mIconMarkerGroup));
        mFloatingGroupsMarker.put(newGroup, marker);
    }

    @Override
    public void onClickCreateNewGroup() {
        setState(STATE_CREATE_GROUP);
        makeAllViewPointMarkerUndraggable();
        mMarkerFloating = addFloatingMarker();
        if (mSelectedRestaurant != null) {
            onMarkNewGroup(mRestaurantsMarker.get(mSelectedRestaurant));
            return;
        }
        onMarkNewGroup(mMarkerFloating);
    }

    @Override
    public void onClickExistGroup(Group group) {
        mPresenter.getGroupDetail(group.getId());
    }

    @Override
    public void onGetGroupDetailSuccess(GroupDetailResponse groupDetailResponse) {
        if (groupDetailResponse.getGroupUser() == null) {
            setState(STATE_JOIN_GROUP);
            mJoinGroupViewModel.setGroupResponse(groupDetailResponse);
            return;
        }
        setState(STATE_SHOW_GROUP_DETAIL);
    }

    @Override
    public void onGetFailed(Throwable e) {
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetNewGroupLocalSuccess(Group newGroup) {
        mPresenter.createNewGroup(newGroup);
    }

    @Override
    public void onGetNewGroupLocalFailed(String e) {
        Toast.makeText(mContext, e, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMarkNewGroup(Marker marker) {
        if (mMarkerNewGroup != null) {
            makeMarkerNomal(mMarkerNewGroup);
        }
        mMarkerNewGroup = marker;
        mMarkerNewGroup.setVisible(true);
        mMarkerNewGroup.setIcon(mIconMarkerNewGroup);
        passAddressIntoCreateGroupFrame(marker);
    }

    @Override
    public void onPinNewViewPoint() {
        LatLng pointCenter = mMap.getCameraPosition().target;
        addMarkerViewPoint(pointCenter);
    }

    @Override
    public void onChangeStateBottomSheet() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED);
            return;
        }
    }

    @Override
    public void onMapLoaded() {
        mProgressDialog.dismiss();
        getMyLocation();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            makeViewPointMarkerDraggable(marker);
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
        Circle circle;
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            mPresenter.getRestaurants(marker, ((Circle) marker.getTag()).getRadius());
            return;
        }
        if (marker.getSnippet().equals(MARKER_RESIZE)) {
            Marker viewPoint = (Marker) marker.getTag();
            circle = (Circle) viewPoint.getTag();
            double radius = circle.getRadius();
            mPresenter.getRestaurants(viewPoint, radius);
            return;
        }
        if (marker.getSnippet().equals(MARKER_FLOATING)) {
            onMarkNewGroup(mMarkerFloating);
            return;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        makeAllViewPointMarkerUndraggable();
        if (mState == STATE_CREATE_GROUP) {
            mMarkerFloating.setPosition(latLng);
            onMarkNewGroup(mMarkerFloating);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet().contains(MARKER_RESTAURANT)) {
            setSelectedRestaurant(marker);
            if (mState == STATE_CREATE_GROUP) {
                onMarkNewGroup(marker);
            }
            return true;
        }
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            makeViewPointMarkerDraggable(marker);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new_group:
                onClickCreateNewGroup();
                break;
            case R.id.create_new_restaurant:
                break;
            case R.id.add_more_view_point:
                makeAllViewPointMarkerUndraggable();
                onPinNewViewPoint();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.START);
        return false;
    }
}
