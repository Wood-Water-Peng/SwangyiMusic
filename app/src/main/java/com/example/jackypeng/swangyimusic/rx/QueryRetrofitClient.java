package com.example.jackypeng.swangyimusic.rx;

import com.example.jackypeng.swangyimusic.rx.factory.ExGsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryRetrofitClient {

    private static OkHttpClient mOkHttpClient;
//    初始化BaseUrl
    private  static String baseUrl;

    private  static Retrofit retrofit;
    /**
     *   RetrofitClient 构造 函数
     *       获取OKhttpClient 实例
     * @param mOkHttpClient
     */
    public QueryRetrofitClient(String baseUrl, OkHttpClient mOkHttpClient)
    {
        this.baseUrl=baseUrl;
        this.mOkHttpClient=mOkHttpClient;
    }


    /**
     *  修改BaseUrl地址
     * @param baseUrl
     */
    public QueryRetrofitClient setBaseUrl(String baseUrl)
    {
        this.baseUrl=baseUrl;
        return this;
    }

    /**
     *  获得对应的ApiServcie对象
     * @param service
     * @param <T>
     * @return
     */
    public  <T> T  builder(Class<T> service)
    {
        if(baseUrl==null)
        {
            throw new RuntimeException("baseUrl is null!");
        }
        if (service == null) {
            throw new RuntimeException("api Service is null!");
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit=new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

}
