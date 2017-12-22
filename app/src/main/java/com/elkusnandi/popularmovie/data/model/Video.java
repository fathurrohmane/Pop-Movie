
package com.elkusnandi.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video implements Parcelable {

    public final static Creator<Video> CREATOR = new Creator<Video>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return (new Video[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<VideoResult> videoResults = null;

    protected Video(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.videoResults, (VideoResult.class.getClassLoader()));
    }

    public Video() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoResult> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(videoResults);
    }

    public int describeContents() {
        return 0;
    }

}
