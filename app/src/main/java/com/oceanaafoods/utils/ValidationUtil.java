package com.oceanaafoods.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class ValidationUtil {

    public static boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString() == null || editText.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
