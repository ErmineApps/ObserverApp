package kondratkov.ermineapps.observerapp.view.navigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;
import kondratkov.ermineapps.observerapp.view.profile.ProfileActivity;
import kondratkov.ermineapps.observerapp.view.references.ReferencesListActivity;
import kondratkov.ermineapps.observerapp.view.setting.SettingActivity;

/**
 * Created by kondratkov on 19.11.2017.
 */

public class NavigationViewMyApp implements NavigationView.OnNavigationItemSelectedListener {

    AppCompatActivity mAppCompatActivity;

    public NavigationViewMyApp(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        NavigationView navigationView = (NavigationView) mAppCompatActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        NavigationView navigationView = (NavigationView) mAppCompatActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(mAppCompatActivity, ProfileActivity.class);
            mAppCompatActivity.startActivity(intent);
            mAppCompatActivity.finish();
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(mAppCompatActivity, MapLabelsActivity.class);
            mAppCompatActivity.startActivity(intent);
            mAppCompatActivity.finish();
        } else if (id == R.id.nav_references) {
            Intent intent = new Intent(mAppCompatActivity, ReferencesListActivity.class);
            mAppCompatActivity.startActivity(intent);
            mAppCompatActivity.finish();
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(mAppCompatActivity, SettingActivity.class);
            mAppCompatActivity.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) mAppCompatActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
