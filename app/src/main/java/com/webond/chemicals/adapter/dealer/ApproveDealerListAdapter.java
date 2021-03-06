package com.webond.chemicals.adapter.dealer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.LoginActivity;
import com.webond.chemicals.custom_class.Animations;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.customer.activity.CustomerDashboardActivity;
import com.webond.chemicals.dealer.activity.DealerDashboardActivity;
import com.webond.chemicals.pojo.ApproveDealerPojo;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveDealerListAdapter extends RecyclerView.Adapter<ApproveDealerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDealerListPojo> getDealerListPojoArrayList;
    private LayoutInflater layoutInflater;
    private boolean isLoaded = false;
    private MySharedPreferences mySharedPreferences;

    public ApproveDealerListAdapter(Context context, ArrayList<GetDealerListPojo> getDealerListPojoArrayList) {
        this.context = context;
        this.getDealerListPojoArrayList = getDealerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_dealer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDealerListPojo getDealerListPojo = getDealerListPojoArrayList.get(position);

        if (!isLoaded && position == 0) {
            boolean show = toggleLayout(!getDealerListPojoArrayList.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
            getDealerListPojoArrayList.get(position).setExpanded(show);
            isLoaded = true;
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDealerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveDealer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPedthiName())) {
            holder.tvPedthiName.setText(getDealerListPojo.getPedthiName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDealerName())) {
            holder.tvNameApproveDealer.setText(getDealerListPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getMobileNo())) {
            holder.tvMobileNoApproveDealer.setText(getDealerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getEmail())) {
            holder.tvEmailApproveDealer.setText(getDealerListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStateName())) {
            holder.tvStateApproveDealer.setText(getDealerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDistrictName())) {
            holder.tvDistrictApproveDealer.setText(getDealerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getTalukaName())) {
            holder.tvTalukaApproveDealer.setText(getDealerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getCityName())) {
            holder.tvCityApproveDealer.setText(getDealerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStatus())) {
            if (getDealerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDealerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveDealer.setText(getDealerListPojo.getStatus() + "");

            if (mySharedPreferences.getLoginType().equalsIgnoreCase(CommonUtil.LOGIN_TYPE_ADMIN) && getDealerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.btnLoginCDD.setVisibility(View.VISIBLE);
            } else {
                holder.btnLoginCDD.setVisibility(View.GONE);
            }

            //Toast.makeText(context.getApplicationContext(),mySharedPreferences.getLoginType(),Toast.LENGTH_LONG).show();

        }
        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!getDealerListPojoArrayList.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
                getDealerListPojoArrayList.get(position).setExpanded(show);
            }
        });
        holder.btnRejectApprovedDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDealerApiCall(getDealerListPojo.getDealerId() + "", CommonUtil.REJECT_STATUS, position, getDealerListPojoArrayList);
            }
        });

        holder.btnLoginCDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetailsForLoginUserDealer(true, true, getDealerListPojo.getMobileNo());

            }
        });

    }

    @Override
    public int getItemCount() {
        return getDealerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveDealer;
        TextViewMediumFont tvNameApproveDealer;
        TextViewRegularFont tvMobileNoApproveDealer;
        TextViewRegularFont tvEmailApproveDealer;
        TextViewMediumFont tvStateApproveDealer;
        TextViewMediumFont tvDistrictApproveDealer;
        TextViewMediumFont tvTalukaApproveDealer;
        TextViewMediumFont tvCityApproveDealer;
        TextViewMediumFont tvStatusApproveDealer;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;

        MaterialButton btnRejectApprovedDealer;
        MaterialButton btnLoginCDD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgApproveDealer = itemView.findViewById(R.id.imgApproveDealer);
            tvNameApproveDealer = itemView.findViewById(R.id.tvNameApproveDealer);
            tvMobileNoApproveDealer = itemView.findViewById(R.id.tvMobileNoApproveDealer);
            tvEmailApproveDealer = itemView.findViewById(R.id.tvEmailApproveDealer);
            tvStateApproveDealer = itemView.findViewById(R.id.tvStateApproveDealer);
            tvDistrictApproveDealer = itemView.findViewById(R.id.tvDistrictApproveDealer);
            tvTalukaApproveDealer = itemView.findViewById(R.id.tvTalukaApproveDealer);
            tvCityApproveDealer = itemView.findViewById(R.id.tvCityApproveDealer);
            tvStatusApproveDealer = itemView.findViewById(R.id.tvStatusApproveDealer);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
            btnRejectApprovedDealer = itemView.findViewById(R.id.btnRejectApprovedDealer);

            btnLoginCDD = itemView.findViewById(R.id.btnLoginCDD);

            mySharedPreferences = new MySharedPreferences(context);

        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

    private void approveOrRejectDealerApiCall(String DealerId, String status, int position, ArrayList<GetDealerListPojo> getDealerListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveDealerImplementer(DealerId, status, new Callback<ArrayList<ApproveDealerPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveDealerPojo>> call, Response<ArrayList<ApproveDealerPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        if (response.body().get(0).getStatus() == 1) {
                            getDealerListPojoArrayList.remove(position);
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
            public void onFailure(Call<ArrayList<ApproveDealerPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailsForLoginUserDealer(boolean isPdShow, boolean isPdHide, String edtMobileNo) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(this.context, "");
        }
        ApiImplementer.getDetailsForLoginUserDealerImplementer(edtMobileNo.trim(),
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

                                ActivityCompat.finishAffinity((Activity) context);
                                Intent intent = new Intent(context, DealerDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity) context).finish();

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
                    public void onFailure(Call<ArrayList<GetDetailsForLoginUserDealerPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setDataForDealer(GetDetailsForLoginUserDealerPojo getDetailsForLoginUserDealerPojo) {

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDealerRegisterUnderStatus())) {
            mySharedPreferences.setDealerUnderRegStatus(getDetailsForLoginUserDealerPojo.getDealerRegisterUnderStatus() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDealerRegisterUnder())) {
            mySharedPreferences.setDealerUnderReg(getDetailsForLoginUserDealerPojo.getDealerRegisterUnder() + "");
        }

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

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getCityId())) {
            mySharedPreferences.setDealerCityId(getDetailsForLoginUserDealerPojo.getCityId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getTalukaId())) {
            mySharedPreferences.setDealerTalukaId(getDetailsForLoginUserDealerPojo.getTalukaId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getDistrictId())) {
            mySharedPreferences.setDealerDistrictId(getDetailsForLoginUserDealerPojo.getDistrictId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDealerPojo.getStateId())) {
            mySharedPreferences.setDealerStateId(getDetailsForLoginUserDealerPojo.getStateId() + "");
        }
    }
}
