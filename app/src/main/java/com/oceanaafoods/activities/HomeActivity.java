package com.oceanaafoods.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.oceanaafoods.R;
import com.oceanaafoods.baseui.BaseAppCompactActivity;
import com.oceanaafoods.fragments.HomeFragment;
import com.oceanaafoods.fragments.OffersFragment;
import com.oceanaafoods.fragments.OthersFragment;
import com.oceanaafoods.fragments.PointsFragment;
import com.oceanaafoods.fragments.ScanFragment;
import com.oceanaafoods.interfaces.IUserDetailView;
import com.oceanaafoods.models.responses.UserDetailsResponse;
import com.oceanaafoods.presenters.UserdetailsPresenter;
import com.oceanaafoods.utils.APIClient;
import com.oceanaafoods.utils.PrefUtils;


import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseAppCompactActivity implements IUserDetailView {
    private View navHeader;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler mHandler;
    private PrefUtils prefUtils;
    private HomeFragment homeFragment;
    private ScanFragment scanFragment;
    private OffersFragment offersFragment;
    private OthersFragment othersFragment;
    private PointsFragment pointsFragment;
    private UserdetailsPresenter userdetailsPresenter;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.menu_layout)
    LinearLayout menu_layout;
    @BindView(R.id.scan_layout)
    LinearLayout scan_layout;
    @BindView(R.id.others_layout)
    LinearLayout others_layout;
    @BindView(R.id.offer_layout)
    LinearLayout offer_layout;
    @BindView(R.id.points_layout)
    LinearLayout points_layout;
    @BindView(R.id.iv_whatsapp)
    ImageView iv_whatsapp;
    TextView tv_userName;

    @OnClick(R.id.iv_whatsapp)
    public void onWhatsappClick() {
        String contact = "+91 7013258972"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            showToast("Whatsapp app not installed in your phone");
            e.printStackTrace();
        }
    }

    @OnClick(R.id.menu_layout)
    public void onMenuClick() {
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
        setBackGroundColor(menu_layout);
    }

    @OnClick(R.id.scan_layout)
    public void onScanClick() {
        scanFragment = new ScanFragment();
        loadFragment(scanFragment);
        setBackGroundColor(scan_layout);
    }

    @OnClick(R.id.others_layout)
    public void onOthersClick() {
        othersFragment = new OthersFragment();
        loadFragment(othersFragment);
        setBackGroundColor(others_layout);
    }

    @OnClick(R.id.points_layout)
    public void onPointsClick() {
        if (PrefUtils.getInstance().isLogin()) {
            pointsFragment = new PointsFragment();
            loadFragment(pointsFragment);
            setBackGroundColor(points_layout);
        } else {
            goToActivity(LoginActivity.class);
        }
    }

    @OnClick(R.id.offer_layout)
    public void onOffersClick() {
        offersFragment = new OffersFragment();
        loadFragment(offersFragment);
        setBackGroundColor(offer_layout);
    }

    @OnClick(R.id.iv_menu)
    public void clickOnMenu() {
        openOrCloseNavigationDrawer();
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_home;
    }


    public HomeActivity() {
        userdetailsPresenter = new UserdetailsPresenter(APIClient.getAPIService(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        navHeader = navigationView.getHeaderView(0);
        tv_userName = navHeader.findViewById(R.id.tv_userName);
        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        offersFragment = new OffersFragment();
        loadFragment(offersFragment);
        setBackGroundColor(offer_layout);
        setUpNavigationView();
        if (userdetailsPresenter == null)
            userdetailsPresenter = new UserdetailsPresenter(APIClient.getAPIService(), this);
        if (PrefUtils.getInstance().isLogin())
            userdetailsPresenter.getUserDetails(PrefUtils.getInstance().getUserId() + "", false);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        homeFragment = new HomeFragment();
                        loadFragment(homeFragment);
                        setBackGroundColor(menu_layout);
                        break;
                    case R.id.nav_scan:
                        scanFragment = new ScanFragment();
                        loadFragment(scanFragment);
                        setBackGroundColor(scan_layout);
                        break;
                    case R.id.nav_logout:
                        showLogoutDialog();
                        break;
                    case R.id.nav_others:
                        othersFragment = new OthersFragment();
                        loadFragment(othersFragment);
                        setBackGroundColor(others_layout);
                        break;
                    case R.id.nav_offers:
                        offersFragment = new OffersFragment();
                        loadFragment(offersFragment);
                        setBackGroundColor(offer_layout);
                        break;
                    case R.id.nav_points:
                        if (PrefUtils.getInstance().isLogin()) {
                            pointsFragment = new PointsFragment();
                            loadFragment(pointsFragment);
                            setBackGroundColor(points_layout);
                        } else {
                            goToActivity(LoginActivity.class);
                        }
                       /* pointsFragment = new PointsFragment();
                        loadFragment(pointsFragment);
                        setBackGroundColor(points_layout);*/
                        break;

                }
                return false;
            }
        });
    }

    public void openOrCloseNavigationDrawer() {
        if (drawer_layout.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawer_layout.closeDrawer(androidx.core.view.GravityCompat.START);
        } else {
            drawer_layout.openDrawer(androidx.core.view.GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    private void setBackGroundColor(LinearLayout linearLayout) {
        switch (linearLayout.getId()) {
            case R.id.menu_layout:
                menu_layout.setBackgroundColor(getResources().getColor(R.color.bottom_clicked_color));
                scan_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                offer_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                points_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                others_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.scan_layout:
                scan_layout.setBackgroundColor(getResources().getColor(R.color.bottom_clicked_color));
                offer_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                points_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                others_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                menu_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.offer_layout:
                offer_layout.setBackgroundColor(getResources().getColor(R.color.bottom_clicked_color));
                points_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                others_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scan_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                menu_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.points_layout:
                points_layout.setBackgroundColor(getResources().getColor(R.color.bottom_clicked_color));
                others_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                menu_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scan_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                offer_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.others_layout:
                others_layout.setBackgroundColor(getResources().getColor(R.color.bottom_clicked_color));
                menu_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scan_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                offer_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                points_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            default:
                break;
        }

    }

    private void loadFragment(final Fragment fragment) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, "");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        drawer_layout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();

    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to logout ?").setTitle("Logout");
        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to logout from this app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrefUtils.getInstance().clearSharedPref();
                        goToActivityWithClearTask(LoginActivity.class);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout");
        alert.show();
    }


    @Override
    public void onUserDetailsSuccess(UserDetailsResponse userDetailsResponse) {
        if (userDetailsResponse != null) {
            if (userDetailsResponse.getStatus().equals("1")) {
                tv_userName.setText(userDetailsResponse.getFirstname() + " " + userDetailsResponse.getLastname());
            }
        }
    }

    @Override
    public void onUserDetailsFailed() {

    }
}
