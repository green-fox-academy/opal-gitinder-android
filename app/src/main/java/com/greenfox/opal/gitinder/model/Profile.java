package com.greenfox.opal.gitinder.model;

public class Profile {
    String login;
    String avatarUrl;
    String repos;
    String languages;

    public Profile() {
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRepos() {
        return repos;
    }

    public String getLanguages() {
        return languages;
    }
}
