package com.oceanaafoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.oceanaafoods.R;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.responses.ScratchcardData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    private Context context;
    private List<ScratchcardData> scratchcardDataList;
    private ItemClickListener itemClickListener;

    public PointsAdapter(Context context, ItemClickListener itemClickListener, List<ScratchcardData> scratchcardDataList) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.scratchcardDataList = scratchcardDataList;
    }

    public void setData(List<ScratchcardData> scratchcardDataList) {
        this.scratchcardDataList = scratchcardDataList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pointsmodel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(scratchcardDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return scratchcardDataList != null ? scratchcardDataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_offer)
        ImageView iv_offer;
        @BindView(R.id.tv_wonprice)
        TextView tv_wonprice;
        @BindView(R.id.scratch_layout)
        LinearLayout scratch_layout;
        @BindView(R.id.iv_scratch)
        ImageView iv_scratch;
        @BindView(R.id.food_layout)
        LinearLayout food_layout;
        @BindView(R.id.iv_food)
        ImageView iv_food;
        @BindView(R.id.tv_foodprice)
        TextView tv_foodprice;

        @OnClick(R.id.cardView)
        void OnCardClick() {
            if (itemClickListener != null)
                itemClickListener.onItemClick(scratchcardDataList.get(getAdapterPosition()));
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ScratchcardData scratchcardData) {
            if (scratchcardData != null) {
                if (scratchcardData.getScratchStatus().equalsIgnoreCase(StringConstants.SCRATCHED)) {
                    scratch_layout.setVisibility(View.VISIBLE);
                    iv_scratch.setVisibility(View.GONE);
                    food_layout.setVisibility(View.GONE);
                    if (scratchcardData.getOffertype().equalsIgnoreCase(StringConstants.AMOUNT)) {
                        tv_wonprice.setText("You’ve won\n₹ " + scratchcardData.getOffer());
                    } else if (scratchcardData.getOffertype().equalsIgnoreCase(StringConstants.FOODITEM)) {
                        food_layout.setVisibility(View.VISIBLE);
                        scratch_layout.setVisibility(View.GONE);
                        tv_foodprice.setText("You’ve won\n₹ " + scratchcardData.getFoodprice());
                        Glide.with(context).load(scratchcardData.getFoodimage())
                                .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(iv_food);
                    }
                } else if (scratchcardData.getScratchStatus().equalsIgnoreCase(StringConstants.UNSCRATCHED)) {
                    scratch_layout.setVisibility(View.GONE);
                    iv_scratch.setVisibility(View.VISIBLE);
                    food_layout.setVisibility(View.GONE);
                }
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(ScratchcardData scratchcardData);
    }
}
