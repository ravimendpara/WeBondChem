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
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiInterface {


    @GET("AddCustomer")
    Call<ArrayList<AddCustomerPojo>> addCustomer(
            @Query("CustomerName") String CustomerName,
            @Query("DealerId") String DealerId,
            @Query("StateId") String StateId,
            @Query("DistrictId") String DistrictId,
            @Query("TalukaId") String TalukaId,
            @Query("CityId") String CityId,
            @Query("MobileNo") String MobileNo,
            @Query("MobileNo2") String MobileNo2,
            @Query("Address") String Address,
            @Query("PinCode") String PinCode,
            @Query("Email") String Email,
            @Query("AadharNo") String AadharNo,
            @Query("AadharProof") String AadharProof,
            @Query("AadharFileName") String AadharFileName,
            @Query("GSTNo") String GSTNo,
            @Query("Photo") String Photo,
            @Query("PhotoFileName") String PhotoFileName,
            @Query("DateOfBirth") String DateOfBirth
    );

    @GET("AddDealer")
    Call<ArrayList<AddDealerPojo>> addDealer(
            @Query("DealerName") String DealerName,
            @Query("DistributorId") String DistributorId,
            @Query("StateId") String StateId,
            @Query("DistrictId") String DistrictId,
            @Query("TalukaId") String TalukaId,
            @Query("CityId") String CityId,
            @Query("MobileNo") String MobileNo,
            @Query("MobileNo2") String MobileNo2,
            @Query("Address") String Address,
            @Query("PinCode") String PinCode,
            @Query("Email") String Email,
            @Query("AadharNo") String AadharNo,
            @Query("AadharProof") String AadharProof,
            @Query("AadharFileName") String AadharFileName,
            @Query("GSTNo") String GSTNo,
            @Query("Photo") String Photo,
            @Query("PhotoFileName") String PhotoFileName,
            @Query("DateOfBirth") String DateOfBirth
    );


    @GET("AddDistributer")
    Call<ArrayList<AddDistributerPojo>> addDistributor(
            @Query("DistributerName") String DistributerName,
            @Query("StateId") String StateId,
            @Query("DistrictId") String DistrictId,
            @Query("TalukaId") String TalukaId,
            @Query("CityId") String CityId,
            @Query("MobileNo") String MobileNo,
            @Query("MobileNo2") String MobileNo2,
            @Query("Address") String Address,
            @Query("PinCode") String PinCode,
            @Query("Email") String Email,
            @Query("AadharNo") String AadharNo,
            @Query("AadharProof") String AadharProof,
            @Query("AadharFileName") String AadharFileName,
            @Query("GSTNo") String GSTNo,
            @Query("Photo") String Photo,
            @Query("PhotoFileName") String PhotoFileName,
            @Query("DateOfBith") String DateOfBith
    );


    @GET("AddProduct")
    Call<ArrayList<AddProductPojo>> addProduct(
            @Query("ProductCode") String ProductCode,
            @Query("ProductName") String ProductName,
            @Query("ProductPhoto1") String ProductPhoto1,
            @Query("ProductPhotoName1") String ProductPhotoName1,
            @Query("ProductPhoto2") String ProductPhoto2,
            @Query("ProductPhotoName2") String ProductPhotoName2,
            @Query("ProductPhoto3") String ProductPhoto3,
            @Query("ProductPhotoName3") String ProductPhotoName3,
            @Query("ProductPhoto4") String ProductPhoto4,
            @Query("ProductPhotoName4") String ProductPhotoName4,
            @Query("ProductPhoto5") String ProductPhoto5,
            @Query("ProductPhotoName5") String ProductPhotoName5,
            @Query("ProductPrice") String ProductPrice,
            @Query("ProductTotalPoint") String ProductTotalPoint,
            @Query("DistPer") String DistPer,
            @Query("DealerPer") String DealerPer,
            @Query("CustomerPer") String CustomerPer,
            @Query("ProductDescription") String ProductDescription
    );

    @GET("ApproveCustomer")
    Call<ArrayList<ApproveCustomerPojo>> approveCustomer(@Query("CustomerId") String CustomerId);

    @GET("ApproveDealer")
    Call<ArrayList<ApproveDealerPojo>> approveDealer(@Query("DealerId") String DealerId);

    @GET("ApproveDistributor")
    Call<ArrayList<ApproveDistributorPojo>> approveDistributor(@Query("DistributorId") String DistributorId);

    @GET("CheckMobileNoExitstOrNo")
    Call<ArrayList<CheckMobileNoExitstOrNoPojo>> checkMobileNoExistOrNot(@Query("MobileNo") String MobileNo);

    @GET("GetCityList")
    Call<ArrayList<GetCityListPojo>> getCityList(@Query("TalukaId") String TalukaId);

    @GET("GetCustomerList")
    Call<GetCustomerListPojo> getCustomerList(@Query("LoginType") String LoginType,
                                              @Query("LoginId") String LoginId,
                                              @Query("FilterValue") String FilterValue);

    @GET("GetDealerList")
    Call<GetDealerListPojo> getDealerList(@Query("LoginType") String LoginType,
                                          @Query("LoginId") String LoginId,
                                          @Query("FilterValue") String FilterValue);


    @GET("GetDetailForLoginUser")
    Call<ArrayList<GetDetailForLoginUserAdminPojo>> getDetailsForLoginUserAdmin(@Query("MobileNo") String MobileNo);

    @GET("GetDetailForLoginUser")
    Call<ArrayList<GetDetailsForLoginUserDistributorPojo>> getDetailsForLoginUserDistributor(@Query("MobileNo") String MobileNo);

    @GET("GetDetailForLoginUser")
    Call<ArrayList<GetDetailsForLoginUserDealerPojo>> getDetailsForLoginUserDealer(@Query("MobileNo") String MobileNo);

    @GET("GetDetailForLoginUser")
    Call<ArrayList<GetDetailsForLoginUserCustomerPojo>> getDetailsForLoginUserCustomer(@Query("MobileNo") String MobileNo);

    @GET("GetDistributorList")
    Call<ArrayList<GetDistributorListPojo>> getDistributorList(@Query("LoginId") String LoginId,
                                                    @Query("FilterValue") String FilterValue);

    @GET("GetDistrictList")
    Call<ArrayList<GetDistrictListPojo>> getDistrictList(@Query("StateId") String StateId);

    @GET("GetProductList")
    Call<ArrayList<GetProductListPojo>> getProductList();

    @GET("GetStateList")
    Call<ArrayList<GetStateListPojo>> getStateList();

    @GET("GetTalukaList")
    Call<ArrayList<GetTalukaListPojo>> getTalukaList(@Query("DistrictId") String DistrictId);

    @GET("SendOtp")
    Call<ArrayList<SendOtpPojo>> sendOtp(@Query("MobileNo") String MobileNo,
                                         @Query("Otp") String Otp);

}
