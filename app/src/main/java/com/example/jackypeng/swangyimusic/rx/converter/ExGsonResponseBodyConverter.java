package com.example.jackypeng.swangyimusic.rx.converter;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class ExGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public ExGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;

    }

    /**
     * 进行解析预处理操作
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String value = responseBody.string();
        try {
            new JSONObject(value);
            return gson.fromJson(value, type);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new com.alibaba.fastjson.JSONException(value);
        }
    }
}
