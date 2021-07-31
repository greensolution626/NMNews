package com.android.nmnewsagency.modelclass;

import java.io.Serializable;
import java.util.List;

public class GetProfileDataModel {


    /**
     * Status : true
     * Message : Success
     * Data : {"AspNetUser":{"NewsCount":37,"UserTagVideo":0,"SavedNewsCount":4,"IsFollowed":false,"Followers":6,"Followings":1,"FollowersSuffix":"6","FollowingsSuffix":"1","Id":"e3efdf6a-e61f-4a2a-bfd3-baea75854bc6","Email":null,"EmailConfirmed":false,"PasswordHash":null,"SecurityStamp":null,"ContactNumber":null,"PhoneNumberConfirmed":false,"TwoFactorEnabled":false,"LockoutEndDateUtc":"0001-01-01T00:00:00","LockoutEnabled":false,"AccessFailedCount":0,"UserName":"ashokpawartv","FirstName":"Ashok News (TV REPORTER ATPADI)","LastName":"","FullName":"Ashok News (TV REPORTER ATPADI) ","UserType":"USER","AddDate":null,"ModifyDate":null,"IsActive":false,"BusinessName":null,"Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgcOHfqdaeHeNDM9GChvOI5Ga3i8WNg15XRMSqfjw","PageUrl":null,"AboutMe":null,"Profile_Score":54},"UserAddress":[]}
     */

    private boolean Status;
    private String Message;
    private DataBean Data;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * AspNetUser : {"NewsCount":37,"UserTagVideo":0,"SavedNewsCount":4,"IsFollowed":false,"Followers":6,"Followings":1,"FollowersSuffix":"6","FollowingsSuffix":"1","Id":"e3efdf6a-e61f-4a2a-bfd3-baea75854bc6","Email":null,"EmailConfirmed":false,"PasswordHash":null,"SecurityStamp":null,"ContactNumber":null,"PhoneNumberConfirmed":false,"TwoFactorEnabled":false,"LockoutEndDateUtc":"0001-01-01T00:00:00","LockoutEnabled":false,"AccessFailedCount":0,"UserName":"ashokpawartv","FirstName":"Ashok News (TV REPORTER ATPADI)","LastName":"","FullName":"Ashok News (TV REPORTER ATPADI) ","UserType":"USER","AddDate":null,"ModifyDate":null,"IsActive":false,"BusinessName":null,"Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgcOHfqdaeHeNDM9GChvOI5Ga3i8WNg15XRMSqfjw","PageUrl":null,"AboutMe":null,"Profile_Score":54}
         * UserAddress : []
         */

        private AspNetUserBean AspNetUser;
        private List<?> UserAddress;

        public AspNetUserBean getAspNetUser() {
            return AspNetUser;
        }

        public void setAspNetUser(AspNetUserBean AspNetUser) {
            this.AspNetUser = AspNetUser;
        }

        public List<?> getUserAddress() {
            return UserAddress;
        }

        public void setUserAddress(List<?> UserAddress) {
            this.UserAddress = UserAddress;
        }

        public static class AspNetUserBean implements Serializable {
            /**
             * NewsCount : 37
             * UserTagVideo : 0
             * SavedNewsCount : 4
             * IsFollowed : false
             * Followers : 6
             * Followings : 1
             * FollowersSuffix : 6
             * FollowingsSuffix : 1
             * Id : e3efdf6a-e61f-4a2a-bfd3-baea75854bc6
             * Email : null
             * EmailConfirmed : false
             * PasswordHash : null
             * SecurityStamp : null
             * ContactNumber : null
             * PhoneNumberConfirmed : false
             * TwoFactorEnabled : false
             * LockoutEndDateUtc : 0001-01-01T00:00:00
             * LockoutEnabled : false
             * AccessFailedCount : 0
             * UserName : ashokpawartv
             * FirstName : Ashok News (TV REPORTER ATPADI)
             * LastName :
             * FullName : Ashok News (TV REPORTER ATPADI)
             * UserType : USER
             * AddDate : null
             * ModifyDate : null
             * IsActive : false
             * BusinessName : null
             * Avatar : https://lh3.googleusercontent.com/a-/AOh14GgcOHfqdaeHeNDM9GChvOI5Ga3i8WNg15XRMSqfjw
             * PageUrl : null
             * AboutMe : null
             * Profile_Score : 54.0
             */

            private int NewsCount;
            private int UserTagVideo;
            private int SavedNewsCount;
            private boolean IsFollowed;
            private int Followers;
            private int Followings;
            private String FollowersSuffix;
            private String FollowingsSuffix;
            private String Id;
            private Object Email;
            private boolean EmailConfirmed;
            private Object PasswordHash;
            private Object SecurityStamp;
            private Object ContactNumber;
            private boolean PhoneNumberConfirmed;
            private boolean TwoFactorEnabled;
            private String LockoutEndDateUtc;
            private boolean LockoutEnabled;
            private int AccessFailedCount;
            private String UserName;
            private String FirstName;
            private String LastName;
            private String FullName;
            private String UserType;
            private Object AddDate;
            private Object ModifyDate;
            private boolean IsActive;
            private Object BusinessName;
            private String Avatar;
            private Object PageUrl;
            private Object AboutMe;
            private double Profile_Score;

            public int getNewsCount() {
                return NewsCount;
            }

            public void setNewsCount(int NewsCount) {
                this.NewsCount = NewsCount;
            }

            public int getUserTagVideo() {
                return UserTagVideo;
            }

            public void setUserTagVideo(int UserTagVideo) {
                this.UserTagVideo = UserTagVideo;
            }

            public int getSavedNewsCount() {
                return SavedNewsCount;
            }

            public void setSavedNewsCount(int SavedNewsCount) {
                this.SavedNewsCount = SavedNewsCount;
            }

            public boolean isIsFollowed() {
                return IsFollowed;
            }

            public void setIsFollowed(boolean IsFollowed) {
                this.IsFollowed = IsFollowed;
            }

            public int getFollowers() {
                return Followers;
            }

            public void setFollowers(int Followers) {
                this.Followers = Followers;
            }

            public int getFollowings() {
                return Followings;
            }

            public void setFollowings(int Followings) {
                this.Followings = Followings;
            }

            public String getFollowersSuffix() {
                return FollowersSuffix;
            }

            public void setFollowersSuffix(String FollowersSuffix) {
                this.FollowersSuffix = FollowersSuffix;
            }

            public String getFollowingsSuffix() {
                return FollowingsSuffix;
            }

            public void setFollowingsSuffix(String FollowingsSuffix) {
                this.FollowingsSuffix = FollowingsSuffix;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public Object getEmail() {
                return Email;
            }

            public void setEmail(Object Email) {
                this.Email = Email;
            }

            public boolean isEmailConfirmed() {
                return EmailConfirmed;
            }

            public void setEmailConfirmed(boolean EmailConfirmed) {
                this.EmailConfirmed = EmailConfirmed;
            }

            public Object getPasswordHash() {
                return PasswordHash;
            }

            public void setPasswordHash(Object PasswordHash) {
                this.PasswordHash = PasswordHash;
            }

            public Object getSecurityStamp() {
                return SecurityStamp;
            }

            public void setSecurityStamp(Object SecurityStamp) {
                this.SecurityStamp = SecurityStamp;
            }

            public Object getContactNumber() {
                return ContactNumber;
            }

            public void setContactNumber(Object ContactNumber) {
                this.ContactNumber = ContactNumber;
            }

            public boolean isPhoneNumberConfirmed() {
                return PhoneNumberConfirmed;
            }

            public void setPhoneNumberConfirmed(boolean PhoneNumberConfirmed) {
                this.PhoneNumberConfirmed = PhoneNumberConfirmed;
            }

            public boolean isTwoFactorEnabled() {
                return TwoFactorEnabled;
            }

            public void setTwoFactorEnabled(boolean TwoFactorEnabled) {
                this.TwoFactorEnabled = TwoFactorEnabled;
            }

            public String getLockoutEndDateUtc() {
                return LockoutEndDateUtc;
            }

            public void setLockoutEndDateUtc(String LockoutEndDateUtc) {
                this.LockoutEndDateUtc = LockoutEndDateUtc;
            }

            public boolean isLockoutEnabled() {
                return LockoutEnabled;
            }

            public void setLockoutEnabled(boolean LockoutEnabled) {
                this.LockoutEnabled = LockoutEnabled;
            }

            public int getAccessFailedCount() {
                return AccessFailedCount;
            }

            public void setAccessFailedCount(int AccessFailedCount) {
                this.AccessFailedCount = AccessFailedCount;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getFirstName() {
                return FirstName;
            }

            public void setFirstName(String FirstName) {
                this.FirstName = FirstName;
            }

            public String getLastName() {
                return LastName;
            }

            public void setLastName(String LastName) {
                this.LastName = LastName;
            }

            public String getFullName() {
                return FullName;
            }

            public void setFullName(String FullName) {
                this.FullName = FullName;
            }

            public String getUserType() {
                return UserType;
            }

            public void setUserType(String UserType) {
                this.UserType = UserType;
            }

            public Object getAddDate() {
                return AddDate;
            }

            public void setAddDate(Object AddDate) {
                this.AddDate = AddDate;
            }

            public Object getModifyDate() {
                return ModifyDate;
            }

            public void setModifyDate(Object ModifyDate) {
                this.ModifyDate = ModifyDate;
            }

            public boolean isIsActive() {
                return IsActive;
            }

            public void setIsActive(boolean IsActive) {
                this.IsActive = IsActive;
            }

            public Object getBusinessName() {
                return BusinessName;
            }

            public void setBusinessName(Object BusinessName) {
                this.BusinessName = BusinessName;
            }

            public String getAvatar() {
                return Avatar;
            }

            public void setAvatar(String Avatar) {
                this.Avatar = Avatar;
            }

            public Object getPageUrl() {
                return PageUrl;
            }

            public void setPageUrl(Object PageUrl) {
                this.PageUrl = PageUrl;
            }

            public Object getAboutMe() {
                return AboutMe;
            }

            public void setAboutMe(Object AboutMe) {
                this.AboutMe = AboutMe;
            }

            public double getProfile_Score() {
                return Profile_Score;
            }

            public void setProfile_Score(double Profile_Score) {
                this.Profile_Score = Profile_Score;
            }
        }
    }
}
