package com.greenfox.opal.gitinder;

import android.app.Application;

public class GitinderApp extends Application {
    private static GitinderApp app;
    private BasicComponent basicComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        basicComponent = DaggerBasicComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public static GitinderApp app() {
        return app;
    }

    public BasicComponent basicComponent() {
        return basicComponent;
    }
}
