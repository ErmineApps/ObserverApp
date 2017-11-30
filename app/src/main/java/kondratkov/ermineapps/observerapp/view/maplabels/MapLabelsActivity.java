package kondratkov.ermineapps.observerapp.view.maplabels;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.Point;
import com.mapbox.services.commons.models.Position;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kondratkov.ermineapps.observerapp.MyApplication;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.LabelsMap;
import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.representation.Convector_DP_PX;
import kondratkov.ermineapps.observerapp.representation.DateTimePepresentation;
import kondratkov.ermineapps.observerapp.representation.DecodeImage;
import kondratkov.ermineapps.observerapp.representation.JsonToAddress;
import kondratkov.ermineapps.observerapp.view.addviolation.AddViolationActivity;
import kondratkov.ermineapps.observerapp.view.violation.ViolationProfileActivity;

public class MapLabelsActivity extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private boolean markerSelected = false;

    private MyPositionLocation mMyPositionLocation;
    private ValueAnimator mAnimator;

    private double latitude_old = 0;
    private double longitude_old = 0;

    private boolean FAB_Status = false;
    private Animation anim_show_fab, anim_show_buttons;
    private Animation anim_hide_fab, anim_hide_buttons;
    private ImageButton[] mImageButtonArray;

    private Violation new_violation;

    LinearLayout linearLayout_dialog_add_violation_image;

    @BindView(R.id.linearLayout_mapLabel_add)LinearLayout linearLayout_mapLabel_add;
    @BindView(R.id.fab_mapLabel) FloatingActionButton fab_mapLabel;

    @BindView(R.id.linearLayout_mapLabel_buttons) LinearLayout linearLayout_mapLabel_buttons;
    @BindView(R.id.imageButton_mapLabel_alcohol_drinking) ImageButton imageButton_mapLabel_alcohol_drinking;
    @BindView(R.id.imageButton_mapLabel_hooliganism) ImageButton imageButton_mapLabel_hooliganism;
    @BindView(R.id.imageButton_mapLabel_fight) ImageButton imageButton_mapLabel_fight;
    @BindView(R.id.imageButton_mapLabel_crime) ImageButton imageButton_mapLabel_crime;
    @BindView(R.id.imageButton_mapLabel_wrong_parking) ImageButton imageButton_mapLabel_wrong_parking;
    @BindView(R.id.imageButton_mapLabel_loud_music) ImageButton imageButton_mapLabel_loud_music;
    @BindView(R.id.imageButton_mapLabel_suspicious_object) ImageButton imageButton_mapLabel_suspicious_object;
    @BindView(R.id.imageButton_mapLabel_vandalism) ImageButton imageButton_mapLabel_vandalism;
    @BindView(R.id.imageButton_mapLabel_other) ImageButton imageButton_mapLabel_other;

    @BindView(R.id.textView_mapLabel_type)TextView textView_mapLabel_type;
    @BindView(R.id.editText_mapLabel_body)EditText editText_mapLabel_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_labels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        MyApplication.getInstance().getNavigationViewMyApp().setAppCompatActivity(MapLabelsActivity.this);

        new_violation = new Violation();
        new_violation.setType_violation(getResources().getStringArray(R.array.array_violations_enum)[0]);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mImageButtonArray = new ImageButton[]{imageButton_mapLabel_alcohol_drinking, imageButton_mapLabel_hooliganism, imageButton_mapLabel_fight,
                imageButton_mapLabel_crime, imageButton_mapLabel_wrong_parking, imageButton_mapLabel_loud_music, imageButton_mapLabel_suspicious_object,
                imageButton_mapLabel_vandalism, imageButton_mapLabel_other};



        Mapbox.getInstance(this, getString(R.string.token_map));

        mMyPositionLocation = new MyPositionLocation(MapLabelsActivity.this);



        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mMapboxMap) {

                mapboxMap = mMapboxMap;
                mapboxMap.setStyle(Style.MAPBOX_STREETS);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(), mMyPositionLocation.onGetMyLocation().getLongitude())) // set the camera's center position
                        .zoom(12)  // set the camera's zoom level
                        .tilt(20)  // set the camera's tilt
                        .build();

                // Move the camera to that position
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mapboxMap.setOnCameraChangeListener(new MapboxMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition position) {
                        LatLng latLng = position.target;

                        if(Math.abs(latitude_old - latLng.getLatitude())>0.0002 || Math.abs(longitude_old - latLng.getLongitude())>0.0002 ){
                            latitude_old = latLng.getLatitude();
                            longitude_old = latLng.getLongitude();

                            //new FileReadTask().execute();
                        }
                    }
                });

                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(),
                        mMyPositionLocation.onGetMyLocation().getLongitude())).title("ddDDD").snippet("wwwwwww"));

            }
        });

        anim_show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_show);
        anim_hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_hide);
        anim_show_buttons = AnimationUtils.loadAnimation(getApplication(), R.anim.button_map_show);
        anim_hide_buttons = AnimationUtils.loadAnimation(getApplication(), R.anim.button_map_hide);
        linearLayout_mapLabel_add.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        linearLayout_mapLabel_add.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        linearLayout_mapLabel_add.setVisibility(View.GONE);
                        linearLayout_mapLabel_buttons.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

                        int d = linearLayout_mapLabel_add.getHeight();
                        mAnimator = slideAnimator(0, linearLayout_mapLabel_add.getHeight()*2);//.getMeasuredHeight());

                        linearLayout_mapLabel_add.measure(widthSpec, heightSpec);

                        return true;
                    }
                }
        );
    }



    @OnClick(R.id.fab_mapLabel)
    public void onClickFab(View view){
        expand();
    }

    @OnClick(R.id.fab_mapLabel_my_position)
    public void onClickFabPosition(View view){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(), mMyPositionLocation.onGetMyLocation().getLongitude())) // set the camera's center position
                        .build();

                // Move the camera to that position
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(), mMyPositionLocation.onGetMyLocation().getLongitude())).title("ddDDD").snippet("wwwwwww"));
            }
        });
    }

    @OnClick(R.id.button_mapLabel_yes)
    public void onClickButtonYes(View view){
        new FileReadTask().execute();

    }

    @OnClick(R.id.button_mapLabel_cancel)
    public void onClickButtonCancel(View view){
        collapse();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickButtonViolation(View view){
        for(int i = 0; i<mImageButtonArray.length; i++){
            if(view == mImageButtonArray[i]){
                mImageButtonArray[i].setBackgroundTintList(getResources().getColorStateList(R.color.colorButtonVolationMapYes));
                textView_mapLabel_type.setText(getResources().getStringArray(R.array.array_violations)[i]);
                new_violation.setType_violation(getResources().getStringArray(R.array.array_violations_enum)[i]);
            }else{
                mImageButtonArray[i].setBackgroundTintList(getResources().getColorStateList(R.color.colorButtonVolationMapNo));
            }
        }
    }


    private void expand() {
        // set Visible
        linearLayout_mapLabel_add.setVisibility(View.VISIBLE);

        fab_mapLabel.startAnimation(anim_hide_fab);
        linearLayout_mapLabel_buttons.startAnimation(anim_show_buttons);
        fab_mapLabel.setVisibility(View.GONE);
        fab_mapLabel.setClickable(false);
        linearLayout_mapLabel_buttons.setVisibility(View.VISIBLE);

        mAnimator.start();
    }

    private void collapse() {

        int finalHeight = linearLayout_mapLabel_add.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                // Height=0, but it set visibility to GONE
                linearLayout_mapLabel_add.setVisibility(View.GONE);
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
        fab_mapLabel.startAnimation(anim_show_fab);
        linearLayout_mapLabel_buttons.startAnimation(anim_hide_buttons);
        fab_mapLabel.setVisibility(View.VISIBLE);
        fab_mapLabel.setClickable(true);
        linearLayout_mapLabel_buttons.setVisibility(View.GONE);
    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = linearLayout_mapLabel_add
                        .getLayoutParams();
                layoutParams.height = value;
                linearLayout_mapLabel_add.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void addDataViolation(String address){
        new_violation.setBody_observation(String.valueOf(editText_mapLabel_body.getText()));
        new_violation.setAddress(JsonToAddress.getAddress(address));
        new_violation.setDate(DateTimePepresentation.getCurrentTime(this));
        LabelsMap labelsMap = new LabelsMap();
        labelsMap.setDate_creation(DateTimePepresentation.getCurrentTime(this));
        labelsMap.setLatitude(latitude_old);
        labelsMap.setLongitude(longitude_old);
        LabelsMap[]labelsMaps = {labelsMap};
        new_violation.setLabelsMaps(labelsMaps);

        MyApplication.getInstance().setViolation(new_violation);

        Intent intent = new Intent(MapLabelsActivity.this, AddViolationActivity.class);
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

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

        if(MyApplication.getInstance().isNewViolationNewActivity()){
            MyApplication.getInstance().setNewViolationNewActivity(false);
            Intent intent = new Intent(MapLabelsActivity.this, MapLabelsActivity.class);
            startActivity(intent);
            this.finish();
        }
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

    private class FileReadTask extends AsyncTask<Void, Void, Void> {

        String textResult;

        @Override
        protected Void doInBackground(Void... params) {

            URL textUrl;

            try {
                textUrl = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude_old  + "," +
                        longitude_old +"&sensor=false&language=ru");

                BufferedReader bufferReader = new BufferedReader(
                        new InputStreamReader(textUrl.openStream()));

                String StringBuffer;
                String stringText = "";
                while ((StringBuffer = bufferReader.readLine()) != null) {
                    stringText += StringBuffer;
                }
                bufferReader.close();

                textResult = stringText;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("qwerty", "OTVET " + textResult);
            if(textResult!= null) {
                try {
                    JSONObject js = new JSONObject(textResult);
                    if(js.getString("status").equals("OK")){
                        addDataViolation(textResult);
                    }
                    else {
                        addDataViolation("");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}
