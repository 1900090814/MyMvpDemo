package com.example.administrator.mymvpdemo.fragment;


import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.administrator.mymvpdemo.R;
import com.example.administrator.mymvpdemo.base.BaseFragment;
import com.example.administrator.mymvpdemo.model.entity.InfoBean;
import com.example.administrator.mymvpdemo.presenter.OkHttpFragmentPresenter;
import com.example.administrator.mymvpdemo.presenter.contract.HomeContract;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OkhttpFragment extends BaseFragment implements HomeContract.OkhttpFragmentView {
    public static Map<String,String> mapLoad=new HashMap<>();
    public static final String DIR_PATH= Environment.getExternalStorageDirectory().getPath();

    @BindView(R.id.btn_Load)
    Button btnLoad;
    @BindView(R.id.btn_DownLoad)
    Button btnDownLoad;
    Unbinder unbinder;

    private HomeContract.OkhttpFragmentPresenter okhttpFragmentPresenter;

    public OkhttpFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initFragmentListener() {

    }

    @Override
    protected void initFragmentData() {
        OkHttpFragmentPresenter okHttpFragmentPresenter=new OkHttpFragmentPresenter(this);
    }

    @Override
    protected void initFragmentView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_okhttp;
    }

    @Override
    protected void initFragmentShow() {

    }

    @Override
    protected void initFragmentHidden() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setMiainActivityResult(InfoBean infoBean) {
        showToast(infoBean.getReason());
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void setPresenter(HomeContract.OkhttpFragmentPresenter okhttpFragmentPresenter) {
        this.okhttpFragmentPresenter = okhttpFragmentPresenter;
    }


    @OnClick({R.id.btn_Load, R.id.btn_DownLoad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Load:
                mapLoad.put("key","097060266650f67b2cebd2a06aded587");
                mapLoad.put("type","yule");
                okhttpFragmentPresenter.startLoadResult();
                mapLoad.clear();
                break;
            case R.id.btn_DownLoad:
                okhttpFragmentPresenter.startDownLoadResult();
                break;
        }
    }
}
