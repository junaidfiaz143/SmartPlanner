package com.inventors.jd.smartplanner.activities;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inventors.jd.smartplanner.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager mLocationManager;

    private Double dbLat, dbLng;
    private int radius = 0;

    private View mMapView;

    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    private CircleOptions circleOptions;
    private Circle circle;

    private Geocoder geocoder;

    public static String myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMapView = mapFragment.getView();

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 70, 70, 0);

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                dbLat = place.getLatLng().latitude;
                dbLng = place.getLatLng().longitude;

//                Toast.makeText(MapsActivity.this, dbLat + " : Lat - Long : " + dbLng, Toast.LENGTH_SHORT).show();
//                Log.d("Location", dbLat + " : Lat - Long : " + dbLng);

                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dbLat, dbLng), 18.0f));

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(dbLat, dbLng)).title(dbLat + " : Lat - Long : " + dbLng);
                mMap.addMarker(marker);

                circleOptions = new CircleOptions()
                        .center(new LatLng(dbLat, dbLng))
                        .radius(radius)
                        .strokeWidth(2f)
                        .strokeColor(Color.parseColor("#64B5F6"))
                        .fillColor(Color.parseColor("#5964B5F6"));

                circle = mMap.addCircle(circleOptions);

                try {
                    List<Address> listAddresses = geocoder.getFromLocation(dbLat, dbLng, 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        myLocation = listAddresses.get(0).getAddressLine(0);
                        Toast.makeText(MapsActivity.this, "" + myLocation, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            final Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18.0f));
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(location.getLatitude(), location.getLongitude())).title("current location");
                mMap.addMarker(marker);

                try {
                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        myLocation = listAddresses.get(0).getAddressLine(0);
                        Toast.makeText(MapsActivity.this, "" + myLocation, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {

                        dbLat = point.latitude;
                        dbLng = point.longitude;

                        Toast.makeText(MapsActivity.this, dbLat + " : Lat - Long : " + dbLng, Toast.LENGTH_SHORT).show();
                        Log.d("Location", dbLat + " : Lat - Long : " + dbLng);

                        mMap.clear();
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(point.latitude, point.longitude)).title(dbLat + " : Lat - Long : " + dbLng);
                        mMap.addMarker(marker);

                        circleOptions = new CircleOptions()
                                .center(new LatLng(dbLat, dbLng))
                                .radius(radius)
                                .strokeWidth(2f)
                                .strokeColor(Color.parseColor("#64B5F6"))
                                .fillColor(Color.parseColor("#5964B5F6"));

                        circle = mMap.addCircle(circleOptions);

                        try {
                            List<Address> listAddresses = geocoder.getFromLocation(dbLat, dbLng, 1);
                            if (null != listAddresses && listAddresses.size() > 0) {
                                myLocation = listAddresses.get(0).getAddressLine(0);
                                Toast.makeText(MapsActivity.this, "" + myLocation, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
            afterPermission();
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(MapsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                getLocation();
            } else {
                getLocation();
            }
        } catch (Exception exp) {
            Toast.makeText(this, "Error : " + exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void afterPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

}