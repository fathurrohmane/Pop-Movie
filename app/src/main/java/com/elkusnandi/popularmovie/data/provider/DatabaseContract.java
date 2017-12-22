package com.elkusnandi.popularmovie.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Taruna 98 on 30/07/2017.
 */

public class DatabaseContract {

    public static final String SCHEME = "content://";

    public static final String AUTHORITY = "com.elkusnandi.popularmovie.content";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final String PATH_FAVORITE = "favorites";

    public static final class FavoriteMoviesTable implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_POSTER = "movie_poster";
        public static final String COLUMN_TITLE = "movie_title";
        public static final String COLUMN_RATING = "movie_rating";
        public static final String COLUMN_SYNOPSIS = "movie_synopsis";
        public static final String COLUMN_RELEASEDATE = "movie_releasedate";
    }
}
