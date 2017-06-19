package com.greenfox.opal.gitinder.model;

public class Profile {
    String login;
    String avatarUrl;
    String repos;
    String languages;

    public Profile() {
    }

    public Profile(String login, String avatarUrl, String repos, String languages) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.repos = repos;
        this.languages = languages;
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
