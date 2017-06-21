package com.greenfox.opal.gitinder.model.response;

import java.util.List;

public class Profile extends BaseResponse {
    String login;
    String avatarUrl;
    List<String> repos;
    List<String> languages;

    public Profile() {
    }

    public Profile(String login, String avatarUrl, List<String> repos, List<String> languages) {
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

    public List<String> getRepos() {
        return repos;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
