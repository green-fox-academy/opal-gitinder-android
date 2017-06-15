package com.greenfox.opal.gitinder.model;

/**
 * Created by Nagy Dóra on 2017.06.13..
 */

public class OnError {

    String error;

    public OnError() {
    }

    public OnError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
