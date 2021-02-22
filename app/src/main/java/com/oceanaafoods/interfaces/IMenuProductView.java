package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.MenuProductsResponse;

public interface IMenuProductView extends IBaseView {

    void getMenuProductsSuccess(MenuProductsResponse menuProductsResponse);

    void getMenuProductsFailed();

}
