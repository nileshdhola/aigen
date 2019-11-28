package com.aigentech.in;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CarDetailsDto implements Serializable {
    //car info
    @SerializedName("car_number")
    private String carNumber;

    @SerializedName("car_company_name")
    private String carCompanyName;

    @SerializedName("car_name")
    private String carName;


    //seller info
    @SerializedName("seller_name")
    private String sellerName;

    @SerializedName("seller_mobile")
    private String selleMobileNo;

    @SerializedName("seller_email_address")
    private String sellerEmailAddress;

    @SerializedName("total_photo_count")
    private String totalPhotoCount;


    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarCompanyName() {
        return carCompanyName;
    }

    public void setCarCompanyName(String carCompanyName) {
        this.carCompanyName = carCompanyName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSelleMobileNo() {
        return selleMobileNo;
    }

    public void setSelleMobileNo(String selleMobileNo) {
        this.selleMobileNo = selleMobileNo;
    }

    public String getSellerEmailAddress() {
        return sellerEmailAddress;
    }

    public void setSellerEmailAddress(String sellerEmailAddress) {
        this.sellerEmailAddress = sellerEmailAddress;
    }


    public String getTotalPhotoCount() {
        return totalPhotoCount;
    }

    public void setTotalPhotoCount(String totalPhotoCount) {
        this.totalPhotoCount = totalPhotoCount;
    }
}
