package com.webond.chemicals.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreferences {

    SharedPreferences sharedPreferencesForUserDetails;
    SharedPreferences.Editor editorForUserDetails;
    private SharedPreferences sharedPreferencesForFirebaseFcmToken;
    private SharedPreferences.Editor editorForFirebaseFcmToken;
    Context context;
    private static final String PREFERENCES_CONTROL_WEBOND_APP = "webond_app";

    private static final String FIREBASE_FCM_TOKEN = "fcm_token_details";//please do not use this file name in other places

    public MySharedPreferences(Context context) {
        this.context = context;
        sharedPreferencesForUserDetails = context.getSharedPreferences(PREFERENCES_CONTROL_WEBOND_APP, MODE_PRIVATE);
        editorForUserDetails = sharedPreferencesForUserDetails.edit();
        sharedPreferencesForFirebaseFcmToken = context.getSharedPreferences(FIREBASE_FCM_TOKEN, MODE_PRIVATE);
        editorForFirebaseFcmToken = sharedPreferencesForFirebaseFcmToken.edit();
    }

    public void setFCMToken(String fcmToken) {
        editorForFirebaseFcmToken.putString(CommonPreferencesConstants.FCM_TOKEN, fcmToken);
        editorForFirebaseFcmToken.apply();
    }


    public void logOutUser() {
        editorForUserDetails.clear();
        editorForUserDetails.commit();
    }

    public String getFCMToken() {
        return sharedPreferencesForFirebaseFcmToken.getString(CommonPreferencesConstants.FCM_TOKEN, "");
    }
}
