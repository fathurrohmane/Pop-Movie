
package com.elkusnandi.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCasts implements Parcelable {

    public final static Creator<MovieCasts> CREATOR = new Creator<MovieCasts>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieCasts createFromParcel(Parcel in) {
            return new MovieCasts(in);
        }

        public MovieCasts[] newArray(int size) {
            return (new MovieCasts[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    protected MovieCasts(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.cast, (com.elkusnandi.popularmovie.data.model.Cast.class.getClassLoader()));
        in.readList(this.crew, (com.elkusnandi.popularmovie.data.model.Crew.class.getClassLoader()));
    }

    public MovieCasts() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return 0;
    }

}
