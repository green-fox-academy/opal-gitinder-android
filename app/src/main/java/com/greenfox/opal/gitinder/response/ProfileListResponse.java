package com.greenfox.opal.gitinder.response;

import com.greenfox.opal.gitinder.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileListResponse extends BaseResponse {
    List<Profile> profiles;
    int count;
    int all;

    public ProfileListResponse(String message) {
        super("error", message);
    }

    public ProfileListResponse(List<Profile> list, int count, int all) {
        profiles =list;
        this.count = count;
        this.all = all;
    }

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
