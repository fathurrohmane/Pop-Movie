package com.elkusnandi.popularmovie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Taruna 98 on 06/01/2018.
 */

public class PostMovie {

    public static String TYPE_MOVIE = "movie";
    public static String TYPE_TV = "tv";

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private int mediaId;
    @SerializedName("watchlist")
    @Expose
    private boolean watchlist;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public PostMovie(String mediaType, int mediaId, boolean watchlist) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.watchlist = watchlist;
        this.favorite = watchlist;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
