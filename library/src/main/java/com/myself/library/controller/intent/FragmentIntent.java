package com.myself.library.controller.intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fragment意图
 * Created by guchenkai on 2015/10/30.
 */
public class FragmentIntent {
    private Bundle mExtras;
    private Fragment mCurrentFragment;
    private Class<? extends Fragment> mTargetFragmentClazz;

    public FragmentIntent(Fragment currentFragment, Class<? extends Fragment> targetFragmentClazz) {
        mCurrentFragment = currentFragment;
        mTargetFragmentClazz = targetFragmentClazz;
    }

    public FragmentIntent(Fragment currentFragment, Class<? extends Fragment> targetFragmentClazz, Bundle bundle) {
        mCurrentFragment = currentFragment;
        mTargetFragmentClazz = targetFragmentClazz;
        mExtras = bundle;
    }

    public Bundle getExtras() {
        return mExtras;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public Class<? extends Fragment> getTargetFragmentClazz() {
        return mTargetFragmentClazz;
    }

    public FragmentIntent putExtra(String name, boolean value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putBoolean(name, value);
        return this;
    }

    public FragmentIntent putExtra(String name, int value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putInt(name, value);
        return this;
    }

    public FragmentIntent putExtra(String name, String value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putString(name, value);
        return this;
    }

    public FragmentIntent putExtra(String name, Serializable value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putSerializable(name, value);
        return this;
    }

    public FragmentIntent putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putIntegerArrayList(name, value);
        return this;
    }

    public FragmentIntent putStringArrayListExtra(String name, ArrayList<String> value) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putStringArrayList(name, value);
        return this;
    }

    public FragmentIntent putExtras(Bundle extras) {
        if (mExtras == null)
            mExtras = new Bundle();
        mExtras.putAll(extras);
        return this;
    }
}
