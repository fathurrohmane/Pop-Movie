package com.elkusnandi.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;
import com.elkusnandi.popularmovie.data.provider.AppDatabase;
import com.elkusnandi.popularmovie.data.provider.FavouriteMovieDao;
import com.elkusnandi.popularmovie.utils.TestSchedulerProvider;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

@RunWith(AndroidJUnit4.class)
public class AndroidDatabaseTest {
    private AppDatabase appDatabase;
    private CompositeDisposable compositeDisposable;

    @Before
    public void createDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).build();
        compositeDisposable = new CompositeDisposable();
    }

    @After
    public void closeDatabase() throws IOException {
        appDatabase.close();
        compositeDisposable.clear();
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

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(appDatabase.favouriteMovieDao().getAll()
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((favouriteMovieEntities, throwable) -> {
                    Assert.assertEquals(movieId, favouriteMovieEntities.get(0).getId());
                })
        );
    }

    @Test
    public void removeAllMovieFromDatabaseTest() {
        addSomeFavouriteMovies();

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(appDatabase.favouriteMovieDao().getAll()
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((favouriteMovieEntities, throwable) -> {
                    Assert.assertTrue(favouriteMovieEntities.size() == 4);
                })
        );

        appDatabase.favouriteMovieDao().deleteAll();

        compositeDisposable.add(appDatabase.favouriteMovieDao().getAll()
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((favouriteMovieEntities, throwable) -> {
                    Assert.assertEquals(0, favouriteMovieEntities.size());
                })
        );
    }

    @Test
    public void getAllFavouriteMoviesFromDatabaseTest() {
        addSomeFavouriteMovies();

        int[] ids = new int[]{1, 2, 3, 4};

        compositeDisposable.add(appDatabase.favouriteMovieDao().getAll()
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((favouriteMovieEntities, throwable) -> {
                    int counter = 0;
                    for (FavouriteMovieEntity favouriteMovieEntity : favouriteMovieEntities) {
                        Assert.assertEquals(ids[counter++], favouriteMovieEntity.getId());
                    }
                })
        );
    }

    @Test
    public void checkIfFavouriteMovieIsInDatabaseTest() {
        int movieId = 1;
        addAFavouriteMovie(movieId);

        compositeDisposable.add(appDatabase.favouriteMovieDao().isFavourites(movieId)
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((aBoolean, throwable) ->
                        Assert.assertTrue(aBoolean)
                )
        );
    }

    @Test
    public void deleteFavouriteMovieFromDatabaseTest() {
        int movieId = 1;
        addAFavouriteMovie(movieId);

        FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(movieId);

        appDatabase.favouriteMovieDao().removeFromFavouriteMovieList(favouriteMovieEntity);

        compositeDisposable.add(appDatabase.favouriteMovieDao().isFavourites(movieId)
                .subscribeOn(TestSchedulerProvider.getInstance().io())
                .observeOn(TestSchedulerProvider.getInstance().io())
                .subscribe((aBoolean, throwable) ->
                        Assert.assertFalse(aBoolean)
                )
        );
    }


}
