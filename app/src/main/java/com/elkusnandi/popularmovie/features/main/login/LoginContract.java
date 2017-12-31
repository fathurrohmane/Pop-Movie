package com.elkusnandi.popularmovie.features.main.login;

import com.elkusnandi.popularmovie.interfaces.BaseView;
import com.elkusnandi.popularmovie.interfaces.MyPresenter;

/**
 * Created by Taruna 98 on 29/12/2017.
 */

public class LoginContract {

    public interface View extends BaseView {

        void requestLogin(String token);

        void onLoginSuccess(String sessionId);

        void onLoginFail(String message);

    }

    public interface Presenter extends MyPresenter<View> {

        void requestToken(String apiKey);

        void requestSessionId(String apiKey, String token);
    }
}
