package com.elkusnandi.popularmovie.features.search;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.MyPresenter;

/**
 * Created by Taruna 98 on 26/12/2017.
 */

public interface SearchContract {

    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        void showResult();

        void showError();

    }

    interface Presenter{

        void search(String query);

    }
}
