package com.elkusnandi.popularmovie.features.login;

import com.elkusnandi.popularmovie.data.model.UserDetailRespond;

/**
 * Login Contract class
 * Created by Taruna 98 on 29/12/2017.
 */

public interface LoginContract {

    interface View {

        void requestLogin(String token);

        void onLoginSuccess(String sessionId);

        void onUserDetailReceived(UserDetailRespond userDetailRespond);

        void onError(int code, String message);

    }

    interface Presenter {

        void requestToken();

        void requestSessionId(String token);

        void requestUserDetails(String sessionId);
    }
}
