package com.webond.chemicals.dealer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.dealer.fragments.DealerAllCustomerFragment;
import com.webond.chemicals.dealer.fragments.DealerApproveCustomerFragment;
import com.webond.chemicals.dealer.fragments.DealerPendingCustomerFragment;
import com.webond.chemicals.distributor.fragments.customer.DistributorAllCustomerFragment;
import com.webond.chemicals.distributor.fragments.customer.DistributorApproveCustomerFragment;
import com.webond.chemicals.distributor.fragments.customer.DistributorPendingCustomerFragment;

public class DealerManageCustomerActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageCustomer;
    private ViewPager vpManageCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_manage_customer);
        initView();
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
        viewPagerAdapter.addFragment(new DealerPendingCustomerFragment(), "Pending");
        viewPagerAdapter.addFragment(new DealerApproveCustomerFragment(), "Approve");
        viewPagerAdapter.addFragment(new DealerAllCustomerFragment(), "All");
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