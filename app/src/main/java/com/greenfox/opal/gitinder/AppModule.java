package com.greenfox.opal.gitinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.greenfox.opal.gitinder.service.ApiService;
import com.greenfox.opal.gitinder.service.MockServer;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class AppModule {
    private Context context;
    private static final boolean CONNECT_TO_BACKEND = true;
    private static final String SERVER_URL = "http://gitinder.herokuapp.com";

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return context;
    }

    @Singleton @Provides
    public SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton @Provides
    public Gson provideGson(){
        return new Gson();
    }

    @Singleton @Provides
    public ObjectManager provideObjectManager(SharedPreferences sharedPreferences, Gson gson){
        return new ObjectManager(sharedPreferences, gson);
    }

    @Singleton @Provides
    public MockServer provideMockServer() {
        return new MockServer();
    }

    @Singleton @Provides
    public ApiService provideApiService() {
        if (CONNECT_TO_BACKEND) {
            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            ApiService service = retrofit.create(ApiService.class);
            return service;
        } else {
            return provideMockServer();
        }
    }
}
