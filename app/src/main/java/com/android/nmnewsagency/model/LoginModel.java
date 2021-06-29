package com.android.nmnewsagency.model;

import java.util.List;

public class LoginModel {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;
    private String message;
    private List<Data> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {


        private String UserId;
        private String Name;
        private String Email;
        private boolean EmailVerified;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public boolean isEmailVerified() {
            return EmailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            EmailVerified = emailVerified;
        }

        public boolean isUserActive() {
            return UserActive;
        }

        public void setUserActive(boolean userActive) {
            UserActive = userActive;
        }

        public String getProfileImage() {
            return ProfileImage;
        }

        public void setProfileImage(String profileImage) {
            ProfileImage = profileImage;
        }

        public int getLimit() {
            return Limit;
        }

        public void setLimit(int limit) {
            Limit = limit;
        }

        private boolean UserActive;
        private String ProfileImage;
        private int Limit;


    }
}
