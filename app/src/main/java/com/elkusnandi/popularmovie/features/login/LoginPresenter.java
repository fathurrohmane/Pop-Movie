package com.elkusnandi.popularmovie.features.login;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

/**
 * Login Presenter class to handle all data logic of the Login Activity
 * Created by Taruna 98 on 29/12/2017.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
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
                                    throw new IllegalArgumentException("RequestTokenRespond is Null");
                                }

                            } else {
                                // Show error message
                                if (throwable instanceof HttpException) {
                                    HttpException httpException = (HttpException) throwable;
                                    view.onError(httpException.code(), httpException.getMessage());
                                } else {
                                    view.onError(0, throwable.getMessage());
                                }

                            }
                        })
        );
    }

    @Override
    public void requestSessionId(String token) {
        disposable.add(repository.requestSessionId(token)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        (requestSessionIdRespond, throwable) -> {
                            if (throwable == null) {
                                view.onLoginSuccess(requestSessionIdRespond.getSessionId());
                            } else {
                                if (throwable instanceof HttpException) {
                                    HttpException httpException = (HttpException) throwable;
                                    view.onError(httpException.code(), throwable.getMessage());
                                } else {
                                    view.onError(-1, throwable.getMessage());
                                }
                            }
                        }
                )
        );
    }

    @Override
    public void requestUserDetails(String sessionId) {
        disposable.add(repository.requestUserDetail(sessionId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe((userDetailRespond, throwable) -> {
                    if (throwable == null) {
                        view.onUserDetailReceived(userDetailRespond);
                    } else {
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            view.onError(httpException.code(), throwable.getMessage());
                        } else {
                            view.onError(-1, throwable.getMessage());
                        }
                    }
                })
        );
    }

    public void login(String token) {
        disposable.add(repository
                .requestSessionId(token)
                .subscribeOn(schedulerProvider.io())
                .flatMap(requestSessionIdRespond -> repository.requestUserDetail(requestSessionIdRespond.getSessionId()))
                .subscribe((userDetailRespond, throwable) -> {
                    if (throwable == null) {
                        view.onUserDetailReceived(userDetailRespond);
                    } else {
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            view.onError(httpException.code(), throwable.getMessage());
                        } else {
                            view.onError(-1, throwable.getMessage());
                        }
                    }
                })
        );
    }
}