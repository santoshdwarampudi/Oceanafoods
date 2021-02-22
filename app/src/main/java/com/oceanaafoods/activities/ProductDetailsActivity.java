package com.oceanaafoods.activities;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.oceanaafoods.R;
import com.oceanaafoods.adapters.ProductDetailImagesAdapter;
import com.oceanaafoods.baseui.BaseAppCompactActivity;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.responses.MenuProductsImagesResponse;
import com.oceanaafoods.models.responses.MenuProductsResponseData;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailsActivity extends BaseAppCompactActivity implements ProductDetailImagesAdapter.ItemListener {
    private MenuProductsResponseData menuProductsResponseData;
    private ProductDetailImagesAdapter productDetailImagesAdapter;
    @BindView(R.id.tv_heading)
    TextView tv_heading;
    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    @BindView(R.id.iv_menuImage)
    ImageView iv_menuImage;

    @OnClick(R.id.iv_menu)
    public void onBackClick() {
        finish();
    }


    @Override
    public int getActivityLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initRecyclerView();
            menuProductsResponseData = (MenuProductsResponseData) bundle.getSerializable(StringConstants.RESPONSE);
            if (menuProductsResponseData != null) {
                tv_heading.setText(menuProductsResponseData.getTitle().toUpperCase());
                if (menuProductsResponseData.getPrimages() != null && menuProductsResponseData.getPrimages().size() > 0)
                    productDetailImagesAdapter.setData(menuProductsResponseData.getPrimages(), menuProductsResponseData.getImagepath());
                rv_images.scrollToPosition(0);
                String image = menuProductsResponseData.getImagepath() + "/" + menuProductsResponseData.getMenuDisplayImage();
                Glide.with(ProductDetailsActivity.this).load(image)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_horizontalbanner).error(R.drawable.ic_horizontalbanner)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(iv_menuImage);
            }
        }
    }

    private void initRecyclerView() {
        productDetailImagesAdapter = new ProductDetailImagesAdapter(ProductDetailsActivity.this, null, null, this::onItemClick);
        rv_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_images.setItemAnimator(new DefaultItemAnimator());
        rv_images.setAdapter(productDetailImagesAdapter);
        rv_images.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(int position, MenuProductsImagesResponse menuProductsImagesResponse) {

    }
}
