package com.webond.chemicals.customer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.LoginActivity;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AddCustomerPojo;
import com.webond.chemicals.pojo.GetCityListPojo;
import com.webond.chemicals.pojo.GetDealerListByCityIdPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetStateListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.FileUtils;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SELECT_STATE = "Select State";
    private static final String SELECT_DISTRICT = "Select District";
    private static final String SELECT_TALUKA = "Select Taluka";
    private static final String SELECT_CITY = "Select City";
    private static final String SELECT_DEALER = "Select Dealer";

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtCustomerName;
    private Spinner spState;
    private Spinner spDistrict;
    private Spinner spTaluka;
    private Spinner spCity;
    private Spinner spDealer;
    private TextInputEditText edtMobileNo;
    private TextInputEditText edtMobileNo2;
    private TextInputEditText edtAddress;
    private TextInputEditText edtPincode;
    private TextInputEditText edtEMail;
    private TextInputEditText edtAadharNo;
    private TextInputEditText edtUploadAadharProof;
    private TextInputEditText edtGSTNo;
    private TextInputEditText edtUploadPhoto;
    private TextInputEditText edtDOB;
    private MaterialCardView cvSubmit;
    private Calendar myCalendar = Calendar.getInstance();
    private String uploadedAadharProofBase64 = "";
    private String uploadedAadharProofName = "";
    private String uploadedPhotoBase64 = "";
    private String uploadedPhotoName = "";

    private SpinnerSimpleAdapter spinnerAdapterState;
    private SpinnerSimpleAdapter spinnerAdapterDistrict;
    private SpinnerSimpleAdapter spinnerAdapterUserTaluka;
    private SpinnerSimpleAdapter spinnerAdapterUserCity;
    private SpinnerSimpleAdapter spinnerAdapterDealerList;

    private ArrayList<String> stateArrayList;
    private HashMap<String, String> stateHashMap;
    private ArrayList<String> districtArrayList;
    private HashMap<String, String> districtHashMap;
    private ArrayList<String> talukaArrayList;
    private HashMap<String, String> talukaHashMap;
    private ArrayList<String> cityArrayList;
    private HashMap<String, String> cityHashMap;

    private ArrayList<String> dealerArrayList;
    private HashMap<String, String> dealerHashMap;


    final DatePickerDialog.OnDateSetListener dob = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            edtDOB.setText(sdf.format(myCalendar.getTime()));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        initView();
        getStateApiCall(true, false);
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(CustomerRegistrationActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        spState = findViewById(R.id.spState);
        spDistrict = findViewById(R.id.spDistrict);
        spTaluka = findViewById(R.id.spTaluka);
        spCity = findViewById(R.id.spCity);
        spDealer = findViewById(R.id.spDealer);
        edtMobileNo = findViewById(R.id.edtMobileNo);
        edtMobileNo2 = findViewById(R.id.edtMobileNo2);
        edtAddress = findViewById(R.id.edtAddress);
        edtPincode = findViewById(R.id.edtPincode);
        edtEMail = findViewById(R.id.edtEMail);
        edtAadharNo = findViewById(R.id.edtAadharNo);
        edtUploadAadharProof = findViewById(R.id.edtUploadAadharProof);
        edtUploadAadharProof.setOnClickListener(this);
        edtGSTNo = findViewById(R.id.edtGSTNo);
        edtUploadPhoto = findViewById(R.id.edtUploadPhoto);
        edtUploadPhoto.setOnClickListener(this);
        edtDOB = findViewById(R.id.edtDOB);
        edtDOB.setOnClickListener(this);
        cvSubmit = findViewById(R.id.cvSubmit);
        cvSubmit.setOnClickListener(this);

        edtMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(s.toString())) {
                    edtMobileNo2.setText(s.toString() + "");
                }
            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    String stateId = stateHashMap.get(stateArrayList.get(position));
                    getDistrictApiCall(true, false, stateId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    String districtId = districtHashMap.get(districtArrayList.get(position));
                    getTalukaApiCall(true, false, districtId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTaluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    String talukaId = talukaHashMap.get(talukaArrayList.get(position));
                    getCityApiCall(true, true, talukaId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    String cityId = cityHashMap.get(cityArrayList.get(position));
                    getDealerListByCityIdApiCall(true, true, cityId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtCustomerName.getText().toString().trim())) {
            Toast.makeText(this, "Please enter customer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spState.getSelectedItemPosition() == -1 || spState.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select state", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spDistrict.getSelectedItemPosition() == -1 || spDistrict.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select district", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spTaluka.getSelectedItemPosition() == -1 || spTaluka.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select taluka", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spCity.getSelectedItemPosition() == -1 || spCity.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spDealer.getSelectedItemPosition() == -1 || spDealer.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select dealer", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtMobileNo.getText().toString().trim()) ||
                edtMobileNo.getText().toString().trim().length() != 10) {
            Toast.makeText(this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtAddress.getText().toString().trim())) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtPincode.getText().toString().trim())) {
            Toast.makeText(this, "Please enter pincode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtEMail.getText().toString().trim())) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtAadharNo.getText().toString().trim())) {
            Toast.makeText(this, "Please enter aadhar no.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(uploadedAadharProofBase64)) {
            Toast.makeText(this, "Please upload aadhar proof", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtGSTNo.getText().toString().trim())) {
            Toast.makeText(this, "Please enter GST No.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(uploadedPhotoBase64)) {
            Toast.makeText(this, "Please upload photo", Toast.LENGTH_SHORT).show();
            return false;
        } else if (CommonUtil.checkIsEmptyOrNullCommon(edtDOB.getText().toString().trim())) {
            Toast.makeText(this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.edtUploadAadharProof) {
            try {
                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhoto.setType("image/*|application/*");
                pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_UPLOAD_AADHAR_PROOF);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else if (v.getId() == R.id.edtUploadPhoto) {
            Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
            pickPhoto.setType("image/*|application/*");
            pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_UPLOAD_PHOTO);
        } else if (v.getId() == R.id.edtDOB) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(CustomerRegistrationActivity.this, dob, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();
        } else if (v.getId() == R.id.cvSubmit) {
            if (isValid()) {
                String customerName = edtCustomerName.getText().toString().trim();
                String dealerId = dealerHashMap.get(dealerArrayList.get(spDealer.getSelectedItemPosition()));
                String stateId = stateHashMap.get(stateArrayList.get(spState.getSelectedItemPosition()));
                String districtId = districtHashMap.get(districtArrayList.get(spDistrict.getSelectedItemPosition()));
                String talukaId = talukaHashMap.get(talukaArrayList.get(spTaluka.getSelectedItemPosition()));
                String cityId = cityHashMap.get(cityArrayList.get(spCity.getSelectedItemPosition()));
                String mobileNo = edtMobileNo.getText().toString().trim();
                String mobileNo2 = edtMobileNo2.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                String pinCode = edtPincode.getText().toString().trim();
                String email = edtEMail.getText().toString().trim();
                String aadharNo = edtAadharNo.getText().toString().trim();
                String aadharProof = uploadedAadharProofBase64;
                String aadharFileName = uploadedAadharProofName;
                String gstnNo = edtGSTNo.getText().toString().trim();
                String photo = uploadedPhotoBase64;
                String photoName = uploadedPhotoName;
                String dob = edtDOB.getText().toString().trim();
                addCustomerApiCall(true, true, customerName, dealerId, stateId, districtId, talukaId, cityId, mobileNo, mobileNo2,
                        address, pinCode, email, aadharNo, aadharProof, aadharFileName, gstnNo, photo, photoName, dob);
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

        if (requestCode == IntentConstants.REQUEST_CODE_FOR_UPLOAD_AADHAR_PROOF && resultCode == RESULT_OK) {
            try {

                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(CustomerRegistrationActivity.this, data.getData());
                    File uploadedAadharProof = new File(fileUrl);
                    uploadedAadharProofBase64 = CommonUtil.getBase64StringFromFileObj(uploadedAadharProof);
                    uploadedAadharProofName = uploadedAadharProof.getName();
                    edtUploadAadharProof.setText(uploadedAadharProofName);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (requestCode == IntentConstants.REQUEST_CODE_FOR_UPLOAD_PHOTO && resultCode == RESULT_OK) {
            try {
                if (data != null && data.getData() != null) {
                    String fileUrl = FileUtils.getPath(CustomerRegistrationActivity.this, data.getData());
                    File uploadedPhotoFile = new File(fileUrl);
                    uploadedPhotoBase64 = CommonUtil.getBase64StringFromFileObj(uploadedPhotoFile);
                    uploadedPhotoName = uploadedPhotoFile.getName();
                    edtUploadPhoto.setText(uploadedPhotoName);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getStateApiCall(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.getStateListApiImplementer(new Callback<ArrayList<GetStateListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetStateListPojo>> call, Response<ArrayList<GetStateListPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetStateListPojo> getStateListPojoArrayList = response.body();
                            stateArrayList = new ArrayList<>();
                            stateArrayList.add(SELECT_STATE);
                            stateHashMap = new HashMap<>();
                            for (int i = 0; i < getStateListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getStateListPojoArrayList.get(i).getStateName())) {
                                    String stateName = getStateListPojoArrayList.get(i).getStateName().trim();
                                    stateArrayList.add(stateName);
                                    stateHashMap.put(stateName, getStateListPojoArrayList.get(i).getStateId().toString());
                                }
                            }

                            spinnerAdapterState = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, stateArrayList);
                            spState.setAdapter(spinnerAdapterState);

//                            spState.setSelection(1);
                            String stateId = stateHashMap.get(stateArrayList.get(1));
                            getDistrictApiCall(false, false, stateId);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            Toast.makeText(CustomerRegistrationActivity.this, "State Not Found!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetStateListPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistrictApiCall(boolean isPdShow, boolean isPdHide, String stateId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.getDistrictListApiImplementer(stateId, new Callback<ArrayList<GetDistrictListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistrictListPojo>> call, Response<ArrayList<GetDistrictListPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDistrictListPojo> getDistrictListPojoArrayList = response.body();
                            districtArrayList = new ArrayList<>();
                            districtArrayList.add(SELECT_DISTRICT);
                            districtHashMap = new HashMap<>();
                            for (int i = 0; i < getDistrictListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDistrictListPojoArrayList.get(i).getDistrictName())) {
                                    String districtName = getDistrictListPojoArrayList.get(i).getDistrictName().trim();
                                    districtArrayList.add(districtName);
                                    districtHashMap.put(districtName, getDistrictListPojoArrayList.get(i).getDistrictId().toString());
                                }
                            }

                            spinnerAdapterDistrict = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, districtArrayList);
                            spDistrict.setAdapter(spinnerAdapterDistrict);

//                            spDistrict.setSelection(1);
                            String districtId = districtHashMap.get(districtArrayList.get(1));
                            getTalukaApiCall(false, false, districtId);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            districtArrayList = new ArrayList<>();
                            districtArrayList.add(SELECT_DISTRICT);
                            districtHashMap = new HashMap<>();
                            spinnerAdapterDistrict = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, districtArrayList);
                            spDistrict.setAdapter(spinnerAdapterDistrict);
                            Toast.makeText(CustomerRegistrationActivity.this, "District Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistrictListPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTalukaApiCall(boolean isPdShow, boolean isPdHide, String districtId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.getTalukaListApiImplementer(districtId, new Callback<ArrayList<GetTalukaListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetTalukaListPojo>> call, Response<ArrayList<GetTalukaListPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetTalukaListPojo> getTalukaListPojoArrayList = response.body();
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            for (int i = 0; i < getTalukaListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getTalukaListPojoArrayList.get(i).getTalukaName())) {
                                    String districtName = getTalukaListPojoArrayList.get(i).getTalukaName().trim();
                                    talukaArrayList.add(districtName);
                                    talukaHashMap.put(districtName, getTalukaListPojoArrayList.get(i).getTalukaId().toString());
                                }
                            }

                            spinnerAdapterUserTaluka = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, talukaArrayList);
                            spTaluka.setAdapter(spinnerAdapterUserTaluka);

//                            spTaluka.setSelection(1);
                            String talukaId = talukaHashMap.get(talukaArrayList.get(1));
                            getCityApiCall(false, false, talukaId);
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            spinnerAdapterUserTaluka = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, talukaArrayList);
                            spTaluka.setAdapter(spinnerAdapterUserTaluka);
                            Toast.makeText(CustomerRegistrationActivity.this, "Taluka Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetTalukaListPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCityApiCall(boolean isPdShow, boolean isPdHide, String talukaId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.getCityListImplementer(talukaId, new Callback<ArrayList<GetCityListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCityListPojo>> call, Response<ArrayList<GetCityListPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetCityListPojo> getCityListPojoArrayList = response.body();
                            cityArrayList = new ArrayList<>();
                            cityArrayList.add(SELECT_CITY);
                            cityHashMap = new HashMap<>();
                            for (int i = 0; i < getCityListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getCityListPojoArrayList.get(i).getCityName())) {
                                    String districtName = getCityListPojoArrayList.get(i).getCityName().trim();
                                    cityArrayList.add(districtName);
                                    cityHashMap.put(districtName, getCityListPojoArrayList.get(i).getCityId().toString());
                                }
                            }

                            spinnerAdapterUserCity = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, cityArrayList);
                            spCity.setAdapter(spinnerAdapterUserCity);
//                            spCity.setSelection(1);
                            String cityId = cityHashMap.get(cityArrayList.get(1));
                            getDealerListByCityIdApiCall(false, true, cityId);
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            cityArrayList = new ArrayList<>();
                            cityArrayList.add(SELECT_CITY);
                            cityHashMap = new HashMap<>();
                            spinnerAdapterUserCity = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, cityArrayList);
                            spCity.setAdapter(spinnerAdapterUserCity);
                            Toast.makeText(CustomerRegistrationActivity.this, "City Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCityListPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDealerListByCityIdApiCall(boolean isPdShow, boolean isPdHide, String cityId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.getDealerListByCityIdApiImplementer(cityId, new Callback<ArrayList<GetDealerListByCityIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerListByCityIdPojo>> call, Response<ArrayList<GetDealerListByCityIdPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDealerListByCityIdPojo> getDealerListByCityIdPojoArrayList = response.body();
                            dealerArrayList = new ArrayList<>();
                            dealerArrayList.add(SELECT_DEALER);
                            dealerHashMap = new HashMap<>();
                            for (int i = 0; i < getDealerListByCityIdPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListByCityIdPojoArrayList.get(i).getDealerName())) {
                                    String dealerName = getDealerListByCityIdPojoArrayList.get(i).getDealerName().trim();
                                    dealerArrayList.add(dealerName);
                                    dealerHashMap.put(dealerName, getDealerListByCityIdPojoArrayList.get(i).getDealerId().toString());
                                }
                            }

                            spinnerAdapterDealerList = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, dealerArrayList);
                            spDealer.setAdapter(spinnerAdapterDealerList);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            dealerArrayList = new ArrayList<>();
                            dealerArrayList.add(SELECT_DEALER);
                            dealerHashMap = new HashMap<>();
                            spinnerAdapterDealerList = new SpinnerSimpleAdapter(CustomerRegistrationActivity.this, dealerArrayList);
                            spDealer.setAdapter(spinnerAdapterDealerList);
                            Toast.makeText(CustomerRegistrationActivity.this, "Dealer Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerListByCityIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addCustomerApiCall(boolean isPdShow, boolean isPdHide, String CustomerName,
                                    String DealerId, String StateId, String DistrictId, String TalukaId,
                                    String CityId, String MobileNo, String MobileNo2, String Address,
                                    String PinCode, String Email, String AadharNo, String AadharProof,
                                    String AadharFileName, String GSTNo, String Photo,
                                    String PhotoFileName, String DateOfBirth) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
        }
        ApiImplementer.addCustomerImplementer(CustomerName, DealerId, StateId, DistrictId, TalukaId,
                CityId, MobileNo, MobileNo2, Address, PinCode, Email, AadharNo, AadharProof, AadharFileName,
                GSTNo, Photo, PhotoFileName, DateOfBirth, new Callback<ArrayList<AddCustomerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddCustomerPojo>> call, Response<ArrayList<AddCustomerPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(CustomerRegistrationActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    finish();
//                                    getDetailsForLoginUserCustomer(false, true);
                                } else {
                                    if (!isPdHide) {
                                        DialogUtil.hideProgressDialog();
                                    }
                                    Toast.makeText(CustomerRegistrationActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (!isPdHide) {
                                    DialogUtil.hideProgressDialog();
                                }
                                Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddCustomerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsForLoginUserCustomer(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(CustomerRegistrationActivity.this, "");
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
                                Intent intent = new Intent(CustomerRegistrationActivity.this, LoginActivity.class);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else {
                                if (!isPdHide) {
                                    DialogUtil.hideProgressDialog();
                                }
                                Toast.makeText(CustomerRegistrationActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(CustomerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

}