package com.webond.chemicals.admin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AddBannerPojo;
import com.webond.chemicals.pojo.AddProductPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.FileUtils;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddBannerActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtProductCode;
    private TextInputEditText edtUploadBannerPhoto1;
    private MaterialCardView cvSubmit;
    private boolean isPhotoUploaded1 = false;

    private String uploadPhoto1Base64 = "";
    private String uploadPhoto1Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_banner);
        initView();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminAddBannerActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Add Banner");
        edtProductCode = findViewById(R.id.edtBannerTitle);
        edtUploadBannerPhoto1 = findViewById(R.id.edtUploadBannerPhoto1);
        edtUploadBannerPhoto1.setOnClickListener(this);
        cvSubmit = findViewById(R.id.cvSubmit);
        cvSubmit.setOnClickListener(this);
    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtProductCode.getText().toString())) {
            Toast.makeText(this, "Please enter Banner Title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isPhotoUploaded1) {
            Toast.makeText(this, "Please upload banner photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.cvSubmit) {
            if (isValid()) {

                String productCode = edtProductCode.getText().toString().trim();

                String ProductPhoto1 = uploadPhoto1Base64;
                String ProductPhotoName1 = uploadPhoto1Name;
                addBannerApiCall(productCode, ProductPhoto1,
                        ProductPhotoName1);
            }
        } else if (v.getId() == R.id.edtUploadBannerPhoto1) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_1);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_1 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddBannerActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto1Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto1Name = uploadedAadharProof.getName();
                        edtUploadBannerPhoto1.setText(uploadPhoto1Name);
                        isPhotoUploaded1 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded1 = false;
                ex.printStackTrace();
            }
        }
    }

    private void addBannerApiCall(String BannerTitle,
                                   String ProductPhoto1,
                                   String ProductPhotoName1
                                   ) {
        DialogUtil.showProgressDialogNotCancelable(AdminAddBannerActivity.this, "");
        ApiImplementer.addBannerImplementer(BannerTitle,
                ProductPhoto1,
                ProductPhotoName1,
                new Callback<ArrayList<AddBannerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddBannerPojo>> call, Response<ArrayList<AddBannerPojo>> response) {
                        DialogUtil.hideProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(AdminAddBannerActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminAddBannerActivity.this, AdminProductListActivity.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AdminAddBannerActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminAddBannerActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(AdminAddBannerActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddBannerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(AdminAddBannerActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}