package com.elkusnandi.popularmovie.features.login;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.MyPresenter;
import com.elkusnandi.popularmovie.data.model.UserDetailRespond;

/**
 * Login Contract class
 * Created by Taruna 98 on 29/12/2017.
 */

public class LoginContract {

    public interface View extends BaseView {

        void requestLogin(String token);

        void onLoginSuccess(String sessionId);

        void onUserDetailReceived(UserDetailRespond userDetailRespond);

        void onError(int code, String message);

    }

    public interface Presenter extends MyPresenter<View> {

        void requestToken();

        void requestSessionId(String token);

        void requestUserDetails(String sessionId);
    }
}
