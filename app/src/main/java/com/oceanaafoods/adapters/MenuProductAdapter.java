package com.oceanaafoods.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
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
import com.oceanaafoods.models.responses.MenuProductsResponseData;
import com.oceanaafoods.widgets.StrikeTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuProductAdapter extends RecyclerView.Adapter<MenuProductAdapter.ViewHolder> {
    private Context context;
    private List<MenuProductsResponseData> menuProductsResponseDataList;
    private ItemListener itemListener;

    public MenuProductAdapter(Context context, List<MenuProductsResponseData> menuProductsResponseDataList, ItemListener itemListener) {
        this.context = context;
        this.menuProductsResponseDataList = menuProductsResponseDataList;
        this.itemListener = itemListener;
    }

    public void setData(List<MenuProductsResponseData> menuProductsResponseData) {
        this.menuProductsResponseDataList = menuProductsResponseData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productlistmodel, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(menuProductsResponseDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuProductsResponseDataList != null ? menuProductsResponseDataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menuName)
        TextView tv_menuName;
        @BindView(R.id.iv_banner)
        ImageView iv_banner;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_retailprice)
        TextView tv_retailprice;
        @BindView(R.id.tv_retailpriceNumber)
        StrikeTextView tv_retailpriceNumber;
        @BindView(R.id.tv_offerprice)
        TextView tv_offerprice;
        @BindView(R.id.retail_layout)
        LinearLayout retail_layout;

        @OnClick(R.id.relativeLayout)
        void onItemClick() {
            if (itemListener != null) {
                itemListener.onItemClick(getAdapterPosition(), menuProductsResponseDataList.get(getAdapterPosition()));
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(MenuProductsResponseData menuProductsResponseData) {
            tv_menuName.setText(menuProductsResponseData.getTitle());
            tv_menuName.setVisibility(View.VISIBLE);
            if (menuProductsResponseData.getDescription() != null && !menuProductsResponseData.getDescription().isEmpty()) {
                tv_description.setText(menuProductsResponseData.getDescription());
            } else
                tv_description.setVisibility(View.GONE);

            if (menuProductsResponseData.getOfferapply()) {
                retail_layout.setVisibility(View.VISIBLE);
                tv_offerprice.setVisibility(View.VISIBLE);
                tv_retailprice.setText("PRICE : ");
                tv_retailpriceNumber.setText("₹ " + menuProductsResponseData.getRetailPrice());
                //tv_retailprice.setPaintFlags(tv_retailprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tv_offerprice.setText("OFFER : ₹ " + menuProductsResponseData.getOfferprice());

            } else {
                retail_layout.setVisibility(View.GONE);
                tv_offerprice.setVisibility(View.VISIBLE);
                tv_offerprice.setText("PRICE : ₹ " + menuProductsResponseData.getRetailPrice());
                tv_offerprice.setGravity(Gravity.LEFT);
            }

            if (menuProductsResponseData != null && menuProductsResponseData.getPrimages() != null &&
                    menuProductsResponseData.getPrimages().get(0) != null) {
                String image = menuProductsResponseData.getImagepath() + "/" + menuProductsResponseData.getPrimages().get(0).getProductImage();
                Glide.with(context).load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(iv_banner);
            }

        }
    }

    public interface ItemListener {
        void onItemClick(int position, MenuProductsResponseData menuProductsResponseData);
    }
}
