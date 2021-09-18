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
import com.webond.chemicals.pojo.GetBannerDetailByIdPojo;
import com.webond.chemicals.pojo.UpdateBannerPojo;
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

public class AdminUpdateBannerActivity  extends AppCompatActivity implements View.OnClickListener  {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtBannerTitle;
    private TextInputEditText edtUploadProductPhoto1;
    private MaterialCardView cvUpdate;
    private boolean isPhotoUploaded1 = false;

    private String uploadPhoto1Base64 = "";
    private String uploadPhoto1Name = "";

    String bannerid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_banner);
        initView();
        if (getIntent().hasExtra(IntentConstants.BANNERID)) {
            bannerid = getIntent().getStringExtra(IntentConstants.BANNERID);
            if (!CommonUtil.checkIsEmptyOrNullCommon(bannerid)) {
                getBannerDetailsByProductId(bannerid);
            } else {
                Toast.makeText(this, "Banner Id not found!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Banner Id not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminUpdateBannerActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Update Banner");
        edtBannerTitle = findViewById(R.id.edtBannerTitle);
        edtUploadProductPhoto1 = findViewById(R.id.edtUploadProductPhoto1);
        edtUploadProductPhoto1.setOnClickListener(this);
        cvUpdate = findViewById(R.id.cvUpdate);
        cvUpdate.setOnClickListener(this);
    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtBannerTitle.getText().toString())) {
            Toast.makeText(this, "Please enter banner title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isPhotoUploaded1) {
            Toast.makeText(this, "Please upload product photo1", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.cvUpdate) {
            if (isValid()) {

                String productCode = edtBannerTitle.getText().toString().trim();
                String ProductPhoto1 = uploadPhoto1Base64;
                String ProductPhotoName1 = uploadPhoto1Name;

                updateBannerApiCall(
                        bannerid,
                        productCode, ProductPhoto1,
                        ProductPhotoName1);
            }
        } else if (v.getId() == R.id.edtUploadProductPhoto1) {
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
                    String fileUrl = FileUtils.getPath(AdminUpdateBannerActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto1Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto1Name = uploadedAadharProof.getName();
                        edtUploadProductPhoto1.setText(uploadPhoto1Name);
                        isPhotoUploaded1 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded1 = false;
                ex.printStackTrace();
            }
        }
    }


    private void getBannerDetailsByProductId(String bannerid) {
        DialogUtil.showProgressDialogNotCancelable(AdminUpdateBannerActivity.this, "");
        ApiImplementer.getBannerDetailsByIdApiImplementer(bannerid, new Callback<ArrayList<GetBannerDetailByIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetBannerDetailByIdPojo>> call, Response<ArrayList<GetBannerDetailByIdPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        GetBannerDetailByIdPojo getBannerDetailByIdPojo = response.body().get(0);
                        setDate(getBannerDetailByIdPojo);
                    } else {
                        Toast.makeText(AdminUpdateBannerActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(AdminUpdateBannerActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetBannerDetailByIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(AdminUpdateBannerActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDate(GetBannerDetailByIdPojo getProductDetailByIdPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getBannerName())) {
            edtBannerTitle.setText(getProductDetailByIdPojo.getBannerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getBannerPath())) {
            //uploadPhoto1Base64 = getProductDetailByIdPojo.getBannerPath() + "";
            isPhotoUploaded1 = true;
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getBannerUrl())) {
            uploadPhoto1Name = getProductDetailByIdPojo.getBannerUrl();
            edtUploadProductPhoto1.setText(uploadPhoto1Name);
        }
    }


    private void updateBannerApiCall(
            String BannerId,
            String BannerTitle,
            String ProductPhoto1,
            String ProductPhotoName1) {
        DialogUtil.showProgressDialogNotCancelable(AdminUpdateBannerActivity.this, "");
        ApiImplementer.updateBannerImplementer(
                BannerId,
                BannerTitle,
                ProductPhoto1,
                ProductPhotoName1, new Callback<ArrayList<UpdateBannerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UpdateBannerPojo>> call, Response<ArrayList<UpdateBannerPojo>> response) {
                        DialogUtil.hideProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(AdminUpdateBannerActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminUpdateBannerActivity.this, AdminManageBannerActivity.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AdminUpdateBannerActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminUpdateBannerActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(AdminUpdateBannerActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UpdateBannerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(AdminUpdateBannerActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}