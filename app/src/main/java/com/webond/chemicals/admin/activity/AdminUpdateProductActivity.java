package com.webond.chemicals.admin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.webond.chemicals.pojo.GetProductDetailByIdPojo;
import com.webond.chemicals.pojo.UpdateProductPojo;
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

public class AdminUpdateProductActivity extends AppCompatActivity implements View.OnClickListener {

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
    private MaterialCardView cvUpdate;
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
    String productId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_product);
        initView();
        if (getIntent().hasExtra(IntentConstants.PRODUCT_ID)) {
            productId = getIntent().getStringExtra(IntentConstants.PRODUCT_ID);
            if (!CommonUtil.checkIsEmptyOrNullCommon(productId)) {
                getProductDetailsByProductId(productId);
            } else {
                Toast.makeText(this, "Product Id not found!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Product Id not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminUpdateProductActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Update Product");
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
        cvUpdate = findViewById(R.id.cvUpdate);
        cvUpdate.setOnClickListener(this);
    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtProductCode.getText().toString())) {
            Toast.makeText(this, "Please enter product code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductName.getText().toString())) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isPhotoUploaded1 && !isPhotoUploaded2 && !isPhotoUploaded3 && !isPhotoUploaded4 && !isPhotoUploaded5) {
            Toast.makeText(this, "Please upload at least one product photo", Toast.LENGTH_SHORT).show();
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
        } else if (v.getId() == R.id.cvUpdate) {
            if (isValid()) {

                String productCode = edtProductCode.getText().toString().trim();
                String productName = edtProductName.getText().toString().trim();
                String ProductPhoto1 = uploadPhoto1Base64;
                String ProductPhotoName1 = uploadPhoto1Name;
                String ProductPhoto2 = uploadPhoto2Base64;
                String ProductPhotoName2 = uploadPhoto2Name;
                String ProductPhoto3 = uploadPhoto3Base64;
                String ProductPhotoName3 = uploadPhoto3Name;
                String ProductPhoto4 = uploadPhoto4Base64;
                String ProductPhotoName4 = uploadPhoto4Name;
                String ProductPhoto5 = uploadPhoto5Base64;
                String ProductPhotoName5 = uploadPhoto5Name;
                String productPrice = edtProductPrice.getText().toString().trim();
                String productTotalPoints = edtProductTotalPoint.getText().toString().trim();
                String productDistPer = edtDistributorPercentage.getText().toString().trim();
                String productDealerPer = edtDealerPercentage.getText().toString().trim();
                String productCustomerPer = edtCustomerPercentage.getText().toString().trim();
                String productDescription = edtProductDescription.getText().toString().trim();
                updateProductApiCall(
                        productId,
                        productCode, productName, ProductPhoto1,
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
                    String fileUrl = FileUtils.getPath(AdminUpdateProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadPhoto1Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadPhoto1Name = uploadedAadharProof.getName();
                    edtUploadProductPhoto1.setText(uploadPhoto1Name);
                    isPhotoUploaded1 = true;
                }
            } catch (Exception ex) {
                isPhotoUploaded1 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_2 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminUpdateProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadPhoto2Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadPhoto2Name = uploadedAadharProof.getName();
                    edtUploadProductPhoto2.setText(uploadPhoto2Name);
                    isPhotoUploaded2 = true;
                }
            } catch (Exception ex) {
                isPhotoUploaded2 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_3 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminUpdateProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadPhoto3Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadPhoto3Name = uploadedAadharProof.getName();
                    edtUploadProductPhoto3.setText(uploadPhoto3Name);
                    isPhotoUploaded3 = true;
                }
            } catch (Exception ex) {
                isPhotoUploaded3 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_4 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminUpdateProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadPhoto4Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadPhoto4Name = uploadedAadharProof.getName();
                    edtUploadProductPhoto4.setText(uploadPhoto4Name);
                    isPhotoUploaded4 = true;
                }
            } catch (Exception ex) {
                isPhotoUploaded4 = false;
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_PHOTO_UPLOAD_5 && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminUpdateProductActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadPhoto5Base64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadPhoto5Name = uploadedAadharProof.getName();
                    edtUploadProductPhoto5.setText(uploadPhoto5Name);
                    isPhotoUploaded5 = true;
                }
            } catch (Exception ex) {
                isPhotoUploaded5 = false;
                ex.printStackTrace();
            }
        }
    }


    private void getProductDetailsByProductId(String productId) {
        DialogUtil.showProgressDialogNotCancelable(AdminUpdateProductActivity.this, "");
        ApiImplementer.getProductDetailsByIdApiImplementer(productId, new Callback<ArrayList<GetProductDetailByIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetProductDetailByIdPojo>> call, Response<ArrayList<GetProductDetailByIdPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        GetProductDetailByIdPojo getProductDetailByIdPojo = response.body().get(0);
                        setDate(getProductDetailByIdPojo);
                    } else {
                        Toast.makeText(AdminUpdateProductActivity.this, "Something went wrong,Please try agin later.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(AdminUpdateProductActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetProductDetailByIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(AdminUpdateProductActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDate(GetProductDetailByIdPojo getProductDetailByIdPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductCode())) {
            edtProductCode.setText(getProductDetailByIdPojo.getProductCode() + "");
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductName())) {
            edtProductName.setText(getProductDetailByIdPojo.getProductName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPhoto1())) {
            uploadPhoto1Base64 = getProductDetailByIdPojo.getProductPhoto1() + "";
            isPhotoUploaded1 = true;
            uploadPhoto1Name = "photo1.png";
            edtUploadProductPhoto1.setText(uploadPhoto1Name);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPhoto2())) {
            uploadPhoto2Base64 = getProductDetailByIdPojo.getProductPhoto2() + "";
            isPhotoUploaded2 = true;
            uploadPhoto2Name = "photo2.png";
            edtUploadProductPhoto2.setText(uploadPhoto2Name);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPhoto3())) {
            uploadPhoto3Base64 = getProductDetailByIdPojo.getProductPhoto3() + "";
            isPhotoUploaded3 = true;
            uploadPhoto3Name = "photo3.png";
            edtUploadProductPhoto3.setText(uploadPhoto3Name);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPhoto4())) {
            uploadPhoto4Base64 = getProductDetailByIdPojo.getProductPhoto4() + "";
            isPhotoUploaded4 = true;
            uploadPhoto4Name = "photo1.png";
            edtUploadProductPhoto4.setText(uploadPhoto4Name);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPhoto5())) {
            uploadPhoto5Base64 = getProductDetailByIdPojo.getProductPhoto5() + "";
            isPhotoUploaded5 = true;
            uploadPhoto5Name = "photo1.png";
            edtUploadProductPhoto5.setText(uploadPhoto5Name);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductPrice())) {
            edtProductPrice.setText(getProductDetailByIdPojo.getProductPrice() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductTotalPoint())) {
            edtProductTotalPoint.setText(getProductDetailByIdPojo.getProductTotalPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getDistPer())) {
            edtDistributorPercentage.setText(getProductDetailByIdPojo.getDistPer() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getDealerPer())) {
            edtDealerPercentage.setText(getProductDetailByIdPojo.getDealerPer() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getCustomerPer())) {
            edtCustomerPercentage.setText(getProductDetailByIdPojo.getCustomerPer() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductDetailByIdPojo.getProductDescription())) {
            edtProductDescription.setText(getProductDetailByIdPojo.getProductDescription() + "");
        }

    }


    private void updateProductApiCall(
            String ProductId,
            String ProductCode,
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
        DialogUtil.showProgressDialogNotCancelable(AdminUpdateProductActivity.this, "");
        ApiImplementer.updateProductImplementer(
                ProductId,
                ProductCode,
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
                ProductDescription, new Callback<ArrayList<UpdateProductPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UpdateProductPojo>> call, Response<ArrayList<UpdateProductPojo>> response) {
                        DialogUtil.hideProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(AdminUpdateProductActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminUpdateProductActivity.this, AdminProductListActivity.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AdminUpdateProductActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminUpdateProductActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(AdminUpdateProductActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UpdateProductPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(AdminUpdateProductActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

