package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    private String name, handle, description, location;
    private int following, followers;
    private String profileImageURL;

    public User() {
    }

    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.handle = jsonObject.getString("screen_name");
        user.description = jsonObject.getString("description");
        user.location = jsonObject.getString("location");
        user.following = jsonObject.getInt("friends_count");
        user.followers = jsonObject.getInt("followers_count");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");

        return user;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowing() {
        return following;
    }

    public int getFollowers() {
        return followers;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }
}
