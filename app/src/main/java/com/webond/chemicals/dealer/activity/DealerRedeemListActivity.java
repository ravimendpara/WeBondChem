package com.webond.chemicals.dealer.activity;

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
import com.webond.chemicals.customer.activity.CustomerRedeemListActivity;
import com.webond.chemicals.distributor.activity.DistributorDashboardActivity;
import com.webond.chemicals.distributor.activity.DistributorRedeemListActivity;
import com.webond.chemicals.distributor.adapter.DistributorRedeemProductAdapter;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

public class DealerRedeemListActivity extends AppCompatActivity implements View.OnClickListener, UserPendingRedeemListAdapter.ICancelUserPendingRedeemReq {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MySharedPreferences mySharedPreferences;
    private TabLayout tlRedeemListDealer;
    private ViewPager vpRedeemListDealer;
    private String totalPoint = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_redeem_list2);
        initView();
        if (getIntent().hasExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ)){
            totalPoint = getIntent().getStringExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ);
        }
    }


    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        mySharedPreferences = new MySharedPreferences(DealerRedeemListActivity.this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("My Redemption");
        tlRedeemListDealer = findViewById(R.id.tlRedeemListDealer);
        vpRedeemListDealer = findViewById(R.id.vpRedeemListDealer);
        tlRedeemListDealer.setupWithViewPager(vpRedeemListDealer);
        setupViewPager(vpRedeemListDealer);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PendingUserRedeemListFragment.newInstance(mySharedPreferences.getDealerId(),"3"), "Pending");
        viewPagerAdapter.addFragment(ApproveUserRedeemListFragment.newInstance(mySharedPreferences.getDealerId(),"3"), "Approve");
        viewPagerAdapter.addFragment(RejectUserRedeemListFragment.newInstance(mySharedPreferences.getDealerId(),"3"), "Reject");
        viewPagerAdapter.addFragment(AllUserRedeemListFragment.newInstance(mySharedPreferences.getDealerId(),"3"), "All");
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
        Intent intent = new Intent(DealerRedeemListActivity.this, DistributorDashboardActivity.class);
        intent.putExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ, totalPoint);
        setResult(IntentConstants.REQUEST_CODE_FOR_CANCEL_REDEEM_REQ, intent);
        super.onBackPressed();
    }

    @Override
    public void onRedeemReqCanceled(String remainingTotalPoints) {
        totalPoint = remainingTotalPoints;
    }
}