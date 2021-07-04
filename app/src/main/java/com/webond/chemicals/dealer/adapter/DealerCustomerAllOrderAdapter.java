package com.webond.chemicals.dealer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.webond.chemicals.pojo.DeleteOrderPojo;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerCustomerAllOrderAdapter extends RecyclerView.Adapter<DealerCustomerAllOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DealerCustomerAllOrderAdapter(Context context, ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList) {
        this.context = context;
        this.getCustomerOrderListPojoArrayList = getCustomerOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_manage_customer_all_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCustomerOrderListPojo getCustomerOrderListPojo = getCustomerOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getCustomerName())) {
            holder.tvCustomerName.setText(getCustomerOrderListPojo.getCustomerName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getCustomerOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getCustomerOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getCustomerOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getCustomerOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getTotalPoint())) {
            holder.tvTotalPoint.setText(getCustomerOrderListPojo.getTotalPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getCustomerOrderListPojo.getStatus() + "");

            if (getCustomerOrderListPojo.getStatus().equals("Reject")) {
                holder.btnDeleteOrder.setVisibility(View.VISIBLE);
            } else {
                holder.btnDeleteOrder.setVisibility(View.GONE);
            }

        }

        holder.btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to delete ?. Once you delete this order can't retrive.");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        DeleteOrder(getCustomerOrderListPojo.getOrderId() + "", CommonUtil.DELETE_STATUS,
                                position,getCustomerOrderListPojoArrayList);
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
        return getCustomerOrderListPojoArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvTotalPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvCustomerName;
        MaterialButton btnDeleteOrder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPoint = itemView.findViewById(R.id.tvTotalPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }

    private void DeleteOrder(String orderId, String status,int pos,ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.DeleteOrderApiImplementer(orderId, status, new Callback<ArrayList<DeleteOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<DeleteOrderPojo>> call, Response<ArrayList<DeleteOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getCustomerOrderListPojoArrayList.remove(pos);
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
