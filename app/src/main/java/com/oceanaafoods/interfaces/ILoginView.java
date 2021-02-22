package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.LoginResponse;

public interface ILoginView extends IActivityBaseView {
    void onLoginSuccess(LoginResponse loginResponse);

    void onLoginFailed();
}
