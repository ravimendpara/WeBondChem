package com.demoapp.demoapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.se.omapi.SEService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationTrackerNew {

    private Activity activity;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastKnownLocation = null;
    //    private GoogleApiClient mGoogleApiClient;
    IGetUserCurrentLocation iGetUserCurrentLocation;

    public LocationTrackerNew(Activity activity, IGetUserCurrentLocation iGetUserCurrentLocation) {
        this.activity = activity;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        getLastLocation();
        this.iGetUserCurrentLocation = iGetUserCurrentLocation;
    }


    private void getLastLocation() {
        // check if permissions are given
        if (CommonUtil.checkIsGpsEnabled(activity)) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        lastKnownLocation = location;
                        if (!CommonUtil.checkIsEmptyOrNullCommon(getCityNameFromLatLong())) {
                            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                            try {
                                iGetUserCurrentLocation.fetchCurrentLocation(lastKnownLocation);
//                                iGetUserCurrentLocation.fetchCurrentLocation(getCityNameFromLatLong(),
//                                        geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

        } else {
            DialogUtil.showGPSNotEnabledDialog(activity);
        }
    }

    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        // Criteria criteria = new Criteria();
        // criteria.setAccuracy(Criteria.ACCURACY_FINE);//accuracy fine calls accuracy high


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    public String getCityNameFromLatLong() {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String cityName = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
            cityName = addresses.get(0).getLocality();
        } catch (Exception ex) {
            cityName = null;
        }
        return cityName;
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            if (!CommonUtil.checkIsEmptyOrNullCommon(mLastLocation)) {
                lastKnownLocation = mLastLocation;
                if (!CommonUtil.checkIsEmptyOrNullCommon(getCityNameFromLatLong())) {
                    Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                    try {
                        iGetUserCurrentLocation.fetchCurrentLocation(lastKnownLocation);
//                        iGetUserCurrentLocation.fetchCurrentLocation(getCityNameFromLatLong(),
//                                geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    public interface IGetUserCurrentLocation {
        void fetchCurrentLocation(Location location);
    }

}
