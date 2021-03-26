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
import com.webond.chemicals.custom_class.Animations;
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

public class ApproveDistributorListAdapter extends RecyclerView.Adapter<ApproveDistributorListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDistributorListPojo> getDistributorListPojos;
    private LayoutInflater layoutInflater;
    private boolean isLoaded = false;

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
                            notifyItemRangeChanged(position, getDistributorListPojos.size());
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

}
