package com.example.administrator.mymvpdemo;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.example.administrator.mymvpdemo.base.BaseFragment;

/**
 * 创建日期：2018/4/25 0025 on 15:53
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class App extends Application {
    public static Activity mActivity;
    public static BaseFragment mFragment;
    public static FragmentActivity mFragmentActivity;
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
