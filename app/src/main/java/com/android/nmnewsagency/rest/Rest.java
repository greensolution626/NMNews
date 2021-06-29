package com.android.nmnewsagency.rest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Created by Vishnu Saini on 2/20/2018.
 * vishnusainideveloper27@gmail.com
 */

public class Rest {
    public static final String MULTIPARt_FORM_DATA = "multipart/form-data";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public Context ctx;
    public ProgressDialog dialog;
    Callback callback;
    RestService restService;
    private String pDialogMessage = "Loading...";

    public Rest(Context ctx, Callback callback) {
        this.callback = callback;
        this.ctx = ctx;
        init();
    }

    public static void printLog(String msg) {
        Log.e("hommzi", msg);
    }

    public static String getExtension(String filePath) {
        File f = new File(filePath);
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(".");

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private void init() {
        dialog = new ProgressDialog(ctx);
        dialog.setMessage(pDialogMessage);
        dialog.setCancelable(false);
        restService = RestAdapter.getAdapter();

    }

    public boolean isInterentAvaliable() {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
    }

    public void AlertForInternet() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setMessage("Internet Not avalible");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismissProgressdialog();
            }
        });
        alert.show();
    }

    public void ShowDialogue(String message) {

        dialog.setMessage(message);
        dialog.show();
    }

    public void dismissProgressdialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            Rest.printLog("" + e);
        }
    }
    public void loginUser(String email, String name, String deviceType,
                          String deviceId, String deviceToken,
                        String profileimage,String provider) {

        if (isInterentAvaliable()) {

            RestAdapter.getAdapter().loginUser(name,email, deviceId, deviceToken, deviceType, profileimage,provider).enqueue(callback);

        } else {
            AlertForInternet();
        }
    }
    public void loginUser1(String email, String password, String deviceType, String deviceId, String deviceToken, String userType) {

        if (isInterentAvaliable()) {

            RestAdapter.getAdapter().loginUser1(email, password, deviceId, deviceToken, deviceType, userType).enqueue(callback);

        } else {
            AlertForInternet();
        }
    }
    /*public void sendEvent(String hello, String s, String jaipur, String y, String s1,
                          String path1, String path2, String path3, String path4) {

        if (isInterentAvaliable()) {

            MultipartBody.Part body1 = null;
            MultipartBody.Part body2 = null;
            MultipartBody.Part body3 = null;
            MultipartBody.Part body4 = null;
            if (path1 != null && !path1.isEmpty() && !path1.contains("http")) {
                File file = new File(path1);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body1 = MultipartBody.Part.createFormData("image1", file.getName(), requestFile);
            }

            if (path2 != null && !path2.isEmpty() && !path2.contains("http")) {
                File file = new File(path2);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body2 = MultipartBody.Part.createFormData("image2", file.getName(), requestFile);
            }

            if (path3 != null && !path3.isEmpty() && !path3.contains("http")) {
                File file = new File(path3);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body3 = MultipartBody.Part.createFormData("image3", file.getName(), requestFile);
            }

            if (path4 != null && !path4.isEmpty() && !path4.contains("http")) {
                File file = new File(path4);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body4 = MultipartBody.Part.createFormData("image4", file.getName(), requestFile);
            }


// create a map of data to pass along
            RequestBody nameRB = createPartFromString(hello);
            RequestBody LASTNAME = createPartFromString(s);
            RequestBody phoneRB = createPartFromString(jaipur);
            RequestBody genderRB = createPartFromString(y);
            RequestBody deviceIdRB = createPartFromString(s1);


            HashMap<String, RequestBody> map = new HashMap<>();

            map.put("name", nameRB);
            map.put("desc", LASTNAME);
            map.put("location", phoneRB);
            map.put("ispublic", genderRB);
            map.put("timestamp", deviceIdRB);

            RestAdapter.getAdapter().sendEvent(map, body1, body2, body3, body4).enqueue(callback);


        } else {
            AlertForInternet();
        }

    }

    @NonNull
    public RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    public void getEventList(String s) {
        if (isInterentAvaliable()) {

            RestAdapter.getAdapter().getEventList(s).enqueue(callback);
        } else {
            AlertForInternet();
        }
    }*/


}
