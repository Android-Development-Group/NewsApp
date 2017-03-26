package com.myself.newsapp.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.controller.BaseFragment;
import com.myself.library.view.SettingItem;
import com.myself.newsapp.R;
import com.myself.newsapp.na_me.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 * Created by Jusenr on 2017/3/25.
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.iv_user_icon)
    ImageView mIvUserIcon;
    @BindView(R.id.tv_user_nickname)
    TextView mTvUserNickname;
    @BindView(R.id.ll_user)
    LinearLayout mLlUser;
    @BindView(R.id.rl_user_head_icon)
    RelativeLayout mRlUserHeadIcon;
    @BindView(R.id.si_message)
    SettingItem mSiMessage;
    @BindView(R.id.si_advice)
    SettingItem mSiAdvice;
    @BindView(R.id.si_collection)
    SettingItem mSiCollection;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick({R.id.si_message, R.id.si_advice, R.id.si_collection, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.si_message:
//                break;
            case R.id.si_advice:
//                break;
            case R.id.si_collection:
//                break;
            case R.id.iv_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }
}
