package com.elkusnandi.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TV class extends to Show
 * Created by Taruna 98 on 07/01/2018.
 */

public class Tv extends Show implements Parcelable {

    public static final String TV_TYPE_TODAY_AIRING = "tv_today_airing";
    public static final String TV_TYPE_ON_THE_AIR = "tv_on_the_air";
    public static final String TV_TYPE_POPULAR_TV = "tv_popular";
    public static final String TV_TYPE_RECENTLY_ADDED = "tv_recently_added";

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original_name")
    @Expose
    private String originalName;

    protected Tv(Parcel in) {
        firstAirDate = in.readString();
        originCountry = in.createStringArrayList();
        name = in.readString();
        originalName = in.readString();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstAirDate);
        dest.writeStringList(originCountry);
        dest.writeString(name);
        dest.writeString(originalName);
    }
}
