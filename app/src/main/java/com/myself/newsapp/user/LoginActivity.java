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
import com.avos.avoscloud.LogInCallback;
import com.myself.library.controller.BaseActivity;
import com.myself.library.view.CleanableEditText;
import com.myself.newsapp.MainActivity;
import com.myself.newsapp.R;
import com.myself.newsapp.account.AccountHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 * Created by Jusenr on 2017/3/25.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_progress)
    ProgressBar mLoginProgress;
    @BindView(R.id.et_email)
    CleanableEditText mEtEmail;
    @BindView(R.id.et_password)
    CleanableEditText mEtPassword;
    @BindView(R.id.ll_login_form)
    LinearLayout mLlLoginForm;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

        if (AVUser.getCurrentUser() != null) {
            AccountHelper.login();
            startActivity(MainActivity.class);
            LoginActivity.this.finish();
        }

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

    @OnClick({R.id.left_title, R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_title:
                LoginActivity.this.finish();
                break;
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                LoginActivity.this.finish();
                break;
            case R.id.tv_forget:
                startActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    private void attemptLogin() {
        mEtEmail.setError(null);
        mEtPassword.setError(null);

        final String username = mEtEmail.getText().toString();
        final String password = mEtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEtPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mEtEmail.setError(getString(R.string.error_field_required));
            focusView = mEtEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        LoginActivity.this.finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        showProgress(false);
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLlLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mLlLoginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLlLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mLlLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

