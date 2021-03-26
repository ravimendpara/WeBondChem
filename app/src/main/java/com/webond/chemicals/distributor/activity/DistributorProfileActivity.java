package com.webond.chemicals.distributor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class DistributorProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_profile);
        initView();
        setData();
    }
    public void initView() {
        mySharedPreferences = new MySharedPreferences(DistributorProfileActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
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
    }

    private void setData() {
        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorPhotoPath())) {
            Glide.with(DistributorProfileActivity.this)
                    .load(mySharedPreferences.getDistributorPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(imgProfile);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorName())) {
            tvName.setText(mySharedPreferences.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorEmail())) {
            tvEmail.setText(mySharedPreferences.getDistributorEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorMobileNo())) {
            tvPhone.setText("+91 " + mySharedPreferences.getDistributorMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorStateName())) {
            tvState.setText(mySharedPreferences.getDistributorStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorDistrictName())) {
            tvDistrict.setText(mySharedPreferences.getDistributorDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorTalukaName())) {
            tvTaluka.setText(mySharedPreferences.getDistributorTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorCityName())) {
            tvCity.setText(mySharedPreferences.getDistributorCityName() + "");
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}