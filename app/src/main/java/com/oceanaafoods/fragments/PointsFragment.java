package com.oceanaafoods.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.oceanaafoods.R;
import com.oceanaafoods.adapters.PointsAdapter;
import com.oceanaafoods.baseui.BaseFragment;
import com.oceanaafoods.interfaces.IScratchCardView;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.responses.ScratchCardResponse;
import com.oceanaafoods.models.responses.ScratchcardData;
import com.oceanaafoods.models.responses.UpdateScratchResponse;
import com.oceanaafoods.presenters.ScratchcardPresenter;
import com.oceanaafoods.utils.APIClient;
import com.oceanaafoods.utils.PrefUtils;
import com.oceanaafoods.utils.SpacesItemDecoration;

import butterknife.BindView;


public class PointsFragment extends BaseFragment implements PointsAdapter.ItemClickListener, IScratchCardView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private PointsAdapter pointsAdapter;
    private ScratchcardPresenter scratchcardPresenter;
    private boolean scratchUpdated;
    @BindView(R.id.rv_points)
    RecyclerView rv_points;
    @BindView(R.id.tv_msg)
    TextView tv_msg;


    public PointsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PointsFragment newInstance(String param1, String param2) {
        PointsFragment fragment = new PointsFragment();
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
        return R.layout.fragment_points;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView();
        scratchcardPresenter = new ScratchcardPresenter(APIClient.getAPIService(), this);
        callScratchCardsApi();
        return view;
    }

    private void callScratchCardsApi() {
        scratchcardPresenter.getScratchCards(PrefUtils.getInstance().getUserId() + "");
    }

    private void initRecyclerView() {
        pointsAdapter = new PointsAdapter(getActivity(), this::onItemClick, null);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv_points.setLayoutManager(manager);
        rv_points.setItemAnimator(new DefaultItemAnimator());
        rv_points.setAdapter(pointsAdapter);
        int spacingInPixels = (int) getResources().getDimension(R.dimen._5sdp);
        rv_points.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }


    @Override
    public void onItemClick(ScratchcardData scratchcardData) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.scratchview);
        ScratchView scratch_view = dialog.findViewById(R.id.scratch_view);
        ImageView iv_offer = dialog.findViewById(R.id.iv_offer);
        TextView tv_wonprice = dialog.findViewById(R.id.tv_wonprice);

        if (scratchcardData.getScratchStatus() != null && !scratchcardData.getScratchStatus().isEmpty()) {
            if (scratchcardData.getScratchStatus().equalsIgnoreCase(StringConstants.SCRATCHED)) {
                scratch_view.setVisibility(View.GONE);
            } else if (scratchcardData.getScratchStatus().equalsIgnoreCase(StringConstants.UNSCRATCHED)) {
                scratch_view.setVisibility(View.VISIBLE);
            }
        } else {
            scratch_view.setVisibility(View.VISIBLE);
        }

        if (scratchcardData.getOffertype().equalsIgnoreCase(StringConstants.FOODITEM)) {
            tv_wonprice.setText("You’ve won\n₹ " + scratchcardData.getOffer());
            Glide.with(getActivity()).load(scratchcardData.getFoodimage())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(iv_offer);
        } else if (scratchcardData.getOffertype().equalsIgnoreCase(StringConstants.AMOUNT)) {
            tv_wonprice.setText("You’ve won\n₹ " + scratchcardData.getOffer());
        } else if (scratchcardData.getOffertype().equalsIgnoreCase(StringConstants.NONE)) {
            tv_wonprice.setText(scratchcardData.getOffer());
        }
        scratch_view.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {
                scratchUpdated = true;
            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                if (percent > 0.02) {
                    scratchUpdated = true;
                }
            }
        });
        dialog.setTitle("Custom Dialog");

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (scratchUpdated)
                    scratchcardPresenter.updateScratchCard(scratchcardData.getUniqueid());
            }
        });
        dialog.show();


    }

    @Override
    public void onScratchCardSuccess(ScratchCardResponse scratchCardResponse) {
        if (scratchCardResponse != null) {
            if (scratchCardResponse.getSuccess() == 1) {
                if (scratchCardResponse.getScratchcards() != null && scratchCardResponse.getScratchcards().size() > 0) {
                    if (pointsAdapter != null)
                        pointsAdapter.setData(scratchCardResponse.getScratchcards());
                    else {
                        pointsAdapter = new PointsAdapter(getActivity(), this::onItemClick, null);
                        pointsAdapter.setData(scratchCardResponse.getScratchcards());
                    }
                } else {
                    failedToGetScratchCards(scratchCardResponse.getMessage());
                }
            } else {
                failedToGetScratchCards(scratchCardResponse.getMessage());
            }
        } else {
            failedToGetScratchCards("No scratch Cards found");
        }
    }

    @Override
    public void onScratchCardFailed() {
        failedToGetScratchCards("No scratch Cards found");
    }

    @Override
    public void onScratchUpdateSuccess(UpdateScratchResponse updateScratchResponse) {
        callScratchCardsApi();
        scratchUpdated = false;
    }

    @Override
    public void onScratchUpdateFailed() {

    }

    private void failedToGetScratchCards(String message) {
        tv_msg.setVisibility(View.VISIBLE);
        rv_points.setVisibility(View.GONE);
        tv_msg.setText(message);
    }
}
