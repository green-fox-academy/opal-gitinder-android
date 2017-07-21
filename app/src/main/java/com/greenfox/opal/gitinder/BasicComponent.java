package com.greenfox.opal.gitinder;

import com.greenfox.opal.gitinder.fragments.MatchDialogFragment;
import com.greenfox.opal.gitinder.fragments.MatchesFragment;
import com.greenfox.opal.gitinder.service.MatchesBroadcast;
import com.greenfox.opal.gitinder.fragments.SwipingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface BasicComponent {
  void inject(LoginActivity loginActivity);
  void inject(MainActivity mainActivity);
  void inject(MessagesActivity messagesActivity);
  void inject(MatchesBroadcast matchesBroadcast);
  void inject(SwipingFragment swipingFragment);
  void inject(MatchesFragment matchesFragment);
  void inject(MatchDialogFragment matchesFragment);
}
