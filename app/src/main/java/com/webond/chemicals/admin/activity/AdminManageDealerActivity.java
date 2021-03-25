package com.webond.chemicals.admin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.dealer.AdminAllDealerFragment;
import com.webond.chemicals.admin.fragments.dealer.AdminApproveDealerFragment;
import com.webond.chemicals.admin.fragments.dealer.AdminPendingDealerFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;

public class AdminManageDealerActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageDealer;
    private ViewPager vpManageDealer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_dealer);
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
        viewPagerAdapter.addFragment(new AdminPendingDealerFragment(), "Pending");
        viewPagerAdapter.addFragment(new AdminApproveDealerFragment(), "Approve");
        viewPagerAdapter.addFragment(new AdminAllDealerFragment(), "All");
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