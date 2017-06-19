package com.greenfox.opal.gitinder.response;

import com.greenfox.opal.gitinder.model.Profile;

import java.util.List;

public class ProfileListResponse extends BaseResponse {
    List<Profile> profiles;
    int count;
    int all;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public int getCount() {
        return count;
    }

    public int getAll() {
        return all;
    }
}
