package com.framgia.feastival.data.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.framgia.feastival.util.FeastivalApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tmd on 13/05/2017.
 */
public class ServiceGenerator {
    public static final String SHARE_PREFERENCE = "SHARE_PREFERENCE";
    public static final String SHARE_PREFERENCE_TOKEN = "SHARE_PREFERENCE_TOKEN";
    private static final String USER_TOKEN = "USER-TOKEN";
    private static final String BASE_URL = "http://feastival-react.herokuapp.com/api/";
    private static Retrofit sRetrofit = null;
    private static Retrofit.Builder sRetrofitBuilder = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create());
    private static HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
        .addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader(USER_TOKEN, getToken())
                    .build();
                return chain.proceed(request);
            }
        })
        .addNetworkInterceptor(sHttpLoggingInterceptor)
        .build();
    private String mToken;

    public static <T> T createService(Class<T> serviceClass) {
        if (sRetrofit == null) {
            sRetrofit = sRetrofitBuilder.client(sOkHttpClient).build();
        }
        return sRetrofit.create(serviceClass);
    }

    public static String getToken() {
        Context context = FeastivalApplication.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
            SHARE_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARE_PREFERENCE_TOKEN, "");
    }
}
