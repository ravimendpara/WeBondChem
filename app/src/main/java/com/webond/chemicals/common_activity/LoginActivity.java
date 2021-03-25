package com.webond.chemicals.common_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminDashboardActivity;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.BottomSheetDialogForVerifyOTP;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.customer.CustomerDashboardActivity;
import com.webond.chemicals.customer.CustomerRegistrationActivity;
import com.webond.chemicals.dealer.DealerDashboardActivity;
import com.webond.chemicals.dealer.DealerRegistrationActivity;
import com.webond.chemicals.distributor.DistributorDashboardActivity;
import com.webond.chemicals.distributor.DistributorRegistrationActivity;
import com.webond.chemicals.pojo.CheckMobileNoExitstOrNoPojo;
import com.webond.chemicals.pojo.GetDetailForLoginUserAdminPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDistributorPojo;
import com.webond.chemicals.pojo.SendOtpPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetDialogForVerifyOTP.IOnOTPReceived {

    private MySharedPreferences mySharedPreferences;
    private static final String SELECT_USER_TYPE = "Select User Type";
    private ArrayList<String> selectUserTypeArrayList = new ArrayList<>();
    private static final String ADMIN = "Admin";
    private static final String DISTRIBUTOR = "Distributor";
    private static final String DEALER = "Dealer";
    private static final String CUSTOMER = "Customer";

    private Spinner spUserType;
    private SpinnerSimpleAdapter spinnerAdapterUserType;
    private AppCompatEditText edtMobileNo;
    private MaterialCardView cvLogin;
    private MaterialCardView cvRegister;
    private BottomSheetDialogForVerifyOTP bottomSheetDialogForVerifyOTP;
    private String randomSixDigitOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        initView();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(LoginActivity.this);
        spUserType = findViewById(R.id.spUserType);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        cvLogin = findViewById(R.id.cvLogin);
        cvLogin.setOnClickListener(this);
        cvRegister = findViewById(R.id.cvRegister);
        cvRegister.setOnClickListener(this);
        selectUserTypeArrayList.add(SELECT_USER_TYPE);
        selectUserTypeArrayList.add(ADMIN);
        selectUserTypeArrayList.add(DISTRIBUTOR);
        selectUserTypeArrayList.add(DEALER);
        selectUserTypeArrayList.add(CUSTOMER);
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
                Toast.makeText(this, "sended otp:- " + randomSixDigitOTP, Toast.LENGTH_LONG).show();
                sendOTPApiCall(true, true, edtMobileNo.getText().toString().trim(), randomSixDigitOTP);
            }
        } else if (v.getId() == R.id.cvRegister) {
            if (isValid()) {
                checkIsMobileNoIsExistOrNot(true, true, edtMobileNo.getText().toString().trim());
            }
        }
    }

    @Override
    public void onOTPSubmit(String enteredOTP) {
        if (enteredOTP.equalsIgnoreCase(randomSixDigitOTP)) {
            bottomSheetDialogForVerifyOTP.dismiss();
            Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
            mySharedPreferences.setVerifiedMobileNo(edtMobileNo.getText().toString().trim());
            String selectedLoginUserType = selectUserTypeArrayList.get(spUserType.getSelectedItemPosition());
            if (selectedLoginUserType.equalsIgnoreCase(ADMIN)) {
                getDetailsForLoginUserAdmin(true, true);
            } else if (selectedLoginUserType.equalsIgnoreCase(DISTRIBUTOR)) {
                getDetailsForLoginUserDistributor(true, true);
            } else if (selectedLoginUserType.equalsIgnoreCase(DEALER)) {
                getDetailsForLoginUserDealer(true, true);
            } else if (selectedLoginUserType.equalsIgnoreCase(CUSTOMER)) {
                getDetailsForLoginUserCustomer(true, true);
            }
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
                            Toast.makeText(LoginActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            //TODO remove below three if else condition before going to live and also remove selectedLoginUserType line because mobile no already exist no need to register
                            String selectedLoginUserType = selectUserTypeArrayList.get(spUserType.getSelectedItemPosition());
                            if (selectedLoginUserType.equalsIgnoreCase(DISTRIBUTOR)) {
                                Intent intent = new Intent(LoginActivity.this, DistributorRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_DISTRIBUTOR_REGISTRATION);
                            } else if (selectedLoginUserType.equalsIgnoreCase(DEALER)) {
                                Intent intent = new Intent(LoginActivity.this, DealerRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_DEALER_REGISTRATION);
                            } else if (selectedLoginUserType.equalsIgnoreCase(CUSTOMER)) {
                                Intent intent = new Intent(LoginActivity.this, CustomerRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_CUSTOMER_REGISTRATION);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            String selectedLoginUserType = selectUserTypeArrayList.get(spUserType.getSelectedItemPosition());
                            if (selectedLoginUserType.equalsIgnoreCase(DISTRIBUTOR)) {
                                Intent intent = new Intent(LoginActivity.this, DistributorRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_DISTRIBUTOR_REGISTRATION);
                            } else if (selectedLoginUserType.equalsIgnoreCase(DEALER)) {
                                Intent intent = new Intent(LoginActivity.this, DealerRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_DEALER_REGISTRATION);
                            } else if (selectedLoginUserType.equalsIgnoreCase(CUSTOMER)) {
                                Intent intent = new Intent(LoginActivity.this, CustomerRegistrationActivity.class);
                                startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_CUSTOMER_REGISTRATION);
                            }
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

    private void getDetailsForLoginUserAdmin(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "");
        }
        ApiImplementer.getDetailsForLoginUserAdminImplementer(edtMobileNo.getText().toString().trim(),
                new Callback<ArrayList<GetDetailForLoginUserAdminPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<GetDetailForLoginUserAdminPojo>> call, Response<ArrayList<GetDetailForLoginUserAdminPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null &&
                                    response.body().size() > 0) {
                                GetDetailForLoginUserAdminPojo getDetailForLoginUserAdminPojo = response.body().get(0);
                                setDataForAdmin(getDetailForLoginUserAdminPojo);
                                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                startActivity(intent);
                                finish();
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
                    public void onFailure(Call<ArrayList<GetDetailForLoginUserAdminPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsForLoginUserDealer(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "");
        }
        ApiImplementer.getDetailsForLoginUserDealerImplementer(edtMobileNo.getText().toString().trim(),
                new Callback<ArrayList<GetDetailsForLoginUserDealerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<GetDetailsForLoginUserDealerPojo>> call, Response<ArrayList<GetDetailsForLoginUserDealerPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null &&
                                    response.body().size() > 0) {
                                GetDetailsForLoginUserDealerPojo getDetailsForLoginUserDealerPojo = response.body().get(0);
                                setDataForDealer(getDetailsForLoginUserDealerPojo);
                                Intent intent = new Intent(LoginActivity.this, DealerDashboardActivity.class);
                                startActivity(intent);
                                finish();
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
                    public void onFailure(Call<ArrayList<GetDetailsForLoginUserDealerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsForLoginUserDistributor(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "");
        }
        ApiImplementer.getDetailsForLoginUserDistributorImplementer(edtMobileNo.getText().toString().trim(),
                new Callback<ArrayList<GetDetailsForLoginUserDistributorPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<GetDetailsForLoginUserDistributorPojo>> call, Response<ArrayList<GetDetailsForLoginUserDistributorPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null &&
                                    response.body().size() > 0) {
                                GetDetailsForLoginUserDistributorPojo getDetailsForLoginUserDistributorPojo = response.body().get(0);
                                setDataForDistributor(getDetailsForLoginUserDistributorPojo);
                                Intent intent = new Intent(LoginActivity.this, DistributorDashboardActivity.class);
                                startActivity(intent);
                                finish();
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
                    public void onFailure(Call<ArrayList<GetDetailsForLoginUserDistributorPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsForLoginUserCustomer(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(LoginActivity.this, "");
        }
        ApiImplementer.getDetailsForLoginUserCustomerImplementer(edtMobileNo.getText().toString().trim(),
                new Callback<ArrayList<GetDetailsForLoginUserCustomerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<GetDetailsForLoginUserCustomerPojo>> call, Response<ArrayList<GetDetailsForLoginUserCustomerPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null &&
                                    response.body().size() > 0) {
                                GetDetailsForLoginUserCustomerPojo getDetailsForLoginUserCustomerPojo = response.body().get(0);
                                setDataForCustomer(getDetailsForLoginUserCustomerPojo);
                                Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
                                startActivity(intent);
                                finish();
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
                    public void onFailure(Call<ArrayList<GetDetailsForLoginUserCustomerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setDataForAdmin(GetDetailForLoginUserAdminPojo getDetailForLoginUserAdminPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getLoginType())) {
            mySharedPreferences.setLoginType(getDetailForLoginUserAdminPojo.getLoginType());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getCompanyId())) {
            mySharedPreferences.setAdminCompanyId(getDetailForLoginUserAdminPojo.getCompanyId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getCompanyTagLine())) {
            mySharedPreferences.setAdminCompanyTagLine(getDetailForLoginUserAdminPojo.getCompanyTagLine() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getCopanyName())) {
            mySharedPreferences.setAdminCompanyName(getDetailForLoginUserAdminPojo.getCopanyName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getCopanyName())) {
            mySharedPreferences.setAdminCompanyName(getDetailForLoginUserAdminPojo.getCopanyName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailForLoginUserAdminPojo.getEmail())) {
            mySharedPreferences.setAdminCompanyEmail(getDetailForLoginUserAdminPojo.getEmail() + "");
        }

    }

    private void setDataForDistributor(GetDetailsForLoginUserDistributorPojo getDetailsForLoginUserDistributorPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getLoginType())) {
            mySharedPreferences.setLoginType(getDetailsForLoginUserDistributorPojo.getLoginType() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getDistributorId())) {
            mySharedPreferences.setDistributorId(getDetailsForLoginUserDistributorPojo.getDistributorId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getDistributorName())) {
            mySharedPreferences.setDistributorName(getDetailsForLoginUserDistributorPojo.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getMobileNo())) {
            mySharedPreferences.setDistributorMobileNo(getDetailsForLoginUserDistributorPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getMobileNo2())) {
            mySharedPreferences.setDistributorMobileNo2(getDetailsForLoginUserDistributorPojo.getMobileNo2() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getEmail())) {
            mySharedPreferences.setDistributorEmail(getDetailsForLoginUserDistributorPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getPhotoPath())) {
            mySharedPreferences.setDistributorPhotoPath(getDetailsForLoginUserDistributorPojo.getPhotoPath() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getCityName())) {
            mySharedPreferences.setDistributorCityName(getDetailsForLoginUserDistributorPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getTalukaName())) {
            mySharedPreferences.setDistributorTalukaName(getDetailsForLoginUserDistributorPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getDistrictName())) {
            mySharedPreferences.setDistributorDistrictName(getDetailsForLoginUserDistributorPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getStateName())) {
            mySharedPreferences.setDistributorStateName(getDetailsForLoginUserDistributorPojo.getStateName() + "");
        }
    }

    private void setDataForDealer(GetDetailsForLoginUserDealerPojo getDetailsForLoginUserDealerPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getLoginType())) {
            mySharedPreferences.setLoginType(getDetailsForLoginUserDealerPojo.getLoginType() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDealerId())) {
            mySharedPreferences.setDealerId(getDetailsForLoginUserDealerPojo.getDealerId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDealerName())) {
            mySharedPreferences.setDealerName(getDetailsForLoginUserDealerPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getMobileNo())) {
            mySharedPreferences.setDealerMobileNo(getDetailsForLoginUserDealerPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getMobileNo2())) {
            mySharedPreferences.setDealerMobileNo2(getDetailsForLoginUserDealerPojo.getMobileNo2() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getEmail())) {
            mySharedPreferences.setDealerEmail(getDetailsForLoginUserDealerPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getPhotoPath())) {
            mySharedPreferences.setDealerPhotoPath(getDetailsForLoginUserDealerPojo.getPhotoPath() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getCityName())) {
            mySharedPreferences.setDealerCityName(getDetailsForLoginUserDealerPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getTalukaName())) {
            mySharedPreferences.setDealerTalukaName(getDetailsForLoginUserDealerPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDistrictName())) {
            mySharedPreferences.setDealerDistrictName(getDetailsForLoginUserDealerPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getStateName())) {
            mySharedPreferences.setDealerStateName(getDetailsForLoginUserDealerPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDistributorName())) {
            mySharedPreferences.setDealerDistributorName(getDetailsForLoginUserDealerPojo.getDistributorName() + "");
        }

    }

    private void setDataForCustomer(GetDetailsForLoginUserCustomerPojo getDetailsForLoginUserCustomerPojo) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getLoginType())) {
            mySharedPreferences.setLoginType(getDetailsForLoginUserCustomerPojo.getLoginType() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCustomerId())) {
            mySharedPreferences.setCustomerId(getDetailsForLoginUserCustomerPojo.getCustomerId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCustomerName())) {
            mySharedPreferences.setCustomerName(getDetailsForLoginUserCustomerPojo.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getMobileNo())) {
            mySharedPreferences.setCustomerMobileNo(getDetailsForLoginUserCustomerPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getMobileNo2())) {
            mySharedPreferences.setCustomerMobileNo2(getDetailsForLoginUserCustomerPojo.getMobileNo2() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getEmailId())) {
            mySharedPreferences.setCustomerEmail(getDetailsForLoginUserCustomerPojo.getEmailId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getPhotoPath())) {
            mySharedPreferences.setCustomerPhotoPath(getDetailsForLoginUserCustomerPojo.getPhotoPath() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getDealerName())) {
            mySharedPreferences.setCustomerDealerName(getDetailsForLoginUserCustomerPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getDistributorName())) {
            mySharedPreferences.setCustomerDistributorName(getDetailsForLoginUserCustomerPojo.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCityName())) {
            mySharedPreferences.setCustomerCityName(getDetailsForLoginUserCustomerPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getTalukaName())) {
            mySharedPreferences.setCustomerTalukaName(getDetailsForLoginUserCustomerPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getDistrictName())) {
            mySharedPreferences.setCustomerDistrictName(getDetailsForLoginUserCustomerPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getStateName())) {
            mySharedPreferences.setCustomerStateName(getDetailsForLoginUserCustomerPojo.getStateName() + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_FOR_CUSTOMER_REGISTRATION) {
            Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
            startActivity(intent);
            finish();
        }else if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_FOR_DEALER_REGISTRATION) {
            Intent intent = new Intent(LoginActivity.this, DealerDashboardActivity.class);
            startActivity(intent);
            finish();
        }else if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_FOR_DISTRIBUTOR_REGISTRATION) {
            Intent intent = new Intent(LoginActivity.this, DistributorDashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}