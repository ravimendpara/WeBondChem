package com.webond.chemicals.admin.activity;

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
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.FileUtils;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

public class AdminAddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtProductCode;
    private TextInputEditText edtProductName;
    private TextInputEditText edtUploadProductPhoto;
    private TextInputEditText edtProductPrice;
    private TextInputEditText edtProductTotalPoint;
    private TextInputEditText edtDistributorPercentage;
    private TextInputEditText edtDealerPercentage;
    private TextInputEditText edtCustomerPercentage;
    private TextInputEditText edtProductDescription;
    private MaterialCardView cvSubmit;
    private boolean isFileUploaded = false;

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
        edtUploadProductPhoto = findViewById(R.id.edtUploadProductPhoto);
        edtUploadProductPhoto.setOnClickListener(this);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductTotalPoint = findViewById(R.id.edtProductTotalPoint);
        edtDistributorPercentage = findViewById(R.id.edtDistributorPercentage);
        edtDealerPercentage = findViewById(R.id.edtDealerPercentage);
        edtCustomerPercentage = findViewById(R.id.edtCustomerPercentage);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        cvSubmit = findViewById(R.id.cvSubmit);
        cvSubmit.setOnClickListener(this);
    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtProductCode.getText().toString())) {
            Toast.makeText(this, "Please enter product code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtProductName.getText().toString())) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isFileUploaded) {
            Toast.makeText(this, "Please upload product photo", Toast.LENGTH_SHORT).show();
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

            }
        } else if (v.getId() == R.id.edtUploadProductPhoto) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_UPLOAD_PHOTO);
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

        if (requestCode == IntentConstants.REQUEST_CODE_FOR_UPLOAD_PHOTO && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(AdminAddProductActivity.this, data.getData());

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}