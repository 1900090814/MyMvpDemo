package com.example.administrator.mymvpdemo.model.biz;

import com.example.administrator.mymvpdemo.fragment.OkhttpFragment;
import com.example.administrator.mymvpdemo.model.callback.MyCallBack;
import com.example.administrator.mymvpdemo.model.entity.InfoBean;
import com.example.administrator.mymvpdemo.model.net.OnDownloadListener;
import com.example.administrator.mymvpdemo.model.net.Urls;

/**
 * 创建日期：2018/4/26 0026 on 13:56
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class OkHttpFragmentModelImpl implements IOkHttpFragmentModel {


    @Override
    public void getLoadResult(MyCallBack<InfoBean> myCallBack) {
        iHttp.post(Urls.INFO_URL, OkhttpFragment.mapLoad,myCallBack);
    }

    @Override
    public void getDownLoadResult(OnDownloadListener onDownloadListener) {
        iHttp.downLoad(Urls.DOWN_LOAD,OkhttpFragment.DIR_PATH,onDownloadListener);
    }
}
