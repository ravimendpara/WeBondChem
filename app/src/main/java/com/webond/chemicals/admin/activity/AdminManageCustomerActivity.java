package com.webond.chemicals.admin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.customer.AdminAllCustomerFragment;
import com.webond.chemicals.admin.fragments.customer.AdminApproveCustomerFragment;
import com.webond.chemicals.admin.fragments.customer.AdminPendingCustomerFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;

public class AdminManageCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageCustomer;
    private ViewPager vpManageCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);
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
        viewPagerAdapter.addFragment(new AdminPendingCustomerFragment(), "Pending");
        viewPagerAdapter.addFragment(new AdminApproveCustomerFragment(), "Approve");
        viewPagerAdapter.addFragment(new AdminAllCustomerFragment(), "All");
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