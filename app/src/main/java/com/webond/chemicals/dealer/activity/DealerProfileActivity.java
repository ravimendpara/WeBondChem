package com.webond.chemicals.dealer.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class DealerProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private AppCompatImageView imgLogout;
    private TextViewMediumFont tvHeaderTitle;
    private LinearLayout llProfileDetails;
    private CircleImageView imgProfile;
    private TextViewRegularFont tvName;
    private TextViewRegularFont tvEmail;
    private TextViewMediumFont tvPhone;
    private TextViewMediumFont tvState;
    private TextViewMediumFont tvDistrict;
    private TextViewMediumFont tvTaluka;
    private TextViewMediumFont tvCity;
    private TextViewMediumFont tvDistributorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_profile);
        initView();
        setData();
    }

    public void initView() {
        mySharedPreferences = new MySharedPreferences(DealerProfileActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        imgLogout = findViewById(R.id.imgLogout);
        imgLogout.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Profile");
        llProfileDetails = findViewById(R.id.llProfileDetails);
        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvState = findViewById(R.id.tvState);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvTaluka = findViewById(R.id.tvTaluka);
        tvCity = findViewById(R.id.tvCity);
        tvDistributorName = findViewById(R.id.tvDistributorName);
    }

    private void setData() {
        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerPhotoPath())) {
            Glide.with(DealerProfileActivity.this)
                    .load(mySharedPreferences.getCustomerPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(imgProfile);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerName())) {
            tvName.setText(mySharedPreferences.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerEmail())) {
            tvEmail.setText(mySharedPreferences.getDealerEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerMobileNo())) {
            tvPhone.setText("+91 " + mySharedPreferences.getDealerMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerStateName())) {
            tvState.setText(mySharedPreferences.getDealerStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerDistrictName())) {
            tvDistrict.setText(mySharedPreferences.getDealerDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerTalukaName())) {
            tvTaluka.setText(mySharedPreferences.getDealerTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerCityName())) {
            tvCity.setText(mySharedPreferences.getDealerCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDealerDistributorName())) {
            tvDistributorName.setText(mySharedPreferences.getDealerDistributorName() + "");
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.imgLogout) {
            new MaterialAlertDialogBuilder(DealerProfileActivity.this)
                    .setTitle(Html.fromHtml("<b>" + "Logout" + " </b>"))
                    .setMessage("Do you wan to logout from this app ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySharedPreferences.logOutUser();
                            Intent intent = new Intent(DealerProfileActivity.this, DealerDashboardActivity.class);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}