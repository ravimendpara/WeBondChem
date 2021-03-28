package com.webond.chemicals.distributor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.distributor_order.AdminAllDistributorOrderFragment;
import com.webond.chemicals.admin.fragments.distributor_order.AdminApproveDistributorOrderFragment;
import com.webond.chemicals.admin.fragments.distributor_order.AdminPendingDistributorOrderFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.distributor.fragments.manage_dealer_order.DistributorAllDealerOrderFragment;
import com.webond.chemicals.distributor.fragments.manage_dealer_order.DistributorApproveDealerOrderFragment;
import com.webond.chemicals.distributor.fragments.manage_dealer_order.DistributorPendingDealerOrderFragment;

public class DistributorManageDealerOrderActivity extends AppCompatActivity implements View.OnClickListener{


    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageDealerOrder;
    private ViewPager vpManageDealerOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_manage_dealer_order);
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Dealer Order");
        tlManageDealerOrder = findViewById(R.id.tlManageDealerOrder);
        vpManageDealerOrder = findViewById(R.id.vpManageDealerOrder);
        tlManageDealerOrder.setupWithViewPager(vpManageDealerOrder);
        setupViewPager(vpManageDealerOrder);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DistributorPendingDealerOrderFragment(), "Pending");
        viewPagerAdapter.addFragment(new DistributorApproveDealerOrderFragment(), "Approve");
        viewPagerAdapter.addFragment(new DistributorAllDealerOrderFragment(), "All");
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