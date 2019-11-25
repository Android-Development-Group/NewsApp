package com.myself.newsapp.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.controller.BaseFragment;
import com.myself.library.view.SettingItem;
import com.myself.library.view.image.RoundImageView;
import com.myself.newsapp.R;
import com.myself.newsapp.na_me.setting.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 * Created by Jusenr on 2017/3/25.
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.iv_user_icon)
    RoundImageView mIvUserIcon;
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

    @BindView(R.id.tv_me_integral)
    TextView mTvMeIntegral;
    @BindView(R.id.tv_me_m_collection)
    TextView mTvMeMCollection;
    @BindView(R.id.tv_me_m_concern)
    TextView mTvMeMConcern;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
//        getUserInfo(null);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick({R.id.ll_integral, R.id.ll_collection, R.id.ll_concern, R.id.si_message, R.id.si_advice, R.id.si_collection, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_integral:
                break;
            case R.id.ll_collection:
                break;
            case R.id.ll_concern:
                break;
            case R.id.si_message:
                break;
            case R.id.si_advice:
//                FeedbackAgent agent = new FeedbackAgent(mActivity);
//                agent.startDefaultThreadActivity();
                break;
            case R.id.si_collection:
//                break;
            case R.id.iv_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }


//    @Subcriber(tag = SettingActivity.EVENT_PERFECT)
//    private void getUserInfo(String s) {
//        Log.e(TAG, "getUserInfo: " );
//        String nickname = AVUser.getCurrentUser().getString("nickname");
//        mTvUserNickname.setText(StringUtils.isEmpty(nickname) ? getString(R.string.me_default_name) : nickname);
//
//        AVFile userpic = AVUser.getCurrentUser().getAVFile("userpic");
//        Picasso.with(mActivity)
//                .load(userpic == null ? "www" : userpic.getUrl())
//                .transform(new RoundedTransformation(9, 0))
//                .into(mIvUserIcon);
    }
//}
