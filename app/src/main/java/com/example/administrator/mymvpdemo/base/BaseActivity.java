package com.example.administrator.mymvpdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mymvpdemo.App;
import com.example.administrator.mymvpdemo.R;
import com.example.administrator.mymvpdemo.utils.NetworkUtil;
import com.example.administrator.mymvpdemo.utils.SetPopupwindow;
import com.example.administrator.mymvpdemo.utils.StatusBar;

/**
 * 创建日期：2018/4/25 0025 on 15:53
 * 描述:
 * 作者:侯宇航 Administrator
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.mActivity=this;
        App.mFragmentActivity=this;
        StatusBar.fullScreen(this);
        boolean netAvailable = NetworkUtil.isNetAvailable(this);
        if (netAvailable==false){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
        setContentView(getActivityLayout());
        initActivityView();
        initActivityListener();
        initActivityData();
    }

    protected abstract void initActivityData();

    protected abstract void initActivityListener();

    protected abstract void initActivityView();

    public abstract int getActivityLayout();

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    };

    public PopupWindow showPopupWindow(Context context, int layout ){
        SetPopupwindow.Builder setPopupwindow=new SetPopupwindow.Builder(context);
        View contentview = LayoutInflater.from(context).inflate(layout,null);
        PopupWindow popupWindow=new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return popupWindow;
    }
    public void setTitleBar(String title){
        Toolbar toolbar = findViewById(R.id.tb_mToolbar);
        TextView tb_title = findViewById(R.id.tb_title);
        if (toolbar!=null){
           toolbar.setNavigationIcon(R.mipmap.return_icon_btn);
           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   finish();
                   System.exit(0);
               }
           });
       }
       if (tb_title!=null){
            tb_title.setText(title);
       }
    }
}
