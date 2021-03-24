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


    public void setLoginType(String loginType) {
        editorForUserDetails.putString(CommonPreferencesConstants.LOGIN_TYPE, loginType);
        editorForUserDetails.apply();
    }

    public String getLoginType() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.LOGIN_TYPE, "");
    }

    public void setVerifiedMobileNo(String verifiedMobileNo) {
        editorForUserDetails.putString(CommonPreferencesConstants.VERIFIED_MOBILE_NO, verifiedMobileNo);
        editorForUserDetails.apply();
    }

    public String getVerifiedMobileNo() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.VERIFIED_MOBILE_NO, "");
    }

    //    --Admin--
    public void setAdminCompanyId(String adminCompanyId) {
        editorForUserDetails.putString(CommonPreferencesConstants.ADMIN_COMPANY_ID, adminCompanyId);
        editorForUserDetails.apply();
    }

    public String getAdminCompanyId() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.ADMIN_COMPANY_ID, "");
    }

    public void setAdminCompanyTagLine(String adminCompanyTagLine) {
        editorForUserDetails.putString(CommonPreferencesConstants.ADMIN_COMPANY_TAG_LINE, adminCompanyTagLine);
        editorForUserDetails.apply();
    }

    public String getAdminCompanyTagLine() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.ADMIN_COMPANY_TAG_LINE, "");
    }

    public void setAdminCompanyName(String adminCompanyName) {
        editorForUserDetails.putString(CommonPreferencesConstants.ADMIN_COMPANY_NAME, adminCompanyName);
        editorForUserDetails.apply();
    }

    public String getAdminCompanyName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.ADMIN_COMPANY_NAME, "");
    }

    public void setAdminCompanyEmail(String adminCompanyEmail) {
        editorForUserDetails.putString(CommonPreferencesConstants.ADMIN_COMPANY_EMAIL, adminCompanyEmail);
        editorForUserDetails.apply();
    }

    public String getAdminCompanyEmail() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.ADMIN_COMPANY_EMAIL, "");
    }

//    --Distributor--

    public void setDistributorId(String distributorId) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_ID, distributorId);
        editorForUserDetails.apply();
    }

    public String getDistributorId() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_ID, "");
    }

    public void setDistributorName(String distributorName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_NAME, distributorName);
        editorForUserDetails.apply();
    }

    public String getDistributorName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_NAME, "");
    }

    public void setDistributorMobileNo(String distributorMobileNo) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_MOBILE_NO, distributorMobileNo);
        editorForUserDetails.apply();
    }

    public String getDistributorMobileNo() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_MOBILE_NO, "");
    }

    public void setDistributorMobileNo2(String distributorMobileNo2) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_MOBILE_NO_2, distributorMobileNo2);
        editorForUserDetails.apply();
    }

    public String getDistributorMobileNo2() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_MOBILE_NO_2, "");
    }

    public void setDistributorEmail(String distributorEmail) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_EMAIL, distributorEmail);
        editorForUserDetails.apply();
    }

    public String getDistributorEmail() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_EMAIL, "");
    }

    public void setDistributorPhotoPath(String distributorPhotoPath) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_PHOTO_PATH, distributorPhotoPath);
        editorForUserDetails.apply();
    }

    public String getDistributorPhotoPath() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_PHOTO_PATH, "");
    }

    public void setDistributorCityName(String distributorCityName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_CITY_NAME, distributorCityName);
        editorForUserDetails.apply();
    }

    public String getDistributorCityName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_CITY_NAME, "");
    }

    public void setDistributorTalukaName(String distributorTalukaName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_TALUKA_NAME, distributorTalukaName);
        editorForUserDetails.apply();
    }

    public String getDistributorTalukaName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_TALUKA_NAME, "");
    }

    public void setDistributorDistrictName(String distributorDistrictName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_DISTRICT_NAME, distributorDistrictName);
        editorForUserDetails.apply();
    }

    public String getDistributorDistrictName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_DISTRICT_NAME, "");
    }

    public void setDistributorStateName(String distributorStateName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DISTRIBUTOR_STATE_NAME, distributorStateName);
        editorForUserDetails.apply();
    }

    public String getDistributorStateName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DISTRIBUTOR_STATE_NAME, "");
    }

//    --for dealer--


    public void setDealerId(String dealerId) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_ID, dealerId);
        editorForUserDetails.apply();
    }

    public String getDealerId() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_ID, "");
    }

    public void setDealerName(String dealerName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_NAME, dealerName);
        editorForUserDetails.apply();
    }

    public String getDealerName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_NAME, "");
    }

    public void setDealerMobileNo(String dealerMobileNo) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_MOBILE_NO, dealerMobileNo);
        editorForUserDetails.apply();
    }

    public String getDealerMobileNo() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_MOBILE_NO, "");
    }

    public void setDealerMobileNo2(String dealerMobileNo2) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_MOBILE_NO_2, dealerMobileNo2);
        editorForUserDetails.apply();
    }

    public String getDealerMobileNo2() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_MOBILE_NO_2, "");
    }

    public void setDealerEmail(String dealerEmail) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_EMAIL, dealerEmail);
        editorForUserDetails.apply();
    }

    public String getDealerEmail() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_EMAIL, "");
    }

    public void setDealerPhotoPath(String dealerPhotoPath) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_PHOTO_PATH, dealerPhotoPath);
        editorForUserDetails.apply();
    }

    public String getDealerPhotoPath() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_PHOTO_PATH, "");
    }

    public void setDealerCityName(String dealerCityName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_CITY_NAME, dealerCityName);
        editorForUserDetails.apply();
    }

    public String getDealerCityName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_CITY_NAME, "");
    }

    public void setDealerTalukaName(String dealerTalukaName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_TALUKA_NAME, dealerTalukaName);
        editorForUserDetails.apply();
    }

    public String getDealerTalukaName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_TALUKA_NAME, "");
    }

    public void setDealerDistrictName(String dealerDistrictName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_DISTRICT_NAME, dealerDistrictName);
        editorForUserDetails.apply();
    }

    public String getDealerDistrictName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_DISTRICT_NAME, "");
    }

    public void setDealerStateName(String dealerStateName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_STATE_NAME, dealerStateName);
        editorForUserDetails.apply();
    }

    public String getDealerStateName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_STATE_NAME, "");
    }

    public void setDealerDistributorName(String dealerDistributorName) {
        editorForUserDetails.putString(CommonPreferencesConstants.DEALER_DISTRIBUTOR_NAME, dealerDistributorName);
        editorForUserDetails.apply();
    }

    public String getDealerDistributorName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.DEALER_DISTRIBUTOR_NAME, "");
    }

//    --Customer--

    public void setCustomerId(String customerId) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_ID, customerId);
        editorForUserDetails.apply();
    }

    public String getCustomerId() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_ID, "");
    }

    public void setCustomerName(String customerName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_NAME, customerName);
        editorForUserDetails.apply();
    }

    public String getCustomerName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_NAME, "");
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_MOBILE_NO, customerMobileNo);
        editorForUserDetails.apply();
    }

    public String getCustomerMobileNo() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_MOBILE_NO, "");
    }

    public void setCustomerMobileNo2(String customerMobileNo2) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_MOBILE_NO_2, customerMobileNo2);
        editorForUserDetails.apply();
    }

    public String getCustomerMobileNo2() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_MOBILE_NO_2, "");
    }

    public void setCustomerEmail(String customerEmail) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_EMAIL, customerEmail);
        editorForUserDetails.apply();
    }

    public String getCustomerEmail() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_EMAIL, "");
    }

    public void setCustomerPhotoPath(String customerPhotoPath) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_PHOTO_PATH, customerPhotoPath);
        editorForUserDetails.apply();
    }

    public String getCustomerPhotoPath() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_PHOTO_PATH, "");
    }

    public void setCustomerDealerName(String customerDealerName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_DEALER_NAME, customerDealerName);
        editorForUserDetails.apply();
    }

    public String getCustomerDealerName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_DEALER_NAME, "");
    }

    public void setCustomerDistributorName(String customerDistributorName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_DISTRIBUTOR_NAME, customerDistributorName);
        editorForUserDetails.apply();
    }

    public String getCustomerDistributorName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_DISTRIBUTOR_NAME, "");
    }

    public void setCustomerCityName(String customerCityName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_CITY_NAME, customerCityName);
        editorForUserDetails.apply();
    }

    public String getCustomerCityName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_CITY_NAME, "");
    }

    public void setCustomerTalukaName(String customerTaluka) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_TALUKA_NAME, customerTaluka);
        editorForUserDetails.apply();
    }

    public String getCustomerTalukaName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_TALUKA_NAME, "");
    }

    public void setCustomerDistrictName(String customerDistrictName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_DISTRICT_NAME, customerDistrictName);
        editorForUserDetails.apply();
    }

    public String getCustomerDistrictName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_DISTRICT_NAME, "");
    }

    public void setCustomerStateName(String customerStateName) {
        editorForUserDetails.putString(CommonPreferencesConstants.CUSTOMER_STATE_NAME, customerStateName);
        editorForUserDetails.apply();
    }

    public String getCustomerStateName() {
        return sharedPreferencesForUserDetails.getString(CommonPreferencesConstants.CUSTOMER_STATE_NAME, "");
    }

}
