package com.webond.chemicals.admin.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminOrderRegisterPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private TextInputEditText edtFromDate, edTodate;
    private ArrayList<String> selectTypeArrayList = new ArrayList<>();
    private Spinner spLoginType;
    private static final String SELECT_USER_TYPE = "Select Order Status";
    private static final String All = "All";
    private static final String Pending = "Pending";
    private static final String Approve = "Approve";
    private static final String Reject = "Reject";
    private SpinnerSimpleAdapter spinnerAdapterUserType;
//    private RecyclerView rvAdminOrderReport;
    private LinearLayout llLoading;
    private Calendar myCalendar = Calendar.getInstance();
    private Calendar myCalendar1 = Calendar.getInstance();
    private ExtendedFloatingActionButton extFabOrderRegisterRepoert;
    private LinearLayout llNoDateFound;
    private HashMap<String, String> userTypeHashMap = new HashMap<>();
    private String loginType = "";
    String orderStatus = "";
    private ScrollView svAdminOrderRegisterReport;
    private LinearLayout llAdminDynamicOrderRegisterReportMainRow;
    private String orderRegisterReportUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_register);
        initView();
        spLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedType = selectTypeArrayList.get(spLoginType.getSelectedItemPosition());
                    if (selectedType.equals("All")) {
                        orderStatus = "3";
                        if (!edtFromDate.getText().toString().trim().equals("") && !edTodate.getText().toString().trim().equals("")) {
                            llAdminDynamicOrderRegisterReportMainRow.removeAllViewsInLayout();
                            getOrderReisterList(edtFromDate.getText().toString(), edTodate.getText().toString(), orderStatus);
                        } else {
                            Toast.makeText(OrderRegisterActivity.this, "Please Select From-Date and To-Date", Toast.LENGTH_SHORT).show();

                        }

                    } else if (selectedType.equals("Pending")) {
                        orderStatus = "0";
                        if (!edtFromDate.getText().toString().trim().equals("") && !edTodate.getText().toString().trim().equals("")) {
                            llAdminDynamicOrderRegisterReportMainRow.removeAllViewsInLayout();
                            getOrderReisterList(edtFromDate.getText().toString(), edTodate.getText().toString(), orderStatus);
                        } else {
                            Toast.makeText(OrderRegisterActivity.this, "Please Select From-Date and To-Date", Toast.LENGTH_SHORT).show();

                        }
                    } else if (selectedType.equals("Approve")) {
                        orderStatus = "1";
                        if (!edtFromDate.getText().toString().trim().equals("") && !edTodate.getText().toString().trim().equals("")) {
                            llAdminDynamicOrderRegisterReportMainRow.removeAllViewsInLayout();
                            getOrderReisterList(edtFromDate.getText().toString(), edTodate.getText().toString(), orderStatus);
                        } else {
                            Toast.makeText(OrderRegisterActivity.this, "Please Select From-Date and To-Date", Toast.LENGTH_SHORT).show();

                        }
                    } else if (selectedType.equals("Reject")) {
                        orderStatus = "2";
                        if (!edtFromDate.getText().toString().trim().equals("") && !edTodate.getText().toString().trim().equals("")) {
                            llAdminDynamicOrderRegisterReportMainRow.removeAllViewsInLayout();
                            getOrderReisterList(edtFromDate.getText().toString(), edTodate.getText().toString(), orderStatus);
                        } else {
                            Toast.makeText(OrderRegisterActivity.this, "Please Select From-Date and To-Date", Toast.LENGTH_SHORT).show();

                        }
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    final DatePickerDialog.OnDateSetListener dob = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            edtFromDate.setText(sdf.format(myCalendar.getTime()));
            spLoginType.setSelection(0);
        }
    };
    final DatePickerDialog.OnDateSetListener dob1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            edTodate.setText(sdf.format(myCalendar1.getTime()));
            spLoginType.setSelection(0);
        }
    };

    private void initView() {
        extFabOrderRegisterRepoert = findViewById(R.id.extFabOrderRegisterRepoert);
        extFabOrderRegisterRepoert.setOnClickListener(this::onClick);
        extFabOrderRegisterRepoert.setVisibility(View.GONE);
        imgBack = findViewById(R.id.imgBack);
        svAdminOrderRegisterReport = findViewById(R.id.svAdminOrderRegisterReport);
        llAdminDynamicOrderRegisterReportMainRow = findViewById(R.id.llAdminDynamicOrderRegisterReportMainRow);
//        rvAdminOrderReport = findViewById(R.id.rvAdminOrderReport);
        imgBack.setOnClickListener(this);
        spLoginType = findViewById(R.id.spLoginType);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        edtFromDate = findViewById(R.id.edFromDate);
        edTodate = findViewById(R.id.edToDate);
        tvHeaderTitle.setText("Order Register Report");
        selectTypeArrayList.add(SELECT_USER_TYPE);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        selectTypeArrayList.add(All);
        userTypeHashMap.put(All, "2");
        selectTypeArrayList.add(Pending);
        selectTypeArrayList.add(Approve);
        selectTypeArrayList.add(Reject);
        userTypeHashMap.put(Pending, "3");
        userTypeHashMap.put(Reject, "2");

        edTodate.setOnClickListener(this::onClick);
        edtFromDate.setOnClickListener(this::onClick);
        spinnerAdapterUserType = new SpinnerSimpleAdapter(OrderRegisterActivity.this, selectTypeArrayList);
        spLoginType.setAdapter(spinnerAdapterUserType);


        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtFromDate.setText(sdf.format(myCalendar.getTime()));

        edTodate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();

        } else if (v.getId() == R.id.edFromDate) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(OrderRegisterActivity.this, dob, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();

        } else if (v.getId() == R.id.edToDate) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(OrderRegisterActivity.this, dob1, myCalendar1
                    .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                    myCalendar1.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();

        } else if (v.getId() == R.id.extFabOrderRegisterRepoert) {
//            if (arrayLists != null && arrayLists.size() > 0) {
//                String fileUrl = arrayLists.get(0).getReportPDFLink();
                String fileExtension = orderRegisterReportUrl.substring(orderRegisterReportUrl.lastIndexOf("."));
                new DownloadPdfFromUrl(OrderRegisterActivity.this, orderRegisterReportUrl, fileExtension, "Order Register Reports");
//            }
        }
    }

    private void getOrderReisterList(String fromDate, String toDate, String orderStatus) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminOrderRegisterReport.setVisibility(View.GONE);
        ApiImplementer.getOrderRegister(fromDate, toDate, orderStatus, new Callback<ArrayList<AdminOrderRegisterPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminOrderRegisterPojo>> call, Response<ArrayList<AdminOrderRegisterPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {

                        if (response.body().size() > 0) {
//                            arrayLists = response.body();
                            if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPDFLink())) {
                                orderRegisterReportUrl = response.body().get(0).getReportPDFLink().trim();
                                extFabOrderRegisterRepoert.setVisibility(View.VISIBLE);
                            } else {
                                extFabOrderRegisterRepoert.setVisibility(View.GONE);
                            }
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            svAdminOrderRegisterReport.setVisibility(View.VISIBLE);
                            generateDynamicStockReport(response.body());
//                            rvAdminOrderReport.setAdapter(new AdminOrderRegisterAdapter(OrderRegisterActivity.this, response.body()));
                        } else {
                            extFabOrderRegisterRepoert.setVisibility(View.GONE);
                           llLoading.setVisibility(View.GONE);
                           llNoDateFound.setVisibility(View.VISIBLE);
                            svAdminOrderRegisterReport.setVisibility(View.GONE);
                        }
                    } else {
                        extFabOrderRegisterRepoert.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        svAdminOrderRegisterReport.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminOrderRegisterPojo>> call, Throwable t) {
//                String url = call.request().url().toString();
                extFabOrderRegisterRepoert.setVisibility(View.GONE);
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                svAdminOrderRegisterReport.setVisibility(View.GONE);
            }
        });
    }

    private void generateDynamicStockReport(ArrayList<AdminOrderRegisterPojo> adminOrderRegisterPojoArrayList) {

        for (int i = 0; i <= adminOrderRegisterPojoArrayList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(OrderRegisterActivity.this);
            llDynamicAdminStockReport.setOnClickListener(this);
            llDynamicAdminStockReport.setTag(adminOrderRegisterPojoArrayList.get(i));

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(160, adminOrderRegisterPojoArrayList.get(i).getCdName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(160, adminOrderRegisterPojoArrayList.get(i).getProductName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90, adminOrderRegisterPojoArrayList.get(i).getProductCode() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90, adminOrderRegisterPojoArrayList.get(i).getQty() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90, adminOrderRegisterPojoArrayList.get(i).getPoints() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(140, adminOrderRegisterPojoArrayList.get(i).getOrderNo() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(140, adminOrderRegisterPojoArrayList.get(i).getOrderStatus() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90, adminOrderRegisterPojoArrayList.get(i).getCdCode() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(140, adminOrderRegisterPojoArrayList.get(i).getOrderDate() + ""));
            llDynamicAdminStockReport.addView(createDynamicTextView(140, adminOrderRegisterPojoArrayList.get(i).getOrderType() + ""));

            llAdminDynamicOrderRegisterReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(OrderRegisterActivity.this, txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(OrderRegisterActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(OrderRegisterActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }

}