package com.example.mapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class locatr extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    String provider;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locatr);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        Location location;
        location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            Log.i("Location information", "Location found");
        } else {
            Log.i("Location info:", "Location not found ");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat;
        Double lng;
        lat = location.getLatitude();
        lng = location.getLongitude();
        Log.i("Latitude: ", lat.toString());
        Log.i("Longitude: ", lng.toString());
    }
    @SuppressLint({"NewApi", "MissingPermission"})
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
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {

    }
}
