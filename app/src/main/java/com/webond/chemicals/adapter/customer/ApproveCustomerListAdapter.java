package com.webond.chemicals.adapter.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.customer.activity.CustomerDashboardActivity;
import com.webond.chemicals.pojo.ApproveCustomerPojo;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveCustomerListAdapter extends RecyclerView.Adapter<ApproveCustomerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList;
    private LayoutInflater layoutInflater;
    private MySharedPreferences mySharedPreferences;

    public ApproveCustomerListAdapter(Context context, ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList) {
        this.context = context;
        this.getCustomerListPojoArrayList = getCustomerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_customer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCustomerListPojo getCustomerListPojo = getCustomerListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getCustomerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveCustomer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCustomerName())) {
            holder.tvNameApproveCustomer.setText(getCustomerListPojo.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getMobileNo())) {
            holder.tvMobileNoApproveCustomer.setText(getCustomerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getEmailId())) {
            holder.tvEmailApproveCustomer.setText(getCustomerListPojo.getEmailId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStateName())) {
            holder.tvStateApproveCustomer.setText(getCustomerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getDistrictName())) {
            holder.tvDistrictApproveCustomer.setText(getCustomerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getTalukaName())) {
            holder.tvTalukaApproveCustomer.setText(getCustomerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCityName())) {
            holder.tvCityApproveCustomer.setText(getCustomerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStatus())) {
            if (getCustomerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getCustomerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveCustomer.setText(getCustomerListPojo.getStatus() + "");

            if (mySharedPreferences.getLoginType().equalsIgnoreCase(CommonUtil.LOGIN_TYPE_ADMIN) && getCustomerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.btnLoginCDD.setVisibility(View.VISIBLE);
            } else {
                holder.btnLoginCDD.setVisibility(View.GONE);
            }

            //Toast.makeText(context.getApplicationContext(),mySharedPreferences.getLoginType(),Toast.LENGTH_LONG).show();


        }

        holder.btnRejectApprovedCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectCustomer(getCustomerListPojo.getCustomerId() + "", CommonUtil.REJECT_STATUS, position, getCustomerListPojoArrayList);
            }
        });

        holder.btnLoginCDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetailsForLoginUserCustomer(true, true, getCustomerListPojo.getMobileNo());

            }
        });

    }

    @Override
    public int getItemCount() {
        return getCustomerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveCustomer;
        TextViewMediumFont tvNameApproveCustomer;
        TextViewRegularFont tvMobileNoApproveCustomer;
        TextViewRegularFont tvEmailApproveCustomer;
        TextViewMediumFont tvStateApproveCustomer;
        TextViewMediumFont tvDistrictApproveCustomer;
        TextViewMediumFont tvTalukaApproveCustomer;
        TextViewMediumFont tvCityApproveCustomer;
        TextViewMediumFont tvStatusApproveCustomer;
        MaterialButton btnRejectApprovedCustomer;
        MaterialButton btnLoginCDD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgApproveCustomer = itemView.findViewById(R.id.imgApproveCustomer);
            tvNameApproveCustomer = itemView.findViewById(R.id.tvNameApproveCustomer);
            tvMobileNoApproveCustomer = itemView.findViewById(R.id.tvMobileNoApproveCustomer);
            tvEmailApproveCustomer = itemView.findViewById(R.id.tvEmailApproveCustomer);
            tvStateApproveCustomer = itemView.findViewById(R.id.tvStateApproveCustomer);
            tvDistrictApproveCustomer = itemView.findViewById(R.id.tvDistrictApproveCustomer);
            tvTalukaApproveCustomer = itemView.findViewById(R.id.tvTalukaApproveCustomer);
            tvCityApproveCustomer = itemView.findViewById(R.id.tvCityApproveCustomer);
            tvStatusApproveCustomer = itemView.findViewById(R.id.tvStatusApproveCustomer);
            btnRejectApprovedCustomer = itemView.findViewById(R.id.btnRejectApprovedCustomer);
            btnLoginCDD = itemView.findViewById(R.id.btnLoginCDD);

            mySharedPreferences = new MySharedPreferences(context);
        }
    }

    private void approveOrRejectCustomer(String CustomerId, String status, int position, ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveCustomerImplementer(CustomerId, status, new Callback<ArrayList<ApproveCustomerPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveCustomerPojo>> call, Response<ArrayList<ApproveCustomerPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        if (response.body().get(0).getStatus() == 1) {
                            getCustomerListPojoArrayList.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ApproveCustomerPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailsForLoginUserCustomer(boolean isPdShow, boolean isPdHide, String edtMobileNo) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(context, "");
        }
        ApiImplementer.getDetailsForLoginUserCustomerImplementer(edtMobileNo.toString().trim(),
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
                                ActivityCompat.finishAffinity((Activity)context);
                                Intent intent = new Intent(context, CustomerDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                                //int pid = android.os.Process.myPid();
                                //android.os.Process.killProcess(pid);
                                //Intent intent = new Intent(Intent.ACTION_MAIN);
                                //intent.addCategory(Intent.CATEGORY_HOME);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //context.startActivity(intent);
                            } else {
                                if (!isPdHide) {
                                    DialogUtil.hideProgressDialog();
                                }
                                Toast.makeText(context, "Failed to get login details.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setDataForCustomer(GetDetailsForLoginUserCustomerPojo getDetailsForLoginUserCustomerPojo) {

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCustomerUnderRegiterStatus())) {
            mySharedPreferences.setCustomerUnderRegStatus(getDetailsForLoginUserCustomerPojo.getCustomerUnderRegiterStatus() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCustomerUnderRegiter())) {
            mySharedPreferences.setCustomerUnderReg(getDetailsForLoginUserCustomerPojo.getCustomerUnderRegiter() + "");
        }

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

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getCityId())) {
            mySharedPreferences.setCustomerCityId(getDetailsForLoginUserCustomerPojo.getCityId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getTalukaId())) {
            mySharedPreferences.setCustomerTalukaId(getDetailsForLoginUserCustomerPojo.getTalukaId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getDistrictId())) {
            mySharedPreferences.setCustomerDistrictId(getDetailsForLoginUserCustomerPojo.getDistrictId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getStateId())) {
            mySharedPreferences.setCustomerStateId(getDetailsForLoginUserCustomerPojo.getStateId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserCustomerPojo.getDealerId())) {
            mySharedPreferences.setCustomerDealerId(getDetailsForLoginUserCustomerPojo.getDealerId() + "");
        }
    }

}
