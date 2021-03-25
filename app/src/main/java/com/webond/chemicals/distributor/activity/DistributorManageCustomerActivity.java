package com.webond.chemicals.distributor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.distributor.fragments.customer.DistributorAllCustomerFragment;
import com.webond.chemicals.distributor.fragments.customer.DistributorApproveCustomerFragment;
import com.webond.chemicals.distributor.fragments.customer.DistributorPendingCustomerFragment;

public class DistributorManageCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageCustomer;
    private ViewPager vpManageCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_manage_customer);
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Customer");
        tlManageCustomer = findViewById(R.id.tlManageCustomer);
        vpManageCustomer = findViewById(R.id.vpManageCustomer);
        tlManageCustomer.setupWithViewPager(vpManageCustomer);
        setupViewPager(vpManageCustomer);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DistributorPendingCustomerFragment(), "Pending");
        viewPagerAdapter.addFragment(new DistributorApproveCustomerFragment(), "Approve");
        viewPagerAdapter.addFragment(new DistributorAllCustomerFragment(), "All");
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