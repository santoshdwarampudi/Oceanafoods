package com.oceanaafoods.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oceanaafoods.R;
import com.oceanaafoods.adapters.OffersHorizontalAdapter;
import com.oceanaafoods.adapters.OffersVerticalAdapter;
import com.oceanaafoods.baseui.BaseFragment;
import com.oceanaafoods.interfaces.IOffersView;
import com.oceanaafoods.models.responses.OffersResponse;
import com.oceanaafoods.models.responses.OffersResponseData;
import com.oceanaafoods.presenters.OffersPresenter;
import com.oceanaafoods.utils.APIClient;
import com.oceanaafoods.utils.CirclePagerIndicatorDecoration;
import com.oceanaafoods.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class OffersFragment extends BaseFragment implements IOffersView, OffersHorizontalAdapter.ItemListener, OffersVerticalAdapter.ItemListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private OffersPresenter offersPresenter;
    private OffersHorizontalAdapter offersHorizontalAdapter;
    private OffersVerticalAdapter offersVerticalAdapter;
    private Handler mHandler;
    private List<OffersResponseData> horizontalList, verticalList;
    private LinearLayoutManager linearLayoutManager;
    private Timer timer;
    public int position = 0;
    @BindView(R.id.rv_verticalOffers)
    RecyclerView rv_verticalOffers;
    @BindView(R.id.rv_horizontalOffers)
    RecyclerView rv_horizontalOffers;
    @BindView(R.id.fab_refresh)
    FloatingActionButton fab_refresh;

    @OnClick(R.id.fab_refresh)
    public void onRefreshClick() {
        horizontalList.clear();
        verticalList.clear();
        position = 0;
        rv_horizontalOffers.setOnFlingListener(null);
        offersVerticalAdapter.notifyDataSetChanged();
        offersHorizontalAdapter.notifyDataSetChanged();
        cancelTimer();
        getOffers();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();  // Terminates this timer, discarding any currently scheduled tasks.
            timer.purge();   // Removes all cancelled tasks from this timer's task queue.
            timer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    public OffersFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
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
        return R.layout.fragment_offers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView();
        initLists();
        getOffers();
        timer = new Timer();
        return view;
    }

    private void getOffers() {
        if (offersPresenter == null)
            offersPresenter = new OffersPresenter(APIClient.getAPIService(), this);
        offersPresenter.getOffers(true);
    }

    private void initLists() {
        horizontalList = new ArrayList<>();
        verticalList = new ArrayList<>();
    }

    private void initRecyclerView() {

        offersHorizontalAdapter = new OffersHorizontalAdapter(getActivity(), null, null, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_horizontalOffers.setLayoutManager(linearLayoutManager);
        rv_horizontalOffers.setItemAnimator(new DefaultItemAnimator());
        rv_horizontalOffers.setAdapter(offersHorizontalAdapter);
        rv_horizontalOffers.setNestedScrollingEnabled(false);
        int activeColor = getActivity().getResources().getColor(R.color.bottom_clicked_color);
        rv_horizontalOffers.addItemDecoration(new CirclePagerIndicatorDecoration(activeColor, 0));

        offersVerticalAdapter = new OffersVerticalAdapter(getActivity(), null, this, null);
        int spacingInPixels = (int) getResources().getDimension(R.dimen._3sdp);
        rv_verticalOffers.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv_verticalOffers.setLayoutManager(manager);
        rv_verticalOffers.setItemAnimator(new DefaultItemAnimator());
        rv_verticalOffers.setNestedScrollingEnabled(false);
        rv_verticalOffers.setAdapter(offersVerticalAdapter);
    }


    @Override
    public void getOffersResponseSuccess(OffersResponse offersResponse) {
        if (offersResponse != null) {
            if (offersResponse.getSliders() != null && offersResponse.getSliders().size() > 0) {
                for (OffersResponseData offersResponseData : offersResponse.getSliders()) {
                    if (offersResponseData.getPriority() != null && !offersResponseData.getPriority().isEmpty()) {
                        if (offersResponseData.getPriority().equals("1")) {
                            horizontalList.add(offersResponseData);
                        } else if (offersResponseData.getPriority().equals("2")) {
                            verticalList.add(offersResponseData);
                        }
                    }
                }
                offersHorizontalAdapter.setData(horizontalList, offersResponse.getImagepath());
                offersVerticalAdapter.setData(verticalList, offersResponse.getImagepath());
                setSnapHelper();
                if (timer == null)
                    timer = new Timer();
                timer.scheduleAtFixedRate(new RemindTask(), 2000, 2000); // delay*/

            } else {
                showToast("No offers found");
            }
        } else {
            showToast("Failed to get the offers");
        }
    }

    private void setSnapHelper() {
        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(rv_horizontalOffers);
        rv_horizontalOffers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                position = linearLayoutManager.findFirstVisibleItemPosition();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void getOffersResponseFailed() {
        showToast("Failed to get the offers");
    }

    @Override
    public void onHorizontalItemClick(int position, OffersResponseData offersResponseData) {

    }

    @Override
    public void onVerticalItemClick(int position, OffersResponseData offersResponseData) {

    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (horizontalList != null && horizontalList.size() > 0) {
                            if (position == horizontalList.size()) {
                                position = 0;
                            } else {
                                position++;

                            }
                        }
                        if (horizontalList != null && position == horizontalList.size()) {
                            position = 0;
                        }
                        if (getActivity() != null) {
                            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
                                @Override
                                protected int getVerticalSnapPreference() {
                                    return LinearSmoothScroller.SNAP_TO_START;
                                }
                            };
                            smoothScroller.setTargetPosition(position);
                            linearLayoutManager.startSmoothScroll(smoothScroller);
                        }


                    }
                });
            }

        }
    }
}
