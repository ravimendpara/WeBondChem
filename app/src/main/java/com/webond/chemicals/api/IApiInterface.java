package com.webond.chemicals.api;


import com.webond.chemicals.pojo.AddCustomerPojo;
import com.webond.chemicals.pojo.AddDealerOrderDataPojo;
import com.webond.chemicals.pojo.AddDealerPojo;
import com.webond.chemicals.pojo.AddDistributerPojo;
import com.webond.chemicals.pojo.AddDistributorOrderDataPojo;
import com.webond.chemicals.pojo.AddProductPojo;
import com.webond.chemicals.pojo.ApproveCustomerPojo;
import com.webond.chemicals.pojo.ApproveDealerPojo;
import com.webond.chemicals.pojo.ApproveDistributorPojo;
import com.webond.chemicals.pojo.CheckMobileNoExitstOrNoPojo;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.pojo.GetCityListPojo;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.pojo.GetDealerListByCityIdPojo;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.pojo.GetDetailForLoginUserAdminPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserCustomerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDealerPojo;
import com.webond.chemicals.pojo.GetDetailsForLoginUserDistributorPojo;
import com.webond.chemicals.pojo.GetDistributorListByCityIdPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetProductDetailByIdPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetStateListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.pojo.SendOtpPojo;
import com.webond.chemicals.pojo.UpdateProductPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiInterface {


    @FormUrlEncoded
    @POST("AddDistributorOrderData")
    Call<ArrayList<AddDistributorOrderDataPojo>> addDistributorOrderData(
            @Field("OrderDate") String OrderDate,
            @Field("DistributorId") String DistributorId,
            @Field("ContactPersonDetail") String ContactPersonDetail,
            @Field("SiteAddress") String SiteAddress,
            @Field("ContactNo") String ContactNo,
            @Field("PinCode") String PinCode,
            @Field("DistrictId") String DistrictId,
            @Field("TalukaId") String TalukaId,
            @Field("ProductList") String ProductList
    );

    @FormUrlEncoded
    @POST("AddDealerOrderData")
    Call<ArrayList<AddDealerOrderDataPojo>> addDealerOrder(
            @Field("OrderDate") String OrderDate,
            @Field("DealerId") String DealerId,
            @Field("DealerDistributorId") String DealerDistributorId,
            @Field("ContactPersonDetail") String ContactPersonDetail,
            @Field("SiteAddress") String SiteAddress,
            @Field("ContactNo") String ContactNo,
            @Field("PinCode") String PinCode,
            @Field("DistrictId") String DistrictId,
            @Field("TalukaId") String TalukaId,
            @Field("ProductList") String ProductList
    );

    @FormUrlEncoded
    @POST("AddCustomer")
    Call<ArrayList<AddCustomerPojo>> addCustomer(
            @Field("CustomerName") String CustomerName,
            @Field("DealerId") String DealerId,
            @Field("StateId") String StateId,
            @Field("DistrictId") String DistrictId,
            @Field("TalukaId") String TalukaId,
            @Field("CityId") String CityId,
            @Field("MobileNo") String MobileNo,
            @Field("MobileNo2") String MobileNo2,
            @Field("Address") String Address,
            @Field("PinCode") String PinCode,
            @Field("Email") String Email,
            @Field("AadharNo") String AadharNo,
            @Field("AadharProof") String AadharProof,
            @Field("AadharFileName") String AadharFileName,
            @Field("GSTNo") String GSTNo,
            @Field("Photo") String Photo,
            @Field("PhotoFileName") String PhotoFileName,
            @Field("DateOfBirth") String DateOfBirth
    );

    @FormUrlEncoded
    @POST("AddDealer")
    Call<ArrayList<AddDealerPojo>> addDealer(
            @Field("DealerName") String DealerName,
            @Field("PethiName") String PethiName,
            @Field("DistributorId") String DistributorId,
            @Field("StateId") String StateId,
            @Field("DistrictId") String DistrictId,
            @Field("TalukaId") String TalukaId,
            @Field("CityId") String CityId,
            @Field("MobileNo") String MobileNo,
            @Field("MobileNo2") String MobileNo2,
            @Field("Address") String Address,
            @Field("PinCode") String PinCode,
            @Field("Email") String Email,
            @Field("AadharNo") String AadharNo,
            @Field("AadharProof") String AadharProof,
            @Field("AadharFileName") String AadharFileName,
            @Field("GSTNo") String GSTNo,
            @Field("Photo") String Photo,
            @Field("PhotoFileName") String PhotoFileName,
            @Field("DateOfBirth") String DateOfBirth
    );

    @FormUrlEncoded
    @POST("AddDistributer")
    Call<ArrayList<AddDistributerPojo>> addDistributor(
            @Field("DistributerName") String DistributerName,
            @Field("PethiName") String PethiName,
            @Field("StateId") String StateId,
            @Field("DistrictId") String DistrictId,
            @Field("TalukaId") String TalukaId,
            @Field("CityId") String CityId,
            @Field("MobileNo") String MobileNo,
            @Field("MobileNo2") String MobileNo2,
            @Field("Address") String Address,
            @Field("PinCode") String PinCode,
            @Field("Email") String Email,
            @Field("AadharNo") String AadharNo,
            @Field("AadharProof") String AadharProof,
            @Field("AadharFileName") String AadharFileName,
            @Field("GSTNo") String GSTNo,
            @Field("Photo") String Photo,
            @Field("PhotoFileName") String PhotoFileName,
            @Field("DateOfBith") String DateOfBith
    );

    @FormUrlEncoded
    @POST("AddProduct")
    Call<ArrayList<AddProductPojo>> addProduct(
            @Field("ProductCode") String ProductCode,
            @Field("ProductName") String ProductName,
            @Field("ProductPhoto1") String ProductPhoto1,
            @Field("ProductPhotoName1") String ProductPhotoName1,
            @Field("ProductPhoto2") String ProductPhoto2,
            @Field("ProductPhotoName2") String ProductPhotoName2,
            @Field("ProductPhoto3") String ProductPhoto3,
            @Field("ProductPhotoName3") String ProductPhotoName3,
            @Field("ProductPhoto4") String ProductPhoto4,
            @Field("ProductPhotoName4") String ProductPhotoName4,
            @Field("ProductPhoto5") String ProductPhoto5,
            @Field("ProductPhotoName5") String ProductPhotoName5,
            @Field("ProductPrice") String ProductPrice,
            @Field("ProductTotalPoint") String ProductTotalPoint,
            @Field("DistPer") String DistPer,
            @Field("DealerPer") String DealerPer,
            @Field("CustomerPer") String CustomerPer,
            @Field("ProductDescription") String ProductDescription
    );

    @FormUrlEncoded
    @POST("UpdateProduct")
    Call<ArrayList<UpdateProductPojo>> updateProduct(
            @Field("ProductId") String ProductId,
            @Field("ProductCode") String ProductCode,
            @Field("ProductName") String ProductName,
            @Field("ProductPhoto1") String ProductPhoto1,
            @Field("ProductPhotoName1") String ProductPhotoName1,
            @Field("ProductPhoto2") String ProductPhoto2,
            @Field("ProductPhotoName2") String ProductPhotoName2,
            @Field("ProductPhoto3") String ProductPhoto3,
            @Field("ProductPhotoName3") String ProductPhotoName3,
            @Field("ProductPhoto4") String ProductPhoto4,
            @Field("ProductPhotoName4") String ProductPhotoName4,
            @Field("ProductPhoto5") String ProductPhoto5,
            @Field("ProductPhotoName5") String ProductPhotoName5,
            @Field("ProductPrice") String ProductPrice,
            @Field("ProductTotalPoint") String ProductTotalPoint,
            @Field("DistPer") String DistPer,
            @Field("DealerPer") String DealerPer,
            @Field("CustomerPer") String CustomerPer,
            @Field("ProductDescription") String ProductDescription
    );

    @GET("ApproveCustomer")
    Call<ArrayList<ApproveCustomerPojo>> approveCustomer(@Query("CustomerId") String CustomerId,
                                                         @Query("Status") String Status);

    @GET("ApproveDealer")
    Call<ArrayList<ApproveDealerPojo>> approveDealer(@Query("DealerId") String DealerId,
                                                     @Query("Status") String Status);

    @GET("ApproveDistributor")
    Call<ArrayList<ApproveDistributorPojo>> approveDistributor(@Query("DistributorId") String DistributorId, @Query("Status") String status);

    @GET("CheckMobileNoExitstOrNo")
    Call<ArrayList<CheckMobileNoExitstOrNoPojo>> checkMobileNoExistOrNot(@Query("MobileNo") String MobileNo);

    @GET("GetCityList")
    Call<ArrayList<GetCityListPojo>> getCityList(@Query("TalukaId") String TalukaId);

    @GET("GetCustomerList")
    Call<ArrayList<GetCustomerListPojo>> getCustomerList(@Query("LoginType") String LoginType,
                                                         @Query("LoginId") String LoginId,
                                                         @Query("FilterValue") String FilterValue);

    @GET("GetDealerList")
    Call<ArrayList<GetDealerListPojo>> getDealerList(@Query("LoginType") String LoginType,
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

    @GET("GetDealerListByCityId")
    Call<ArrayList<GetDealerListByCityIdPojo>> getDealerListByCityId(@Query("cityid") String cityId);

    @GET("GetDistributorListByCityId")
    Call<ArrayList<GetDistributorListByCityIdPojo>> getDistributorListByCityId(@Query("cityid") String cityid);

    @GET("GetBannerList")
    Call<ArrayList<GetBannerListPojo>> getBannerList();


    @GET("GetProductDetailById")
    Call<ArrayList<GetProductDetailByIdPojo>> getProductDetailsById(@Query("ProductId") String ProductId);
}
