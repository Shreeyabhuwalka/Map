package com.example.mapdemo;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    LocationManager locationManager;
    String provider;
    private GoogleMap mMap;
    DatabaseReference myRef;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        Location location;
        location = locationManager.getLastKnownLocation(provider);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        /*if (location == null) {
            onLocationChanged(location);

        } else {
            Log.i("Location info:", "Location not found ");
        }*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera 12.97, 79.15
        LatLng marker = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in place"));
        marker = new LatLng(78, 28);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in place"));
        marker = new LatLng(19,29);
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker in place"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat;
        Double lng;
        lat = location.getLatitude();
        lng = location.getLongitude();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng( lat,lng)).title("Marker in place"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( lat,lng),12));

        /*Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address>listAddress = geocoder.getFromLocation(lat,lng,1);
            Log.i("Place info: ", listAddress.get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


}
