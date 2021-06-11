package com.webond.chemicals.customer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.adapter.common_adapter.UserPendingRedeemListAdapter;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.AllUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.ApproveUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.PendingUserRedeemListFragment;
import com.webond.chemicals.common_fragments.user_redeemlist_fragments.RejectUserRedeemListFragment;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.dealer.activity.DealerRedeemListActivity;
import com.webond.chemicals.dealer.fragments.DealerAllCustomerFragment;
import com.webond.chemicals.dealer.fragments.DealerApproveCustomerFragment;
import com.webond.chemicals.dealer.fragments.DealerPendingCustomerFragment;
import com.webond.chemicals.distributor.activity.DistributorDashboardActivity;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

public class CustomerRedeemListActivity extends AppCompatActivity implements View.OnClickListener, UserPendingRedeemListAdapter.ICancelUserPendingRedeemReq {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MySharedPreferences mySharedPreferences;
    private TabLayout tlRedeemListCustomer;
    private ViewPager vpRedeemListCustomer;
    private String totalPoint = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_redeem_list);
        initView();
        if (getIntent().hasExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ)){
            totalPoint = getIntent().getStringExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ);
        }
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        mySharedPreferences = new MySharedPreferences(CustomerRedeemListActivity.this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("My Redemption");
        tlRedeemListCustomer = findViewById(R.id.tlRedeemListCustomer);
        vpRedeemListCustomer = findViewById(R.id.vpRedeemListCustomer);
        tlRedeemListCustomer.setupWithViewPager(vpRedeemListCustomer);
        setupViewPager(vpRedeemListCustomer);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PendingUserRedeemListFragment.newInstance(mySharedPreferences.getCustomerId(),"4"), "Pending");
        viewPagerAdapter.addFragment(ApproveUserRedeemListFragment.newInstance(mySharedPreferences.getCustomerId(),"4"), "Approve");
        viewPagerAdapter.addFragment(RejectUserRedeemListFragment.newInstance(mySharedPreferences.getCustomerId(),"4"), "Reject");
        viewPagerAdapter.addFragment(AllUserRedeemListFragment.newInstance(mySharedPreferences.getCustomerId(),"4"), "All");
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
        Intent intent = new Intent(CustomerRedeemListActivity.this, DistributorDashboardActivity.class);
        intent.putExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ, totalPoint);
        setResult(IntentConstants.REQUEST_CODE_FOR_CANCEL_REDEEM_REQ, intent);
        super.onBackPressed();
    }

    @Override
    public void onRedeemReqCanceled(String remainingTotalPoints) {
        totalPoint = remainingTotalPoints;
    }
}