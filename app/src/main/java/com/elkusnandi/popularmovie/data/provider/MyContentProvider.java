package com.elkusnandi.popularmovie.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MyDatabaseHelper myDatabaseHelper;

    public MyContentProvider() {
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_FAVORITE, FAVORITES);
        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_FAVORITE + "/#", FAVORITES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        myDatabaseHelper = new MyDatabaseHelper(context);

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int movieDeleted;
        String id;
        switch (match) {
            case FAVORITES:
                movieDeleted = db.delete(DatabaseContract.FavoriteMoviesTable.TABLE_NAME, selection, selectionArgs);
                break;
//            case FAVORITES_WITH_ID:
//                id = uri.getPathSegments().get(1);
//                movieDeleted = db.delete(DatabaseContract.FavoriteMoviesTable.TABLE_NAME, "_id=?", new String[]{id});
//                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return movieDeleted;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case FAVORITES:
                long id = db.insert(DatabaseContract.FavoriteMoviesTable.TABLE_NAME, null, values);
                returnUri = ContentUris.withAppendedId(DatabaseContract.FavoriteMoviesTable.CONTENT_URI, id);
                if (id > 0) {

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri + " " + returnUri);
                }
                break;
            case FAVORITES_WITH_ID:
                break;

            default:
                throw new UnsupportedOperationException("Uri = " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;

        switch (match) {
            case FAVORITES:
                cursor = db.query(DatabaseContract.FavoriteMoviesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mArgs = new String[]{id};

                cursor = db.query(DatabaseContract.FavoriteMoviesTable.TABLE_NAME,
                        projection,
                        mSelection,
                        mArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                throw new UnsupportedOperationException("Uri = " + uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
