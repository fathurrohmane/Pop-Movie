package com.elkusnandi.popularmovie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Taruna 98 on 14/01/2018.
 */

public class ShowDetail {

    @SerializedName("backdrop_path")
    @Expose
    protected String backdropPath;
    @SerializedName("homepage")
    @Expose
    protected String homepage;
    @SerializedName("genres")
    @Expose
    protected List<Genre> genres = null;
    @SerializedName("id")
    @Expose
    protected long id;
    @SerializedName("overview")
    @Expose
    protected String overview;
    @SerializedName("popularity")
    @Expose
    protected double popularity;
    @SerializedName("poster_path")
    @Expose
    protected String posterPath;
    @SerializedName("production_companies")
    @Expose
    protected List<ProductionCompany> productionCompanies = null;
    @SerializedName("status")
    @Expose
    protected String status;
    @SerializedName("vote_average")
    @Expose
    protected double voteAverage;
    @SerializedName("vote_count")
    @Expose
    protected int voteCount;
    @SerializedName("original_language")
    @Expose
    protected String originalLanguage;
}
