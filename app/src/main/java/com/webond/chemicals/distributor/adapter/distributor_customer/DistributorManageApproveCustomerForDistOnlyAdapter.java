package com.webond.chemicals.distributor.adapter.distributor_customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.ApproveCustomerPojo;
import com.webond.chemicals.pojo.GetCustomerListForDistributorPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorManageApproveCustomerForDistOnlyAdapter extends RecyclerView.Adapter<DistributorManageApproveCustomerForDistOnlyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetCustomerListForDistributorPojo> getCustomerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DistributorManageApproveCustomerForDistOnlyAdapter(Context context, ArrayList<GetCustomerListForDistributorPojo> getCustomerListPojoArrayList) {
        this.context = context;
        this.getCustomerListPojoArrayList = getCustomerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DistributorManageApproveCustomerForDistOnlyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_customer_list_item, parent, false);
        return new DistributorManageApproveCustomerForDistOnlyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorManageApproveCustomerForDistOnlyAdapter.MyViewHolder holder, int position) {
        GetCustomerListForDistributorPojo getCustomerListPojo = getCustomerListPojoArrayList.get(position);

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
        }

        holder.btnRejectApprovedCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectCustomer(getCustomerListPojo.getCustomerId() + "", CommonUtil.REJECT_STATUS, position, getCustomerListPojoArrayList);
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
        }
    }

    private void approveOrRejectCustomer(String CustomerId, String status, int position, ArrayList<GetCustomerListForDistributorPojo> getCustomerListPojoArrayList) {
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


}
