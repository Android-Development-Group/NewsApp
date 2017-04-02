package com.myself.newsapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myself.library.utils.ToastUtils;
import com.myself.library.view.CleanableEditText;
import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.na_me.setting.SettingActivity;

import butterknife.BindView;

/**
 * 完善用户信息
 * Created by Jusenr on 2017/04/02.
 */
public class PerfectActivity extends TitleActivity {

    @BindView(R.id.et_nickname)
    CleanableEditText mEtNickname;
    @BindView(R.id.et_intro)
    EditText mEtIntro;
    @BindView(R.id.tv_info_limit)
    TextView mTvInfoLimit;

    private String mNickName;
    private String mName;

    private String mUserInfo;
    private String mInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perfect;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        Bundle bundle = args.getBundle(SettingActivity.TYPE);
        if (bundle != null) {
            String type = bundle.getString(SettingActivity.TYPE);

            if (SettingActivity.NICK_NAME.equals(type)) {
                mNickName = bundle.getString(SettingActivity.NICK_NAME);
                mEtNickname.setVisibility(View.VISIBLE);
                mEtIntro.setVisibility(View.GONE);
                mEtNickname.setText(mNickName);
            }

            if (SettingActivity.USER_INFO.equals(type)) {
                mUserInfo = bundle.getString(SettingActivity.USER_INFO);
                mEtNickname.setVisibility(View.GONE);
                mEtIntro.setVisibility(View.VISIBLE);
                mEtIntro.setText(mUserInfo);
            }
        }

        initView();
        addListener();
    }

    /**
     * 保存用户信息
     * 请求参数 String类型 昵称、图片url、个人简介
     */
    @Override
    public void onRightAction() {
        mName = mEtNickname.getText().toString();
        if (mNickName.equals(mName)) {
            ToastUtils.showToastShort(mContext, "没有更改无需保存");
            return;
        }
        if (mName.length() < 2 || mName.length() > 12) {
            ToastUtils.showToastShort(mContext, "设置2-12字内的昵称");
            return;
        }
        upload();
    }

    private void upload() {
        Log.e("%%%%%", "mInfo: " + mName);
        Intent intent = new Intent();
        intent.putExtra(SettingActivity.NICK_NAME, mName);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        mEtNickname.setFilters(new InputFilter[]{
                new CleanableEditText.EmojiInputFilter(),
                new CleanableEditText.SpaceInputFilter()});
        mEtNickname.setText(mNickName);
        Editable etext = mEtNickname.getText();
        Selection.setSelection(etext, etext.length());
    }

    /**
     * 添加监听
     */
    private void addListener() {
        mEtIntro.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvInfoLimit.setText((40 - mEtIntro.getText().toString().length()) + "");
            }
        });
    }
}
