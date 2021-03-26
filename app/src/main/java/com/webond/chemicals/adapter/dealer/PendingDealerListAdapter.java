package com.webond.chemicals.adapter.dealer;

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
import com.webond.chemicals.pojo.ApproveDealerPojo;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingDealerListAdapter extends RecyclerView.Adapter<PendingDealerListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<GetDealerListPojo> getDealerListPojoArrayList;

    public PendingDealerListAdapter(Context context, ArrayList<GetDealerListPojo> getDealerListPojoArrayList) {
        this.context = context;
        this.getDealerListPojoArrayList = getDealerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_pending_dealer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDealerListPojo getDealerListPojo = getDealerListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDealerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgPendingDealer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPedthiName())) {
            holder.tvPedthiName.setText(getDealerListPojo.getPedthiName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDealerName())) {
            holder.tvNamePendingDealer.setText(getDealerListPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getMobileNo())) {
            holder.tvMobileNoPendingDealer.setText(getDealerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getEmail())) {
            holder.tvEmailPendingDealer.setText(getDealerListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStateName())) {
            holder.tvStatePendingDealer.setText(getDealerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDistrictName())) {
            holder.tvDistrictPendingDealer.setText(getDealerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getTalukaName())) {
            holder.tvTalukaPendingDealer.setText(getDealerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getCityName())) {
            holder.tvCityPendingDealer.setText(getDealerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStatus())) {
            if (getDealerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusPendingDealer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDealerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusPendingDealer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusPendingDealer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusPendingDealer.setText(getDealerListPojo.getStatus() + "");
        }

        holder.btnApprovePendingDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDealerApiCall(getDealerListPojo.getDealerId() + "", CommonUtil.APPROVE_STATUS, position, getDealerListPojoArrayList);
            }
        });

        holder.btnRejectPendingDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectDealerApiCall(getDealerListPojo.getDealerId() + "", CommonUtil.REJECT_STATUS, position, getDealerListPojoArrayList);
            }
        });

        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayoutForDefaultOpenCard(!getDealerListPojoArrayList.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
                getDealerListPojoArrayList.get(position).setExpanded(show);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getDealerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgPendingDealer;
        TextViewMediumFont tvNamePendingDealer;
        TextViewRegularFont tvMobileNoPendingDealer;
        TextViewRegularFont tvEmailPendingDealer;
        TextViewMediumFont tvStatePendingDealer;
        TextViewMediumFont tvDistrictPendingDealer;
        TextViewMediumFont tvTalukaPendingDealer;
        TextViewMediumFont tvCityPendingDealer;
        TextViewMediumFont tvStatusPendingDealer;
        MaterialButton btnApprovePendingDealer;
        MaterialButton btnRejectPendingDealer;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPendingDealer = itemView.findViewById(R.id.imgPendingDealer);
            tvNamePendingDealer = itemView.findViewById(R.id.tvNamePendingDealer);
            tvMobileNoPendingDealer = itemView.findViewById(R.id.tvMobileNoPendingDealer);
            tvEmailPendingDealer = itemView.findViewById(R.id.tvEmailPendingDealer);
            tvStatePendingDealer = itemView.findViewById(R.id.tvStatePendingDealer);
            tvDistrictPendingDealer = itemView.findViewById(R.id.tvDistrictPendingDealer);
            tvTalukaPendingDealer = itemView.findViewById(R.id.tvTalukaPendingDealer);
            tvCityPendingDealer = itemView.findViewById(R.id.tvCityPendingDealer);
            tvStatusPendingDealer = itemView.findViewById(R.id.tvStatusPendingDealer);
            btnApprovePendingDealer = itemView.findViewById(R.id.btnApprovePendingDealer);
            btnRejectPendingDealer = itemView.findViewById(R.id.btnRejectPendingDealer);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
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
                            notifyItemRemoved(position);
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

}
