package com.webond.chemicals.adapter.common_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.ApproveRedemRequestPojo;
import com.webond.chemicals.pojo.GetRedemListAdminPojo;
import com.webond.chemicals.pojo.GetRedemListForUserPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPendingRedeemListAdapter extends RecyclerView.Adapter<UserPendingRedeemListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetRedemListForUserPojo> getRedemListForUserPojoArrayList;
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder builder;
    private ICancelUserPendingRedeemReq iCancelUserPendingRedeemReq;

    public UserPendingRedeemListAdapter(Context context, ArrayList<GetRedemListForUserPojo> getRedemListForUserPojoArrayList) {
        this.context = context;
        this.getRedemListForUserPojoArrayList = getRedemListForUserPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
        builder = new AlertDialog.Builder(context);
        iCancelUserPendingRedeemReq = (ICancelUserPendingRedeemReq) context;
    }

    @NonNull
    @Override
    public UserPendingRedeemListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.usert_pending_redeem_list_item_adapter, parent, false);
        return new UserPendingRedeemListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPendingRedeemListAdapter.MyViewHolder holder, int position) {

        GetRedemListForUserPojo getRedemListForUserPojo = getRedemListForUserPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getSchemeProductName())) {
            holder.tvProductName.setText(getRedemListForUserPojo.getSchemeProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getRedemPoint())) {
            holder.tvRedeemPoint.setText(getRedemListForUserPojo.getRedemPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getQty())) {
            holder.tvQuantity.setText(getRedemListForUserPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getCdCode())) {
            holder.tvCode.setText(getRedemListForUserPojo.getCdCode() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getRedemStatus())) {
            holder.tvRedeemStatus.setText(getRedemListForUserPojo.getRedemStatus() + "");

            if (getRedemListForUserPojo.getRedemStatus().contains("Reject")) {
                holder.tvRedeemStatus.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.redeem_product_reject_border));
            } else {
                holder.tvRedeemStatus.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_odd));
            }

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getSchemeProductImagePathLink())) {
            Picasso.get().load(getRedemListForUserPojo.getSchemeProductImagePathLink().trim())
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgRedeemedProduct);
        }

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogForCancelRedeemRequest(getRedemListForUserPojo, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getRedemListForUserPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvRedeemPoint;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvCode;
        TextViewRegularFont tvRedeemStatus;
        AppCompatImageView imgRedeemedProduct;
        MaterialButton btnCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvRedeemPoint = itemView.findViewById(R.id.tvRedeemPoint);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvRedeemStatus = itemView.findViewById(R.id.tvRedeemStatus);
            imgRedeemedProduct = itemView.findViewById(R.id.imgRedeemedProduct);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    private void showAlertDialogForCancelRedeemRequest(GetRedemListForUserPojo getRedemListForUserPojo, int position) {
        builder.setMessage("Are you sure you want to Cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        cancelRedeemRequestApiCall(getRedemListForUserPojo.getRedemId() + "", "4", position);
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

    private void cancelRedeemRequestApiCall(String redeemId, String status, int position) {
        DialogUtil.showProgressDialogCancelable(context, "Please wait...");
        ApiImplementer.ApproveRedemRequest(redeemId, status, new Callback<ArrayList<ApproveRedemRequestPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ApproveRedemRequestPojo>> call, Response<ArrayList<ApproveRedemRequestPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                        iCancelUserPendingRedeemReq.onRedeemReqCanceled(response.body().get(0).getTotalPoint().toString());
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getRedemListForUserPojoArrayList.remove(position);
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

    public interface ICancelUserPendingRedeemReq {
        void onRedeemReqCanceled(String remainingTotalPoints);
    }

}
