package com.webond.chemicals.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminUpdateProductActivity;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.IntentConstants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProductListAdapter extends RecyclerView.Adapter<AdminProductListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetProductListPojo> getProductListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminProductListAdapter(Context context, ArrayList<GetProductListPojo> getProductListPojoArrayList) {
        this.context = context;
        this.getProductListPojoArrayList = getProductListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_admin_product_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GetProductListPojo getProductListPojo = getProductListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductPhoto1())) {
            Glide.with(context)
                    .load(getProductListPojo.getProductPhoto1())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgProduct);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductId())) {
                    Intent intent = new Intent(context, AdminUpdateProductActivity.class);
                    intent.putExtra(IntentConstants.PRODUCT_ID, getProductListPojo.getProductId() + "");
                    ((Activity) context).startActivityForResult(intent, IntentConstants.REQUEST_CODE_UPDATE_PRODUCT);
                } else {
                    Toast.makeText(context, "Product Id Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getProductListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }

}
