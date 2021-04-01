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

public class AdminAddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtProductCode;
    private TextInputEditText edtProductName;
    private TextInputEditText edtUploadProductPhoto1;
    private TextInputEditText edtUploadProductPhoto2;
    private TextInputEditText edtUploadProductPhoto3;
    private TextInputEditText edtUploadProductPhoto4;
    private TextInputEditText edtUploadProductPhoto5;
    private TextInputEditText edtProductPrice;
    private TextInputEditText edtProductTotalPoint;
    private TextInputEditText edtDistributorPercentage;
    private TextInputEditText edtDealerPercentage;
    private TextInputEditText edtCustomerPercentage;
    private TextInputEditText edtProductDescription;
    private MaterialCardView cvSubmit;
    private boolean isPhotoUploaded1 = false;
    private boolean isPhotoUploaded2 = false;
    private boolean isPhotoUploaded3 = false;
    private boolean isPhotoUploaded4 = false;
    private boolean isPhotoUploaded5 = false;

    private String uploadPhoto1Base64 = "";
    private String uploadPhoto1Name = "";
    private String uploadPhoto2Base64 = "";
    private String uploadPhoto2Name = "";
    private String uploadPhoto3Base64 = "";
    private String uploadPhoto3Name = "";
    private String uploadPhoto4Base64 = "";
    private String uploadPhoto4Name = "";
    private String uploadPhoto5Base64 = "";
    private String uploadPhoto5Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);
        initView();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminAddProductActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Add Product");
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductName = findViewById(R.id.edtProductName);
        edtUploadProductPhoto1 = findViewById(R.id.edtUploadProductPhoto1);
        edtUploadProductPhoto1.setOnClickListener(this);
        edtUploadProductPhoto2 = findViewById(R.id.edtUploadProductPhoto2);
        edtUploadProductPhoto2.setOnClickListener(this);
        edtUploadProductPhoto3 = findViewById(R.id.edtUploadProductPhoto3);
        edtUploadProductPhoto3.setOnClickListener(this);
        edtUploadProductPhoto4 = findViewById(R.id.edtUploadProductPhoto4);
        edtUploadProductPhoto4.setOnClickListener(this);
        edtUploadProductPhoto5 = findViewById(R.id.edtUploadProductPhoto5);
        edtUploadProductPhoto5.setOnClickListener(this);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductTotalPoint = findViewById(R.id.edtProductTotalPoint);
        edtDistributorPercentage = findViewById(R.id.edtDistributorPercentage);
        edtDealerPercentage = findViewById(R.id.edtDealerPercentage);
        edtCustomerPercentage = findViewById(R.id.edtCustomerPercentage);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        cvSubmit = findViewById(R.id.cvSubmit);
        cvSubmit.setOnClickListener(this);


        edtDistributorPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!CommonUtil.checkIsEmptyOrNullCommon(s.toString().trim())) {
                        if (Double.parseDouble(s.toString().trim()) > 100) {
                            edtDistributorPercentage.setText("100");
                            Toast.makeText(AdminAddProductActivity.this, "You can't enter more than 100%", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        edtDealerPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!CommonUtil.checkIsEmptyOrNullCommon(s.toString().trim())) {
                        if (Double.parseDouble(s.toString().trim()) > 100) {
                            edtDealerPercentage.setText("100");
                            Toast.makeText(AdminAddProductActivity.this, "You can't enter more than 100%", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        edtCustomerPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!CommonUtil.checkIsEmptyOrNullCommon(s.toString().trim())) {
                        if (Double.parseDouble(s.toString().trim()) > 100) {
                            edtCustomerPercentage.setText("100");
                            Toast.makeText(AdminAddProductActivity.this, "You can't enter more than 100%", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtProductCode.getText().toString())) {
            Toast.makeText(this, "Please enter product code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductName.getText().toString())) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isPhotoUploaded1) {
            Toast.makeText(this, "Please upload product photo1", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductPrice.getText().toString())) {
            Toast.makeText(this, "Please enter product price", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductTotalPoint.getText().toString())) {
            Toast.makeText(this, "Please enter product total point", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtDistributorPercentage.getText().toString())) {
            Toast.makeText(this, "Please enter distributor percentage", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtDealerPercentage.getText().toString())) {
            Toast.makeText(this, "Please enter dealer percentage", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtCustomerPercentage.getText().toString())) {
            Toast.makeText(this, "Please enter customer percentage", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductDescription.getText().toString())) {
            Toast.makeText(this, "Please enter product description", Toast.LENGTH_SHORT).show();
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
                String productName = edtProductName.getText().toString().trim();
                String ProductPhoto1 = uploadPhoto1Base64;
                String ProductPhotoName1 = uploadPhoto1Name;
                String ProductPhoto2 = "";
                String ProductPhotoName2 = "";
                String ProductPhoto3 = "";
                String ProductPhotoName3 = "";
                String ProductPhoto4 = "";
                String ProductPhotoName4 = "";
                String ProductPhoto5 = "";
                String ProductPhotoName5 = "";
                String productPrice = edtProductPrice.getText().toString().trim();
                String productTotalPoints = edtProductTotalPoint.getText().toString().trim();
                String productDistPer = edtDistributorPercentage.getText().toString().trim();
                String productDealerPer = edtDealerPercentage.getText().toString().trim();
                String productCustomerPer = edtCustomerPercentage.getText().toString().trim();
                String productDescription = edtProductDescription.getText().toString().trim();
                addProductApiCall(productCode, productName, ProductPhoto1,
                        ProductPhotoName1,
                        ProductPhoto2,
                        ProductPhotoName2,
                        ProductPhoto3,
                        ProductPhotoName3,
                        ProductPhoto4,
                        ProductPhotoName4,
                        ProductPhoto5,
                        ProductPhotoName5, productPrice, productTotalPoints, productDistPer, productDealerPer, productCustomerPer, productDescription);
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
        } else if (v.getId() == R.id.edtUploadProductPhoto2) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_2);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else if (v.getId() == R.id.edtUploadProductPhoto3) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_3);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else if (v.getId() == R.id.edtUploadProductPhoto4) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_4);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else if (v.getId() == R.id.edtUploadProductPhoto5) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_5);
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
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());
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
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_2 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto2Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto2Name = uploadedAadharProof.getName();
                        edtUploadProductPhoto2.setText(uploadPhoto2Name);
                        isPhotoUploaded2 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded2 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_3 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto3Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto3Name = uploadedAadharProof.getName();
                        edtUploadProductPhoto3.setText(uploadPhoto3Name);
                        isPhotoUploaded3 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded3 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_4 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto4Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto4Name = uploadedAadharProof.getName();
                        edtUploadProductPhoto4.setText(uploadPhoto4Name);
                        isPhotoUploaded4 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded4 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_5 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    if (uploadedAadharProof.length() > 500000) { //500000bytes == 500KB
                        Toast.makeText(this, "File length must be less than 500KB", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadPhoto5Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                        uploadPhoto5Name = uploadedAadharProof.getName();
                        edtUploadProductPhoto5.setText(uploadPhoto5Name);
                        isPhotoUploaded5 = true;
                    }
                }
            } catch (Exception ex) {
                isPhotoUploaded5 = false;
                ex.printStackTrace();
            }
        }
    }

    private void addProductApiCall(String ProductCode,
                                   String ProductName,
                                   String ProductPhoto1,
                                   String ProductPhotoName1,
                                   String ProductPhoto2,
                                   String ProductPhotoName2,
                                   String ProductPhoto3,
                                   String ProductPhotoName3,
                                   String ProductPhoto4,
                                   String ProductPhotoName4,
                                   String ProductPhoto5,
                                   String ProductPhotoName5,
                                   String ProductPrice,
                                   String ProductTotalPoint,
                                   String DistPer,
                                   String DealerPer,
                                   String CustomerPer,
                                   String ProductDescription) {
        DialogUtil.showProgressDialogNotCancelable(AdminAddProductActivity.this, "");
        ApiImplementer.addProductImplementer(ProductCode,
                ProductName,
                ProductPhoto1,
                ProductPhotoName1,
                ProductPhoto2,
                ProductPhotoName2,
                ProductPhoto3,
                ProductPhotoName3,
                ProductPhoto4,
                ProductPhotoName4,
                ProductPhoto5,
                ProductPhotoName5,
                ProductPrice,
                ProductTotalPoint,
                DistPer,
                DealerPer,
                CustomerPer,
                ProductDescription, new Callback<ArrayList<AddProductPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddProductPojo>> call, Response<ArrayList<AddProductPojo>> response) {
                        DialogUtil.hideProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(AdminAddProductActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminAddProductActivity.this, AdminProductListActivity.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AdminAddProductActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminAddProductActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(AdminAddProductActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddProductPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(AdminAddProductActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}