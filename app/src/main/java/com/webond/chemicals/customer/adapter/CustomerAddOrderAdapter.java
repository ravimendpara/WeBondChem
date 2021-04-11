package com.webond.chemicals.customer.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.AddCustomerOrderDataPojo;
import com.webond.chemicals.pojo.GetDealerListByTalukaIdPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddOrderAdapter extends RecyclerView.Adapter<CustomerAddOrderAdapter.MyViewHolder> {

    public static final String SELECT_DISTRICT = "Select District*";
    public static final String SELECT_TALUKA = "Select Taluka*";
    public static final String SELECT_DEALER = "Select Dealer*";
    private Context context;
    private ArrayList<GetProductListPojo> getProductListPojoArrayList;
    private LayoutInflater layoutInflater;
    private MySharedPreferences mySharedPreferences;
    private int MAX_ORDER_QUANTITY = 10000000;

    private ArrayList<String> districtArrayList;
    private HashMap<String, String> districtHashMap;
    private ArrayList<String> talukaArrayList;
    private HashMap<String, String> talukaHashMap;
    private ArrayList<String> dealerArrayList;
    private HashMap<String, String> dealerHashMap;
    private ArrayList<String> distributorArrayList;
    private HashMap<String, String> distributorHashMap;

    TextInputEditText etContactPersonAtSite,
            etSiteAddress, etContactNo, etPinCode;
    SearchableSpinner spDistrict, spTaluka, spDealer,spDistributor;
    AppCompatButton btnSubmit;
    AlertDialog orderDialog;
    private Calendar myCalendar = Calendar.getInstance();
    LinearLayout llDealer;
    LinearLayout llDistributor;

    public CustomerAddOrderAdapter(Context context, ArrayList<GetProductListPojo> getProductListPojoArrayList,
                                   ArrayList<String> districtArrayList,
                                   HashMap<String, String> districtHashMap,
                                   ArrayList<String> talukaArrayList,
                                   HashMap<String, String> talukaHashMap,
                                   ArrayList<String> dealerArrayList,
                                   HashMap<String, String> dealerHashMap,
                                   ArrayList<String> distributorArrayList,
                                   HashMap<String, String> distributorHashMap) {
        this.context = context;
        this.getProductListPojoArrayList = getProductListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
        mySharedPreferences = new MySharedPreferences(context);
        this.districtArrayList = districtArrayList;
        this.districtHashMap = districtHashMap;
        this.talukaArrayList = talukaArrayList;
        this.talukaHashMap = talukaHashMap;
        this.dealerArrayList = dealerArrayList;
        this.dealerHashMap = dealerHashMap;
        this.distributorArrayList = distributorArrayList;
        this.distributorHashMap = distributorHashMap;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_add_order_inflater, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetProductListPojo getProductListPojo = getProductListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getCustomerPoint())) {
            holder.tvTotalPoint.setText(((int) Double.parseDouble(getProductListPojo.getCustomerPoint().toString())) + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductName())) {
            holder.tvProductName.setText(getProductListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductPrice())) {
            holder.tvProductPrice.setText(" " + context.getString(R.string.rupee_symbol) + getProductListPojo.getProductPrice() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getCustomerPoint())) {
            holder.tvProductPoint.setText(((int) Double.parseDouble(getProductListPojo.getCustomerPoint().toString())) + "");

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductPhoto1())) {
            Picasso.get().load(getProductListPojo.getProductPhoto1() + "").fit().centerCrop()
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgProductEven);

            Picasso.get().load(getProductListPojo.getProductPhoto1() + "").fit().centerCrop()
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgProductForOdd);

        }

        if (position % 2 == 0) {
            holder.llForEven.setVisibility(View.VISIBLE);
            holder.llForOdd.setVisibility(View.GONE);
            holder.ll_paceholder.setGravity(Gravity.RIGHT);
            holder.tvPlaceOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.border_odd));
        } else {
            holder.llForEven.setVisibility(View.GONE);
            holder.llForOdd.setVisibility(View.VISIBLE);
            holder.ll_paceholder.setGravity(Gravity.LEFT);
            holder.tvPlaceOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.border_even));
        }

        holder.btnAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//                    if (Integer.valueOf(holder.etQty.getText().toString()) <= MAX_ORDER_QUANTITY) {
                    addQuantity(1, holder.etQty);
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.btnSubQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    if (!TextUtils.isEmpty(holder.etQty.getText().toString())) {
                    subtractQuantity(1, holder.etQty);
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (holder.etQty.getText().toString().length() > 0 &&
                            Integer.parseInt(holder.etQty.getText().toString()) > 0) {
                        if (Integer.parseInt(holder.etQty.getText().toString()) <= MAX_ORDER_QUANTITY) {
                            int points = calculatePoints(
                                    (int) Double.parseDouble(getProductListPojo.getCustomerPoint().toString()),
                                    Integer.parseInt(holder.etQty.getText().toString()));
                            holder.tvTotalPoint.setText(String.valueOf(points));
                        } else {
                            holder.etQty.setText(String.valueOf(MAX_ORDER_QUANTITY));
                            int points = calculatePoints(
                                    (int) Double.parseDouble(getProductListPojo.getCustomerPoint().toString()),
                                    MAX_ORDER_QUANTITY);
                            holder.tvTotalPoint.setText(String.valueOf(points));
                            Toast.makeText(context, "You can not enter more than " + MAX_ORDER_QUANTITY + " quantity", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        holder.etQty.setText("1");
                        if (!CommonUtil.checkIsEmptyOrNullCommon(holder.etQty.getText().toString())) {
                            int pointPerQuantity = (int) Double.parseDouble(getProductListPojo.getCustomerPoint().toString()) / Integer.parseInt(holder.etQty.getText().toString());
                            holder.tvTotalPoint.setText(String.valueOf(pointPerQuantity));
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(holder.etQty.getText().toString().trim())) {
                    showOrderDetailsDialog(getProductListPojo, holder);
                } else {
                    Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getProductListPojoArrayList.size();
    }

    private int calculatePoints(int initPoints, int newQty) {

        if (newQty > 0) {
            return newQty * initPoints;
        }

        return initPoints;
    }

    void addQuantity(int addBy, AppCompatEditText etQty) {

        int avlQty = 0;

        if (!TextUtils.isEmpty(etQty.getText().toString())) {
            avlQty = Integer.valueOf(String.valueOf(etQty.getText()));
        }

        avlQty = avlQty + addBy;

        etQty.setText(String.valueOf(avlQty));
    }

    void subtractQuantity(int subtractBy, AppCompatEditText etQty) {
        int avlQty = Integer.valueOf(String.valueOf(etQty.getText()));

        if (avlQty > 1) {
            avlQty = avlQty - subtractBy;
            etQty.setText(String.valueOf(avlQty));
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llForEven;
        AppCompatImageView imgProductEven;
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvProductPrice;
        ImageButton btnAddQty;
        AppCompatEditText etQty;
        ImageButton btnSubQty;
        TextViewMediumFont tvProductPoint;
        TextViewMediumFont tvTotalPoint;
        LinearLayout llForOdd;
        AppCompatImageView imgProductForOdd;
        LinearLayout ll_paceholder;
        TextViewRegularFont tvPlaceOrder;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llForEven = itemView.findViewById(R.id.llForEven);
            imgProductEven = itemView.findViewById(R.id.imgProductEven);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAddQty = itemView.findViewById(R.id.btnAddQty);
            etQty = itemView.findViewById(R.id.etQty);
            btnSubQty = itemView.findViewById(R.id.btnSubQty);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            tvTotalPoint = itemView.findViewById(R.id.tvTotalPoint);
            llForOdd = itemView.findViewById(R.id.llForOdd);
            imgProductForOdd = itemView.findViewById(R.id.imgProductForOdd);
            ll_paceholder = itemView.findViewById(R.id.ll_paceholder);
            tvPlaceOrder = itemView.findViewById(R.id.tvPlaceOrder);
        }
    }

    private void showOrderDetailsDialog(GetProductListPojo getProductListPojo, final MyViewHolder holder) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(true);
        LayoutInflater layoutInflater1 = LayoutInflater.from(context);
        View dialogView = layoutInflater1.inflate(R.layout.inflater_customer_order_dialog, null);

        initView(dialogView);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {

                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    String orderDate = sdf.format(myCalendar.getTime()) + "";
                    String customerDealerId = mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("1") ?
                            dealerHashMap.get(dealerArrayList.get(spDealer.getSelectedItemPosition())) : "0";
                    String contactPerson = etContactPersonAtSite.getText().toString();
                    String siteAddress = etSiteAddress.getText().toString();
                    String contactNo = etContactNo.getText().toString();
                    String pincode = etPinCode.getText().toString();
                    String districtId = districtHashMap.get(districtArrayList.get(spDistrict.getSelectedItemPosition()));
                    String talukaId = talukaHashMap.get(talukaArrayList.get(spTaluka.getSelectedItemPosition()));
                    String DistributorId = mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("1") ?
                            "0" : distributorHashMap.get(distributorArrayList.get(spDistributor.getSelectedItemPosition()));

                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("ProductId", getProductListPojo.getProductId() + "");
                        jsonObject.put("Qty", holder.etQty.getText().toString());
                        jsonObject.put("SinglePoint", ((int) Double.parseDouble(getProductListPojo.getCustomerPoint())) + "");
                        jsonObject.put("TotalPoint", holder.tvTotalPoint.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);

                    String productList = jsonArray.toString();

                    addCustomerOrderApiCall(orderDate, customerDealerId, contactPerson, siteAddress, contactNo,
                            pincode, districtId, talukaId, productList,mySharedPreferences.getCustomerUnderRegStatus(),DistributorId);

                }
            }
        });

        dialogBuilder.setView(dialogView);
        orderDialog = dialogBuilder.create();
        orderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        orderDialog.show();
    }

    private void initView(View dialogView) {

        etContactPersonAtSite = dialogView.findViewById(R.id.etContactPersonAtSite);
        etSiteAddress = dialogView.findViewById(R.id.etSiteAddress);
        etContactNo = dialogView.findViewById(R.id.etContactNo);
        etPinCode = dialogView.findViewById(R.id.etPinCode);

        spDistrict = dialogView.findViewById(R.id.spDistrict);
        spTaluka = dialogView.findViewById(R.id.spTaluka);
        spDealer = dialogView.findViewById(R.id.spDealer);
        spDistributor = dialogView.findViewById(R.id.spDistributor);
        btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        llDealer = dialogView.findViewById(R.id.llDealer);
        llDistributor = dialogView.findViewById(R.id.llDistributor);

        if (mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("1")){
            llDealer.setVisibility(View.VISIBLE);
            llDistributor.setVisibility(View.GONE);
        }else {
            llDistributor.setVisibility(View.VISIBLE);
            llDealer.setVisibility(View.GONE);
        }


        ArrayAdapter<String> districtListAdapter = new ArrayAdapter<String>
                (context, R.layout.custome_textview_for_sp,
                        districtArrayList);
        districtListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
        spDistrict.setAdapter(districtListAdapter);

        ArrayAdapter<String> talukaListAdapter = new ArrayAdapter<String>
                (context, R.layout.custome_textview_for_sp,
                        talukaArrayList);
        talukaListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
        spTaluka.setAdapter(talukaListAdapter);

        ArrayAdapter<String> dealerListAdapter = new ArrayAdapter<String>
                (context, R.layout.custome_textview_for_sp,
                        dealerArrayList);
        dealerListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
        spDealer.setAdapter(dealerListAdapter);

        ArrayAdapter<String> distributorListAdapter = new ArrayAdapter<String>
                (context, R.layout.custome_textview_for_sp,
                        distributorArrayList);
        distributorListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
        spDistributor.setAdapter(distributorListAdapter);

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    String districtId = districtHashMap.get(districtArrayList.get(position));
                    getTalukaApiCall(true, true, districtId);
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
                    getDealerListByTalukaIdApiCall(true, true, talukaId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(etContactPersonAtSite.getText().toString().trim())) {
            isValid = false;
            Toast.makeText(context, "Please enter contact person at site", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etSiteAddress.getText().toString().trim())) {
            isValid = false;
            Toast.makeText(context, "Please enter site address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etContactNo.getText().toString().trim()) ||
                etContactNo.getText().toString().trim().length() != 10) {
            isValid = false;
            Toast.makeText(context, "Please enter valid contact no.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etPinCode.getText().toString().trim()) ||
                etPinCode.getText().toString().trim().length() != 6) {
            isValid = false;
            Toast.makeText(context, "Please enter valid pincode", Toast.LENGTH_SHORT).show();
        } else if (spDistrict.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(context, "Please select district", Toast.LENGTH_SHORT).show();
        } else if (spTaluka.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(context, "Please select taluka", Toast.LENGTH_SHORT).show();
        } else if (mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("1") && spDealer.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(context, "Please select dealer", Toast.LENGTH_SHORT).show();
        }else if (mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("2") && spDistributor.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(context, "Please select distributor", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }


    private void getTalukaApiCall(boolean isPdShow, boolean isPdHide, String districtId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(context, "");
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

                            ArrayAdapter<String> talukaListAdapter = new ArrayAdapter<String>
                                    (context, R.layout.custome_textview_for_sp,
                                            talukaArrayList);
                            talukaListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
                            spTaluka.setAdapter(talukaListAdapter);
                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            ArrayAdapter<String> talukaListAdapter = new ArrayAdapter<String>
                                    (context, R.layout.custome_textview_for_sp,
                                            talukaArrayList);
                            talukaListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
                            spTaluka.setAdapter(talukaListAdapter);
                            Toast.makeText(context, "Taluka Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDealerListByTalukaIdApiCall(boolean isPdShow, boolean isPdHide, String talukaId) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(context, "");
        }
        ApiImplementer.getDealerListByTalukaIdApiImplementer(talukaId, new Callback<ArrayList<GetDealerListByTalukaIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerListByTalukaIdPojo>> call, Response<ArrayList<GetDealerListByTalukaIdPojo>> response) {
                if (isPdHide) {
                    DialogUtil.hideProgressDialog();
                }
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDealerListByTalukaIdPojo> getDealerListByCityIdPojoArrayList = response.body();
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

                            ArrayAdapter<String> dealerListAdapter = new ArrayAdapter<String>
                                    (context, R.layout.custome_textview_for_sp,
                                            dealerArrayList);
                            dealerListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
                            spDealer.setAdapter(dealerListAdapter);

                        } else {
                            if (!isPdHide) {
                                DialogUtil.hideProgressDialog();
                            }
                            dealerArrayList = new ArrayList<>();
                            dealerArrayList.add(SELECT_DEALER);
                            dealerHashMap = new HashMap<>();
                            ArrayAdapter<String> dealerListAdapter = new ArrayAdapter<String>
                                    (context, R.layout.custome_textview_for_sp,
                                            dealerArrayList);
                            dealerListAdapter.setDropDownViewResource(R.layout.custome_textview_for_sp);
                            spDealer.setAdapter(dealerListAdapter);
                            Toast.makeText(context, "Dealer Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!isPdHide) {
                            DialogUtil.hideProgressDialog();
                        }
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (!isPdHide) {
                        DialogUtil.hideProgressDialog();
                    }
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerListByTalukaIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCustomerOrderApiCall(String OrderDate,
                                         String customerDealerId,
                                         String ContactPersonDetail,
                                         String SiteAddress,
                                         String ContactNo,
                                         String PinCode,
                                         String DistrictId,
                                         String TalukaId,
                                         String ProductList,
                                         String OrderUnderRegisterStatus,
                                         String DistributorId) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.addCustomerOrderImplementer(OrderDate,
                mySharedPreferences.getCustomerId(),
                customerDealerId,
                ContactPersonDetail,
                SiteAddress,
                ContactNo,
                PinCode,
                DistrictId,
                TalukaId,
                ProductList,
                OrderUnderRegisterStatus,
                DistributorId, new Callback<ArrayList<AddCustomerOrderDataPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AddCustomerOrderDataPojo>> call, Response<ArrayList<AddCustomerOrderDataPojo>> response) {
                        DialogUtil.hideProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null &&
                                    response.body().size() > 0 && response.body().get(0).getStatus() == 1) {
                                if (orderDialog != null) {
                                    orderDialog.dismiss();
                                }
                                Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getMsg())) {
                                    Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AddCustomerOrderDataPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(context, "Request Failde:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
