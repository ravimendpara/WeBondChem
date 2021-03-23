package com.webond.chemicals.common_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.webond.chemicals.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SplashActivity extends AppCompatActivity {


    private final String[] RunTimePerMissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;
    private AppCompatImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        loadSplashScreenAnimationAndAskForPermission();
    }

    private void initView() {
        imgLogo = findViewById(R.id.imgLogo);
    }

    private void loadSplashScreenAnimationAndAskForPermission() {
        Animation slide_up = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.slide_up);
        imgLogo.startAnimation(slide_up);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!hasPermissions(SplashActivity.this, RunTimePerMissions)) {
                            ActivityCompat.requestPermissions(SplashActivity.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
                        } else {
                            redirectToLoginActivity();
                        }
                    } else {
                        redirectToLoginActivity();
                    }
                }
            }
        };
        timer.start();
    }

    private void redirectToLoginActivity() {
//        Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
//        startActivity(openMainActivity);
//        finish();
    }


    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void alertAlert(String msg) {
        new MaterialAlertDialogBuilder(SplashActivity.this)
                .setTitle(Html.fromHtml("<b>" + getResources().getString(R.string.permission_request) + " </b>"))
                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {

            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                redirectToLoginActivity();
            } else {
                alertAlert(getResources().getString(R.string.permissions_has_not_grant));
            }
        }
    }
}