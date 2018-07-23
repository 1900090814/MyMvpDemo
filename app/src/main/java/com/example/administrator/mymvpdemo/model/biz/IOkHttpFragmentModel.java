package com.example.administrator.mymvpdemo.model.biz;

import com.example.administrator.mymvpdemo.model.callback.MyCallBack;
import com.example.administrator.mymvpdemo.model.entity.InfoBean;
import com.example.administrator.mymvpdemo.model.net.OnDownloadListener;

/**
 * 创建日期：2018/4/26 0026 on 13:47
 * 描述:
 * 作者:侯宇航 Administrator
 */
public interface IOkHttpFragmentModel extends BaseModel{
    void getLoadResult(MyCallBack<InfoBean> myCallBack);
    void getDownLoadResult(OnDownloadListener onDownloadListener);
}
