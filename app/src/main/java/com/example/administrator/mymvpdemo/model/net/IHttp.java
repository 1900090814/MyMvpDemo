package com.example.administrator.mymvpdemo.model.net;

import com.example.administrator.mymvpdemo.model.callback.MyCallBack;

import java.util.Map;

/**
 * 创建日期：2018/4/26 0026 on 10:22
 * 描述:
 * 作者:侯宇航 Administrator
 */
public interface IHttp {
    <T>void get(String url, Map<String,String> params, MyCallBack<T> myCallBack);
    <T>void post(String url, Map<String,String> params, MyCallBack<T> myCallBack);
    <T>void downLoad(String url, final String saveDir, OnDownloadListener onDownloadListener);
}
