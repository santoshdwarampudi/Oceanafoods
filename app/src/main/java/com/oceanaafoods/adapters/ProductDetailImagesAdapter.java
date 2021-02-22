package com.oceanaafoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.oceanaafoods.R;
import com.oceanaafoods.models.responses.MenuProductsImagesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailImagesAdapter extends RecyclerView.Adapter<ProductDetailImagesAdapter.ViewHolder> {
    private Context context;
    private List<MenuProductsImagesResponse> menuProductsImagesResponseList;
    private String imagePath;
    private ItemListener itemListener;

    public ProductDetailImagesAdapter(Context context, List<MenuProductsImagesResponse> menuProductsImagesResponseList, String imagePath, ItemListener itemListener) {
        this.context = context;
        this.menuProductsImagesResponseList = menuProductsImagesResponseList;
        this.imagePath = imagePath;
        this.itemListener = itemListener;
    }

    public void setData(List<MenuProductsImagesResponse> menuProductsImagesResponses,String imagePath) {
        this.menuProductsImagesResponseList = menuProductsImagesResponses;
        this.imagePath=imagePath;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productimagesmodel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(menuProductsImagesResponseList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuProductsImagesResponseList != null ? menuProductsImagesResponseList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_banner)
        ImageView iv_banner;

        @OnClick(R.id.iv_banner)
        void onBannerClick() {
            if (itemListener != null)
                itemListener.onItemClick(getAdapterPosition(), menuProductsImagesResponseList.get(getAdapterPosition()));
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(MenuProductsImagesResponse menuProductsImagesResponse) {
            if (menuProductsImagesResponse != null && imagePath != null && !imagePath.isEmpty()) {
                String image = imagePath + "/" + menuProductsImagesResponse.getProductImage();
                Glide.with(context).load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(iv_banner);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(int position, MenuProductsImagesResponse menuProductsImagesResponse);
    }
}
