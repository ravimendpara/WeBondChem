package com.webond.chemicals.api;


import com.webond.chemicals.pojo.AddCustomerPojo;
import com.webond.chemicals.pojo.AddDealerPojo;
import com.webond.chemicals.pojo.AddDistributerPojo;
import com.webond.chemicals.pojo.AddProductPojo;
import com.webond.chemicals.pojo.ApproveCustomerPojo;
import com.webond.chemicals.pojo.ApproveDealerPojo;
import com.webond.chemicals.pojo.ApproveDistributorPojo;
import com.webond.chemicals.pojo.CheckMobileNoExitstOrNoPojo;
import com.webond.chemicals.pojo.GetCityListPojo;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.pojo.GetDetailForLoginUserAdminPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDistributorPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetStateListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.pojo.SendOtpPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiImplementer {

    //TODO Student side module implementers

    public static void addCustomerImplementer(String CustomerName,
                                              String DealerId,
                                              String StateId,
                                              String DistrictId,
                                              String TalukaId,
                                              String CityId,
                                              String MobileNo,
                                              String MobileNo2,
                                              String Address,
                                              String PinCode,
                                              String Email,
                                              String AadharNo,
                                              String AadharProof,
                                              String AadharFileName,
                                              String GSTNo,
                                              String Photo,
                                              String PhotoFileName,
                                              String DateOfBirth, Callback<ArrayList<AddCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddCustomerPojo>> call = apiService.addCustomer(CustomerName,
                DealerId,
                StateId,
                DistrictId,
                TalukaId,
                CityId,
                MobileNo,
                MobileNo2,
                Address,
                PinCode,
                Email,
                AadharNo,
                AadharProof,
                AadharFileName,
                GSTNo,
                Photo,
                PhotoFileName,
                DateOfBirth);
        call.enqueue(cb);
    }


    public static void addDealerImplementer(String DealerName,
                                            String DistributorId,
                                            String StateId,
                                            String DistrictId,
                                            String TalukaId,
                                            String CityId,
                                            String MobileNo,
                                            String MobileNo2,
                                            String Address,
                                            String PinCode,
                                            String Email,
                                            String AadharNo,
                                            String AadharProof,
                                            String AadharFileName,
                                            String GSTNo,
                                            String Photo,
                                            String PhotoFileName,
                                            String DateOfBirth,
                                            Callback<ArrayList<AddDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddDealerPojo>> call = apiService.addDealer(
                DealerName,
                DistributorId,
                StateId,
                DistrictId,
                TalukaId,
                CityId,
                MobileNo,
                MobileNo2,
                Address,
                PinCode,
                Email,
                AadharNo,
                AadharProof,
                AadharFileName,
                GSTNo,
                Photo,
                PhotoFileName,
                DateOfBirth);
        call.enqueue(cb);
    }

    public static void addDistributorImplementer(
            String DistributerName,
            String StateId,
            String DistrictId,
            String TalukaId,
            String CityId,
            String MobileNo,
            String MobileNo2,
            String Address,
            String PinCode,
            String Email,
            String AadharNo,
            String AadharProof,
            String AadharFileName,
            String GSTNo,
            String Photo,
            String PhotoFileName,
            String DateOfBith,
            Callback<ArrayList<AddDistributerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddDistributerPojo>> call = apiService.addDistributor(
                DistributerName,
                StateId,
                DistrictId,
                TalukaId,
                CityId,
                MobileNo,
                MobileNo2,
                Address,
                PinCode,
                Email,
                AadharNo,
                AadharProof,
                AadharFileName,
                GSTNo,
                Photo,
                PhotoFileName,
                DateOfBith);
        call.enqueue(cb);
    }

    public static void addProductImplementer(
            String ProductCode,
            String ProductName,
            String ProductPhoto1,
            String ProductPhotoName1,
            String ProductPhoto2,
            String ProductPhotoName2,
            String ProductPhoto3,
            String ProductPhotoName3,
            String ProductPhoto4,
            String ProductPhotoName4,
            String ProductPhoto5,
            String ProductPhotoName5,
            String ProductPrice,
            String ProductTotalPoint,
            String DistPer,
            String DealerPer,
            String CustomerPer,
            String ProductDescription,
            Callback<ArrayList<AddProductPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddProductPojo>> call = apiService.addProduct(
                ProductCode,
                ProductName,
                ProductPhoto1,
                ProductPhotoName1,
                ProductPhoto2,
                ProductPhotoName2,
                ProductPhoto3,
                ProductPhotoName3,
                ProductPhoto4,
                ProductPhotoName4,
                ProductPhoto5,
                ProductPhotoName5,
                ProductPrice,
                ProductTotalPoint,
                DistPer,
                DealerPer,
                CustomerPer,
                ProductDescription);
        call.enqueue(cb);
    }

    public static void approveCustomerImplementer(String CustomerId, Callback<ArrayList<ApproveCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveCustomerPojo>> call = apiService.approveCustomer(CustomerId);
        call.enqueue(cb);
    }

    public static void approveDealerImplementer(String DealerId, Callback<ArrayList<ApproveDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveDealerPojo>> call = apiService.approveDealer(DealerId);
        call.enqueue(cb);
    }

    public static void approveDistributorImplementer(String DistributorId, Callback<ArrayList<ApproveDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveDistributorPojo>> call = apiService.approveDistributor(DistributorId);
        call.enqueue(cb);
    }

    public static void checkMobileNoExistOrNotImplementer(String MobileNo, Callback<ArrayList<CheckMobileNoExitstOrNoPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<CheckMobileNoExitstOrNoPojo>> call = apiService.checkMobileNoExistOrNot(MobileNo);
        call.enqueue(cb);
    }

    public static void getCityListImplementer(String TalukaId, Callback<ArrayList<GetCityListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetCityListPojo>> call = apiService.getCityList(TalukaId);
        call.enqueue(cb);
    }

    public static void getCustomerListImplementer(String LoginType, String LoginId, String FilterValue, Callback<ArrayList<GetCustomerListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetCustomerListPojo>> call = apiService.getCustomerList(LoginType, LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getDealerListImplementer(String LoginType, String LoginId, String FilterValue, Callback<ArrayList<GetDealerListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDealerListPojo>> call = apiService.getDealerList(LoginType, LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getDetailsForLoginUserAdminImplementer(String MobileNo, Callback<ArrayList<GetDetailForLoginUserAdminPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDetailForLoginUserAdminPojo>> call = apiService.getDetailsForLoginUserAdmin(MobileNo);
        call.enqueue(cb);
    }

    public static void getDetailsForLoginUserDistributorImplementer(String MobileNo, Callback<ArrayList<GetDetailsForLoginUserDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDetailsForLoginUserDistributorPojo>> call = apiService.getDetailsForLoginUserDistributor(MobileNo);
        call.enqueue(cb);
    }

    public static void getDetailsForLoginUserDealerImplementer(String MobileNo, Callback<ArrayList<GetDetailsForLoginUserDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDetailsForLoginUserDealerPojo>> call = apiService.getDetailsForLoginUserDealer(MobileNo);
        call.enqueue(cb);
    }

    public static void getDetailsForLoginUserCustomerImplementer(String MobileNo, Callback<ArrayList<GetDetailsForLoginUserCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDetailsForLoginUserCustomerPojo>> call = apiService.getDetailsForLoginUserCustomer(MobileNo);
        call.enqueue(cb);
    }

    public static void getDistributorListApiImplementer(String LoginId, String FilterValue, Callback<ArrayList<GetDistributorListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDistributorListPojo>> call = apiService.getDistributorList(LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getDistrictListApiImplementer(String StateId, Callback<ArrayList<GetDistrictListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDistrictListPojo>> call = apiService.getDistrictList(StateId);
        call.enqueue(cb);
    }

    public static void getProductListApiImplementer(Callback<ArrayList<GetProductListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetProductListPojo>> call = apiService.getProductList();
        call.enqueue(cb);
    }

    public static void getStateListApiImplementer(Callback<ArrayList<GetStateListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetStateListPojo>> call = apiService.getStateList();
        call.enqueue(cb);
    }

    public static void getTalukaListApiImplementer(String districtId, Callback<ArrayList<GetTalukaListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetTalukaListPojo>> call = apiService.getTalukaList(districtId);
        call.enqueue(cb);
    }

    public static void sendOtpApiImplementer(String MobileNo, String Otp, Callback<ArrayList<SendOtpPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<SendOtpPojo>> call = apiService.sendOtp(MobileNo, Otp);
        call.enqueue(cb);
    }

}
