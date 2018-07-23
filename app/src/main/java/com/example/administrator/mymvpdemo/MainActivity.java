package com.example.administrator.mymvpdemo;

import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.mymvpdemo.base.BaseActivity;
import com.example.administrator.mymvpdemo.fragment.FragmentBuilder;
import com.example.administrator.mymvpdemo.fragment.OkhttpFragment;
import com.example.administrator.mymvpdemo.fragment.RetrofitFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.rb_Okhttp)
    RadioButton rbOkhttp;
    @BindView(R.id.rb_Retrofit)
    RadioButton rbRetrofit;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    private long mExitTime;

    @Override
    protected void initActivityData() {
        FragmentBuilder.getInstance().start(R.id.fragment_container, OkhttpFragment.class).buid();
    }

    @Override
    protected void initActivityListener() {

    }

    @Override
    protected void initActivityView() {
        ButterKnife.bind(this);
        setTitleBar("宇航制造");


    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }



    @OnClick({R.id.rb_Okhttp, R.id.rb_Retrofit,R.id.rg_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_Okhttp:
                FragmentBuilder.getInstance().start(R.id.fragment_container, OkhttpFragment.class).buid();
                break;
            case R.id.rb_Retrofit:
                FragmentBuilder.getInstance().start(R.id.fragment_container, RetrofitFragment.class).buid();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
