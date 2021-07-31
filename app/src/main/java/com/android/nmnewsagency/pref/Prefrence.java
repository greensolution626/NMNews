package com.android.nmnewsagency.pref;

import android.content.Context;
import android.content.SharedPreferences;


public class Prefrence {
    public static final String CONFIG_FILE = "NMNEWS";
    public static final String INVALID_STRING = "";
    public static final boolean INVALID_BOOLEAN = false;
    public static final int INVALID_INT = 0;
    public static final boolean IS_FULL_LOGIN = false;
    public static final boolean IS_LOGIN = false;
    public static final String FLD_LOGIN = "login";
    public static final String FLD_TAHSIL = "tahsil";
    public static final String FLD_DEVICEID = "deviceId";
    public static final String FLD_USERID = "userId";
    public static final String FLD_NAME = "name";
    public static final String FLD_EMAILID = "emailId";
    public static final String FLD_PROFILEIMAGE = "profileImage";
    public static final String FLD_COUNTRYID = "countryId";
    public static final String FLD_CITYID = "cityId";
    public static final String FLD_STATEID = "stateId";
    public static final String FLD_TAHSILID = "tahsilId";
    public static final String FLD_MEDIAFILE = "file";
    public static final String FLD_COUNTRYNAME = "countryName";
    public static final String FLD_CITYNAME = "cityName";
    public static final String FLD_STATENAME = "stateName";
    public static final String FLD_FIRSTNAME = "firstName";
    public static final String FLD_LASTNAME = "lastName";
    public static final String FLD_NEWSID = "newsId";
    public static final String FLD_ISLOCATIONMATCH = "IsLocationMatch";
    public static final String FLD_DEVICETOKEN = "deviceToken";
    public static final String FLD_POSITION = "recPosition";
    public static final String FLD_ISUPLOAD = "isUpload";

    private static SharedPreferences preference = null;
    private static SharedPreferences.Editor editor;
    private static Context mContext;

    public static void init(Context mContext) {
        Prefrence.mContext = mContext;
        Prefrence.preference = mContext.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        Prefrence.editor = preference.edit();
    }

    public static void removeOrClearPerferance(String key) {
        if (key != null && key.trim().length() != 0)
            editor.remove(key);
        else {
            editor.clear();
        }
        editor.commit();
    }

    public static void removeOrClearAllPerferanceData() {
        editor.clear();
        editor.apply();
    }

    public static String getlogin() {
        return preference.getString(FLD_LOGIN, INVALID_STRING);
    }

    public static void setlogin(String login) {
        editor.putString(FLD_LOGIN, login);
        editor.commit();
    }

    public static String gettahsil() {
        return preference.getString(FLD_TAHSIL, INVALID_STRING);
    }

    public static void settahsil(String login) {
        editor.putString(FLD_TAHSIL, login);
        editor.commit();
    }

    public static void setDeviceId(String login) {
        editor.putString(FLD_DEVICEID, login);
        editor.commit();
    }

    public static String getDeviceId() {
        return preference.getString(FLD_DEVICEID, INVALID_STRING);
    }

    public static void setUserId(String login) {
        editor.putString(FLD_USERID, login);
        editor.commit();
    }

    public static String getUserId() {
        return preference.getString(FLD_USERID, INVALID_STRING);
    }

    public static void setName(String login) {
        editor.putString(FLD_NAME, login);
        editor.commit();
    }

    public static String getName() {
        return preference.getString(FLD_NAME, INVALID_STRING);
    }

    public static void setEmail(String login) {
        editor.putString(FLD_EMAILID, login);
        editor.commit();
    }

    public static String getEmail() {
        return preference.getString(FLD_EMAILID, INVALID_STRING);
    }

    public static void setProfileImage(String login) {
        editor.putString(FLD_PROFILEIMAGE, login);
        editor.commit();
    }

    public static String getProfileImage() {
        return preference.getString(FLD_PROFILEIMAGE, INVALID_STRING);
    }

    public static void setCountryId(String login) {
        editor.putString(FLD_COUNTRYID, login);
        editor.commit();
    }

    public static String getCountryIdd() {
        return preference.getString(FLD_COUNTRYID, INVALID_STRING);
    }

    public static void setCityIdd(String login) {
        editor.putString(FLD_CITYID, login);
        editor.commit();
    }

    public static String getCityIdd() {
        return preference.getString(FLD_CITYID, INVALID_STRING);
    }

    public static void setStateIdd(String login) {
        editor.putString(FLD_STATEID, login);
        editor.commit();
    }

    public static String getStateIdd() {
        return preference.getString(FLD_STATEID, INVALID_STRING);
    }

    public static void settahsilIdd(String login) {
        editor.putString(FLD_TAHSILID, login);
        editor.commit();
    }

    public static String gettahsilIdd() {
        return preference.getString(FLD_TAHSILID, INVALID_STRING);
    }

    public static boolean isFullLogin() {
        return preference.getBoolean(String.valueOf(IS_FULL_LOGIN), INVALID_BOOLEAN);
    }

    public static void setFullLogin(boolean login) {
        editor.putBoolean(String.valueOf(IS_FULL_LOGIN), login);
        editor.commit();

    }
    public static boolean isLogin() {
        return preference.getBoolean(String.valueOf(IS_LOGIN), INVALID_BOOLEAN);
    }

    public static void setLogin(boolean login) {
        editor.putBoolean(String.valueOf(IS_LOGIN), login);
        editor.commit();

    }
    public static void setVideoFIle(String login) {
        editor.putString(FLD_MEDIAFILE, login);
        editor.commit();
    }

    public static String getVideoFile() {
        return preference.getString(FLD_MEDIAFILE, INVALID_STRING);
    }

    public static void setStateName(String login) {
        editor.putString(FLD_STATENAME, login);
        editor.commit();
    }

    public static String getStateName() {
        return preference.getString(FLD_STATENAME, INVALID_STRING);
    }

    public static void setCityName(String login) {
        editor.putString(FLD_CITYNAME, login);
        editor.commit();
    }

    public static String getCityName() {
        return preference.getString(FLD_CITYNAME, INVALID_STRING);
    }

    public static void setCountryName(String login) {
        editor.putString(FLD_COUNTRYNAME, login);
        editor.commit();
    }

    public static String getCountryName() {
        return preference.getString(FLD_COUNTRYNAME, INVALID_STRING);
    }

    public static void setFirstName(String login) {
        editor.putString(FLD_FIRSTNAME, login);
        editor.commit();
    }

    public static String getFirstName() {
        return preference.getString(FLD_FIRSTNAME, INVALID_STRING);
    }

    public static void setLastName(String login) {
        editor.putString(FLD_LASTNAME, login);
        editor.commit();
    }

    public static String getLastName() {
        return preference.getString(FLD_LASTNAME, INVALID_STRING);
    }
    public static void setDeviceToken(String login) {
        editor.putString(FLD_DEVICETOKEN, login);
        editor.commit();
    }

    public static String getDeviceToken() {
        return preference.getString(FLD_DEVICETOKEN, INVALID_STRING);
    }
    public static void setPosition(int login) {
        editor.putInt(FLD_POSITION, login);
        editor.commit();
    }

    public static int getPosition() {
        return preference.getInt(FLD_POSITION, INVALID_INT);
    }
    public static void setnewsId(String login) {
        editor.putString(FLD_NEWSID, login);
        editor.commit();
    }

    public static String getnewsId() {
        return preference.getString(FLD_NEWSID, INVALID_STRING);
    }
    public static void setisLocationMatch(boolean login) {
        editor.putBoolean(FLD_ISLOCATIONMATCH,login);
        editor.commit();
    }

    public static boolean getisLocationMatch() {
        return preference.getBoolean(FLD_ISLOCATIONMATCH, INVALID_BOOLEAN);
    }
    public static void setisUpload(boolean login) {
        editor.putBoolean(FLD_ISUPLOAD,login);
        editor.commit();
    }

    public static boolean getisUpload() {
        return preference.getBoolean(FLD_ISUPLOAD, INVALID_BOOLEAN);
    }

}

