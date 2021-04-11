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
import com.webond.chemicals.pojo.DeleteCustomerPojo;
import com.webond.chemicals.pojo.GetCustomerListForDistributorPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorManagePendingCustomerForDistOnlyAdapter extends RecyclerView.Adapter<DistributorManagePendingCustomerForDistOnlyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetCustomerListForDistributorPojo> getCustomerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DistributorManagePendingCustomerForDistOnlyAdapter(Context context, ArrayList<GetCustomerListForDistributorPojo> getCustomerListPojoArrayList) {
        this.context = context;
        this.getCustomerListPojoArrayList = getCustomerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DistributorManagePendingCustomerForDistOnlyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_pending_customer_list_item, parent, false);
        return new DistributorManagePendingCustomerForDistOnlyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorManagePendingCustomerForDistOnlyAdapter.MyViewHolder holder, int position) {
        GetCustomerListForDistributorPojo getCustomerListPojo = getCustomerListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getCustomerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgPendingCustomer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCustomerName())) {
            holder.tvNamePendingCustomer.setText(getCustomerListPojo.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getMobileNo())) {
            holder.tvMobileNoPendingCustomer.setText(getCustomerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getEmailId())) {
            holder.tvEmailPendingCustomer.setText(getCustomerListPojo.getEmailId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStateName())) {
            holder.tvStatePendingCustomer.setText(getCustomerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getDistrictName())) {
            holder.tvDistrictPendingCustomer.setText(getCustomerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getTalukaName())) {
            holder.tvTalukaPendingCustomer.setText(getCustomerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCityName())) {
            holder.tvCityPendingCustomer.setText(getCustomerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStatus())) {
            if (getCustomerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusPendingCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getCustomerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusPendingCustomer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusPendingCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusPendingCustomer.setText(getCustomerListPojo.getStatus() + "");
        }

        holder.btnApprovePendingCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectCustomer(getCustomerListPojo.getCustomerId() + "", CommonUtil.APPROVE_STATUS, position, getCustomerListPojoArrayList);
            }
        });

        holder.btnRejectPendingCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrRejectCustomer(getCustomerListPojo.getCustomerId() + "", CommonUtil.REJECT_STATUS, position, getCustomerListPojoArrayList);
            }
        });

        holder.btnDeleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomerApiCall(getCustomerListPojo.getCustomerId() + "",  position, getCustomerListPojoArrayList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getCustomerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgPendingCustomer;
        TextViewMediumFont tvNamePendingCustomer;
        TextViewRegularFont tvMobileNoPendingCustomer;
        TextViewRegularFont tvEmailPendingCustomer;
        TextViewMediumFont tvStatePendingCustomer;
        TextViewMediumFont tvDistrictPendingCustomer;
        TextViewMediumFont tvTalukaPendingCustomer;
        TextViewMediumFont tvCityPendingCustomer;
        TextViewMediumFont tvStatusPendingCustomer;
        MaterialButton btnApprovePendingCustomer;
        MaterialButton btnRejectPendingCustomer;
        MaterialButton btnDeleteCustomer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPendingCustomer = itemView.findViewById(R.id.imgPendingCustomer);
            tvNamePendingCustomer = itemView.findViewById(R.id.tvNamePendingCustomer);
            tvMobileNoPendingCustomer = itemView.findViewById(R.id.tvMobileNoPendingCustomer);
            tvEmailPendingCustomer = itemView.findViewById(R.id.tvEmailPendingCustomer);
            tvStatePendingCustomer = itemView.findViewById(R.id.tvStatePendingCustomer);
            tvDistrictPendingCustomer = itemView.findViewById(R.id.tvDistrictPendingCustomer);
            tvTalukaPendingCustomer = itemView.findViewById(R.id.tvTalukaPendingCustomer);
            tvCityPendingCustomer = itemView.findViewById(R.id.tvCityPendingCustomer);
            tvStatusPendingCustomer = itemView.findViewById(R.id.tvStatusPendingCustomer);
            btnApprovePendingCustomer = itemView.findViewById(R.id.btnApprovePendingCustomer);
            btnRejectPendingCustomer = itemView.findViewById(R.id.btnRejectPendingCustomer);
            btnDeleteCustomer = itemView.findViewById(R.id.btnDeleteCustomer);
        }
    }

    private void deleteCustomerApiCall(String customerId, int position, ArrayList<GetCustomerListForDistributorPojo> GetCustomerListPojo) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.deleteCustomerApiImplementer(customerId, new Callback<ArrayList<DeleteCustomerPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<DeleteCustomerPojo>> call, Response<ArrayList<DeleteCustomerPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        if (response.body().get(0).getStatus() == 1) {
                            GetCustomerListPojo.remove(position);
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
            public void onFailure(Call<ArrayList<DeleteCustomerPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

