package com.webond.chemicals.distributor.adapter;

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
import com.webond.chemicals.pojo.ApproveOrderPojo;
import com.webond.chemicals.pojo.DeleteOrderPojo;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorPendingDealerOrderAdapter extends RecyclerView.Adapter<DistributorPendingDealerOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DistributorPendingDealerOrderAdapter(Context context, ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArrayList) {
        this.context = context;
        this.getDealerOrderListPojoArrayList = getDealerOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_manage_dealer_pending_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDealerOrderListPojo getDealerOrderListPojo = getDealerOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getDealerName())) {
            holder.tvDealerName.setText(getDealerOrderListPojo.getDealerName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getDealerOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getDealerOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getDealerOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getDealerOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getTotalPoint())) {
            holder.tvTotalPoint.setText(getDealerOrderListPojo.getTotalPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getDealerOrderListPojo.getStatus() + "");

            if (getDealerOrderListPojo.getStatus().equals("Reject")) {
                holder.btnDeleteOrder.setVisibility(View.VISIBLE);
                holder.btnReject.setVisibility(View.GONE);
            } else {
                holder.btnDeleteOrder.setVisibility(View.GONE);
                holder.btnReject.setVisibility(View.VISIBLE);
            }

        }

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveOrder(getDealerOrderListPojo.getOrderId() + "", CommonUtil.APPROVE_STATUS,
                        position,getDealerOrderListPojoArrayList);
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveOrder(getDealerOrderListPojo.getOrderId() + "", CommonUtil.REJECT_STATUS,
                        position,getDealerOrderListPojoArrayList);
            }
        });

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
                        DeleteOrder(getDealerOrderListPojo.getOrderId() + "", CommonUtil.DELETE_STATUS,
                                position,getDealerOrderListPojoArrayList);
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
        return getDealerOrderListPojoArrayList.size();
    }
    

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvTotalPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvDealerName;
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
            tvDealerName = itemView.findViewById(R.id.tvDealerName);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }

    private void approveOrder(String orderId, String status,int pos,ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.approveOrderApiImplementer(orderId, status, new Callback<ArrayList<ApproveOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveOrderPojo>> call, Response<ArrayList<ApproveOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1 ) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getDealerOrderListPojoArrayList.remove(pos);
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

    private void DeleteOrder(String orderId, String status,int pos,ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArratList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.DeleteOrderApiImplementer(orderId, status, new Callback<ArrayList<DeleteOrderPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<DeleteOrderPojo>> call, Response<ArrayList<DeleteOrderPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getDealerOrderListPojoArratList.remove(pos);
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
