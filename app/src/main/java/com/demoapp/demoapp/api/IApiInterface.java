package com.demoapp.demoapp.api;


import com.demoapp.demoapp.pojo.AddCustomerPojo;
import com.demoapp.demoapp.pojo.AddDealerPojo;
import com.demoapp.demoapp.pojo.AddDistributerPojo;
import com.demoapp.demoapp.pojo.AddProductPojo;
import com.demoapp.demoapp.pojo.ApproveCustomerPojo;
import com.demoapp.demoapp.pojo.ApproveDealerPojo;
import com.demoapp.demoapp.pojo.ApproveDistributorPojo;
import com.demoapp.demoapp.pojo.CheckMobileNoExitstOrNoPojo;
import com.demoapp.demoapp.pojo.GetCityListPojo;
import com.demoapp.demoapp.pojo.GetCustomerListPojo;
import com.demoapp.demoapp.pojo.GetDealerListPojo;
import com.demoapp.demoapp.pojo.GetDetailForLoginUserPojo;
import com.demoapp.demoapp.pojo.GetDistributorListPojo;
import com.demoapp.demoapp.pojo.GetDistrictListPojo;
import com.demoapp.demoapp.pojo.GetProductListPojo;
import com.demoapp.demoapp.pojo.GetStateListPojo;
import com.demoapp.demoapp.pojo.GetTalukaListPojo;
import com.demoapp.demoapp.pojo.SendOtpPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiInterface {


    @GET("AddCustomer")
    Call<AddCustomerPojo> addCustomer(
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
    Call<AddDealerPojo> addDealer(
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
    Call<AddDistributerPojo> addDistributor(
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
    Call<AddProductPojo> addProduct(
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
    Call<ApproveCustomerPojo> approveCustomer(@Query("CustomerId") String CustomerId);

    @GET("ApproveDealer")
    Call<ApproveDealerPojo> approveDealer(@Query("DealerId") String DealerId);

    @GET("ApproveDistributor")
    Call<ApproveDistributorPojo> approveDistributor(@Query("DistributorId") String DistributorId);

    @GET("CheckMobileNoExitstOrNo")
    Call<CheckMobileNoExitstOrNoPojo> checkMobileNoExistOrNot(@Query("MobileNo") String MobileNo);

    @GET("GetCityList")
    Call<GetCityListPojo> getCityList(@Query("TalukaId") String TalukaId);

    @GET("GetCustomerList")
    Call<GetCustomerListPojo> getCustomerList(@Query("LoginType") String LoginType,
                                              @Query("LoginId") String LoginId,
                                              @Query("FilterValue") String FilterValue);

    @GET("GetDealerList")
    Call<GetDealerListPojo> getDealerList(@Query("LoginType") String LoginType,
                                          @Query("LoginId") String LoginId,
                                          @Query("FilterValue") String FilterValue);


    @GET("GetDetailForLoginUser")
    Call<GetDetailForLoginUserPojo> getDetailsForLoginUser(@Query("MobileNo") String MobileNo);

    @GET("GetDistributorList")
    Call<GetDistributorListPojo> getDistributorList(@Query("LoginId") String LoginId,
                                                    @Query("FilterValue") String FilterValue);

    @GET("GetDistrictList")
    Call<GetDistrictListPojo> getDistrictList(@Query("StateId") String StateId);

    @GET("GetProductList")
    Call<GetProductListPojo> getProductList();

    @GET("GetStateList")
    Call<GetStateListPojo> getStateList();

    @GET("GetTalukaList")
    Call<GetTalukaListPojo> getTalukaList(@Query("DistrictId") String DistrictId);

    @GET("SendOtp")
    Call<SendOtpPojo> sendOtp(@Query("MobileNo") String MobileNo,
                              @Query("Otp") String Otp);

}
