package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.fragments.redeem_req_list.AllRedeemRequestListFragment;
import com.webond.chemicals.admin.fragments.redeem_req_list.ApproveRedeemRequestListFragment;
import com.webond.chemicals.admin.fragments.redeem_req_list.PendingRedeemRequestListFragment;
import com.webond.chemicals.admin.fragments.redeem_req_list.RejectRedeemRequestListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.AllUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.ApproveUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.PendingUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.RejectUserRedeemListFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.customer.activity.CustomerRedeemListActivity;
import com.webond.chemicals.utils.MySharedPreferences;

public class AdminRedeemRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MySharedPreferences mySharedPreferences;
    private TabLayout tlAdminRedeemListCustomer;
    private ViewPager vpAdminRedeemListCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_redeem_request);
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        mySharedPreferences = new MySharedPreferences(AdminRedeemRequestActivity.this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Redeem Requests");
        tlAdminRedeemListCustomer = findViewById(R.id.tlAdminRedeemListCustomer);
        vpAdminRedeemListCustomer = findViewById(R.id.vpAdminRedeemListCustomer);
        tlAdminRedeemListCustomer.setupWithViewPager(vpAdminRedeemListCustomer);
        setupViewPager(vpAdminRedeemListCustomer);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PendingRedeemRequestListFragment.newInstance(), "Pending");
        viewPagerAdapter.addFragment(ApproveRedeemRequestListFragment.newInstance(), "Approve");
        viewPagerAdapter.addFragment(RejectRedeemRequestListFragment.newInstance(), "Reject");
        viewPagerAdapter.addFragment(AllRedeemRequestListFragment.newInstance(), "All");
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