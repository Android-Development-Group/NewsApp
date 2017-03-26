package com.myself.newsapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.myself.library.controller.ActivityManager;
import com.myself.library.controller.BaseActivity;
import com.myself.library.controller.eventbus.EventBusHelper;
import com.myself.library.controller.eventbus.Subcriber;
import com.myself.library.view.image.FastBlur;
import com.myself.library.view.select.TabBar;
import com.myself.library.view.select.TabItem;
import com.myself.library.view.viewpager.UnScrollableViewPager;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.guidance.GuidanceActivity;
import com.myself.newsapp.home.FindFragment;
import com.myself.newsapp.home.MeFragment;
import com.myself.newsapp.home.NearbyFragment;
import com.myself.newsapp.home.StoreFragment;
import com.myself.newsapp.util.Constants;

import butterknife.BindView;

/**
 * 主页
 * Created by Jusenr on 2017/03/25.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rl_index_main)
    RelativeLayout mRlIndexMain;
    @BindView(R.id.ti_index_store)
    TabItem mTiIndexStore;
    @BindView(R.id.ti_index_nearby)
    TabItem mTiIndexNearby;
    @BindView(R.id.ti_index_find)
    TabItem mTiIndexFind;
    @BindView(R.id.ti_index_me)
    TabItem mTiIndexMe;
    @BindView(R.id.tb_index_tab)
    TabBar mTbIndexTab;
    @BindView(R.id.vp_content)
    UnScrollableViewPager mVpContent;
    @BindView(R.id.ivBlur)
    ImageView mIvBlur;

    private SparseArray<Fragment> mFragments;
//    private Subscription upgradeSubscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
//        FirInfoBean infoBean = FirImConfig.getFirAppVersionInfo(this, TotalApplication.FIR_API_TOKEN);

//        NativeLib nativeLib = new NativeLib();
//        mSampleText.setText(nativeLib.stringFromJNI());

//        testLeanCloudSDK();

//        upgradeSubscription = UpgradeHelper.checkUpgradeInfo(this);
        addFragments();
        addListener();
        mVpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        mVpContent.setOffscreenPageLimit(4);
        mTbIndexTab.setTabItemSelected(R.id.ti_index_store);
        mVpContent.setCurrentItem(0);
        refreshMeDot(0);

        ActivityManager.getInstance().popOtherActivity(MainActivity.class);
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        mFragments = new SparseArray<>();
        mFragments.put(0, Fragment.instantiate(mContext, StoreFragment.class.getName()));
        mFragments.put(1, Fragment.instantiate(mContext, NearbyFragment.class.getName()));
        mFragments.put(2, Fragment.instantiate(mContext, FindFragment.class.getName()));
        mFragments.put(3, Fragment.instantiate(mContext, MeFragment.class.getName()));
    }

    private void addListener() {
        mTbIndexTab.setOnTabItemSelectedListener(new TabBar.OnTabItemSelectedListener() {
            @Override
            public void onTabItemSelected(TabItem item, int position) {
                /*switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }*/
                mVpContent.setCurrentItem(position, false);
                EventBusHelper.post("", "123");

//                if (3 == position) hideMeRedDot();
//                if (0 == position) hideCompanionRedDot();
            }
        });
    }

    /**
     * 显示模糊
     */
    private void showBlurView() {
        Bitmap bitmap = FastBlur.convertViewToBitmap(mRlIndexMain);
        Bitmap blurBitmap = FastBlur.apply(mContext, bitmap, 25);
        mIvBlur.setImageBitmap(blurBitmap);
        mIvBlur.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏模糊
     */
    private void hideBlurView() {
        if (mIvBlur == null) return;
        Drawable drawable = mIvBlur.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        mIvBlur.setVisibility(View.GONE);
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return exit();
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 注销
     */
    public void logOut() {
        AccountHelper.logout();
        startActivity(GuidanceActivity.class);
        MainActivity.this.finish();
    }

    /**
     * 测试SDK是否正常工作
     */
    private void testLeanCloudSDK() {
        AVObject testObject = new AVObject("TestObject");
        testObject.put("name", "张文");
        testObject.put("gender", "男");
        testObject.put("birthday", "1993-02-05");
        testObject.put("height", "175.5cm");
        testObject.put("weight", "66kg");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });
    }

    @Subcriber(tag = Constants.EventKey.EVENT_INDEX_SET_ITEM)
    private void setCurrentItem(int position) {
        switch (position) {
            case 0:
                mTbIndexTab.setTabItemSelected(R.id.ti_index_store);
                break;
            case 1:
                mTbIndexTab.setTabItemSelected(R.id.ti_index_nearby);
                break;
            case 2:
                mTbIndexTab.setTabItemSelected(R.id.ti_index_find);
                break;
            case 3:
                mTbIndexTab.setTabItemSelected(R.id.ti_index_me);
                break;
        }
    }

    @Subcriber(tag = Constants.EventKey.EVENT_REFRESH_ME_TAB)
    private void refreshMeDot(int count) {
        if (count > 0) {
            mTiIndexMe.show(count);
        } else {
            mTiIndexMe.show(0);
        }
    }
}
