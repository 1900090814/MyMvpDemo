package com.example.administrator.mymvpdemo.presenter.contract;

import com.example.administrator.mymvpdemo.model.entity.InfoBean;
import com.example.administrator.mymvpdemo.presenter.BasePresenter;
import com.example.administrator.mymvpdemo.view.BaseView;

/**
 * 创建日期：2018/4/26 0026 on 10:37
 * 描述:
 * 作者:侯宇航 Administrator
 */
public interface HomeContract {
    interface OkhttpFragmentView extends BaseView<OkhttpFragmentPresenter>{
        void setMiainActivityResult(InfoBean mainActivityBean);
        void setErrorMsg(String errorMsg);
    }
    interface OkhttpFragmentPresenter extends BasePresenter.OkHttpFragmentPresenter {

    }
}
