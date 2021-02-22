package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.OffersResponse;

public interface IOffersView extends IBaseView {
    void getOffersResponseSuccess(OffersResponse offersResponse);

    void getOffersResponseFailed();
}
