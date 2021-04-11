package com.webond.chemicals.api;


import com.webond.chemicals.pojo.AddCustomerOrderDataPojo;
import com.webond.chemicals.pojo.AddCustomerPojo;
import com.webond.chemicals.pojo.AddDealerOrderDataPojo;
import com.webond.chemicals.pojo.AddDealerPojo;
import com.webond.chemicals.pojo.AddDistributerPojo;
import com.webond.chemicals.pojo.AddDistributorOrderDataPojo;
import com.webond.chemicals.pojo.AddProductPojo;
import com.webond.chemicals.pojo.ApproveCustomerPojo;
import com.webond.chemicals.pojo.ApproveDealerPojo;
import com.webond.chemicals.pojo.ApproveDistributorPojo;
import com.webond.chemicals.pojo.ApproveOrderPojo;
import com.webond.chemicals.pojo.CheckMobileNoExitstOrNoPojo;
import com.webond.chemicals.pojo.DeleteCustomerPojo;
import com.webond.chemicals.pojo.DeleteDealerPojo;
import com.webond.chemicals.pojo.DeleteDistributorPojo;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.pojo.GetCityListPojo;
import com.webond.chemicals.pojo.GetCustomerListForDistributorPojo;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.pojo.GetDashboardDetailsPojo;
import com.webond.chemicals.pojo.GetDealerListByCityIdPojo;
import com.webond.chemicals.pojo.GetDealerListByTalukaIdPojo;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.pojo.GetDetailForLoginUserAdminPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDistributorPojo;
import com.webond.chemicals.pojo.GetDistributorListByCityIdPojo;
import com.webond.chemicals.pojo.GetDistributorListByTalukaIdPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetLoginOrderListForCustomerPojo;
import com.webond.chemicals.pojo.GetLoginOrderListForDealerPojo;
import com.webond.chemicals.pojo.GetLoginOrderListForDistributorPojo;
import com.webond.chemicals.pojo.GetProductDetailByIdPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetStateListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.pojo.GetVersionInfoPojo;
import com.webond.chemicals.pojo.SendOtpPojo;
import com.webond.chemicals.pojo.UpdateProductPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiImplementer {


    public static void addDistributorOrderImplementer(String OrderDate,
                                                      String DistributorId,
                                                      String ContactPersonDetail,
                                                      String SiteAddress,
                                                      String ContactNo,
                                                      String PinCode,
                                                      String DistrictId,
                                                      String TalukaId,
                                                      String ProductList,
                                                      Callback<ArrayList<AddDistributorOrderDataPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddDistributorOrderDataPojo>> call = apiService.addDistributorOrderData(
                OrderDate,
                DistributorId,
                ContactPersonDetail,
                SiteAddress,
                ContactNo,
                PinCode,
                DistrictId,
                TalukaId,
                ProductList);
        call.enqueue(cb);
    }


    public static void addDealerOrderImplementer(String OrderDate,
                                                 String DealerId,
                                                 String DealerDistributorId,
                                                 String ContactPersonDetail,
                                                 String SiteAddress,
                                                 String ContactNo,
                                                 String PinCode,
                                                 String DistrictId,
                                                 String TalukaId,
                                                 String ProductList,
                                                 String OrderUnderRegisterStatus,
                                                 Callback<ArrayList<AddDealerOrderDataPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddDealerOrderDataPojo>> call = apiService.addDealerOrder(
                OrderDate,
                DealerId,
                DealerDistributorId,
                ContactPersonDetail,
                SiteAddress,
                ContactNo,
                PinCode,
                DistrictId,
                TalukaId,
                ProductList,
                OrderUnderRegisterStatus
        );
        call.enqueue(cb);
    }

    public static void addCustomerOrderImplementer(String OrderDate,
                                                   String CustomerId,
                                                   String CustomerDealerId,
                                                   String ContactPersonDetail,
                                                   String SiteAddress,
                                                   String ContactNo,
                                                   String PinCode,
                                                   String DistrictId,
                                                   String TalukaId,
                                                   String ProductList,
                                                   String OrderUnderRegisterStatus,
                                                   String DistributorId,
                                                   Callback<ArrayList<AddCustomerOrderDataPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddCustomerOrderDataPojo>> call = apiService.addCustomerOrderData(
                OrderDate,
                CustomerId,
                CustomerDealerId,
                ContactPersonDetail,
                SiteAddress,
                ContactNo,
                PinCode,
                DistrictId,
                TalukaId,
                ProductList,
                OrderUnderRegisterStatus,
                DistributorId);
        call.enqueue(cb);
    }


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
                                              String DateOfBirth,
                                              String DistributorId,
                                              String CustomerRegisterUnder,
                                              Callback<ArrayList<AddCustomerPojo>> cb) {
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
                DateOfBirth,
                DistributorId,
                CustomerRegisterUnder);
        call.enqueue(cb);
    }


    public static void addDealerImplementer(String DealerName,
                                            String PethiName,
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
                                            String DealerRegisterUnder,
                                            Callback<ArrayList<AddDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<AddDealerPojo>> call = apiService.addDealer(
                DealerName,
                PethiName,
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
                DateOfBirth,
                DealerRegisterUnder);
        call.enqueue(cb);
    }

    public static void addDistributorImplementer(
            String DistributerName,
            String PethiName,
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
                PethiName,
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


    public static void updateProductImplementer(
            String ProductId,
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
            Callback<ArrayList<UpdateProductPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<UpdateProductPojo>> call = apiService.updateProduct(
                ProductId,
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


    public static void approveCustomerImplementer(String CustomerId, String status, Callback<ArrayList<ApproveCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveCustomerPojo>> call = apiService.approveCustomer(CustomerId, status);
        call.enqueue(cb);
    }

    public static void approveDealerImplementer(String DealerId, String Status, Callback<ArrayList<ApproveDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveDealerPojo>> call = apiService.approveDealer(DealerId, Status);
        call.enqueue(cb);
    }

    public static void approveDistributorImplementer(String DistributorId, String status, Callback<ArrayList<ApproveDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveDistributorPojo>> call = apiService.approveDistributor(DistributorId, status);
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

    public static void getBannerListApiImplementer(Callback<ArrayList<GetBannerListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetBannerListPojo>> call = apiService.getBannerList();
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

    public static void getDealerListByCityIdApiImplementer(String cityId, Callback<ArrayList<GetDealerListByCityIdPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDealerListByCityIdPojo>> call = apiService.getDealerListByCityId(cityId);
        call.enqueue(cb);
    }

    public static void getDistributorListByCityIdApiImplementer(String cityId, Callback<ArrayList<GetDistributorListByCityIdPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDistributorListByCityIdPojo>> call = apiService.getDistributorListByCityId(cityId);
        call.enqueue(cb);
    }

    public static void getProductDetailsByIdApiImplementer(String productId, Callback<ArrayList<GetProductDetailByIdPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetProductDetailByIdPojo>> call = apiService.getProductDetailsById(productId);
        call.enqueue(cb);
    }


    public static void getDealerListByTalukaIdApiImplementer(String talukaId, Callback<ArrayList<GetDealerListByTalukaIdPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDealerListByTalukaIdPojo>> call = apiService.getDealerListByTalukaId(talukaId);
        call.enqueue(cb);
    }

    public static void getDistributorListByTalukaIdApiImplementer(String talukaId, Callback<ArrayList<GetDistributorListByTalukaIdPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDistributorListByTalukaIdPojo>> call = apiService.getDistributorListByTalukaId(talukaId);
        call.enqueue(cb);
    }

    public static void approveOrderApiImplementer(String OrderId, String Status, Callback<ArrayList<ApproveOrderPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<ApproveOrderPojo>> call = apiService.approveOrder(OrderId, Status);
        call.enqueue(cb);
    }

    public static void getDistributorsOrderApiImplementer(String LoginType, String LoginId,
                                                          String FilterValue, Callback<ArrayList<GetDistributorOrderListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDistributorOrderListPojo>> call = apiService.getDistributorsOrder(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getDealerOrderApiImplementer(String LoginType, String LoginId,
                                                    String FilterValue, Callback<ArrayList<GetDealerOrderListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDealerOrderListPojo>> call = apiService.getDealerOrder(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getCustomerOrderApiImplementer(String LoginType, String LoginId,
                                                      String FilterValue, Callback<ArrayList<GetCustomerOrderListPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetCustomerOrderListPojo>> call = apiService.getCustomerOrder(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }


    public static void getVersionInfoApiImplementer(String VersionCode, Callback<ArrayList<GetVersionInfoPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetVersionInfoPojo>> call = apiService.getVersionInfo(VersionCode);
        call.enqueue(cb);
    }

    public static void getLoginOrderListForCustomerApiImplementer(String LoginType, String LoginId,
                                                                  String FilterValue, Callback<ArrayList<GetLoginOrderListForCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetLoginOrderListForCustomerPojo>> call = apiService.getLoginOrderListForCustomer(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }


    public static void getLoginOrderListForDealerApiImplementer(String LoginType, String LoginId,
                                                                String FilterValue, Callback<ArrayList<GetLoginOrderListForDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetLoginOrderListForDealerPojo>> call = apiService.getLoginOrderListForDealer(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getLoginOrderListForDistributorApiImplementer(String LoginType, String LoginId,
                                                                     String FilterValue, Callback<ArrayList<GetLoginOrderListForDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetLoginOrderListForDistributorPojo>> call = apiService.getLoginOrderListForDistributor(LoginType,
                LoginId, FilterValue);
        call.enqueue(cb);
    }

    public static void getDashboardDetailsApiImplementer(String LoginType, String LoginId, Callback<ArrayList<GetDashboardDetailsPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetDashboardDetailsPojo>> call = apiService.getDashboardDetails(LoginType,
                LoginId);
        call.enqueue(cb);
    }

    public static void getCustomerListForDistributorApiImplementer(String DistributorId, String FilterValue, Callback<ArrayList<GetCustomerListForDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<GetCustomerListForDistributorPojo>> call = apiService.getCustomerListForDistributor(DistributorId,
                FilterValue);
        call.enqueue(cb);
    }

    public static void deleteCustomerApiImplementer(String customerId, Callback<ArrayList<DeleteCustomerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<DeleteCustomerPojo>> call = apiService.deleteCustomer(customerId);
        call.enqueue(cb);
    }

    public static void deleteDealerApiImplementer(String dealerId, Callback<ArrayList<DeleteDealerPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<DeleteDealerPojo>> call = apiService.deleteDealer(dealerId);
        call.enqueue(cb);
    }

    public static void deleteDistributorApiImplementer(String DistributorId, Callback<ArrayList<DeleteDistributorPojo>> cb) {
        final IApiInterface apiService = ApiClient.getClient().create(IApiInterface.class);
        Call<ArrayList<DeleteDistributorPojo>> call = apiService.deleteDistributor(DistributorId);
        call.enqueue(cb);
    }

}
