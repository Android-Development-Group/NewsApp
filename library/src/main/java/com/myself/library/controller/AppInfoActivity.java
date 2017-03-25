package com.myself.library.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.myself.library.R;
import com.myself.library.utils.AppUtils;
import com.myself.library.utils.DateUtils;
import com.myself.library.utils.FileUtils;
import com.myself.library.utils.StringUtils;

/**
 * App版本信息
 * Created by Jusenr on 2017/03/25.
 */
public class AppInfoActivity extends AppCompatActivity {
    public static final String BUNDLE_APP_INFO = "BUNDLE_APP_INFO";

    TextView tv_left;
    TextView tv_main;
    TextView tv_right;

    TextView tv_appname;
    TextView tv_version;
    TextView tv_versionShort;
    TextView tv_fsize;
    TextView tv_updatetime;
    TextView tv_changelog;
    TextView tv_update_url;
    TextView tv_installUrl;

    private FirInfoBean mBean;
    private String mVersionName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_appname = (TextView) findViewById(R.id.tv_appname);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_versionShort = (TextView) findViewById(R.id.tv_versionShort);
        tv_fsize = (TextView) findViewById(R.id.tv_fsize);
        tv_updatetime = (TextView) findViewById(R.id.tv_updatetime);
        tv_changelog = (TextView) findViewById(R.id.tv_changelog);
        tv_update_url = (TextView) findViewById(R.id.tv_update_url);
        tv_installUrl = (TextView) findViewById(R.id.tv_installUrl);

        mVersionName = AppUtils.getVersionName(this);
        tv_right.setText(mVersionName);

        mBean = (FirInfoBean) getIntent().getExtras().getSerializable(BUNDLE_APP_INFO);

        initView();
        upgrade();

        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void upgrade() {
        if (mBean != null) {
            String versionShort = mBean.getVersionShort();
            try {
                if (!StringUtils.isEmpty(versionShort)) {
                    String substring = mBean.getVersionShort().substring(1);
                    Float aFloat0 = Float.valueOf(mVersionName.substring(1));
                    Float aFloat1 = Float.valueOf(substring);
                    if (aFloat0 < aFloat1)
                        UpgradeHelper.showUpdateDialog(this, false, mBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        tv_right.setText(mVersionName);
        if (mBean != null) {
            tv_appname.setText(mBean.getName());
            tv_version.setText(mBean.getBuild());
            tv_versionShort.setText(mBean.getVersionShort());
            tv_fsize.setText(FileUtils.getFormatSize(mBean.getBinary().getFsize()));
            tv_updatetime.setText(DateUtils.millisecondToDate(mBean.getUpdated_at(), DateUtils.YMD_HMS_PATTERN));
            tv_changelog.setText(mBean.getChangelog());
            tv_update_url.setText(mBean.getUpdate_url());
            tv_installUrl.setText(mBean.getInstallUrl());
        }
    }
}
