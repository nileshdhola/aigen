package com.aigentech.in.utils;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.aigentech.in.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CommonUtils {

    //region validation Email
    public static boolean validateEmail(Context context, TextInputEditText edittextEmail, TextInputLayout inputEmailId) {
        String email = edittextEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_email));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else if (!email.equalsIgnoreCase("test@aigen.tech")) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_email_not_match));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }
        return true;
    }

    //endregion
    //region validation password
    public static boolean validatePassword(Context context, TextInputEditText edittextPassword, TextInputLayout inputEmailId) {
        String pass = edittextPassword.getText().toString().trim();
        if (edittextPassword.getText().toString().trim().isEmpty()) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_password));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else if (!pass.equalsIgnoreCase("AigenTech")) {
            inputEmailId.setError(context.getResources().getString(R.string.err_msg_password_not_match));
            requestFocus((Activity) context, inputEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }

        return true;
    }
    //endregion

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
