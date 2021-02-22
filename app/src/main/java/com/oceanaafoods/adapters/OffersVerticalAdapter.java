package com.oceanaafoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.oceanaafoods.R;
import com.oceanaafoods.models.responses.OffersResponseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersVerticalAdapter extends RecyclerView.Adapter<OffersVerticalAdapter.ViewHolder> {
    private Context context;
    private List<OffersResponseData> offersResponseDataList;
    private ItemListener itemListener;
    private String imagePath;

    public OffersVerticalAdapter(Context context, List<OffersResponseData> offersResponseDataList, ItemListener itemListener, String imagePath) {
        this.context = context;
        this.offersResponseDataList = offersResponseDataList;
        this.itemListener = itemListener;
        this.imagePath = imagePath;
    }

    public void setData(List<OffersResponseData> offersResponseDataList, String imagePath) {
        this.offersResponseDataList = offersResponseDataList;
        this.imagePath = imagePath;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(offersResponseDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return offersResponseDataList != null ? offersResponseDataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menuName)
        TextView tv_menuName;
        @BindView(R.id.iv_menu)
        ImageView iv_menu;

        @OnClick(R.id.relativeLayout)
        void onItemClick() {
            if (itemListener != null) {
                itemListener.onVerticalItemClick(getAdapterPosition(), offersResponseDataList.get(getAdapterPosition()));
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OffersResponseData offersResponseData) {
            tv_menuName.setVisibility(View.GONE);
            String image = imagePath + "/" + offersResponseData.getImage();
            Glide.with(context).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_verticalbanner).error(R.drawable.ic_verticalbanner)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(iv_menu);


        }
    }

    public interface ItemListener {
        void onVerticalItemClick(int position, OffersResponseData offersResponseData);
    }
}
