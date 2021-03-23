package com.webond.chemicals.common_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.BottomSheetDialogForVerifyOTP;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.pojo.CheckMobileNoExitstOrNoPojo;
import com.webond.chemicals.pojo.SendOtpPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetDialogForVerifyOTP.IOnOTPReceived {

    private static final String SELECT_USER_TYPE = "Select User Type";
    private ArrayList<String> selectUserTypeArrayList = new ArrayList<>();

    private Spinner spUserType;
    private SpinnerSimpleAdapter spinnerAdapterUserType;
    private AppCompatEditText edtMobileNo;
    private MaterialCardView cvLogin;
    private BottomSheetDialogForVerifyOTP bottomSheetDialogForVerifyOTP;
    private String randomSixDigitOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        initView();
    }

    private void initView() {
        spUserType = findViewById(R.id.spUserType);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        cvLogin = findViewById(R.id.cvLogin);
        cvLogin.setOnClickListener(this);
        selectUserTypeArrayList.add(SELECT_USER_TYPE);
        selectUserTypeArrayList.add("Admin");
        selectUserTypeArrayList.add("Distributor");
        selectUserTypeArrayList.add("Dealer");
        selectUserTypeArrayList.add("Customer");
        spinnerAdapterUserType = new SpinnerSimpleAdapter(LoginActivity.this, selectUserTypeArrayList);
        spUserType.setAdapter(spinnerAdapterUserType);


        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isValid() {
        if (spUserType.getSelectedItemPosition() == -1 || spUserType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtMobileNo.getText().toString().trim())) {
            Toast.makeText(this, "Please enter mobile no.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMobileNo.getText().toString().trim().length() != 10) {
            Toast.makeText(this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvLogin) {
            if (isValid()) {
                randomSixDigitOTP = CommonUtil.getRandomSixDigitOTP();
                checkIsMobileNoIsExistOrNot(true, false, edtMobileNo.getText().toString().trim());
            }
        }
    }

    @Override
    public void onOTPSubmit(String enteredOTP) {
        if (enteredOTP.equalsIgnoreCase(randomSixDigitOTP)) {
            bottomSheetDialogForVerifyOTP.dismiss();
            Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIsMobileNoIsExistOrNot(boolean isPdShow, boolean isPdHide, String mobileNo) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "");
        }
        ApiImplementer.checkMobileNoExistOrNotImplementer(mobileNo, new Callback<ArrayList<CheckMobileNoExitstOrNoPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckMobileNoExitstOrNoPojo>> call, Response<ArrayList<CheckMobileNoExitstOrNoPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null &&
                            response.body().size() > 0) {
                        if (response.body().get(0).getStatus() == 1) {
                            sendOTPApiCall(false, true, edtMobileNo.getText().toString().trim(), randomSixDigitOTP);
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            Toast.makeText(LoginActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CheckMobileNoExitstOrNoPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOTPApiCall(boolean isPdShow, boolean isPdHide, String mobileNo, String otp) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "Please wait...");
        }
        ApiImplementer.sendOtpApiImplementer(mobileNo, otp, new Callback<ArrayList<SendOtpPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<SendOtpPojo>> call, Response<ArrayList<SendOtpPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        SendOtpPojo sendOtpPojos = response.body().get(0);
                        if (sendOtpPojos.getStatus() == 1) {
                            bottomSheetDialogForVerifyOTP = new BottomSheetDialogForVerifyOTP(LoginActivity.this,
                                    edtMobileNo.getText().toString());
                            if (!bottomSheetDialogForVerifyOTP.isAdded()) {
                                bottomSheetDialogForVerifyOTP.setCancelable(false);
                                bottomSheetDialogForVerifyOTP.show(getSupportFragmentManager(), "verify_otp");
                            }
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            Toast.makeText(LoginActivity.this, "" + sendOtpPojos.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(LoginActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SendOtpPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}