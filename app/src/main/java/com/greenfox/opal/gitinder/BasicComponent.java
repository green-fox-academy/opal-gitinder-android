package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.fragments.SwipingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nagy Dóra on 2017.06.18..
 */

@Singleton
@Component(modules = {AppModule.class})
public interface BasicComponent {

  void inject(LoginActivity loginActivity);
  void inject(MainActivity mainActivity);
  void inject(SwipingFragment swipingFragment);

}
