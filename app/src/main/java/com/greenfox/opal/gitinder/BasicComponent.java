package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.service.MatchesBroadcast;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface BasicComponent {
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
    void inject(MatchesBroadcast matchesBroadcast);
}
