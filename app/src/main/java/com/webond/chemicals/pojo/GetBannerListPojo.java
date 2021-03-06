package com.webond.chemicals.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBannerListPojo {

    @SerializedName("BannerId")
    @Expose
    private Integer bannerId;
    @SerializedName("BannerPath")
    @Expose
    private String bannerPath;
    @SerializedName("BannerName")
    @Expose
    private String bannerName;

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

}
