package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.MenuItemsResponse;
import com.oceanaafoods.models.responses.MenuTypesResponse;

public interface IMenuView extends IBaseView {
    void getMenuTypesSuccess(MenuTypesResponse menuTypesResponse);

    void getMenuTypesFailed();

    void getMenuItemsSuccess(MenuItemsResponse menuItemsResponse);

    void getMenuItemsFailed();
}
