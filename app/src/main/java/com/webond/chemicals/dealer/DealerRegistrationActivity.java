package com.webond.chemicals.dealer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.webond.chemicals.pojo.AddDealerPojo;
import com.webond.chemicals.pojo.GetCityListPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.pojo.GetDistributorListByCityIdPojo;
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

public class DealerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SELECT_STATE = "Select State";
    private static final String SELECT_DISTRICT = "Select District";
    private static final String SELECT_TALUKA = "Select Taluka";
    private static final String SELECT_CITY = "Select City";
    private static final String SELECT_DISTRIBUTOR = "Select Distributor";

    private MySharedPreferences mySharedPreferences;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtDealerName;
    private Spinner spState;
    private Spinner spDistrict;
    private Spinner spTaluka;
    private Spinner spCity;
    private Spinner spDistributor;
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
    private SpinnerSimpleAdapter spinnerAdapterDistributor;

    private ArrayList<String> stateArrayList;
    private HashMap<String, String> stateHashMap;
    private ArrayList<String> districtArrayList;
    private HashMap<String, String> districtHashMap;
    private ArrayList<String> talukaArrayList;
    private HashMap<String, String> talukaHashMap;
    private ArrayList<String> cityArrayList;
    private HashMap<String, String> cityHashMap;
    private ArrayList<String> distributorArrayList;
    private HashMap<String, String> distributorHashMap;


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
        setContentView(R.layout.activity_dealer_registration);
        initView();
        getStateApiCall(true, false);
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(DealerRegistrationActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        edtDealerName = findViewById(R.id.edtDealerName);
        spState = findViewById(R.id.spState);
        spDistrict = findViewById(R.id.spDistrict);
        spTaluka = findViewById(R.id.spTaluka);
        spCity = findViewById(R.id.spCity);
        spDistributor = findViewById(R.id.spDistributor);
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean isValid() {
        if (CommonUtil.checkIsEmptyOrNullCommon(edtDealerName.getText().toString().trim())) {
            Toast.makeText(this, "Please enter dealer name", Toast.LENGTH_SHORT).show();
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
        } else if (spDistributor.getSelectedItemPosition() == -1 || spDistributor.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select distributor", Toast.LENGTH_SHORT).show();
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
                startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_UPLOAD_AADHAR_PROOF);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        } else if (v.getId() == R.id.edtUploadPhoto) {
            Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
            pickPhoto.setType("image/*|application/*");
            startActivityForResult(pickPhoto, IntentConstants.REQUEST_CODE_FOR_UPLOAD_PHOTO);
        } else if (v.getId() == R.id.edtDOB) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(DealerRegistrationActivity.this, dob, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();
        } else if (v.getId() == R.id.cvSubmit) {
            if (isValid()) {
                String dealerName = edtDealerName.getText().toString().trim();
                String distributorId = distributorHashMap.get(distributorArrayList.get(spDistributor.getSelectedItemPosition()));
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
                addDealerApiCall(true, false, dealerName, distributorId, stateId, districtId, talukaId, cityId, mobileNo, mobileNo2,
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
                    String fileUrl = FileUtils.getPath(DealerRegistrationActivity.this, data.getData());
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
                    String fileUrl = FileUtils.getPath(DealerRegistrationActivity.this, data.getData());
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
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
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

                            spinnerAdapterState = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, stateArrayList);
                            spState.setAdapter(spinnerAdapterState);

//                            spState.setSelection(1);
                            String stateId = stateHashMap.get(stateArrayList.get(1));
                            getDistrictApiCall(false, false, stateId);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            Toast.makeText(DealerRegistrationActivity.this, "State Not Found!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(DealerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistrictApiCall(boolean isPdShow, boolean isPdHide, String stateId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
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

                            spinnerAdapterDistrict = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, districtArrayList);
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
                            spinnerAdapterDistrict = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, districtArrayList);
                            spDistrict.setAdapter(spinnerAdapterDistrict);
                            Toast.makeText(DealerRegistrationActivity.this, "District Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(DealerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTalukaApiCall(boolean isPdShow, boolean isPdHide, String districtId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
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

                            spinnerAdapterUserTaluka = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, talukaArrayList);
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
                            spinnerAdapterUserTaluka = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, talukaArrayList);
                            spTaluka.setAdapter(spinnerAdapterUserTaluka);
                            Toast.makeText(DealerRegistrationActivity.this, "Taluka Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(DealerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCityApiCall(boolean isPdShow, boolean isPdHide, String talukaId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
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

                            spinnerAdapterUserCity = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, cityArrayList);
                            spCity.setAdapter(spinnerAdapterUserCity);
//                            spCity.setSelection(1);
                            String cityId = cityHashMap.get(cityArrayList.get(1));
                            getDistributorApiCall(false, true, cityId);
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            cityArrayList = new ArrayList<>();
                            cityArrayList.add(SELECT_CITY);
                            cityHashMap = new HashMap<>();
                            spinnerAdapterUserCity = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, cityArrayList);
                            spCity.setAdapter(spinnerAdapterUserCity);
                            Toast.makeText(DealerRegistrationActivity.this, "City Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(DealerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistributorApiCall(boolean isPdShow, boolean isPdHide, String cityId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
        }
        ApiImplementer.getDistributorListByCityIdApiImplementer(cityId, new Callback<ArrayList<GetDistributorListByCityIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorListByCityIdPojo>> call, Response<ArrayList<GetDistributorListByCityIdPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDistributorListByCityIdPojo> getDistributorListByCityIdPojoArrayList = response.body();
                            distributorArrayList = new ArrayList<>();
                            distributorArrayList.add(SELECT_DISTRIBUTOR);
                            distributorHashMap = new HashMap<>();
                            for (int i = 0; i < getDistributorListByCityIdPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListByCityIdPojoArrayList.get(i).getDistributorName())) {
                                    String distributorName = getDistributorListByCityIdPojoArrayList.get(i).getDistributorName().trim();
                                    distributorArrayList.add(distributorName);
                                    distributorHashMap.put(distributorName, getDistributorListByCityIdPojoArrayList.get(i).getDistributorId().toString());
                                }
                            }

                            spinnerAdapterDistributor = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, distributorArrayList);
                            spDistributor.setAdapter(spinnerAdapterDistributor);
//                            spCity.setSelection(1);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            distributorArrayList = new ArrayList<>();
                            distributorArrayList.add(SELECT_DISTRIBUTOR);
                            distributorHashMap = new HashMap<>();
                            spinnerAdapterDistributor = new SpinnerSimpleAdapter(DealerRegistrationActivity.this, distributorArrayList);
                            spDistributor.setAdapter(spinnerAdapterDistributor);
                            Toast.makeText(DealerRegistrationActivity.this, "Distributors Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(DealerRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorListByCityIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addDealerApiCall(boolean isPdShow, boolean isPdHide,
                                  String dealerName,
                                  String distributorId, String StateId, String DistrictId, String TalukaId,
                                  String CityId, String MobileNo, String MobileNo2, String Address,
                                  String PinCode, String Email, String AadharNo, String AadharProof,
                                  String AadharFileName, String GSTNo, String Photo,
                                  String PhotoFileName, String DateOfBirth) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
        }
        ApiImplementer.addDealerImplementer(dealerName, distributorId, StateId, DistrictId, TalukaId, CityId,
                MobileNo, MobileNo2, Address, PinCode, Email, AadharNo, AadharProof, AadharFileName, GSTNo,
                Photo, PhotoFileName, DateOfBirth, new Callback<ArrayList<AddDealerPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddDealerPojo>> call, Response<ArrayList<AddDealerPojo>> response) {
                        if (isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                    Toast.makeText(DealerRegistrationActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                    getDetailsForLoginUserDealer(false, true);
                                } else {
                                    if (!isPdHide) {
                                        DialogUtil.hideProgressDialog();
                                    }
                                    Toast.makeText(DealerRegistrationActivity.this, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (!isPdHide) {
                                    DialogUtil.hideProgressDialog();
                                }
                                Toast.makeText(DealerRegistrationActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddDealerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDetailsForLoginUserDealer(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(DealerRegistrationActivity.this, "");
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
                                Intent intent = new Intent(DealerRegistrationActivity.this, LoginActivity.class);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else {
                                if (!isPdHide) {
                                    DialogUtil.hideProgressDialog();
                                }
                                Toast.makeText(DealerRegistrationActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DealerRegistrationActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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


}