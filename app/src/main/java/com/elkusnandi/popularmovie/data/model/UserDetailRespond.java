package com.elkusnandi.popularmovie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Taruna 98 on 01/01/2018.
 */

public class UserDetailRespond {

    @SerializedName("avatar")
    @Expose
    private Avatar avatar;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("include_adult")
    @Expose
    private boolean includeAdult;
    @SerializedName("username")
    @Expose
    private String username;

    public Avatar getAvatar() {
        return avatar;
    }

    public String avatarPath() {
        return getAvatar().gravatar.path;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private class Avatar {
        @SerializedName("gravatar")
        @Expose
        private Gravatar gravatar;
    }

    private class Gravatar {
        @SerializedName("hash")
        @Expose
        private String path;
    }

}

