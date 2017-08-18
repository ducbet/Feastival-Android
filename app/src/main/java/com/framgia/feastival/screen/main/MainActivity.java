package com.framgia.feastival.screen.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.CategoryRepository;
import com.framgia.feastival.data.source.RestaurantRepository;
import com.framgia.feastival.data.source.remote.CategoryRemoteDataSource;
import com.framgia.feastival.data.source.remote.RestaurantRemoteDataSource;
import com.framgia.feastival.databinding.ActivityMainBinding;
import com.framgia.feastival.databinding.HeaderBinding;
import com.framgia.feastival.screen.BaseActivity;
import com.framgia.feastival.screen.main.creategroup.CreateGroupContract;
import com.framgia.feastival.screen.main.creategroup.CreateGroupPresenter;
import com.framgia.feastival.screen.main.creategroup.CreateGroupViewModel;
import com.framgia.feastival.screen.main.restaurantdetail.RestaurantDetailViewModel;
import com.google.android.gms.maps.SupportMapFragment;

import static com.framgia.feastival.screen.main.MainViewModel.STATE_SHOW_RESTAURANT_DETAIL;

/**
 * Main Screen.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final long EXIT_DELAY = 2000;
    private static int REQUEST_ACCESS_LOCATION = 123;
    private MainContract.ViewModel mViewModel;
    private RestaurantDetailViewModel mRestaurantDetailViewModel;
    private CreateGroupContract.ViewModel mCreateGroupViewModel;
    private static final String PERMISSION_GET_LOCATION[] =
        {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private boolean mIsDoubleClickedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new MainViewModel(this);
        MainContract.Presenter presenter =
            new MainPresenter(mViewModel,
                new RestaurantRepository(new RestaurantRemoteDataSource()),
                new CategoryRepository(new CategoryRemoteDataSource()));
        mViewModel.setPresenter(presenter);
        ActivityMainBinding binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel((MainViewModel) mViewModel);
        addHeaderNavigationView(binding);
        inflateBottomSheetView(binding);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mViewModel.setMapFragment(mapFragment);
    }

    private void inflateBottomSheetView(ActivityMainBinding binding) {
        mRestaurantDetailViewModel = new RestaurantDetailViewModel((MainViewModel) mViewModel);
        binding.setRestaurantViewModel(mRestaurantDetailViewModel);
        ((MainViewModel) mViewModel).setRestaurantDetailViewModel(mRestaurantDetailViewModel);
        mCreateGroupViewModel = new CreateGroupViewModel((MainViewModel) mViewModel);
        CreateGroupContract.Presenter createGroupPresenter =
            new CreateGroupPresenter(mCreateGroupViewModel);
        mCreateGroupViewModel.setPresenter(createGroupPresenter);
        binding.setCreateGroupViewModel((CreateGroupViewModel) mCreateGroupViewModel);
        ((MainViewModel) mViewModel).setCreateGroupViewModel(
            (CreateGroupViewModel) mCreateGroupViewModel);
    }

    private void addHeaderNavigationView(ActivityMainBinding binding) {
        HeaderBinding headerBinding = DataBindingUtil.inflate(
            getLayoutInflater(), R.layout.header, binding.leftDrawer, false);
        headerBinding.setViewModel((MainViewModel) mViewModel);
        ((MainViewModel) mViewModel).setNavigationView(binding.drawerLayout, binding.leftDrawer);
    }

    public void requestPermission() {
        boolean isGrant = ContextCompat.checkSelfPermission(MainActivity.this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
        if (isGrant) {
            ((MainViewModel) mViewModel).getMyLocation();
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
            Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder aBuiler = new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.request_permission_dialog_title))
                .setMessage(getString(R.string.request_permission_dialog_message))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                            MainActivity.this,
                            PERMISSION_GET_LOCATION,
                            REQUEST_ACCESS_LOCATION);
                    }
                })
                .setNegativeButton(getString(R.string.no), null);
            aBuiler.create().show();
            return;
        }
        ActivityCompat.requestPermissions(
            MainActivity.this,
            PERMISSION_GET_LOCATION,
            REQUEST_ACCESS_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                (grantResults.length == 2 &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                ((MainViewModel) mViewModel).getMyLocation();
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (((MainViewModel) mViewModel).getState() != STATE_SHOW_RESTAURANT_DETAIL) {
            ((MainViewModel) mViewModel).setState(STATE_SHOW_RESTAURANT_DETAIL);
            return;
        }
        if (mIsDoubleClickedBack) {
            super.onBackPressed();
            return;
        }
        mIsDoubleClickedBack = true;
        Toast.makeText(this, getString(R.string.double_click_back), Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsDoubleClickedBack = false;
            }
        }, EXIT_DELAY);
    }
}
