package com.webond.chemicals.distributor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.dealer.AdminAllDealerFragment;
import com.webond.chemicals.admin.fragments.dealer.AdminApproveDealerFragment;
import com.webond.chemicals.admin.fragments.dealer.AdminPendingDealerFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.distributor.fragments.dealer.DistributorAllDealerFragment;
import com.webond.chemicals.distributor.fragments.dealer.DistributorApproveDealerFragment;
import com.webond.chemicals.distributor.fragments.dealer.DistributorPendingDealerFragment;

public class DistributorManageDealerActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageDealer;
    private ViewPager vpManageDealer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_manage_dealer);
        initView();
    }


    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Dealer");
        tlManageDealer = findViewById(R.id.tlManageDealer);
        vpManageDealer = findViewById(R.id.vpManageDealer);
        tlManageDealer.setupWithViewPager(vpManageDealer);
        setupViewPager(vpManageDealer);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DistributorPendingDealerFragment(), "Pending");
        viewPagerAdapter.addFragment(new DistributorApproveDealerFragment(), "Approve");
        viewPagerAdapter.addFragment(new DistributorAllDealerFragment(), "All");
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