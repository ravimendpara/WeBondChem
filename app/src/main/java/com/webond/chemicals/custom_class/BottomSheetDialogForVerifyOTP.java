package com.webond.chemicals.custom_class;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.mukesh.OtpView;
import com.webond.chemicals.R;

public class BottomSheetDialogForVerifyOTP extends BottomSheetDialogFragment implements View.OnClickListener {

    private Activity activity;

    private MaterialCardView cvVerifyOTP;
    private AppCompatImageView imgCloseOTPDialog;
    private String mobileNo;
    private TextViewRegularFont tvMobileNo;
    private OtpView otp_view;
    private IOnOTPReceived iOnOTPReceived;

    public BottomSheetDialogForVerifyOTP(Activity activity, String mobileNo) {
        this.activity = activity;
        this.mobileNo = mobileNo;
        iOnOTPReceived = (IOnOTPReceived) activity;
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_for_verify_otp,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cvVerifyOTP = view.findViewById(R.id.cvVerifyOTP);
        cvVerifyOTP.setOnClickListener(this);
        otp_view = view.findViewById(R.id.otp_view);
        imgCloseOTPDialog = view.findViewById(R.id.imgCloseOTPDialog);
        imgCloseOTPDialog.setOnClickListener(this);
        tvMobileNo = view.findViewById(R.id.tvMobileNo);
        tvMobileNo.setText("+91 " + mobileNo);
    }

    private boolean isValid() {
        if (otp_view.getOTP() != null && otp_view.getOTP().length() != 6) {
            Toast.makeText(activity, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvVerifyOTP) {
            if (isValid()) {
                iOnOTPReceived.onOTPSubmit(otp_view.getOTP());
            }
        } else if (v.getId() == R.id.imgCloseOTPDialog) {
            this.dismiss();
        }
    }

    public interface IOnOTPReceived {
        void onOTPSubmit(String otp);
    }

}