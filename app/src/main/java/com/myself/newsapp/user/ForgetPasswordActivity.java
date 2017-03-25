package com.myself.newsapp.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import com.myself.library.controller.BaseActivity;
import com.myself.library.utils.StringUtils;
import com.myself.library.utils.ToastUtils;
import com.myself.library.view.CleanableEditText;
import com.myself.library.view.SwitchButton;
import com.myself.library.view.TimeButton;
import com.myself.newsapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 * Created by Jusenr on 2017/3/25.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, TextWatcher, SwitchButton.OnSwitchClickListener {

    public static final String FORGET_CODE = "forget_code";

    @BindView(R.id.btn_nextstep)
    Button btn_nextstep;//下一步
    @BindView(R.id.et_mobile)
    CleanableEditText et_mobile;//手机号
    @BindView(R.id.et_sms_verify)
    CleanableEditText et_sms_verify;//获取验证码
    @BindView(R.id.et_password)
    CleanableEditText et_password;//密码
    @BindView(R.id.btn_lock)
    SwitchButton btn_lock;
    @BindView(R.id.tb_get_verify)
    TimeButton tb_get_verify;

    private int mErrorCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

        et_mobile.addTextChangedListener(this);
        et_mobile.setFilters(new InputFilter[]{new CleanableEditText.SpaceInputFilter()});
        //et_password.addTextChangedListener(this);
        btn_lock.setOnSwitchClickListener(this);
    }


    @OnClick({R.id.left_title, R.id.btn_nextstep, R.id.tb_get_verify, R.id.image_graph_verify})
    @Override
    public void onClick(View v) {
        final String mobile = et_mobile.getText().toString().trim();
        final String verify = "";
        switch (v.getId()) {
            case R.id.left_title:
                ForgetPasswordActivity.this.finish();
                break;
            case R.id.btn_nextstep://下一步
//                YouMengHelper.onEvent(mContext, YouMengHelper.Login_action, "找回密码");
                String regExp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
                Pattern p = Pattern.compile(regExp);
                Matcher m = p.matcher(mobile);
                if (!m.matches()) {
                    ToastUtils.showToastShort(mContext, R.string.email_format_error);
                    return;
                }

                final String pwd = et_password.getText().toString();
                if (!StringUtils.isLetterOrDigit(pwd)) {
                    et_password.setText(null);
                    ToastUtils.showToastShort(mContext, R.string.password_fomart);
                    return;
                }

                if (pwd.length() < 6 || pwd.length() > 18) {
                    ToastUtils.showToastShort(mContext, R.string.create_child_toast_9);
                    return;
                }

                String smsCode = et_sms_verify.getText().toString();
              /*  //忘记密码
                networkRequest(AccountApi.forget(mobile, smsCode, pwd), new AccountCallback(loading) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        //图形验证码的登录
                        networkRequest(AccountApi.safeLogin(mobile, pwd, verify),
                                new AccountCallback(loading) {
                                    @Override
                                    public void onSuccess(JSONObject result) {
                                        AccountHelper.setCurrentUid(result.getString("uid"));
                                        AccountHelper.setCurrentToken(result.getString("token"));
                                        AccountHelper.setCurrentMobile(mobile);
                                        checkLogin();
                                    }

                                    @Override
                                    public void onError(int code, String error_msg) {
//                                        Log.e(TAG, "safeLogin-onError: " + error_msg);
                                    }
                                });
                    }

                    @Override
                    public void onError(int code, String error_msg) {
//                        Log.e(TAG, "forget-onError: " + error_msg);
                        TipsUtils.getPrompt(mContext, code);
                    }
                });*/
                break;
            case R.id.tb_get_verify://获取验证码
                if (StringUtils.isEmpty(mobile)) {
                    tb_get_verify.reset();
                    ToastUtils.showToastLong(mContext, R.string.email_not_null);
                    return;
                }
                IsRegister(mobile);
                break;
        }
    }


    @Override
    public void onSwitchClick(View v, boolean isSelect) {
        if (!isSelect) //加密
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        else //不加密
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btn_nextstep.setClickable(true);
            btn_nextstep.setBackgroundResource(R.drawable.btn_get_focus);
        } else {
            btn_nextstep.setClickable(false);
            btn_nextstep.setBackgroundResource(R.drawable.btn_los_focus);
        }
    }

    /**
     * 发送验证码
     *
     * @param mobile
     */
    public void getSendCode(final String mobile) {
       /* networkRequest(AccountApi.sendVerifyCode(mobile, AccountConstants.Action.ACTION_FORGET),
                new AccountCallback(loading) {
                    @Override
                    public void onSuccess(JSONObject result) {
                        ToastUtils.showToastLong(mContext, GlobalApplication.isDebug ? "1234" : getString(R.string.verify_code_send));
                        if (!TextUtils.isEmpty(mDiskFileCacheHelper.getAsString(FORGET_CODE + mobile))) {
                            mDiskFileCacheHelper.remove(FORGET_CODE + mobile);
                        }
                    }

                    @Override
                    public void onError(int code, String error_msg) {
                        tb_get_verify.reset();
//                        Log.e(TAG, "getSendCode-onError: " + error_msg);
                        TipsUtils.getPrompt(mContext, code);
                        mErrorCount++;
                        if (mErrorCount >= 2) {
                            mDiskFileCacheHelper.put(FORGET_CODE + mobile, FORGET_CODE);
                        }
                    }
                });*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 验证登录
     */
    private void checkLogin() {
      /*  networkRequest(AccountApi.login(),
                new SimpleFastJsonCallback<UserInfo>(UserInfo.class, null) {
                    @Override
                    public void onSuccess(String url, UserInfo result) {
                        AccountHelper.setUserInfo(result);
                        EventBusHelper.post(LoginActivity.EVENT_LOGIN, LoginActivity.EVENT_LOGIN);
                        EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_ME_TAB);
                        checkInquiryBind(AccountHelper.getCurrentUid());
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ToastUtils.showToastLong(mContext, R.string.no_network_tips);
                        loading.dismiss();
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        super.onFinish(url, isSuccess, msg);
                    }
                }
        );*/
    }

    /**
     * 查询登录的用户是否绑定过产品
     *
     * @param currentUid
     */
    private void checkInquiryBind(String currentUid) {
      /*  networkRequest(UserApi.checkInquiryBind(currentUid),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        Boolean is_relation = JSONObject.parseObject(result).getBoolean("is_relation");
                        PreferenceUtils.save(GlobalApplication.IS_DEVICE_BIND + AccountHelper.getCurrentUid(), is_relation);
                        loading.dismiss();
                        startActivity(IndexActivity.class, args);
                        finish();
                    }
                });*/
    }

    /**
     * 是否注册
     */
    private void IsRegister(final String mobile) {
       /* networkRequest(AccountApi.checkMobile(mobile), new AccountCallback(loading) {
            @Override
            public void onSuccess(JSONObject result) {//未使用该号码
                ToastUtils.showToastShort(mContext, R.string.account_not_exist);
                et_mobile.setText("");
                tb_get_verify.reset();
            }

            @Override
            public void onError(int code, String error_msg) {//已注册该号码
//                Log.e("####--", "onError: " + error_msg);//61009--手机号为空
                getSendCode(mobile);
            }

            @Override
            public void onFailure(String url, int statusCode, String msg) {
                super.onFailure(url, statusCode, msg);
                ToastUtils.showToastShort(mContext, R.string.no_network_tips);
                tb_get_verify.reset();
            }
        });*/
    }

}