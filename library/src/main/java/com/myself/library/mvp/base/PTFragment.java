package com.myself.library.mvp.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myself.library.R;
import com.myself.library.controller.intent.FragmentIntent;

import butterknife.ButterKnife;

/**
 * Created by riven_chris on 16/7/1.
 */
public abstract class PTFragment<P extends IPresenter> extends Fragment {

    protected P mPresenter;
    private View mFragmentView = null;
    protected Context mContext;

    //fragment管理器
    protected FragmentManager mFragmentManager;
    protected Bundle args;//传递的参数值

//    protected LoadingHUD loading;//加载框
//    protected ILoadState loadState;

    private Fragment currentFragment;//当前Fragment
    private Fragment targetFragment;//目标Fragment

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
//        this.loading = LoadingHUD.getInstance(context);
//        loading.setSpinnerType(LoadingHUD.SIMPLE_ROUND_SPINNER);
        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
//        EventBusHelper.register(this);//注册EventBus
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId == 0)
            throw new RuntimeException("找不到Layout资源,Fragment初始化失败!");
        mFragmentView = inflater.inflate(layoutId, container, false);
//        loadState = (ILoadState) mFragmentView.findViewById(R.id.load_state_view);
        ViewGroup parent = (ViewGroup) mFragmentView.getParent();
        if (parent != null)
            parent.removeView(mFragmentView);
        ButterKnife.bind(this, mFragmentView);
        mPresenter = createPresenter();
        return mFragmentView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedFinish(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
//        loading.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentView != null)
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBusHelper.unregister(this);//反注册EventBus
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    /**
     * 启动Fragment
     *
     * @param intent Fragment意图
     */
    private void startFragment(FragmentIntent intent) {
        currentFragment = intent.getCurrentFragment();
        Class<? extends Fragment> targetFragmentClazz = intent.getTargetFragmentClazz();
        Bundle args = intent.getExtras();
        if (args != null)
            targetFragment = Fragment.instantiate(getActivity(), targetFragmentClazz.getName(), args);
        else
            targetFragment = Fragment.instantiate(getActivity(), targetFragmentClazz.getName());
        switchFragment(currentFragment, targetFragment);
    }

    /**
     * 跳转Fragment
     *
     * @param targetClass 目标Fragment
     * @param args        传递参数
     */
    protected void startFragment(Class<? extends Fragment> targetClass, Bundle args) {
        FragmentIntent fragmentIntent = new FragmentIntent(this, targetClass, args);
        startFragment(fragmentIntent);
    }

    /**
     * 跳转Fragment
     *
     * @param targetClass 目标Fragment
     */
    protected void startFragment(Class<? extends Fragment> targetClass) {
        FragmentIntent fragmentIntent = new FragmentIntent(this, targetClass, null);
        startFragment(fragmentIntent);
    }

    /**
     * 跳转目标Activity
     *
     * @param targetClass 目标Activity类型
     */
    protected void startActivity(Class<? extends Activity> targetClass) {
        ((PTActivity) getActivity()).startActivity(targetClass);
    }

    /**
     * 跳转目标Activity(传递参数)
     *
     * @param targetClass 目标Activity类型
     * @param args        传递参数
     */
    public void startActivity(Class<? extends Activity> targetClass, Bundle args) {
        ((PTActivity) getActivity()).startActivity(targetClass, args);
    }

    /**
     * 隐式跳转目标Activity
     *
     * @param action 隐式动作
     */
    public void startActivity(String action) {
        ((PTActivity) getActivity()).startActivity(action);
    }

    /**
     * 隐式跳转目标Activity
     *
     * @param action 隐式动作
     */
    public void startActivity(String action, Bundle args) {
        ((PTActivity) getActivity()).startActivity(action, args);
    }

    /**
     * 启动目标Service
     *
     * @param targetClass 目标Service类型
     */
    public void startService(Class<? extends Service> targetClass) {
        ((PTActivity) getActivity()).startService(targetClass);
    }

    /**
     * 切换Fragment
     *
     * @param currentFragment 当前fragment
     * @param targetFragment  目标fragment
     */
    private void switchFragment(Fragment currentFragment, Fragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        String tag = targetFragment.getClass().getName();
        if (currentFragment != null)
            transaction.replace(R.id.fragment_container, targetFragment, targetFragment.getClass().getName()).addToBackStack(tag).commit();
        else
            transaction.add(R.id.fragment_container, targetFragment, targetFragment.getClass().getName()).commit();
    }

    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    public abstract void onViewCreatedFinish(Bundle savedInstanceState);


}
