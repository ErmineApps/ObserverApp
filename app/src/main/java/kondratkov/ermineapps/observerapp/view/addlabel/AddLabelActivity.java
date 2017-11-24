package kondratkov.ermineapps.observerapp.view.addlabel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.view.maplabels.MapLabelsActivity;
import kondratkov.ermineapps.observerapp.view.maplabels.MyPositionLocation;

public class AddLabelActivity extends AppCompatActivity {

    private MapView mapView;
    MyPositionLocation mMyPositionLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_label);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMyPositionLocation = new MyPositionLocation(AddLabelActivity.this);
        Mapbox.getInstance(this, getString(R.string.token_map));

        mapView = (MapView) findViewById(R.id.mapViewAddLabel );
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

                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(mMyPositionLocation.onGetMyLocation().getLatitude(), mMyPositionLocation.onGetMyLocation().getLongitude())).title("ddDDD").snippet("wwwwwww"));
            }
        });

    }

}
