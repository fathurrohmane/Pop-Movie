package com.elkusnandi.popularmovie.features.search;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 26/12/2017.
 */

public class SearchPresenter extends BasePresenter implements SearchContract.Presenter {

    private SearchContract.View view;

    public SearchPresenter(CompositeDisposable disposable, Repository repository, BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
        this.view = null;
    }

    @Override
    public void search(String query) {

    }
}
