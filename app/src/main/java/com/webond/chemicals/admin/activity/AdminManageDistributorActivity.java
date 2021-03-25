package com.webond.chemicals.admin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.distributor.AdminAllDistributorFragment;
import com.webond.chemicals.admin.fragments.distributor.AdminApproveDistributorFragment;
import com.webond.chemicals.admin.fragments.distributor.AdminPendingDistributorFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;

public class AdminManageDistributorActivity extends AppCompatActivity implements View.OnClickListener {


    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageDistributor;
    private ViewPager vpManageDistributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_distributor);
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Distributor");
        tlManageDistributor = findViewById(R.id.tlManageDistributor);
        vpManageDistributor = findViewById(R.id.vpManageDistributor);
        tlManageDistributor.setupWithViewPager(vpManageDistributor);
        setupViewPager(vpManageDistributor);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AdminPendingDistributorFragment(), "Pending");
        viewPagerAdapter.addFragment(new AdminApproveDistributorFragment(), "Approve");
        viewPagerAdapter.addFragment(new AdminAllDistributorFragment(), "All");
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