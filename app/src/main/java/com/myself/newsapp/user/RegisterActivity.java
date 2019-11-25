package com.myself.newsapp.user;

import android.content.Intent;
import android.graphics.Paint;
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
import com.myself.library.view.SwitchButton;
import com.myself.library.view.TimeButton;
import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 * Created by Jusenr on 2017/3/25.
 */
public class RegisterActivity extends TitleActivity implements TextWatcher {

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
    @BindView(R.id.iv_sms_verify_icon)
    ImageView mIvSmsVerifyIcon;
    @BindView(R.id.et_sms_verify)
    CleanableEditText mEtSmsVerify;
    @BindView(R.id.tb_get_verify)
    TimeButton mTbGetVerify;
    @BindView(R.id.rl_sms_verify)
    RelativeLayout mRlSmsVerify;
    @BindView(R.id.iv_password_icon)
    ImageView mIvPasswordIcon;
    @BindView(R.id.et_password)
    CleanableEditText mEtPassword;
    @BindView(R.id.btn_is_look)
    SwitchButton mBtnIsLook;
    @BindView(R.id.rl_password)
    RelativeLayout mRlPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.tv_user_protocol)
    TextView mTvUserProtocol;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        mTvUserProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mEtEmail.addTextChangedListener(this);
        mEtMobile.addTextChangedListener(this);
        mEtSmsVerify.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mBtnRegister.setClickable(false);
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
        mEtSmsVerify.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRlSmsVerify.setBackgroundResource(R.drawable.edit_login_sel);
                    mIvSmsVerifyIcon.setImageResource(R.drawable.findpassword_verificationcode_icon_h);
                } else {
                    mRlSmsVerify.setBackgroundResource(R.drawable.edit_login_nor);
                    mIvSmsVerifyIcon.setImageResource(R.drawable.findpassword_verificationcode_icon_n);
                }
            }
        });

        mEtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
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
            mBtnRegister.setClickable(true);
            mBtnRegister.setBackgroundResource(R.drawable.btn_login_sel);
        } else {
            mBtnRegister.setClickable(false);
            mBtnRegister.setBackgroundResource(R.drawable.btn_login_nor);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick({R.id.btn_register, R.id.tv_user_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                attemptRegister();
                break;
            case R.id.tv_user_protocol:
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

    private void attemptRegister() {
        mEtEmail.setError(null);
        mEtPassword.setError(null);

        final String username = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

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
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            RegisterActivity.this.finish();

//            AVUser user = new AVUser();// 新建 AVUser 对象实例
//            user.setUsername(username);// 设置用户名
//            user.setEmail(username);// 设置邮箱
//            user.setPassword(password);// 设置密码

            //重新发送验证邮箱
//            AVUser.requestEmailVerifyInBackground(username, new RequestEmailVerifyCallback() {
//                @Override
//                public void done(AVException e) {
//                    if (e == null) {
//                        // 求重发验证邮件成功
//                        ToastUtils.showToastShort(mContext, "请去邮箱" + username + "验证");
//                    } else {
//                        showProgress(false);
//                        String message = e.getMessage();
//                        ToastUtils.showToastShort(mContext, message);
//                    }
//                }
//            });

//            user.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(AVException e) {
//                    if (e == null) {
//                        ToastUtils.showToastShort(mContext, "请去邮箱" + username + "验证后方可登录！");
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                        RegisterActivity.this.finish();
//                    } else {
//                        TipsUtils.getPrompt(mContext, e.getCode());
//                    }
//                }
//            });
        }
    }
}