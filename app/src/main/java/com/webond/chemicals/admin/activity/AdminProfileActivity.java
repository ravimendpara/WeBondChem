package com.webond.chemicals.admin.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private AppCompatImageView imgLogout;
    private TextViewMediumFont tvHeaderTitle;
    private LinearLayout llProfileDetails;
    private CircleImageView imgProfile;
    private TextViewRegularFont tvName;
    private TextViewRegularFont tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        initView();
        setData();
    }

    public void initView() {
        mySharedPreferences = new MySharedPreferences(AdminProfileActivity.this);
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
    }

    private void setData() {
//        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdmi())) {
//            Glide.with(AdminProfileActivity.this)
//                    .load(mySharedPreferences.getDistributorPhotoPath())
//                    .centerCrop()
//                    .placeholder(R.drawable.person_img)
//                    .into(imgProfile);
//        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdminCompanyName())) {
            tvName.setText(mySharedPreferences.getAdminCompanyName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdminCompanyEmail())) {
            tvEmail.setText(mySharedPreferences.getAdminCompanyEmail() + "");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.imgLogout) {
            new MaterialAlertDialogBuilder(AdminProfileActivity.this)
                    .setTitle(Html.fromHtml("<b>" + "Logout" + " </b>"))
                    .setMessage("Do you wan to logout from this app ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySharedPreferences.logOutUser();
                            Intent intent = new Intent(AdminProfileActivity.this, AdminDashboardActivity.class);
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