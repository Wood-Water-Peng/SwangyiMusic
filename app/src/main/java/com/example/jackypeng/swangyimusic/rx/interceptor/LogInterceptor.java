package com.example.jackypeng.swangyimusic.rx.interceptor;


import android.util.Log;

import com.example.jackypeng.swangyimusic.util.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
        }
        L.i("request_url:"+request.url());
        Response response = chain.proceed(chain.request());
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        long t1 = System.nanoTime();
        L.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        long t2 = System.nanoTime();
        L.i(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        L.i("response_body:" + content);
        if (response.body() != null) {
            ResponseBody body = ResponseBody.create(mediaType, content);
            return response.newBuilder().body(body).build();
        }
        return response;

    }
}