package com.example.administrator.mymvpdemo.utils;

import com.example.administrator.mymvpdemo.App;

/**
 * 创建日期：2018/4/27 0027 on 8:52
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class NetworkFactory {
    public static boolean isNetAvailable(){
        return NetworkUtil.isNetAvailable(App.mActivity);
    }
}
