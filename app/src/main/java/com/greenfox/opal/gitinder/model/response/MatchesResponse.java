package com.greenfox.opal.gitinder.model.response;

import java.util.List;

public class MatchesResponse extends BaseResponse {
    List<Match> matches;

    public MatchesResponse() {
    }

    public MatchesResponse(String message) {
        super("error", message);
    }

    public List<Match> getMatches() {
        return matches;
    }
}
