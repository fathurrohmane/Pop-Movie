package com.elkusnandi.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;
import com.elkusnandi.popularmovie.data.provider.AppDatabase;
import com.elkusnandi.popularmovie.data.provider.FavouriteMovieDao;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AndroidDatabaseTest {
    private AppDatabase appDatabase;

    @Before
    public void createDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).build();
    }

    @After
    public void closeDatabase() throws IOException {
        appDatabase.close();

    }

    private long addAFavouriteMovie(int id) {
        return appDatabase.favouriteMovieDao().addToFavouriteMovieList(new FavouriteMovieEntity(id));
    }

    private void addSomeFavouriteMovies() {
        addAFavouriteMovie(1);
        addAFavouriteMovie(2);
        addAFavouriteMovie(3);
        addAFavouriteMovie(4);
    }

    @Test
    public void addAMovieToDatabaseTest() throws Exception {
        int movieId = 1;
        long id = addAFavouriteMovie(movieId);

        List<FavouriteMovieEntity> dataset = new ArrayList<>(appDatabase.favouriteMovieDao().getAll());
        Assert.assertEquals(movieId, dataset.get(0).getId());
    }

    @Test
    public void removeAllMovieFromDatabaseTest() {
        addSomeFavouriteMovies();

        List<FavouriteMovieEntity> dataset = new ArrayList<>(appDatabase.favouriteMovieDao().getAll());
        Assert.assertTrue(dataset.size() == 4);

        appDatabase.favouriteMovieDao().deleteAll();

        dataset = new ArrayList<>(appDatabase.favouriteMovieDao().getAll());
        Assert.assertEquals(0, dataset.size());
    }

    @Test
    public void getAllFavouriteMoviesFromDatabaseTest() {
        addSomeFavouriteMovies();

        int[] ids = new int[]{1, 2, 3, 4};
        int counter = 0;

        List<FavouriteMovieEntity> dataset = new ArrayList<>(appDatabase.favouriteMovieDao().getAll());

        for (FavouriteMovieEntity favouriteMovieEntity : dataset) {
            Assert.assertEquals(ids[counter++], favouriteMovieEntity.getId());
        }
    }

    @Test
    public void checkIfFavouriteMovieIsInDatabaseTest() {
        addSomeFavouriteMovies();

        List<FavouriteMovieEntity> dataset = new ArrayList<>(appDatabase.favouriteMovieDao().getAll());

        for (FavouriteMovieEntity favouriteMovieEntity : dataset) {
            Assert.assertTrue(appDatabase.favouriteMovieDao().isFavourite(favouriteMovieEntity.getId()));
        }
    }

    @Test
    public void deleteFavouriteMovieFromDatabaseTest() {
        addSomeFavouriteMovies();

        FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(1);

        appDatabase.favouriteMovieDao().removeFromFavouriteMovieList(favouriteMovieEntity);

        Assert.assertFalse(appDatabase.favouriteMovieDao().isFavourite(favouriteMovieEntity.getId()));
    }


}
