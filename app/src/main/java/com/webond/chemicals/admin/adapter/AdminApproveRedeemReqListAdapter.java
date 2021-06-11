package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminRedeemRequestActivity;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.ApproveRedemRequestPojo;
import com.webond.chemicals.pojo.GetRedemListAdminPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import net.seifhadjhassen.recyclerviewpager.PagerAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminApproveRedeemReqListAdapter extends RecyclerView.Adapter<AdminApproveRedeemReqListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetRedemListAdminPojo> getRedemListAdminPojoArrayList;
    private LayoutInflater layoutInflater;
    AlertDialog.Builder builder;

    public AdminApproveRedeemReqListAdapter(Context context, ArrayList<GetRedemListAdminPojo> getRedemListAdminPojoArrayList) {
        this.context = context;
        this.getRedemListAdminPojoArrayList = getRedemListAdminPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.admin_approve_redeem_req_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminApproveRedeemReqListAdapter.MyViewHolder holder, int position) {
        GetRedemListAdminPojo getRedemListAdminPojo = getRedemListAdminPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getCdName())){
            holder.tvPersonName.setText(getRedemListAdminPojo.getCdName() +" ("+getRedemListAdminPojo.getCdCode()+")");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getSchemeProductName())){
            holder.tvProductName.setText(getRedemListAdminPojo.getSchemeProductName()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getRedemPoint())){
            holder.tvRedeemPoint.setText(getRedemListAdminPojo.getRedemPoint()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getQty())){
            holder.tvQuantity.setText(getRedemListAdminPojo.getQty()+"");
        }

//        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getCdCode())){
//            holder.tvCode.setText(getRedemListAdminPojo.getCdCode()+"");
//        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getRedemStatus())){
            holder.tvStatus.setText(getRedemListAdminPojo.getRedemStatus()+"");
            if (getRedemListAdminPojo.getRedemStatus().equalsIgnoreCase("Reject")){
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getSchemeProductImagePathLink())){
            Picasso.get().load(getRedemListAdminPojo.getSchemeProductImagePathLink().trim())
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgRedeemedProduct);
        }

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogForApproveReject(getRedemListAdminPojo, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getRedemListAdminPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialButton btnReject;
        TextViewMediumFont tvPersonName;
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvRedeemPoint;
        TextViewMediumFont tvQuantity;
        //        TextViewMediumFont tvCode;
        TextViewMediumFont tvStatus;
        AppCompatImageView imgRedeemedProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonName = itemView.findViewById(R.id.tvPersonName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvRedeemPoint = itemView.findViewById(R.id.tvRedeemPoint);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
//            tvCode = itemView.findViewById(R.id.tvCode);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgRedeemedProduct = itemView.findViewById(R.id.imgRedeemedProduct);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }

    private void approveOrRejectRedeemRequestApiCall(String redeemId, String status, ArrayList<GetRedemListAdminPojo> newListToUpdate, int position) {
        DialogUtil.showProgressDialogCancelable(context, "Please wait...");
        ApiImplementer.ApproveRedemRequest(redeemId, status, new Callback<ArrayList<ApproveRedemRequestPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveRedemRequestPojo>> call, Response<ArrayList<ApproveRedemRequestPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        newListToUpdate.remove(position);
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
            public void onFailure(Call<ArrayList<ApproveRedemRequestPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Something went wrong,Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertDialogForApproveReject(GetRedemListAdminPojo getRedemListAdminPojo, int position) {
        builder.setMessage("Are you sure you want to Reject ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        approveOrRejectRedeemRequestApiCall(getRedemListAdminPojo.getRedemId() + "", "2", getRedemListAdminPojoArrayList, position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
