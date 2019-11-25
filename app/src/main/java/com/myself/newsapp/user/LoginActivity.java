package com.myself.newsapp.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.utils.StringUtils;
import com.myself.library.view.CleanableEditText;
import com.myself.newsapp.MainActivity;
import com.myself.newsapp.R;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.base.TitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 * Created by Jusenr on 2017/3/25.
 */
public class LoginActivity extends TitleActivity implements TextWatcher {

    @BindView(R.id.iv_email_icon)
    ImageView mIvEmailIcon;
    @BindView(R.id.et_email)
    CleanableEditText mEtEmail;
    @BindView(R.id.rl_email)
    RelativeLayout mRlEmail;
    @BindView(R.id.iv_phone_icon)
    ImageView mIvPhoneIcon;
    @BindView(R.id.et_mobile)
    CleanableEditText mEtMobile;
    @BindView(R.id.rl_phone)
    RelativeLayout mRlPhone;
    @BindView(R.id.iv_password_icon)
    ImageView mIvPasswordIcon;
    @BindView(R.id.et_password)
    CleanableEditText mEtPassword;
    @BindView(R.id.rl_password)
    RelativeLayout mRlPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mEtEmail.addTextChangedListener(this);
        mEtMobile.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mBtnLogin.setClickable(false);
        mEtMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRlPhone.setBackgroundResource(R.drawable.edit_login_sel);
                    mIvPhoneIcon.setImageResource(R.drawable.login_account_icon_h);
                } else {
                    mRlPhone.setBackgroundResource(R.drawable.edit_login_nor);
                    mIvPhoneIcon.setImageResource(R.drawable.login_account_icon_n);
                }
            }
        });
        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRlPassword.setBackgroundResource(R.drawable.edit_login_sel);
                    mIvPasswordIcon.setImageResource(R.drawable.login_password_icon_h);
                } else {
                    mRlPassword.setBackgroundResource(R.drawable.edit_login_nor);
                    mIvPasswordIcon.setImageResource(R.drawable.login_password_icon_n);
                }
            }
        });
        mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRlEmail.setBackgroundResource(R.drawable.edit_login_sel);
                    mIvEmailIcon.setImageResource(R.drawable.findpassword_verificationcode_icon_h);
                } else {
                    mRlEmail.setBackgroundResource(R.drawable.edit_login_nor);
                    mIvEmailIcon.setImageResource(R.drawable.findpassword_verificationcode_icon_n);
                }
            }
        });

        mEtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEtEmail.length() > 6 && mEtPassword.length() >= 6) {
            mBtnLogin.setClickable(true);
            mBtnLogin.setBackgroundResource(R.drawable.btn_login_sel);
        } else {
            mBtnLogin.setClickable(false);
            mBtnLogin.setBackgroundResource(R.drawable.btn_login_nor);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick({R.id.btn_login, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.tv_forget:
                startActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isusernameValid(String username) {
        return StringUtils.checkEmailFormat(username);
    }

    private boolean isPasswordValid(String password) {
        return StringUtils.checkPasswordFormat(password);
    }

    private void attemptLogin() {
        mEtEmail.setError(null);
        mEtPassword.setError(null);

        final String username = mEtEmail.getText().toString();
        final String password = mEtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(getString(R.string.error_field_required_password));
            focusView = mEtPassword;
            cancel = true;
        } else {
            if (!isPasswordValid(password)) {
                mEtPassword.setError(getString(R.string.error_invalid_password));
                focusView = mEtPassword;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(username)) {
            mEtEmail.setError(getString(R.string.error_field_required));
            focusView = mEtEmail;
            cancel = true;
        } else {
            if (!isusernameValid(username)) {
                mEtEmail.setError(getString(R.string.error_invalid_email));
                focusView = mEtEmail;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            LoginActivity.this.finish();
            AccountHelper.login();
            startActivity(MainActivity.class);

//            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
//                @Override
//                public void done(AVUser avUser, AVException e) {
//                    if (e == null) {
//                        LoginActivity.this.finish();
//                        AccountHelper.login();
//                        startActivity(MainActivity.class);
//                    } else {
//                        TipsUtils.getPrompt(mContext, e.getCode());
//                    }
//                }
//            });
        }
    }
}

