package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.ScratchCardResponse;
import com.oceanaafoods.models.responses.UpdateScratchResponse;

public interface IScratchCardView extends IActivityBaseView {
    void onScratchCardSuccess(ScratchCardResponse scratchCardResponse);

    void onScratchCardFailed();

    void onScratchUpdateSuccess(UpdateScratchResponse updateScratchResponse);

    void onScratchUpdateFailed();


}
