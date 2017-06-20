package com.greenfox.opal.gitinder.model.response;

import java.util.List;

public class Profile extends BaseResponse {
    String login;
    String avatarUrl;
    List<String> repos;
    List<String> languages;

    public Profile() {
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
