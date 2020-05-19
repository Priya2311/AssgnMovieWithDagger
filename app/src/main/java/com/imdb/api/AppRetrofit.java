package com.imdb.api;

import android.os.Build;

import com.google.gson.GsonBuilder;
import com.imdb.utility.AppConstants;
import com.imdb.utility.Lg;
import com.imdb.utility.LiveDataCallAdapterFactory;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// This class will be responsible for api calling

@Module
public class AppRetrofit {

    private static AppRetrofit instance;

    public AppService getAppService() {
        return appService;
    }

    private final AppService appService;

    private static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new AppRetrofit();
        }
    }

    public static AppRetrofit getInstance() {
        // Return the instance
        initInstance();
        return instance;
    }

    public AppRetrofit() {
        appService = provideService();
    }

    private AppService provideService() {

        // To show the Api Request & Params
        OkHttpClient httpClient = getUnsafeOkHttpClient();

        return new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create()))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClient)
                .build()
                .create(AppService.class);
    }


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            Interceptor HEADER_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request.Builder builder = chain.request().newBuilder()
                            .addHeader("x-rapidapi-host", "devru-bigflix-movies-download-v1.p.rapidapi.com")
                            .addHeader("x-rapidapi-key", AppConstants.APIKEY);
                    Response response = chain.proceed(builder.build());

                    if (!response.isSuccessful() && isForbidden(response.code())) {
                        Request newRequest = chain.request();
                        newRequest = newRequest.newBuilder()
                                .build();

                        response = chain.proceed(newRequest);
                    }
                    return response;
                }
            };


            //for logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(Lg.ISDEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            // creating builder object
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(loggingInterceptor)
                    .addInterceptor(HEADER_INTERCEPTOR)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isForbidden(int code) {
        return code == HttpURLConnection.HTTP_FORBIDDEN;
    }

//    public AppService getAppService(Converter.Factory gsonConverter) {
//        // To show the Api Request & Params
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);
//
//        return new Retrofit.Builder()
//                .baseUrl(ApiConstant.BASE_URL)
//                .addConverterFactory(gsonConverter)
//                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
//                .client(httpClient.build())
//                .build()
//                .create(AppService.class);
//    }
}
