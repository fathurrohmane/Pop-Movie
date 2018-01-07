package com.elkusnandi.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/** Class to hold information for gathering Movie and Tv Lists
 * Created by Taruna 98 on 12/12/2017.
 */

public class ShowRespond<T extends Show> implements Parcelable {

    public static final Creator<ShowRespond> CREATOR = new Creator<ShowRespond>() {
        @Override
        public ShowRespond createFromParcel(Parcel in) {
            return new ShowRespond(in);
        }

        @Override
        public ShowRespond[] newArray(int size) {
            return new ShowRespond[size];
        }
    };
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<T> results = null;

    protected ShowRespond(Parcel in) {
        page = in.readInt();
        totalResults = in.readInt();
        totalPages = in.readInt();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(totalResults);
        parcel.writeInt(totalPages);
    }
}