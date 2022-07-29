package com.albassami.logistics.network.ApiManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClient_for_strings {

     static OkHttpClient client = new OkHttpClient()

            .newBuilder().addInterceptor(chain -> {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("access-token", "access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
                .method(original.method(), original.body())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        return chain.proceed(request);
    }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();



    private static synchronized Retrofit getRetrofitClient() {

        return new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.Bassami_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiServices getApiService() {
        return getRetrofitClient().create(ApiServices.class);
    }
}