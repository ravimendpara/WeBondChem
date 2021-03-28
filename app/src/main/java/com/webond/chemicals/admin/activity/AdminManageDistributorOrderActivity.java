package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.customer.AdminAllCustomerFragment;
import com.webond.chemicals.admin.fragments.customer.AdminApproveCustomerFragment;
import com.webond.chemicals.admin.fragments.customer.AdminPendingCustomerFragment;
import com.webond.chemicals.admin.fragments.distributor_order.AdminAllDistributorOrderFragment;
import com.webond.chemicals.admin.fragments.distributor_order.AdminApproveDistributorOrderFragment;
import com.webond.chemicals.admin.fragments.distributor_order.AdminPendingDistributorOrderFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;

public class AdminManageDistributorOrderActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageDistributorOrder;
    private ViewPager vpManageDistributorOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_distributor_order);
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Distributor Order");
        tlManageDistributorOrder = findViewById(R.id.tlManageDistributorOrder);
        vpManageDistributorOrder = findViewById(R.id.vpManageDistributorOrder);
        tlManageDistributorOrder.setupWithViewPager(vpManageDistributorOrder);
        setupViewPager(vpManageDistributorOrder);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AdminPendingDistributorOrderFragment(), "Pending");
        viewPagerAdapter.addFragment(new AdminApproveDistributorOrderFragment(), "Approve");
        viewPagerAdapter.addFragment(new AdminAllDistributorOrderFragment(), "All");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}