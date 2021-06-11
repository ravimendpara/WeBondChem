package com.webond.chemicals.dealer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.RedeemSuccessfullyActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.customer.adapter.CustomerRedeemProductAdapter;
import com.webond.chemicals.pojo.GetRedemProductListPojo;
import com.webond.chemicals.pojo.SaveRedemDataPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRedeemProductAdapter extends RecyclerView.Adapter<DealerRedeemProductAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetRedemProductListPojo> getRedemProductListPojoArrayList;
    private LayoutInflater layoutInflater;
    private MySharedPreferences mySharedPreferences;
    private int TOTAL_POINT = 0;
    private IDealerOnRedeemPoint iDealerOnRedeemPoint;

    public DealerRedeemProductAdapter(Context context, ArrayList<GetRedemProductListPojo> getRedemProductListPojoArrayList) {
        this.context = context;
        this.getRedemProductListPojoArrayList = getRedemProductListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
        mySharedPreferences = new MySharedPreferences(context);
        iDealerOnRedeemPoint = (DealerRedeemProductAdapter.IDealerOnRedeemPoint) context;

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojoArrayList.get(0).getTotalPoint())) {
            TOTAL_POINT = getRedemProductListPojoArrayList.get(0).getTotalPoint();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dealer_redeem_product_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerRedeemProductAdapter.MyViewHolder holder, int position) {
        GetRedemProductListPojo getRedemProductListPojo = getRedemProductListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getSchemeProductName())) {
            holder.tvProductName.setText(getRedemProductListPojo.getSchemeProductName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getProdDesc())) {
            holder.tvProductDescription.setText(getRedemProductListPojo.getProdDesc() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getExpiryDate())) {
            holder.tvProductExpiry.setText("( Expire on " + getRedemProductListPojo.getExpiryDate() + " )");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getTotalPoint())) {
            holder.tvProductPoint.setText(getRedemProductListPojo.getSchemeProductPoints() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getSchemeProductImagePathLink())) {
            Picasso.get().load(getRedemProductListPojo.getSchemeProductImagePathLink().trim())
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgRedeemedProduct);
        }

        holder.btnAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Integer.parseInt(holder.tvProductPoint.getText().toString()) <= TOTAL_POINT) {
                        calculatePoints(true, false, getRedemProductListPojo.getSchemeProductPoints(),
                                holder.tvProductPoint, holder.etQty);
                    } else {
                        Toast.makeText(context, "Insufficient Points", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.btnSubQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calculatePoints(false, true, getRedemProductListPojo.getSchemeProductPoints(),
                            holder.tvProductPoint, holder.etQty);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.llAddToRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (Integer.parseInt(holder.tvProductPoint.getText().toString()) <= TOTAL_POINT) {
                        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemProductListPojo.getSchemeProductId())){
                            redeemProductApiCall(getRedemProductListPojo.getSchemeProductId().toString(),
                                    holder.tvProductPoint.getText().toString(), holder.etQty.getText().toString());
                        }
                    } else {
                        Toast.makeText(context, "Insufficient Points", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getRedemProductListPojoArrayList.size();
    }

    private void calculatePoints(boolean isAdd, boolean isSub, int productPoints,
                                 TextViewMediumFont tvProductPoint, AppCompatEditText etQty) {

        int avlQuantity = Integer.parseInt(etQty.getText().toString());

        if (isAdd) {
            etQty.setText(String.valueOf(avlQuantity + 1));
            avlQuantity = avlQuantity + 1;
        } else if (isSub) {
            if (avlQuantity > 1) {
                etQty.setText(String.valueOf(avlQuantity - 1));
                avlQuantity = avlQuantity - 1;
            }
        }

        int totalProductPoint = 0;
        if (avlQuantity > 0 && productPoints > 0) {
            totalProductPoint = avlQuantity * productPoints;
        }

        if (totalProductPoint <= TOTAL_POINT) {
            tvProductPoint.setText(totalProductPoint + "");
        } else {
            Toast.makeText(context, "Insufficient points", Toast.LENGTH_SHORT).show();
            if (isAdd) {
                etQty.setText(String.valueOf(Integer.parseInt(etQty.getText().toString()) - 1));
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton btnAddQty;
        AppCompatEditText etQty;
        ImageButton btnSubQty;
        AppCompatImageView imgRedeemedProduct;
        TextViewMediumFont tvProductName;
        TextViewRegularFont tvProductDescription;
        TextViewRegularFont tvProductExpiry;
        TextViewMediumFont tvProductPoint;
        LinearLayout llAddToRedeem;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgRedeemedProduct = itemView.findViewById(R.id.imgRedeemedProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductExpiry = itemView.findViewById(R.id.tvProductExpiry);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            llAddToRedeem = itemView.findViewById(R.id.llAddToRedeem);
            btnAddQty = itemView.findViewById(R.id.btnAddQty);
            etQty = itemView.findViewById(R.id.etQty);
            btnSubQty = itemView.findViewById(R.id.btnSubQty);
        }
    }

    private void redeemProductApiCall(String RedemProductId, String RedemPoint, String Qty) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.SaveRedeemData(RedemProductId, RedemPoint, "3", "0", mySharedPreferences.getDealerId(), "0", Qty, new Callback<ArrayList<SaveRedemDataPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<SaveRedemDataPojo>> call, Response<ArrayList<SaveRedemDataPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            SaveRedemDataPojo saveRedemDataPojos = response.body().get(0);
                            if(saveRedemDataPojos.getStatus() == 1){
                                iDealerOnRedeemPoint.onRedeemPoints(RedemPoint);
                                Intent intent = new Intent(context, RedeemSuccessfullyActivity.class);
                                ((Activity)context).startActivityForResult(intent, IntentConstants.REQUEST_CODE_REDEEM_SUCCESSFULLY);
//                                Toast.makeText(context, saveRedemDataPojos.getMsg(), Toast.LENGTH_SHORT).show();
//                                iRedeemProductDealer.productRedeemed();
                            }else{
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SaveRedemDataPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface IDealerOnRedeemPoint{
        void onRedeemPoints(String redeemPoints);
    }

}
