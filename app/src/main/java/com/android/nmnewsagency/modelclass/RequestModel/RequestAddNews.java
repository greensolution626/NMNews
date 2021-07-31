package com.android.nmnewsagency.modelclass.RequestModel;

import java.io.Serializable;

public class RequestAddNews {


    /**
     * NewsObj : {"Id":0,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Description":"आटपाडी: ","Suggestion":"News Updated","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498","IsBreakingNews":true,"NewsType":1,"CountryId":1,"Country_Name":"India","StateId":1,"State_Name":"Rajasthan","CityId":1,"City_Name":"Jaipur","TahsilId":1,"Tahsil_Name":"Snaganer","AddressLine_1":"Jaipur","AddressLin_2":null,"Lat":270.25688,"Long":45.8549,"ZipCode":"302015","UserTags":"@neeraj tank","HashTags":"#latest news,#corona news"}
     * NewsMediaObj : {"Id":0,"NewsId":0,"ImageUrl":"xyz.jpg","IsActive":true,"AddedOn":null,"MediaType":"VIDEO","MediaSource":"YOUTUBE","MediaSize":5,"SizeUnit":"MB","VideoUrl":"https://www.youtube.com/watch?v=w88LODwHoQM"}
     */

    private NewsObjBean NewsObj;
    private NewsMediaObjBean NewsMediaObj;

    public NewsObjBean getNewsObj() {
        return NewsObj;
    }

    public void setNewsObj(NewsObjBean NewsObj) {
        this.NewsObj = NewsObj;
    }

    public NewsMediaObjBean getNewsMediaObj() {
        return NewsMediaObj;
    }

    public void setNewsMediaObj(NewsMediaObjBean NewsMediaObj) {
        this.NewsMediaObj = NewsMediaObj;
    }

    public static class NewsObjBean implements Serializable {
        /**
         * Id : 0
         * Title : तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे
         * Description : आटपाडी:
         * Suggestion : News Updated
         * UserId : 6fc00a95-dc1f-473d-b324-a990a9543498
         * IsBreakingNews : true
         * NewsType : 1
         * CountryId : 1
         * Country_Name : India
         * StateId : 1
         * State_Name : Rajasthan
         * CityId : 1
         * City_Name : Jaipur
         * TahsilId : 1
         * Tahsil_Name : Snaganer
         * AddressLine_1 : Jaipur
         * AddressLin_2 : null
         * Lat : 270.25688
         * Long : 45.8549
         * ZipCode : 302015
         * UserTags : @neeraj tank
         * HashTags : #latest news,#corona news
         */

        private int Id;
        private String Title;
        private String Description;
        private String Suggestion;
        private String UserId;
        private boolean IsBreakingNews;
        private int NewsType;
        private int CountryId;
        private String Country_Name;
        private int StateId;
        private String State_Name;
        private int CityId;
        private String City_Name;
        private int TahsilId;
        private String Tahsil_Name;
        private String AddressLine_1;
        private Object AddressLin_2;
        private double Lat;
        private double Long;
        private String ZipCode;
        private String UserTags;
        private String HashTags;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getSuggestion() {
            return Suggestion;
        }

        public void setSuggestion(String Suggestion) {
            this.Suggestion = Suggestion;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public boolean isIsBreakingNews() {
            return IsBreakingNews;
        }

        public void setIsBreakingNews(boolean IsBreakingNews) {
            this.IsBreakingNews = IsBreakingNews;
        }

        public int getNewsType() {
            return NewsType;
        }

        public void setNewsType(int NewsType) {
            this.NewsType = NewsType;
        }

        public int getCountryId() {
            return CountryId;
        }

        public void setCountryId(int CountryId) {
            this.CountryId = CountryId;
        }

        public String getCountry_Name() {
            return Country_Name;
        }

        public void setCountry_Name(String Country_Name) {
            this.Country_Name = Country_Name;
        }

        public int getStateId() {
            return StateId;
        }

        public void setStateId(int StateId) {
            this.StateId = StateId;
        }

        public String getState_Name() {
            return State_Name;
        }

        public void setState_Name(String State_Name) {
            this.State_Name = State_Name;
        }

        public int getCityId() {
            return CityId;
        }

        public void setCityId(int CityId) {
            this.CityId = CityId;
        }

        public String getCity_Name() {
            return City_Name;
        }

        public void setCity_Name(String City_Name) {
            this.City_Name = City_Name;
        }

        public int getTahsilId() {
            return TahsilId;
        }

        public void setTahsilId(int TahsilId) {
            this.TahsilId = TahsilId;
        }

        public String getTahsil_Name() {
            return Tahsil_Name;
        }

        public void setTahsil_Name(String Tahsil_Name) {
            this.Tahsil_Name = Tahsil_Name;
        }

        public String getAddressLine_1() {
            return AddressLine_1;
        }

        public void setAddressLine_1(String AddressLine_1) {
            this.AddressLine_1 = AddressLine_1;
        }

        public Object getAddressLin_2() {
            return AddressLin_2;
        }

        public void setAddressLin_2(Object AddressLin_2) {
            this.AddressLin_2 = AddressLin_2;
        }

        public double getLat() {
            return Lat;
        }

        public void setLat(double Lat) {
            this.Lat = Lat;
        }

        public double getLong() {
            return Long;
        }

        public void setLong(double Long) {
            this.Long = Long;
        }

        public String getZipCode() {
            return ZipCode;
        }

        public void setZipCode(String ZipCode) {
            this.ZipCode = ZipCode;
        }

        public String getUserTags() {
            return UserTags;
        }

        public void setUserTags(String UserTags) {
            this.UserTags = UserTags;
        }

        public String getHashTags() {
            return HashTags;
        }

        public void setHashTags(String HashTags) {
            this.HashTags = HashTags;
        }
    }

    public static class NewsMediaObjBean implements Serializable {
        /**
         * Id : 0
         * NewsId : 0
         * ImageUrl : xyz.jpg
         * IsActive : true
         * AddedOn : null
         * MediaType : VIDEO
         * MediaSource : YOUTUBE
         * MediaSize : 5
         * SizeUnit : MB
         * VideoUrl : https://www.youtube.com/watch?v=w88LODwHoQM
         */

        private int Id;
        private int NewsId;
        private String ImageUrl;
        private boolean IsActive;
        private Object AddedOn;
        private String MediaType;
        private String MediaSource;
        private double MediaSize;
        private String SizeUnit;
        private String VideoUrl;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getNewsId() {
            return NewsId;
        }

        public void setNewsId(int NewsId) {
            this.NewsId = NewsId;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public boolean isIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public Object getAddedOn() {
            return AddedOn;
        }

        public void setAddedOn(Object AddedOn) {
            this.AddedOn = AddedOn;
        }

        public String getMediaType() {
            return MediaType;
        }

        public void setMediaType(String MediaType) {
            this.MediaType = MediaType;
        }

        public String getMediaSource() {
            return MediaSource;
        }

        public void setMediaSource(String MediaSource) {
            this.MediaSource = MediaSource;
        }

        public double getMediaSize() {
            return MediaSize;
        }

        public void setMediaSize(double MediaSize) {
            this.MediaSize = MediaSize;
        }

        public String getSizeUnit() {
            return SizeUnit;
        }

        public void setSizeUnit(String SizeUnit) {
            this.SizeUnit = SizeUnit;
        }

        public String getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(String VideoUrl) {
            this.VideoUrl = VideoUrl;
        }
    }
}
