package com.example.administrator.mymvpdemo.model.net;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.mymvpdemo.App;
import com.example.administrator.mymvpdemo.model.callback.MyCallBack;
import com.example.administrator.mymvpdemo.utils.NetworkFactory;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建日期：2018/4/26 0026 on 9:16
 * 描述:
 * 作者:侯宇航 Administrator
 */
public class OkHttpUtils implements IHttp{
    private OkHttpClient okHttpClient;
    private static OkHttpUtils utils;
    public final static int CONNECT_TIMEOUT =7000;
    public final static int READ_TIMEOUT=7000;
    public final static int WRITE_TIMEOUT=7000;
    private final long MAX_SIZE= 8*1024*1024;
    private static final int DOWNLOAD_FAIL=0;
    private static final int DOWNLOAD_PROGRESS=1;
    private static final int DOWNLOAD_SUCCESS=2;
    private OnDownloadListener onDownloadListener;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DOWNLOAD_PROGRESS:
                    onDownloadListener.onDownloading((Integer) msg.obj);
                    break;
                case DOWNLOAD_FAIL:
                    onDownloadListener.onDownloadFailed();
                    break;
                case DOWNLOAD_SUCCESS:
                    onDownloadListener.onDownloadSuccess((String) msg.obj);
                    break;
            }
        }
    };


    private OkHttpUtils() {
        //创建Cache对象，并设置缓存路径以及缓存区域大小
        File cacheDir = App.mActivity.getCacheDir();//获取应用的缓存路径
        Cache cache = new Cache(cacheDir,MAX_SIZE);
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .cache(cache).addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 1、通过Chain对象获取Request对象
                        Request request = chain.request();
                        //2、通过Chain对象重新发起请求，并获取Response对象
                        Response proceed = chain.proceed(request);
                        //移除头部信息：Pragma、Cache-Control
                        Response response = proceed.newBuilder().removeHeader("Pragma")
                                .removeHeader("Cache-Control").addHeader("Cache-Control", "max-age=" + 1000 * 30)
                                .build();
                        return response;
                    }
                }).build();
    }

    public static synchronized OkHttpUtils getInstance(){
        if (utils==null){
            utils=new OkHttpUtils();
        }
        return utils;
    }

    public <T> void get(String url , Map<String,String>map , final MyCallBack<T> myCallBack){
        //判断网络
        if (NetworkFactory.isNetAvailable()==false){
            Toast.makeText(App.mActivity, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null&&map.size()>0){
            for (Map.Entry<String,String> entry:map.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        final Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                App.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String jsonData = response.body().string();
                App.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            myCallBack.onSuccess(getGeneric(jsonData,myCallBack));
                    }
                });
            }
        });
    }

    @Override
    public <T> void post(String url, Map<String, String> params, final MyCallBack<T> myCallBack) {
        //判断网络
        if (NetworkFactory.isNetAvailable()==false){
            Toast.makeText(App.mActivity, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
        FormBody.Builder builder=new FormBody.Builder();
        if (params!=null&&params.size()>1){
            for (Map.Entry<String,String> entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        final Request request=new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                App.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                final String jsonData = response.body().string();
                App.mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onSuccess(getGeneric(jsonData,myCallBack));
                    }
                });
            }
        });
    }

    @Override
    public  void downLoad(final String url, final String saveDir, OnDownloadListener onDownloadListener) {
        //判断网络
        if (NetworkFactory.isNetAvailable() ==false){
            Toast.makeText(App.mActivity, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
        this.onDownloadListener=onDownloadListener;
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=Message.obtain();
                message.what=DOWNLOAD_FAIL;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is=null;
                byte[] buf=new byte[2048];
                int len=0;
                FileOutputStream fos=null;
                //储存下载文件的目录
                String savePath=isExistDir(saveDir);
                try{
                    is=response.body().byteStream();
                    long total=response.body().contentLength();
                    File file=new File(savePath,getNameFromUrl(url));
                    fos=new FileOutputStream(file);
                    long sum=0;
                    while((len = is.read(buf))!=-1){
                        fos.write(buf,0,len);
                        sum+=len;
                        int progress=(int)(sum*1.0f/total*100);
                        //下载中
                        Message message=Message.obtain();
                        message.what=DOWNLOAD_PROGRESS;
                        message.obj=progress;
                        mHandler.sendMessage(message);
                    }
                    fos.flush();
                    //下载完成
                    Message message= Message.obtain();
                    message.what=DOWNLOAD_SUCCESS;
                    message.obj=file.getAbsolutePath();
                    mHandler.sendMessage(message);
                }catch (Exception e){
                    Message message=Message.obtain();
                    message.what=DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                }finally{
                    try{
                        if(is!=null)
                            is.close();
                    }catch (IOException e){

                    }
                    try {
                        if(fos!=null){
                            fos.close();
                        }
                    }catch (IOException e){

                    }
                }
            }
        });
    }


    private <T> T getGeneric(String jsonData,MyCallBack<T> callBack){
        Gson gson = new Gson();
        //通过反射获取泛型的实例
        Type[] types = callBack.getClass().getGenericInterfaces();
        Type[] actualTypeArguments = ((ParameterizedType) types[0]).getActualTypeArguments();
        Type type = actualTypeArguments[0];

        T t = gson.fromJson(jsonData,type);
        return t;
    }

    private String isExistDir(String saveDir) throws IOException {
        File downloadFile=new File(saveDir);
        if(!downloadFile.mkdirs()){
            downloadFile.createNewFile();
        }
        String savePath=downloadFile.getAbsolutePath();
        return savePath;
    }
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/")+1);
    }

}
