package com.example.administrator.mymvpdemo.presenter;

import android.widget.Toast;

import com.example.administrator.mymvpdemo.App;
import com.example.administrator.mymvpdemo.model.biz.IOkHttpFragmentModel;
import com.example.administrator.mymvpdemo.model.biz.OkHttpFragmentModelImpl;
import com.example.administrator.mymvpdemo.model.callback.MyCallBack;
import com.example.administrator.mymvpdemo.model.entity.InfoBean;
import com.example.administrator.mymvpdemo.model.net.OnDownloadListener;
import com.example.administrator.mymvpdemo.presenter.contract.HomeContract;
import com.example.administrator.mymvpdemo.utils.LogUtil;

/**
 * 创建日期：2018/4/26 0026 on 13:42
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class OkHttpFragmentPresenter implements HomeContract.OkhttpFragmentPresenter{
    private HomeContract.OkhttpFragmentView mainActivityView;
    private IOkHttpFragmentModel mainModel;

    public OkHttpFragmentPresenter(HomeContract.OkhttpFragmentView mainActivityView) {
        this.mainActivityView=mainActivityView;
        mainActivityView.setPresenter(this);
        mainModel = new OkHttpFragmentModelImpl();
    }


    @Override
    public void startLoadResult() {
        mainModel.getLoadResult(new MyCallBack<InfoBean>() {
            @Override
            public void onError(String error) {
                mainActivityView.setErrorMsg(error);
            }

            @Override
            public void onSuccess(InfoBean infoBean) {
                mainActivityView.setMiainActivityResult(infoBean);
            }
        });
    }


    @Override
    public void startDownLoadResult() {
        mainModel.getDownLoadResult(new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                LogUtil.e("下载",path);
            }

            @Override
            public void onDownloading(int progress) {
                LogUtil.e("下载",progress+"");
                if (progress>99){
                    Toast.makeText(App.mActivity, "下载成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDownloadFailed() {
                LogUtil.e("下载","失败");
            }
        });
    }
}
