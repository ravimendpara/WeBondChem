package com.webond.chemicals.adapter.distributor;

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
import com.webond.chemicals.distributor.activity.DistributorDashboardActivity;
import com.webond.chemicals.pojo.ApproveDistributorPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDistributorPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveDistributorListAdapter extends RecyclerView.Adapter<ApproveDistributorListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDistributorListPojo> getDistributorListPojos;
    private LayoutInflater layoutInflater;
    private boolean isLoaded = false;
    private MySharedPreferences mySharedPreferences;

    public ApproveDistributorListAdapter(Context context, ArrayList<GetDistributorListPojo> getDistributorListPojos) {
        this.context = context;
        this.getDistributorListPojos = getDistributorListPojos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_distributor_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDistributorListPojo getDistributorListPojo = getDistributorListPojos.get(position);

        if (!isLoaded && position == 0) {
            boolean show = toggleLayout(!getDistributorListPojos.get(position).isExpanded(), holder.
                    ivViewMoreBtn, holder.llExpandableLayout);
            getDistributorListPojos.get(position).setExpanded(show);
            isLoaded = true;
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDistributorListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveDistributor);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPethiName())) {
            holder.tvPedthiName.setText(getDistributorListPojo.getPethiName() + "");
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistributorName())) {
            holder.tvNameApproveDistributor.setText(getDistributorListPojo.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getMobileNo())) {
            holder.tvMobileNoApproveDistributor.setText(getDistributorListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getEmail())) {
            holder.tvEmailApproveDistributor.setText(getDistributorListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStateName())) {
            holder.tvStateApproveDistributor.setText(getDistributorListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistrictName())) {
            holder.tvDistrictApproveDistributor.setText(getDistributorListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getTalukaName())) {
            holder.tvTalukaApproveDistributor.setText(getDistributorListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getCityName())) {
            holder.tvCityApproveDistributor.setText(getDistributorListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStatus())) {
            if (getDistributorListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDistributorListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveDistributor.setText(getDistributorListPojo.getStatus() + "");

            if (mySharedPreferences.getLoginType().equalsIgnoreCase(CommonUtil.LOGIN_TYPE_ADMIN) && getDistributorListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.btnLoginCDD.setVisibility(View.VISIBLE);
            } else {
                holder.btnLoginCDD.setVisibility(View.GONE);
            }

            //Toast.makeText(context.getApplicationContext(),mySharedPreferences.getLoginType(),Toast.LENGTH_LONG).show();
        }

        holder.btnRejectApprovedDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDistributorApiCall(getDistributorListPojo.getDistributorId() + "", CommonUtil.REJECT_STATUS, position, getDistributorListPojos);
            }
        });


        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!getDistributorListPojos.get(position).isExpanded(), holder.
                        ivViewMoreBtn, holder.llExpandableLayout);
                getDistributorListPojos.get(position).setExpanded(show);
            }
        });

        holder.btnLoginCDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetailsForLoginUserDistributor(true, true, getDistributorListPojo.getMobileNo());

            }
        });

    }

    @Override
    public int getItemCount() {
        return getDistributorListPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveDistributor;
        TextViewMediumFont tvNameApproveDistributor;
        TextViewRegularFont tvMobileNoApproveDistributor;
        TextViewRegularFont tvEmailApproveDistributor;
        TextViewMediumFont tvStateApproveDistributor;
        TextViewMediumFont tvDistrictApproveDistributor;
        TextViewMediumFont tvTalukaApproveDistributor;
        TextViewMediumFont tvCityApproveDistributor;
        TextViewMediumFont tvStatusApproveDistributor;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;

        MaterialButton btnRejectApprovedDistributor;
        MaterialButton btnLoginCDD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            imgApproveDistributor = itemView.findViewById(R.id.imgApproveDistributor);
            tvNameApproveDistributor = itemView.findViewById(R.id.tvNameApproveDistributor);
            tvMobileNoApproveDistributor = itemView.findViewById(R.id.tvMobileNoApproveDistributor);
            tvEmailApproveDistributor = itemView.findViewById(R.id.tvEmailApproveDistributor);
            tvStateApproveDistributor = itemView.findViewById(R.id.tvStateApproveDistributor);
            tvDistrictApproveDistributor = itemView.findViewById(R.id.tvDistrictApproveDistributor);
            tvTalukaApproveDistributor = itemView.findViewById(R.id.tvTalukaApproveDistributor);
            tvCityApproveDistributor = itemView.findViewById(R.id.tvCityApproveDistributor);
            tvStatusApproveDistributor = itemView.findViewById(R.id.tvStatusApproveDistributor);
            btnRejectApprovedDistributor = itemView.findViewById(R.id.btnRejectApprovedDistributor);

            btnLoginCDD = itemView.findViewById(R.id.btnLoginCDD);

            mySharedPreferences = new MySharedPreferences(context);

        }
    }

    private void approveOrRejectDistributorApiCall(String distributorId, String status, int position, ArrayList<GetDistributorListPojo> getDistributorListPojos) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveDistributorImplementer(distributorId, status, new Callback<ArrayList<ApproveDistributorPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveDistributorPojo>> call, Response<ArrayList<ApproveDistributorPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        if (response.body().get(0).getStatus() == 1) {
                            getDistributorListPojos.remove(position);
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
            public void onFailure(Call<ArrayList<ApproveDistributorPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void getDetailsForLoginUserDistributor(boolean isPdShow, boolean isPdHide, String edtMobileNo) {
        if (isPdShow) {
            DialogUtil.showProgressDialogNotCancelable(this.context, "");
        }
        ApiImplementer.getDetailsForLoginUserDistributorImplementer(edtMobileNo.trim(),
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
                                ActivityCompat.finishAffinity((Activity) context);
                                Intent intent = new Intent(context, DistributorDashboardActivity.class);
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
                    public void onFailure(Call<ArrayList<GetDetailsForLoginUserDistributorPojo>> call, Throwable t) {
                        DialogUtil.hideProgressDialog();
                        Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getCityId())) {
            mySharedPreferences.setDistributorCityId(getDetailsForLoginUserDistributorPojo.getCityId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getTalukaId())) {
            mySharedPreferences.setDistributorTalukaId(getDetailsForLoginUserDistributorPojo.getTalukaId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getDistrictId())) {
            mySharedPreferences.setDistributorDistrictId(getDetailsForLoginUserDistributorPojo.getDistrictId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDetailsForLoginUserDistributorPojo.getStateId())) {
            mySharedPreferences.setDistributorStateId(getDetailsForLoginUserDistributorPojo.getStateId() + "");
        }
    }

}
