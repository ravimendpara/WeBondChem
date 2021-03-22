package com.demoapp.demoapp.api;


import com.demoapp.demoapp.utils.CommonUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("ContentType","application/json");

//            .header("Token", CommonUtil.TOKEN) whenever need to add token in each api then add this line in above requestBuilder object

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    });


    private static OkHttpClient client = httpClient
            .readTimeout(320, TimeUnit.SECONDS)
            .connectTimeout(320, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(com.employeecontrol.controle.api.Urls.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
