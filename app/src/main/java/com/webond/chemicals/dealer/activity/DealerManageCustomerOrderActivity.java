package com.webond.chemicals.dealer.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.ViewPagerAdapter;
import com.webond.chemicals.dealer.fragments.mange_customer_order.DealerCustomerAllOrderFragment;
import com.webond.chemicals.dealer.fragments.mange_customer_order.DealerCustomerApproveOrderFragment;
import com.webond.chemicals.dealer.fragments.mange_customer_order.DealerCustomerPendingOrderFragment;

public class DealerManageCustomerOrderActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private TabLayout tlManageCustomerOrder;
    private ViewPager vpManageCustomerOrder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_manage_customer_order);
        initView();
    }


    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Manage Applicant Order");
        tlManageCustomerOrder = findViewById(R.id.tlManageCustomerOrder);
        vpManageCustomerOrder = findViewById(R.id.vpManageCustomerOrder);
        tlManageCustomerOrder.setupWithViewPager(vpManageCustomerOrder);
        setupViewPager(vpManageCustomerOrder);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DealerCustomerPendingOrderFragment(), "Pending");
        viewPagerAdapter.addFragment(new DealerCustomerApproveOrderFragment(), "Approve");
        viewPagerAdapter.addFragment(new DealerCustomerAllOrderFragment(), "All");
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