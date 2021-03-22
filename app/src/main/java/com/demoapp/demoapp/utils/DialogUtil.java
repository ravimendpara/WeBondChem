package com.demoapp.demoapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.demoapp.demoapp.R;
import com.demoapp.demoapp.custom_class.TextViewRegularFont;
//import com.employeecontrol.controle.custom_class.TextViewRegularFont;

public class DialogUtil {

    static Dialog m_Dialog = null;


    public static Dialog showProgressDialogCancelable(Context context, String text) {
        String msg = "";
        if (CommonUtil.checkIsEmptyOrNullCommon(text)) {
            msg = "Please Wait... ";
        } else {
            msg = text;
        }

        m_Dialog = new Dialog(context);
        m_Dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_shape_for_custom_dialog);//if need to change dialog radius in custom_layout_for_progress_dialog
        // layout than also change same radius in bg_shape_for_custom_dialog
        m_Dialog.setCancelable(true);
        View customProgressDialog = LayoutInflater.from(context).inflate(R.layout.custom_layout_for_progress_dialog, null);
        TextViewRegularFont tvMsg = customProgressDialog.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);
        m_Dialog.setContentView(customProgressDialog);
        m_Dialog.show();
        return m_Dialog;
    }

    public static void hideProgressDialog() {
        if (m_Dialog != null) {
            if (m_Dialog.isShowing()) {
                m_Dialog.dismiss();
            }
        }
    }

    public static Dialog showProgressDialogNotCancelable(Context context, String text) {

        String msg = "";
        if (CommonUtil.checkIsEmptyOrNullCommon(text)) {
            msg = "Please Wait... ";
        } else {
            msg = text;
        }

        m_Dialog = new Dialog(context);
        m_Dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_shape_for_custom_dialog);//if need to change dialog radius in custom_layout_for_progress_dialog
        // layout than also change same radius in bg_shape_for_custom_dialog
        m_Dialog.setCancelable(false);
        View customProgressDialog = LayoutInflater.from(context).inflate(R.layout.custom_layout_for_progress_dialog, null);
        TextViewRegularFont tvMsg = customProgressDialog.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);
        m_Dialog.setContentView(customProgressDialog);
        m_Dialog.show();
        return m_Dialog;
    }

    public static void showGPSNotEnabledDialog(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("GPS is not Enabled!");
                alertDialog.setMessage("Please enable GPS to get current location?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_ENABLE_GPS);


                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        activity.finish();
                    }
                });

                alertDialog.show();
            }
        });
    }
}
