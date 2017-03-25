package com.myself.newsapp.guidance;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.myself.library.controller.ActivityManager;
import com.myself.library.controller.BaseActivity;
import com.myself.newsapp.R;
import com.myself.newsapp.user.LoginActivity;
import com.myself.newsapp.user.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导页
 * Created by Jusenr on 2017/03/25.
 */
public class GuidanceActivity extends BaseActivity implements View.OnClickListener {
    private ObjectAnimator waveShiftAnim;//波浪动画

    @BindView(R.id.cb_guidance_banner)
    ViewPager cb_guidanceBanner;
    //    @BindView(R.id.wv_wave)
//    WaveView wv_wave;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;

//    private Subscription upgradeSubscription;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guidance;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        upgradeSubscription = UpgradeHelper.checkUpgradeInfo(this);
        initAnimation();
        initGuidanceBanner();
        ActivityManager.getInstance().popOtherActivity(GuidanceActivity.class);
    }

    private void initGuidanceBanner() {
        cb_guidanceBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }
        });
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
//        waveShiftAnim = ObjectAnimator.ofFloat(wv_wave, "waveShiftRatio", 1f, 0f);
//        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
//        waveShiftAnim.setDuration(3500);
//        waveShiftAnim.setInterpolator(new LinearInterpolator());

        TranslateAnimation btn_loginAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, -0.3f);
        btn_loginAnim.setInterpolator(new BounceInterpolator());
        btn_loginAnim.setDuration(1000);
        btn_loginAnim.setStartOffset(1000);
        btn_login.setAnimation(btn_loginAnim);

        TranslateAnimation btn_regAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, -0.3f);
        btn_regAnim.setInterpolator(new BounceInterpolator());
        btn_regAnim.setDuration(1000);
        btn_register.setAnimation(btn_regAnim);
    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return exit();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        cb_guidanceBanner.startTurning(3000);
//        wv_wave.setShowWave(true);
        if (waveShiftAnim != null) {
            waveShiftAnim.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        cb_guidanceBanner.stopTurning();
        if (waveShiftAnim != null) {
            waveShiftAnim.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (upgradeSubscription != null)
//            upgradeSubscription.unsubscribe();
    }
}