package com.greenfox.opal.gitinder.model.response;

public class Snippet extends BaseResponse {
    String language;
    String code;

    public Snippet() {
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }
}
