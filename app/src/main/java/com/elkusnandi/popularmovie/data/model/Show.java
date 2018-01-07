package com.elkusnandi.popularmovie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Show class
 * Created by Taruna 98 on 07/01/2018.
 */

public class Show {

    @SerializedName("poster_path")
    @Expose
    protected String posterPath;
    @SerializedName("popularity")
    @Expose
    protected double popularity;
    @SerializedName("id")
    @Expose
    protected int id;
    @SerializedName("backdrop_path")
    @Expose
    protected String backdropPath;
    @SerializedName("vote_average")
    @Expose
    protected double voteAverage;
    @SerializedName("overview")
    @Expose
    protected String overview;
    @SerializedName("genre_ids")
    @Expose
    protected List<Integer> genreIds = null;
    @SerializedName("original_language")
    @Expose
    protected String originalLanguage;
    @SerializedName("vote_count")
    @Expose
    protected int voteCount;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
