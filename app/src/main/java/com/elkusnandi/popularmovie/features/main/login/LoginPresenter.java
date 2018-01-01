package com.elkusnandi.popularmovie.features.main.login;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 29/12/2017.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(CompositeDisposable disposable, Repository repository, BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
        disposable.clear();
    }

    @Override
    public void requestToken() {
        disposable.add(
                repository.requestToken()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((requestTokenRespond, throwable) -> {
                            if (throwable == null) {
                                if (requestTokenRespond != null) {
                                    view.requestLogin(requestTokenRespond.getRequestToken());
                                } else {
                                }

                            } else {
                                // throw error
                            }
                        })
        );
    }

    @Override
    public void requestSessionId(String token) {
        disposable.add(
                repository.requestSessionId(token)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((requestSessionIdRespond, throwable) -> {
                                    if (throwable == null) {
                                        view.onLoginSuccess(requestSessionIdRespond.getSessionId());
                                    } else {
                                        view.onLoginFail(throwable.getMessage());
                                    }
                                }
                        )
        );
    }
}
