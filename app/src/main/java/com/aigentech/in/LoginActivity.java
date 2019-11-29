package com.aigentech.in;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.aigentech.in.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edittextPassword)
    TextInputEditText edittextPassword;
    @BindView(R.id.edittextEmail)
    TextInputEditText edittextEmail;

    @BindView(R.id.inputPassword)
    TextInputLayout inputPassword;
    @BindView(R.id.inputEamilId)
    TextInputLayout inputEamilId;

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        edittextEmail.addTextChangedListener(new MyTextWatcher(edittextEmail));
        edittextPassword.addTextChangedListener(new MyTextWatcher(edittextPassword));
        // Capture button clicks
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!CommonUtils.validateEmail(LoginActivity.this, edittextEmail, inputEamilId)) {
                    return;
                }
                if (!CommonUtils.validatePassword(LoginActivity.this, edittextPassword, inputPassword)) {
                    return;
                }

                //Toast.makeText(LoginActivity.this, "Thanks", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edittextEmail:
                    //Context context, TextInputEditText edittextEmail, TextInputLayout inputEmailId
                    CommonUtils.validateEmail(LoginActivity.this, edittextEmail, inputEamilId);
                    break;
                case R.id.edittextPassword:
                    CommonUtils.validatePassword(LoginActivity.this, edittextPassword, inputPassword);
                    break;
            }
        }
    }

}
