package com.riteshwarke.googlemaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.riteshwarke.googlemaps.helpers.AppConstants;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerDragListener{
    private GoogleMap map;
    //private boolean GPSIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
       // map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        checkRuntimePermissions();

    }

    private void setMap() {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);


        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        Toast.makeText(this, "My current Latlong"+latLng, Toast.LENGTH_SHORT).show();

        // Show the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(20));
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMarkerDragListener(this);
    }

    public void checkRuntimePermissions(){


        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // General.showPermissionDialog(this, this);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        AppConstants.LOCATION_PERMISSION_REQUEST_CODE);

            }
            else{

                Log.i("TAG", "ralpher 24 ");

                setMap();

            }
        }else{
            // buildAlertMessageNoGps();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i("TAG","ralph "+requestCode+" "+permissions+ " "+grantResults);
        switch (requestCode) {
            case AppConstants.LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG","ralphorp "+requestCode+" "+permissions+ " "+grantResults);

                    setMap();


                } else {


                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        


    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
