package kondratkov.ermineapps.observerapp.view.maplabels;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.addlabel.AddLabelActivity;

public class MapLabelsActivity extends AppCompatActivity {

    private MapView mapView;
    MyPositionLocation mMyPositionLocation;
    ValueAnimator mAnimator;

    private boolean FAB_Status = false;
    Animation anim_show_fab, anim_show_buttons;
    Animation anim_hide_fab, anim_hide_buttons;
    ImageButton[] mImageButtonArray;

    @BindView(R.id.linearLayoutMapLabel)LinearLayout linearLayoutMapLabel;
    @BindView(R.id.fabMapLabel) FloatingActionButton fabMapLabel;

    @BindView(R.id.linearLayoutMapLabelButtons) LinearLayout linearLayoutMapLabelButtons;
    @BindView(R.id.imageButton_alcohol_drinking) ImageButton imageButton_alcohol_drinking;
    @BindView(R.id.imageButton_hooliganism) ImageButton imageButton_hooliganism;
    @BindView(R.id.imageButton_fight) ImageButton imageButton_fight;
    @BindView(R.id.imageButton_crime) ImageButton imageButton_crime;
    @BindView(R.id.imageButton_wrong_parking) ImageButton imageButton_wrong_parking;
    @BindView(R.id.imageButton_loud_music) ImageButton imageButton_loud_music;
    @BindView(R.id.imageButton_suspicious_object) ImageButton imageButton_suspicious_object;
    @BindView(R.id.imageButton_vandalism) ImageButton imageButton_vandalism;
    @BindView(R.id.imageButton_other) ImageButton imageButton_other;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_labels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        MyApplication.getInstance().getNavigationViewMyApp().setAppCompatActivity(MapLabelsActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mImageButtonArray = new ImageButton[]{imageButton_alcohol_drinking, imageButton_hooliganism, imageButton_fight,
                imageButton_crime, imageButton_wrong_parking, imageButton_loud_music, imageButton_suspicious_object,
                imageButton_vandalism, imageButton_other};

        Mapbox.getInstance(this, getString(R.string.token_map));

        mMyPositionLocation = new MyPositionLocation(MapLabelsActivity.this);

        anim_show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_show);
        anim_hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_hide);
        anim_show_buttons = AnimationUtils.loadAnimation(getApplication(), R.anim.button_map_show);
        anim_hide_buttons = AnimationUtils.loadAnimation(getApplication(), R.anim.button_map_hide);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(), mMyPositionLocation.onGetMyLocation().getLongitude())) // set the camera's center position
                        .zoom(12)  // set the camera's zoom level
                        .tilt(20)  // set the camera's tilt
                        .build();

                // Move the camera to that position
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(48.13863, 11.57603)).title("ddDDD").snippet("wwwwwww"));
            }
        });

        linearLayoutMapLabel.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        linearLayoutMapLabel.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        linearLayoutMapLabel.setVisibility(View.GONE);
                        linearLayoutMapLabelButtons.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

                        int d = linearLayoutMapLabel.getHeight();
                        mAnimator = slideAnimator(0, linearLayoutMapLabel.getHeight()*2);//.getMeasuredHeight());

                        linearLayoutMapLabel.measure(widthSpec, heightSpec);

                        return true;
                    }
                }
        );

    }

    @OnClick(R.id.fabMapLabel)
    public void onClickFab(View view){
        expand();
    }

    @OnClick(R.id.buttonMapLabelYes)
    public void onClickButtonYes(View view){

    }

    @OnClick(R.id.buttonMapLabelCancel)
    public void onClickButtonCancel(View view){
        collapse();
    }

    public void onClickButtonViolation(View view){
        switch (view.getId()){
            case R.id.imageButton_alcohol_drinking:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    newViolationTypeButton(mImageButtonArray[0]);
                }
                break;
            case R.id.imageButton_hooliganism:
                break;
            case R.id.imageButton_fight:
                break;
            case R.id.imageButton_crime:
                break;
            case R.id.imageButton_wrong_parking:
                break;
            case R.id.imageButton_loud_music:
                break;
            case R.id.imageButton_suspicious_object:
                break;
            case R.id.imageButton_vandalism:
                break;
            case R.id.imageButton_other:
                break;
        }
    }

    private void expand() {
        // set Visible
        linearLayoutMapLabel.setVisibility(View.VISIBLE);

        fabMapLabel.startAnimation(anim_hide_fab);
        linearLayoutMapLabelButtons.startAnimation(anim_show_buttons);
        fabMapLabel.setVisibility(View.GONE);
        fabMapLabel.setClickable(false);
        linearLayoutMapLabelButtons.setVisibility(View.VISIBLE);

        mAnimator.start();
    }

    private void collapse() {

        int finalHeight = linearLayoutMapLabel.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                // Height=0, but it set visibility to GONE
                linearLayoutMapLabel.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
        fabMapLabel.startAnimation(anim_show_fab);
        linearLayoutMapLabelButtons.startAnimation(anim_hide_buttons);
        fabMapLabel.setVisibility(View.VISIBLE);
        fabMapLabel.setClickable(true);
        linearLayoutMapLabelButtons.setVisibility(View.GONE);
    }


    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = linearLayoutMapLabel
                        .getLayoutParams();
                layoutParams.height = value;
                linearLayoutMapLabel.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void newViolationTypeButton(ImageButton imageButton){
        for(ImageButton mImageButton1:mImageButtonArray){
            if(mImageButton1 == imageButton){
                ColorStateList colorStateList = ColorStateList.valueOf(Color.argb(12,12,12,12));
                imageButton.setBackgroundTintList(colorStateList);
            }else{
                ColorStateList colorStateList = ColorStateList.valueOf(Color.argb(123,123,123,123));
                imageButton.setBackgroundTintList(colorStateList);
            }
        }
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

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
