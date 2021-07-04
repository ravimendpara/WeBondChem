package com.webond.chemicals.admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
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
import com.webond.chemicals.pojo.DeleteOrderPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllDistributorOrderAdapter extends RecyclerView.Adapter<AdminAllDistributorOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminAllDistributorOrderAdapter(Context context, ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList) {
        this.context = context;
        this.getDistributorOrderListPojoArrayList = getDistributorOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_distributor_all_order_list_item, parent, false);
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

            //Toast.makeText(context.getApplicationContext(),getDistributorOrderListPojo.getStatus(),Toast.LENGTH_LONG).show();

            if (getDistributorOrderListPojo.getStatus().equals("Reject")) {
                holder.btnDeleteOrder.setVisibility(View.VISIBLE);
            } else {
                holder.btnDeleteOrder.setVisibility(View.GONE);
            }
        }

        holder.btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DeleteOrder(getDistributorOrderListPojo.getOrderId() + "", CommonUtil.DELETE_STATUS,
                        //position,getDistributorOrderListPojoArrayList);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to delete ?. Once you delete this order can't retrive.");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        DeleteOrder(getDistributorOrderListPojo.getOrderId() + "", CommonUtil.DELETE_STATUS,
                        position,getDistributorOrderListPojoArrayList);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return getDistributorOrderListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvTotalPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvDistributorName;
        MaterialButton btnApprove;
        MaterialButton btnReject;
        MaterialButton btnDeleteOrder;

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
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
            tvDistributorName = itemView.findViewById(R.id.tvDistributorName);
        }
    }

    private void DeleteOrder(String orderId, String status,int pos,ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.DeleteOrderApiImplementer(orderId, status, new Callback<ArrayList<DeleteOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<DeleteOrderPojo>> call, Response<ArrayList<DeleteOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1) {
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
            public void onFailure(Call<ArrayList<DeleteOrderPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
