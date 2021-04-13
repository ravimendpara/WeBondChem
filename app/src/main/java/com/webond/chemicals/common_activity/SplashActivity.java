package com.webond.chemicals.common_activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetVersionInfoPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    private final String[] RunTimePerMissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;
    private AppCompatImageView imgLogo;
    private Dialog updateDialog;

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
                            getVersionInfoApiCAll();
                        }
                    } else {
                        getVersionInfoApiCAll();
                    }
                }
            }
        };
        timer.start();
    }

    private void redirectToLoginActivity() {
        Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(openMainActivity);
        finish();
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {

            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                getVersionInfoApiCAll();
            } else {
                alertAlert(getResources().getString(R.string.permissions_has_not_grant));
            }
        }
    }

    private void getVersionInfoApiCAll() {
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = pinfo.versionCode;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    DialogUtil.showProgressDialogNotCancelable(SplashActivity.this, "");
//                }
//            });
            ApiImplementer.getVersionInfoApiImplementer(versionCode + "", new Callback<ArrayList<GetVersionInfoPojo>>() {
                @Override
                public void onResponse(Call<ArrayList<GetVersionInfoPojo>> call, Response<ArrayList<GetVersionInfoPojo>> response) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogUtil.hideProgressDialog();
//                        }
//                    });
                    try {
                        if (response.code() == 200 &&
                                response.body() != null) {
                            if (Integer.parseInt(response.body().get(0).getAppVersionCode()) > versionCode) {
                                if (response.body().get(0).getAppVersionType() == 0) {//optional update
                                    updateDialog(false);
                                } else {//forcefully update
                                    updateDialog(true);
                                }
                            } else {
                                //No Need to update app already up to date
                                redirectToLoginActivity();
                            }
                        } else {
                            redirectToLoginActivity();
                        }
                    } catch (Throwable ex) {
                        redirectToLoginActivity();
//                        Toast.makeText(SplashActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<GetVersionInfoPojo>> call, Throwable t) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogUtil.hideProgressDialog();
//                        }
//                    });
                    redirectToLoginActivity();
                }
            });
        } catch (Throwable e) {
            redirectToLoginActivity();
//            e.printStackTrace();
//            finish();
        }
    }

    private void updateDialog(boolean isForceUpdate) {
        updateDialog = new Dialog(SplashActivity.this);
        updateDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_shape_for_custom_dialog);//if need to change dialog radius in custom_layout_for_progress_dialog

        updateDialog.setCancelable(false);
        View customProgressDialog = LayoutInflater.from(SplashActivity.this).inflate(R.layout.custom_layout_for_update_app_dialog, null);
        TextViewMediumFont tvNoThanks = customProgressDialog.findViewById(R.id.tvNoThanks);
        MaterialButton btnUpdate = customProgressDialog.findViewById(R.id.btnUpdate);
        if (isForceUpdate) {
            tvNoThanks.setVisibility(View.GONE);
        } else {
            tvNoThanks.setVisibility(View.VISIBLE);
        }
        tvNoThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                redirectToLoginActivity();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    finish();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });
        updateDialog.setContentView(customProgressDialog);
        updateDialog.show();
    }

}