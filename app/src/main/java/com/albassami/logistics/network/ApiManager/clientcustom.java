package com.albassami.logistics.network.ApiManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class clientcustom {
    private static OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("access-token", "access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Cookie", "session_id=fd0b5c5197ada5bba2f17688f037e0e853216cd3")
                .method(original.method(), original.body())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
        return chain.proceed(request);
    }).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2,TimeUnit.MINUTES).connectTimeout(2,TimeUnit.MINUTES).callTimeout(2,TimeUnit.MINUTES).build();

    private static synchronized Retrofit getRetrofitClient() {

        return new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.Bassami_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiServices getApiService() {
        return getRetrofitClient().create(ApiServices.class);
    }
}
