package com.example.administrator.mymvpdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.mymvpdemo.App;
import com.example.administrator.mymvpdemo.base.BaseFragment;


/**
 * 创建日期：2018/4/25 0025 on 16:22
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class FragmentBuilder {
    private static FragmentBuilder mFragmentBuilder;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private BaseFragment mBaseNowFragment;

    private FragmentBuilder() {
        init();
    }

    public synchronized static FragmentBuilder getInstance() {
        if (mFragmentBuilder == null)
            mFragmentBuilder = new FragmentBuilder();
        return mFragmentBuilder;

    }

    /**
     * 初始化FragmentManager
     */
    private void init() {

        mFragmentManager = App.mFragmentActivity.getSupportFragmentManager();


    }

    /**
     * 切换Fragment用的 管理Fragment(链式调用) getInstance().satrt().buid();
     */   //A碎片 -- 打开--->B 碎片
    public FragmentBuilder start(int containerId, Class<? extends BaseFragment> fragmentClass) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        String fragmentTagName = fragmentClass.getSimpleName();
        mBaseNowFragment = (BaseFragment) mFragmentManager
                .findFragmentByTag(fragmentTagName);

        try {
            if (mBaseNowFragment == null) {
                mBaseNowFragment = fragmentClass.newInstance();
                mFragmentTransaction.add(containerId, mBaseNowFragment, fragmentTagName);
            }
            if (App.mFragment != null) {
                mFragmentTransaction.hide(App.mFragment);
            }
            mFragmentTransaction.show(mBaseNowFragment);
            mFragmentTransaction.addToBackStack(fragmentTagName);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 链式调用的Build()方法
     */
    public FragmentBuilder buid() {
        //吧当前的fragment对象赋值给了上一个fragmet
        App.mFragment = mBaseNowFragment;
        //提交事务
        mFragmentTransaction.commit();
        return this;
    }

    public BaseFragment getFragmentBundle(Bundle bundle) {
        if (bundle != null) {
            mBaseNowFragment.setBundle(bundle);
        }
        return mBaseNowFragment;
    }



}
