package com.greenfox.opal.gitinder.model.response;

import java.util.List;

public class Profile extends BaseResponse {

    String login;
    String avatar_url;
    List<String> repos;
    List<String> languages;
    List<String> snippets;

    public Profile() {
    }

    public Profile(String message) {
        super("error", message);
    }

    public Profile(String login, String avatarUrl, List<String> repos,
        List<String> languages) {
        this.login = login;
        this.avatar_url = avatarUrl;
        this.repos = repos;
        this.languages = languages;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public List<String> getRepos() {
        return repos;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setSnippets(List<String> snippets) {
        this.snippets = snippets;
    }
}
