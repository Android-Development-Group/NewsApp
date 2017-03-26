package com.myself.newsapp.na_me.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.myself.library.utils.PreferenceUtils;
import com.myself.library.utils.StringUtils;
import com.myself.library.view.image.RoundImageView;
import com.myself.newsapp.R;
import com.myself.newsapp.TestActivity;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.guidance.GuidanceActivity;
import com.myself.newsapp.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends TitleActivity {


    @BindView(R.id.iv_header_icon)
    RoundImageView mIvHeaderIcon;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_region)
    TextView mTvRegion;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_password)
    TextView mTvPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        String username = PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_USERNAME, null);
        Log.e(TAG, "onViewCreatedFinish: " + username);
        if (StringUtils.isEmpty(username))
            mTvNickName.setText(username);
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(TestActivity.class);
    }

    @OnClick({R.id.rl_header_icon, R.id.rl_nick_name, R.id.rl_region, R.id.tv_update_email, R.id.tv_update_password, R.id.btn_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_header_icon:
                break;
            case R.id.rl_nick_name:
                break;
            case R.id.rl_region:
                break;
            case R.id.tv_update_email:
                break;
            case R.id.tv_update_password:
                break;
            case R.id.btn_loginout:
                AccountHelper.logout();
                startActivity(GuidanceActivity.class);
                finish();
                break;
        }
    }
}
