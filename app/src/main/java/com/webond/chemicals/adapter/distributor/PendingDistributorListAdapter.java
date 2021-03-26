package com.webond.chemicals.adapter.distributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.CustomAnimationForDefaultExpandableCard;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.ApproveDistributorPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingDistributorListAdapter extends RecyclerView.Adapter<PendingDistributorListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDistributorListPojo> getDistributorArrayList;
    private LayoutInflater layoutInflater;

    public PendingDistributorListAdapter(Context context, ArrayList<GetDistributorListPojo> getDistributorArrayList) {
        this.context = context;
        this.getDistributorArrayList = getDistributorArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_pending_distributor_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDistributorListPojo getDistributorListPojo = getDistributorArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDistributorListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgPendingDistributor);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPethiName())) {
            holder.tvPedthiName.setText(getDistributorListPojo.getPethiName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistributorName())) {
            holder.tvNamePendingDistributor.setText(getDistributorListPojo.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getMobileNo())) {
            holder.tvMobileNoPendingDistributor.setText(getDistributorListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getEmail())) {
            holder.tvEmailPendingDistributor.setText(getDistributorListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStateName())) {
            holder.tvStatePendingDistributor.setText(getDistributorListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistrictName())) {
            holder.tvDistrictPendingDistributor.setText(getDistributorListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getTalukaName())) {
            holder.tvTalukaPendingDistributor.setText(getDistributorListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getCityName())) {
            holder.tvCityPendingDistributor.setText(getDistributorListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStatus())) {
            if (getDistributorListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusPendingDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDistributorListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusPendingDistributor.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusPendingDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusPendingDistributor.setText(getDistributorListPojo.getStatus() + "");
        }

        holder.btnApprovePendingDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDistributorApiCall(getDistributorListPojo.getDistributorId() + "", CommonUtil.APPROVE_STATUS, position, getDistributorArrayList);
            }
        });

        holder.btnRejectPendingDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDistributorApiCall(getDistributorListPojo.getDistributorId() + "", CommonUtil.REJECT_STATUS, position, getDistributorArrayList);
            }
        });

        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayoutForDefaultOpenCard(!getDistributorArrayList.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
                getDistributorArrayList.get(position).setExpanded(show);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getDistributorArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgPendingDistributor;
        TextViewMediumFont tvNamePendingDistributor;
        TextViewRegularFont tvMobileNoPendingDistributor;
        TextViewRegularFont tvEmailPendingDistributor;
        TextViewMediumFont tvStatePendingDistributor;
        TextViewMediumFont tvDistrictPendingDistributor;
        TextViewMediumFont tvTalukaPendingDistributor;
        TextViewMediumFont tvCityPendingDistributor;
        TextViewMediumFont tvStatusPendingDistributor;
        MaterialButton btnApprovePendingDistributor;
        MaterialButton btnRejectPendingDistributor;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
            imgPendingDistributor = itemView.findViewById(R.id.imgPendingDistributor);
            tvNamePendingDistributor = itemView.findViewById(R.id.tvNamePendingDistributor);
            tvMobileNoPendingDistributor = itemView.findViewById(R.id.tvMobileNoPendingDistributor);
            tvEmailPendingDistributor = itemView.findViewById(R.id.tvEmailPendingDistributor);
            tvStatePendingDistributor = itemView.findViewById(R.id.tvStatePendingDistributor);
            tvDistrictPendingDistributor = itemView.findViewById(R.id.tvDistrictPendingDistributor);
            tvTalukaPendingDistributor = itemView.findViewById(R.id.tvTalukaPendingDistributor);
            tvCityPendingDistributor = itemView.findViewById(R.id.tvCityPendingDistributor);
            tvStatusPendingDistributor = itemView.findViewById(R.id.tvStatusPendingDistributor);
            btnApprovePendingDistributor = itemView.findViewById(R.id.btnApprovePendingDistributor);
            btnRejectPendingDistributor = itemView.findViewById(R.id.btnRejectPendingDistributor);
        }
    }

    private boolean toggleLayoutForDefaultOpenCard(boolean isExpanded, View v, LinearLayout layoutExpand) {
        CustomAnimationForDefaultExpandableCard.toggleArrow(v, isExpanded);
        if (isExpanded) {
            CustomAnimationForDefaultExpandableCard.expand(layoutExpand);
        } else {
            CustomAnimationForDefaultExpandableCard.collapse(layoutExpand);
        }
        return isExpanded;
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

}
