package com.example.administrator.mymvpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.mymvpdemo.App;

/**
 * 创建日期：2018/4/25 0025 on 16:22
 * 描述:
 * 作者:侯宇航 Administrator
 */
public abstract class BaseFragment extends Fragment {

    private int fragmentLayoutId;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        App.mFragment=this;
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        initFragmentView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.mFragment=this;
        initFragmentData();
        initFragmentListener();
    }

    protected abstract void initFragmentListener();

    protected abstract void initFragmentData();

    protected abstract void initFragmentView(View view);

    public abstract int getFragmentLayoutId();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            initFragmentHidden();
        }else {
            initFragmentShow();
        }
    }

    protected abstract void initFragmentShow();

    protected abstract void initFragmentHidden();

    public void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void titleBarFinish(Toolbar toolbar){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
