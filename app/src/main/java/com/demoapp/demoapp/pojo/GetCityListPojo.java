package com.demoapp.demoapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCityListPojo {///parse as this model array list in api interface and implementer

    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("TalukaId")
    @Expose
    private Integer talukaId;
    @SerializedName("City_Is_Delete")
    @Expose
    private Integer cityIsDelete;
    @SerializedName("City_Is_Status")
    @Expose
    private Integer cityIsStatus;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public Integer getCityIsDelete() {
        return cityIsDelete;
    }

    public void setCityIsDelete(Integer cityIsDelete) {
        this.cityIsDelete = cityIsDelete;
    }

    public Integer getCityIsStatus() {
        return cityIsStatus;
    }

    public void setCityIsStatus(Integer cityIsStatus) {
        this.cityIsStatus = cityIsStatus;
    }


}
