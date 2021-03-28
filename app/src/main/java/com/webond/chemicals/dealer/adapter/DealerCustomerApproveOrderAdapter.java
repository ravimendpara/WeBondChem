package com.webond.chemicals.dealer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.ApproveOrderPojo;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerCustomerApproveOrderAdapter extends RecyclerView.Adapter<DealerCustomerApproveOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DealerCustomerApproveOrderAdapter(Context context, ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList) {
        this.context = context;
        this.getCustomerOrderListPojoArrayList = getCustomerOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_manage_customer_approve_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCustomerOrderListPojo getCustomerOrderListPojo = getCustomerOrderListPojoArrayList.get(position);

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

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getPoint())) {
            holder.tvProductPoint.setText(getCustomerOrderListPojo.getPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getCustomerOrderListPojo.getStatus() + "");
        }

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveOrder(getCustomerOrderListPojo.getOrderId() + "", CommonUtil.REJECT_STATUS,
                        position,getCustomerOrderListPojoArrayList);
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
        TextViewMediumFont tvProductPoint;
        TextViewMediumFont tvStatus;
        MaterialButton btnReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }

    private void approveOrder(String orderId, String status,int pos,ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveOrderApiImplementer(orderId, status, new Callback<ArrayList<ApproveOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveOrderPojo>> call, Response<ArrayList<ApproveOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1 ) {
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
            public void onFailure(Call<ArrayList<ApproveOrderPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
