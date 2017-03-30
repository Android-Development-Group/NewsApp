package com.myself.newsapp.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.myself.library.controller.BaseActivity;
import com.myself.library.utils.StringUtils;
import com.myself.library.view.CleanableEditText;
import com.myself.newsapp.MainActivity;
import com.myself.newsapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 * Created by Jusenr on 2017/3/25.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_form)
    LinearLayout mRegisterForm;
    @BindView(R.id.register_progress)
    ProgressBar mRegisterProgress;
    @BindView(R.id.username)
    CleanableEditText mUsername;
    @BindView(R.id.password)
    CleanableEditText mPassword;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    @OnClick({R.id.left_title, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_title:
                RegisterActivity.this.finish();
                break;
            case R.id.btn_register:
                attemptRegister();
                break;
        }
    }

    private void attemptRegister() {
        mUsername.setError(null);
        mPassword.setError(null);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required_password));
            focusView = mPassword;
            cancel = true;
        } else {
            if (!isPasswordValid(password)) {
                mPassword.setError(getString(R.string.error_invalid_password));
                focusView = mPassword;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else {
            if (!isusernameValid(username)) {
                mUsername.setError(getString(R.string.error_invalid_email));
                focusView = mUsername;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            AVUser user = new AVUser();// 新建 AVUser 对象实例
            user.setUsername(username);// 设置用户名
            user.setPassword(password);// 设置密码
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        RegisterActivity.this.finish();
                    } else {
                        // 失败的原因可能有多种，常见的是用户名已经存在。
                        showProgress(false);
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isusernameValid(String username) {
        //TODO: Replace this with your own logic

        return StringUtils.checkEmailFormat(username);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return StringUtils.checkPasswordFormat(password);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mRegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mRegisterProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

