package com.mattsencenbaugh.popularmovies.utilities;

import com.mattsencenbaugh.popularmovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by msencenb on 12/13/17.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // This interceptor chains the api_key query parameter to all of my retrofit api calls
    private static Interceptor apiKeyInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url)
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        if (!httpClient.interceptors().contains(apiKeyInterceptor)) {
            httpClient.addInterceptor(apiKeyInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}
