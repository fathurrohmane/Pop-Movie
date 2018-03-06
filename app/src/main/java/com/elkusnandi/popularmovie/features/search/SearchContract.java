package com.elkusnandi.popularmovie.features.search;

/**
 * Created by Taruna 98 on 26/12/2017.
 */

public interface SearchContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showResult();

        void showError();

    }

    interface Presenter{

        void search(String query);

    }
}
