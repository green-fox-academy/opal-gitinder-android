package com.greenfox.opal.gitinder;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nagy Dóra on 2017.06.18..
 */

@Singleton
@Component(modules = {AppModule.class})
public interface BasicComponent {
    void inject(LoginActivity activity);
}
