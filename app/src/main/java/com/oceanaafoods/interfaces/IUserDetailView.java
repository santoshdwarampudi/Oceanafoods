package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.UserDetailsResponse;

public interface IUserDetailView extends IActivityBaseView {
    void onUserDetailsSuccess(UserDetailsResponse userDetailsResponse);

    void onUserDetailsFailed();
}
