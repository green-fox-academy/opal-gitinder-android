package com.greenfox.opal.gitinder;

import android.app.Application;

/**
 * Created by Nagy Dóra on 2017.06.18..
 */

public class MyApp extends Application {
    private static MyApp app;
    private BasicComponent basicComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        /*basicComponent = DaggerBasicComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();*/
    }

    public static MyApp app() {
        return app;
    }

    public BasicComponent basicComponent() {
        return basicComponent;
    }
}
