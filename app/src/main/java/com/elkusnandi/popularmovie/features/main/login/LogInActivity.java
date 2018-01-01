package com.elkusnandi.popularmovie.features.main.login;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.UserDetailRespond;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;
import com.elkusnandi.popularmovie.utils.MyDisposable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements LoginContract.View {

    public static final int REQUEST_CODE_LOGIN = 123;

    @BindView(R.id.web_view)
    WebView webview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    ActionBar actionBar;

    private LoginPresenter presenter;
    private String token;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        // Setup Webview
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyBrowser());

        // Setup Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(webview.getTitle());
            actionBar.setSubtitle(webview.getUrl());
        }

        // Set Presenter
        presenter = new LoginPresenter(MyDisposable.getInstance(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance()
        );
        presenter.onAttach(this);
        presenter.requestToken();
    }

    @Override
    public void requestLogin(String token) {
        this.token = token;
        webview.loadUrl("https://www.themoviedb.org/authenticate/" + token);
        toolbar.setTitle(webview.getTitle());
        toolbar.setSubtitle(webview.getUrl());
    }

    @Override
    public void onLoginSuccess(String sessionId) {
        // Save session id to sharedpreferences and login status to true
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.sharedpreference_session_id), sessionId);
        editor.putBoolean(getString(R.string.sharedpreference_login_status), true);
        editor.apply();

        //Toast.makeText(this, getString(R.string.success_login), Toast.LENGTH_LONG).show();

        // Request user detail
        presenter.requestUserDetails(sessionId);
    }

    @Override
    public void onUserDetailReceived(UserDetailRespond userDetailRespond) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.sharedpreference_user_name), userDetailRespond.getUsername());
        editor.putLong(getString(R.string.sharedpreference_account_id), userDetailRespond.getId());
        editor.apply();

        Toast.makeText(this, getString(R.string.success_get_user_detail, userDetailRespond.getUsername()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int code, String message) {
        // Set login status to false
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.sharedpreference_login_status), false);
        editor.apply();

        Toast.makeText(this, getString(R.string.error_login, message), Toast.LENGTH_LONG).show();
    }

    private class MyBrowser extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            toolbar.setTitle("Loading...");
            toolbar.setSubtitle(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            toolbar.setTitle("Loading...");
            toolbar.setSubtitle(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setRefreshing(false);
            toolbar.setTitle(view.getTitle());

            if (url.contains("allow")) {
                presenter.requestSessionId(token);
            } else if (url.contains("deny")) {
                presenter.requestSessionId(token);
            }

            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            swipeRefreshLayout.setRefreshing(true);
            super.onPageStarted(view, url, favicon);
        }
    }
}
