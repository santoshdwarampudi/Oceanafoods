package com.oceanaafoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oceanaafoods.R;
import com.oceanaafoods.models.responses.MenuItemResponseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {
    private Context context;
    private List<MenuItemResponseData> menuItemResponseDataList;
    private onItemClickListener onItemClickListener;

    public MenuItemsAdapter(Context context, List<MenuItemResponseData> menuItemResponseDataList, MenuItemsAdapter.onItemClickListener onItemClickListener) {
        this.context = context;
        this.menuItemResponseDataList = menuItemResponseDataList;
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<MenuItemResponseData> orderItemModels) {
        this.menuItemResponseDataList = orderItemModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menuitemmodel, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_menuitem.setText(menuItemResponseDataList.get(position).getCategoryname());
    }

    @Override
    public int getItemCount() {
        return menuItemResponseDataList != null ? menuItemResponseDataList.size() : 0;
    }

    public interface onItemClickListener {
        void onMenuItemClicked(MenuItemResponseData menuItemResponseData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_menuitem)
        TextView tv_menuitem;

        @OnClick(R.id.card_menu)
        public void onCardClick() {
            if (onItemClickListener != null)
                onItemClickListener.onMenuItemClicked(menuItemResponseDataList.get(getAdapterPosition()));
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
