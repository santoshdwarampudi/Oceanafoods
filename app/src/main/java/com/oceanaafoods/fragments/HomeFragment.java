package com.oceanaafoods.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.oceanaafoods.R;
import com.oceanaafoods.activities.ProductsListActivity;
import com.oceanaafoods.adapters.MenuItemsAdapter;
import com.oceanaafoods.baseui.BaseFragment;
import com.oceanaafoods.interfaces.IMenuView;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.responses.MenuItemResponseData;
import com.oceanaafoods.models.responses.MenuItemsResponse;
import com.oceanaafoods.models.responses.MenuTypeResponseData;
import com.oceanaafoods.models.responses.MenuTypesResponse;
import com.oceanaafoods.presenters.MenuPresenter;
import com.oceanaafoods.utils.APIClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment implements IMenuView, MenuItemsAdapter.onItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Handler mHandler;
    private List<MenuTypeResponseData> menuTypeResponseDataList;
    private MenuPresenter menuPresenter;
    private MenuItemsAdapter menuItemsAdapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rv_menuItems)
    RecyclerView rv_menuItems;
    @BindView(R.id.fab_refresh)
    FloatingActionButton fab_refresh;


    @OnClick(R.id.fab_refresh)
    public void onRefreshClicked() {
        callHomeService();
    }


    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        mHandler = new Handler();
        menuTypeResponseDataList = new ArrayList<>();
        callHomeService();
        initTabChangeListener();
        initRecyclerView();
        return view;
    }

    private void callHomeService() {
        if (menuPresenter == null)
            menuPresenter = new MenuPresenter(this, APIClient.getAPIService());
        //to get the menu types
        menuPresenter.getMenuTypes(true);
    }

    private void initRecyclerView() {
        menuItemsAdapter = new MenuItemsAdapter(getActivity(), null, this::onMenuItemClicked);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_menuItems.setLayoutManager(mLayoutManager);
        rv_menuItems.setItemAnimator(new DefaultItemAnimator());
        rv_menuItems.setAdapter(menuItemsAdapter);
    }

    private void initTabChangeListener() {
        try {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (menuTypeResponseDataList != null && menuTypeResponseDataList.get(tab.getPosition()) != null) {
                        MenuTypeResponseData menuTypeResponseData = menuTypeResponseDataList.get(tab.getPosition());
                        if (menuTypeResponseData != null) {
                            menuPresenter.getMenuItems(true, menuTypeResponseData.getId());
                        }
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            Log.e("exception at tabclick", e.toString());
            e.printStackTrace();
        }

    }

    private void loadFragment(final Fragment fragment) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, "");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

    }

    @Override
    public void getMenuTypesSuccess(MenuTypesResponse menuTypesResponse) {
        if (menuTypesResponse != null) {
            if (menuTypesResponse.getMenutypes() != null && menuTypesResponse.getMenutypes().size() > 0) {
                menuTypeResponseDataList = menuTypesResponse.getMenutypes();
                int count = tabLayout.getTabCount();
                if (count == 0)
                    setTabs(menuTypesResponse.getMenutypes());
                else {
                    tabLayout.removeAllTabs();
                    setTabs(menuTypesResponse.getMenutypes());
                }
            } else {
                tabLayout.removeAllTabs();
                showToast("No Menu Found");
            }
        } else {
            showToast("Failed to get the menu");
        }
    }

    private void setTabs(List<MenuTypeResponseData> menutypes) {
        if (menutypes.size() < 3) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        for (MenuTypeResponseData menuTypeResponseData : menutypes) {
            tabLayout.addTab(tabLayout.newTab().setText(menuTypeResponseData.getName()));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void getMenuTypesFailed() {
        showToast("Failed to get the menu");
    }

    @Override
    public void getMenuItemsSuccess(MenuItemsResponse menuItemsResponse) {
        if (menuItemsResponse != null) {
            if (menuItemsResponse.getMenuitems() != null && menuItemsResponse.getMenuitems().size() > 0) {
                menuItemsAdapter.setData(menuItemsResponse.getMenuitems());
            } else {
                showToast("No Menu Items Found");
                menuItemsAdapter.setData(null);

            }

        } else {
            menuItemsAdapter.setData(null);
            showToast("Failed to get the menu Items");
        }

    }

    @Override
    public void getMenuItemsFailed() {
        menuItemsAdapter.setData(null);
        showToast("Failed to get the menu Items");
    }

    @Override
    public void onMenuItemClicked(MenuItemResponseData menuItemResponseData) {

        if (menuItemResponseData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(StringConstants.RESPONSE, menuItemResponseData);
            goToActivity(ProductsListActivity.class, bundle);
        }
    }
}
