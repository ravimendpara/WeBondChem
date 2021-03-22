package com.demoapp.demoapp.services;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.demoapp.demoapp.R;
import com.demoapp.demoapp.utils.CommonUtil;
import com.demoapp.demoapp.utils.MySharedPreferences;

import java.security.spec.ECField;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class BackgroundLocationServices extends Service {
public class BackgroundLocationServices {

//    private static final String CHANNEL_ID = "my_channel";
//    private static final String EXTRA_STARTED_FROM_NOTIFICATION = "com.employeecontrol.controle.services" +
//            ".started_from_notification";
//    private final IBinder mBinder = new LocalBinder();
//    private static final long UPDATE_INTERVAL_IN_MIL = 10000;
//    private static final long FASTEST_UPDATE_INTERVAL_IN_MIN = UPDATE_INTERVAL_IN_MIL / 2;
//    private static final int NOTI_ID = 1023;
//    private boolean mChangingConfiguration = false;
//    private NotificationManager notificationManger;
//
//    private LocationRequest locationRequest;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationCallback locationCallback;
//    private Handler mServiceHandler;
//    private Location mLocation;
//    private MySharedPreferences mySharedPreferences;
//
//    public BackgroundLocationServices() {
//        mySharedPreferences = new MySharedPreferences(getApplicationContext());
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        boolean startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false);
//        if (startedFromNotification) {
//            removeLocationUpdate();
//            stopSelf();
//        }
//        return START_NOT_STICKY;
//    }
//
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mChangingConfiguration = true;
//    }
//
//    public void removeLocationUpdate() {
//        try {
//            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//            CommonUtil.setRequestLocationUpdate(this, false);
//            stopSelf();
//        } catch (Exception ex) {
//            CommonUtil.setRequestLocationUpdate(this, true);
//        }
//    }
//
//    @Override
//    public void onCreate() {
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                onNewLocation(locationResult.getLastLocation());
//            }
//        };
//        createLocationRequest();
//        getLastLocation();
//
//        HandlerThread handlerThread = new HandlerThread("Control-E");
//        handlerThread.start();
//        mServiceHandler = new Handler(handlerThread.getLooper());
//        notificationManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
//                    getString(R.string.app_name),
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManger.createNotificationChannel(notificationChannel);
//        }
//
//    }
//
//    private void getLastLocation() {
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        mLocation = task.getResult();
//                    } else {
//                        Log.d("TAGGGG", "Failed to get Location");
//                    }
//                }
//            });
//        } catch (Exception ex) {
//
//        }
//    }
//
//    private void createLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(UPDATE_INTERVAL_IN_MIL);
//        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MIN);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//    }
//
//    private void onNewLocation(Location lastLocation) {
//        mLocation = lastLocation;
////        EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));
//
//        if (lastLocation != null) {
//            sendLocationToServer(lastLocation.getLatitude(), lastLocation.getLongitude());
//            if (!CommonUtil.checkIsCurrentLocationIsWithinGivenLocationByLatLong(lastLocation.getLatitude(),
//                    lastLocation.getLongitude(), Double.parseDouble(mySharedPreferences.getGivenLat()), Double.parseDouble(mySharedPreferences.getGivenLong()),
//                    Integer.parseInt(mySharedPreferences.getGivenRadius()))) {
//                endShiftIfGuardIsOutOfTheZone();
//            }
//        }
//
//        if (serviceIsRunningInForeground(this)) {
//            notificationManger.notify(NOTI_ID, getNotification());
//        }
//
//    }
//
//    private boolean serviceIsRunningInForeground(Context context) {
//
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
//            if (getClass().getName().equals(service.service.getClassName()))
//                if (service.foreground)
//                    return true;
//        return false;
//    }
//
//    private Notification getNotification() {
////        Intent intent = new Intent(this, BackgroundLocationServices.class);
////        String text = CommonUtil.getLocationText(mLocation);
////        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
////
////        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
////                PendingIntent.FLAG_UPDATE_CURRENT);
////        PendingIntent activityPEndingIntent = PendingIntent.getActivity(this, 0, new Intent(this, StaffDashboardActivity.class), 0);
//
//        String text;
//
//        if (!CommonUtil.checkIsCurrentLocationIsWithinGivenLocationByLatLong(mLocation.getLatitude(),
//                mLocation.getLongitude(), Double.parseDouble(mySharedPreferences.getGivenLat()), Double.parseDouble(mySharedPreferences.getGivenLong()),
//                Integer.parseInt(mySharedPreferences.getGivenRadius()))) {
//            text = "Currently you are out of the zone so,your shift has been ended";
//        } else {
//            text = CommonUtil.getLocationText(mLocation);
//        }
//
//
//        Intent intent = new Intent(getApplicationContext(), StaffShiftActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContentText(text)
//                .setContentTitle(CommonUtil.getLocationTitle(this))
//                .setOngoing(true)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker(text)
//                .setContentIntent(contentIntent)
//                .setWhen(System.currentTimeMillis());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder.setChannelId(CHANNEL_ID);
//        }
//        return builder.build();
//
//    }
//
//    public void requestLocationUpdate() {
//        CommonUtil.setRequestLocationUpdate(this, true);
//        startService(new Intent(getApplicationContext(), BackgroundLocationServices.class));
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                    locationCallback, Looper.myLooper());
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public class LocalBinder extends Binder {
//        public BackgroundLocationServices getSerVices() {
//            return BackgroundLocationServices.this;
//        }
//    }
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        stopForeground(true);
//        mChangingConfiguration = false;
//        return mBinder;
//    }
//
//    @Override
//    public void onRebind(Intent intent) {
//        stopForeground(true);
//        mChangingConfiguration = false;
//        super.onRebind(intent);
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        if (!mChangingConfiguration && CommonUtil.requestLocationUpdate(this)) {
//            startForeground(NOTI_ID, getNotification());
//        }
//        return true;
//    }
//
//    @Override
//    public void onDestroy() {
//        mServiceHandler.removeCallbacks(null);
//        super.onDestroy();
//    }
//
//    private void endShiftIfGuardIsOutOfTheZone() {
//        if (mySharedPreferences.getIsShiftStarted()) {
//            if (!mySharedPreferences.getIsBreakStarted()) {
//                endShiftApiCall();
//            } else {
//                Toast.makeText(this, "your shift can't end before break ended!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "your shift not started yet!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void endShiftApiCall() {
//        ApiImplementer.endShiftApiImplementer(mySharedPreferences.getOngoingEventId(), mySharedPreferences.getUserId(), new Callback<EndShiftPojo>() {
//            @Override
//            public void onResponse(Call<EndShiftPojo> call, Response<EndShiftPojo> response) {
//                try {
//                    if (response.code() == 200 && response.body() != null && response.body().getData() != null) {
//                        if (response.body().getSuccess() == 1) {
//                            mySharedPreferences.setShift(false, true,
//                                    false, false, true);
//                            removeLocationUpdate();
//                        }
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EndShiftPojo> call, Throwable t) {
//            }
//        });
//    }
//
//    private void sendLocationToServer(double latitude, double longitude) {
//        ApiImplementer.sendUserGeoLocationApiImplementer(mySharedPreferences.getUserId(),
//                mySharedPreferences.getOngoingEventId(), latitude, longitude, new Callback<SendUserGeoLocationPojo>() {
//                    @Override
//                    public void onResponse(Call<SendUserGeoLocationPojo> call, Response<SendUserGeoLocationPojo> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<SendUserGeoLocationPojo> call, Throwable t) {
//
//                    }
//                });
//    }

}
