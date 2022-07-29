package com.albassami.logistics.network.ApiManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CustomRestClient {
    private static OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("Accept", "application/x-www-form-urlencoded")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2,TimeUnit.MINUTES).connectTimeout(2,TimeUnit.MINUTES).callTimeout(2,TimeUnit.MINUTES).build();

    private static synchronized Retrofit getRetrofitClient() {

        return new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    public static APIInterface getApiService() {
        return getRetrofitClient().create(APIInterface.class);
    }
}
