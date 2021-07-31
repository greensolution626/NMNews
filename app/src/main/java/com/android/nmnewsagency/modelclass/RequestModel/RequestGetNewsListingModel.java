package com.android.nmnewsagency.modelclass.RequestModel;

public class RequestGetNewsListingModel {

    /**
     * Country_Name : India
     * State_Name : Rajasthan
     * City_Name : Jaipur
     * Tahsil_Name : Snaganer
     * UserId : f0523348-0c2f-44e4-b0cd-c5d5d4f66ada
     * PageIndex : 1
     * PageOffset : 5
     */

    private String Country_Name;
    private String State_Name;
    private String City_Name;
    private String Tahsil_Name;
    private String UserId;
    private int PageIndex;
    private int PageOffset;

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String Country_Name) {
        this.Country_Name = Country_Name;
    }

    public String getState_Name() {
        return State_Name;
    }

    public void setState_Name(String State_Name) {
        this.State_Name = State_Name;
    }

    public String getCity_Name() {
        return City_Name;
    }

    public void setCity_Name(String City_Name) {
        this.City_Name = City_Name;
    }

    public String getTahsil_Name() {
        return Tahsil_Name;
    }

    public void setTahsil_Name(String Tahsil_Name) {
        this.Tahsil_Name = Tahsil_Name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public int getPageOffset() {
        return PageOffset;
    }

    public void setPageOffset(int PageOffset) {
        this.PageOffset = PageOffset;
    }
}
