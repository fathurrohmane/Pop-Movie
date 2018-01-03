package com.elkusnandi.popularmovie.features.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.features.login.LogInActivity;
import com.elkusnandi.popularmovie.features.main.discover.DiscoverFragment;
import com.elkusnandi.popularmovie.features.main.my_moviedb.UserFavouriteMovieFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("Discover");

        // Set Fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        // Set nav bar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setNavigationViewState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_discover_movie);

        // Show default fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, DiscoverFragment.newInstance());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_log_in:
                Intent intent = new Intent(this, LogInActivity.class);
                startActivityForResult(intent, LogInActivity.REQUEST_CODE_LOGIN);
                break;
            case R.id.nav_discover_movie:
                changeFragment(DiscoverFragment.newInstance());
                break;
            case R.id.nav_discover_tv:
                break;
            case R.id.nav_movie_list:
                break;
            case R.id.nav_favourite:
                changeFragment(UserFavouriteMovieFragment.newInstance());
                break;
            case R.id.nav_watch_list:
                break;
            case R.id.nav_log_out:
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
        boolean loginStatus = getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE)
                .getBoolean(getString(R.string.sharedpreference_login_status), false);
        if (loginStatus) {
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_in).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_out).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_in).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_group_my_movie_db_logged_out).setVisible(true);
        }
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
