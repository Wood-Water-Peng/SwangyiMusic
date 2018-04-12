package com.example.jackypeng.swangyimusic.rx.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jackypeng on 2017/11/28.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request compressedRequest = originalRequest.newBuilder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        return chain.proceed(compressedRequest);
    }

}
