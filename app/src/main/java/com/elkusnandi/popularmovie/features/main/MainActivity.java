package com.elkusnandi.popularmovie.features.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.features.login.LogInActivity;
import com.elkusnandi.popularmovie.features.main.discover.DiscoverFragment;
import com.elkusnandi.popularmovie.features.main.favourite.FavouriteMovieFragment;
import com.elkusnandi.popularmovie.features.main.watch_list.WatchListFragment;
import com.elkusnandi.popularmovie.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    //@BindView(R.id.imageview_profile)
    ImageView imageViewProfile;
    //@BindView(R.id.textview_username)
    TextView textViewUserName;

    private Fragment nextFragment;
    private int drawerItemClickedId;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        // Set toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("Discover");

        // Set nav drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(drawerListener);
        toggle.syncState();
        imageViewProfile = navigationView.getHeaderView(0).findViewById(R.id.imageview_profile);
        textViewUserName = navigationView.getHeaderView(0).findViewById(R.id.textview_username);

        setNavigationViewState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_discover_movie);

        // Show default fragment
        changeFragment(DiscoverFragment.newInstance());

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_log_in:
                drawerItemClickedId = R.id.nav_log_in;
                break;
            case R.id.nav_discover_movie:
                nextFragment = DiscoverFragment.newInstance();
                drawerItemClickedId = R.id.nav_discover_movie;
                break;
            case R.id.nav_discover_tv:
                drawerItemClickedId = R.id.nav_discover_tv;
                break;
            case R.id.nav_movie_list:
                drawerItemClickedId = R.id.nav_movie_list;
                break;
            case R.id.nav_favourite:
                nextFragment = FavouriteMovieFragment.newInstance();
                drawerItemClickedId = R.id.nav_favourite;
                break;
            case R.id.nav_watch_list:
                nextFragment = WatchListFragment.newInstance();
                drawerItemClickedId = R.id.nav_watch_list;
                break;
            case R.id.nav_log_out:
                new MaterialDialog.Builder(this)
                        .tag("logout_dialog")
                        .title(R.string.dialog_logout_title)
                        .content(R.string.dialog_logout_content)
                        .onPositive(onPositiveDialogButtonClicked)
                        .positiveText(R.string.dialog_logout_button_positive)
                        .negativeText(R.string.dialog_logout_button_negative)
                        .show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 123) {
            setNavigationViewState();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Change the state of the navigation view depend on user is logged in or not
     */
    private void setNavigationViewState() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
        boolean loginStatus = sharedPreferences.getBoolean(getString(R.string.sharedpreference_login_status), false);

        if (loginStatus) {
            String avatarPath = sharedPreferences.getString(getString(R.string.sharedpreference_profile_picture_path), "");
            String userName = sharedPreferences.getString(getString(R.string.sharedpreference_user_name), "");
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_in).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_out).setVisible(false);
            Picasso.with(this)
                    .load("https://secure.gravatar.com/avatar/" + avatarPath + ".jpg?s=50")
                    .transform(new CircleTransform())
                    .into(imageViewProfile);
            imageViewProfile.setVisibility(View.VISIBLE);
            textViewUserName.setText(userName);
        } else {
            textViewUserName.setText(getString(R.string.app_name));
            imageViewProfile.setVisibility(View.INVISIBLE);
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_in).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_out).setVisible(true);
        }
    }

    /**
     * Change fragment in MainActivity fragment container
     *
     * @param fragment fragment to change to
     */
    private void changeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.zoom_in,
                    R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Drawer listener
     */
    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            switch (drawerItemClickedId) {
                case R.id.nav_log_in:
                    Intent intent = new Intent(context, LogInActivity.class);
                    startActivityForResult(intent, LogInActivity.REQUEST_CODE_LOGIN);
                case R.id.nav_discover_movie:
                case R.id.nav_discover_tv:
                case R.id.nav_movie_list:
                case R.id.nav_favourite:
                case R.id.nav_watch_list:
                    changeFragment(nextFragment);
                    break;
                default:
                    break;
            }
            drawerItemClickedId = -1;
        }
    };

    /**
     * Dialog button listener
     */
    private MaterialDialog.SingleButtonCallback onPositiveDialogButtonClicked = (dialog, which) -> {
        String tag = (String) dialog.getTag();

        if (tag != null) {
            switch (tag) {
                case "logout_dialog":
                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.sharedpreference_login_status), false);
                    editor.putString(getString(R.string.sharedpreference_session_id), "");
                    editor.putLong(getString(R.string.sharedpreference_account_id), 0L);
                    editor.apply();

                    setNavigationViewState();
                    Toast.makeText(this, getString(R.string.success_logout), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
