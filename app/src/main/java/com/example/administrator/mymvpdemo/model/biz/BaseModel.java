package com.example.administrator.mymvpdemo.model.biz;

import com.example.administrator.mymvpdemo.model.net.HttpFactroy;
import com.example.administrator.mymvpdemo.model.net.IHttp;

/**
 * 创建日期：2018/4/26 0026 on 13:51
 * 描述:
 * 作者:侯宇航 Administrator
 */
public interface BaseModel {
   public static IHttp iHttp= HttpFactroy.create();
}
