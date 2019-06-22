package com.justincamp.demo.api;

import com.justincamp.demo.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RestClient {

    private Retrofit retrofit;
    private OkHttpClient client;
    private Discogs discogsService;

    @Inject
    public RestClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader("Authorization", "Discogs token="+BuildConfig.API_TOKEN);
                    requestBuilder.addHeader("User-Agent", "Demo/"+BuildConfig.VERSION_NAME);
                    requestBuilder.method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });
        if (BuildConfig.DEBUG) {
            okBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        client = okBuilder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        discogsService = retrofit.create(Discogs.class);
    }

    /**
     * get the retrofit instance
     * @return Retrofit Object Instance
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * get the okHttpClient directly, in case we need to do some crazy direct-downloads
     * or something.
     * @return OkhttpClient Object Instance
     */
    public OkHttpClient getClient() {
        return client;
    }

    /**
     * get the Discogs service instance
     * @return Discogs Service Instance
     */
    public Discogs getDiscogsService() {
        return discogsService;
    }

}
