package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.RegisterResponse;

public interface IRegisterView extends IActivityBaseView {
    void registerSuccess(RegisterResponse registerResponse);

    void registerFailure();

}
