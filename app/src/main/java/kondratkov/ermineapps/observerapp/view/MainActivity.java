package kondratkov.ermineapps.observerapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MyPositionLocation;
import kondratkov.ermineapps.observerapp.view.navigation.NavigationViewMyApp;

public class MainActivity extends AppCompatActivity {

    private NavigationViewMyApp mNavigationViewMyApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationViewMyApp =new NavigationViewMyApp(MainActivity.this);
        MyApplication.getInstance().setNavigationViewMyApp(mNavigationViewMyApp);

        MyPositionLocation positionLocation = new MyPositionLocation(this);
        positionLocation.onGetMyLocation();

        Intent intent = new Intent(MainActivity.this, MapLabelsActivity.class);
        //Intent intent = new Intent(MainActivity.this, MapExActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
