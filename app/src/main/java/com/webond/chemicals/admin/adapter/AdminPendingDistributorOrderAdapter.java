package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.ApproveOrderPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPendingDistributorOrderAdapter extends RecyclerView.Adapter<AdminPendingDistributorOrderAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminPendingDistributorOrderAdapter(Context context, ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList) {
        this.context = context;
        this.getDistributorOrderListPojoArrayList = getDistributorOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_distributor_pending_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDistributorOrderListPojo getDistributorOrderListPojo = getDistributorOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getDistributorName())) {
            holder.tvDistributorName.setText(getDistributorOrderListPojo.getDistributorName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getDistributorOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getDistributorOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getDistributorOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getDistributorOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getTotalPoint())) {
            holder.tvTotalPoint.setText(getDistributorOrderListPojo.getTotalPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getDistributorOrderListPojo.getStatus() + "");
        }

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveDistributorOrder(getDistributorOrderListPojo.getOrderId() + "", CommonUtil.APPROVE_STATUS,
                        position,getDistributorOrderListPojoArrayList);
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveDistributorOrder(getDistributorOrderListPojo.getOrderId() + "", CommonUtil.REJECT_STATUS,
                        position,getDistributorOrderListPojoArrayList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getDistributorOrderListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvTotalPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvDistributorName;
        MaterialButton btnApprove;
        MaterialButton btnReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPoint = itemView.findViewById(R.id.tvTotalPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
            tvDistributorName = itemView.findViewById(R.id.tvDistributorName);
        }
    }

    private void approveDistributorOrder(String orderId, String status,int pos,ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveOrderApiImplementer(orderId, status, new Callback<ArrayList<ApproveOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveOrderPojo>> call, Response<ArrayList<ApproveOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                    response.body().get(0).getStatus() == 1 ) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getDistributorOrderListPojoArrayList.remove(pos);
                        notifyDataSetChanged();
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
            public void onFailure(Call<ArrayList<ApproveOrderPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
