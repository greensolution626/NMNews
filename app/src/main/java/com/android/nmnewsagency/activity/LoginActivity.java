
package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.extras.Constants;
import com.android.nmnewsagency.modelclass.LoginModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.LogLevel;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.ServiceZone;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<Object>, View.OnClickListener {
    RelativeLayout but_user, but_reporter;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 100;
    CallbackManager callbackManager;
    Rest rest;
    String provider, prouserid, name, email, image = "";
    LoginButton login_button;
    QBUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_login);
        // Firebase.setAndroidContext(this);

        //  QBSettings.getInstance().setEndpoints(Constants.API_DOMAIN,Constants.CHAT_DOMAIN, ServiceZone.PRODUCTION);
        // QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);

        // QBSettings.getInstance().setLogLevel(LogLevel.DEBUG);
        //  QBChatService.setDebugEnabled(true);

        //   QBChatService.getInstance().setReconnectionAllowed(true);
        user = new QBUser();
        String deviceId = getDeviceId(LoginActivity.this);
        Log.e("DeviceId=======", String.valueOf(deviceId));
        Prefrence.setDeviceId(deviceId);
        iniIt();
        rest = new Rest(this, this);
    }

    private void iniIt() {
        login_button = (LoginButton) findViewById(R.id.login_button);
        but_reporter = (RelativeLayout) findViewById(R.id.but_reporter);
        but_user = (RelativeLayout) findViewById(R.id.but_user);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        but_reporter.setOnClickListener(this);
        but_user.setOnClickListener(this);


        login_button.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();
        /*Register facebook call back with button click that is use to generate Access token */
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");
                //if (loggedIn)
                //  {
                /*Get all facebook profile public details for login into app */
                getUserProfile(loginResult.getAccessToken());
                //  }
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("API123", "" + " ??");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("API123", "" + " ??");
            }
        });
    }

    private void setDataOnLocation(boolean isAdress) {
        // Prefrence.setlogin("yes");
        if (!isAdress) {
            Intent mainIntent = new Intent(LoginActivity.this, LocationReqActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        } else {
            Intent Intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(Intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // setDataOnLocation();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            name = account.getDisplayName();
            Prefrence.setFirstName(name);
            email = account.getEmail();
            provider = "GOOGLE";
            prouserid = account.getId();
            Log.e("image", String.valueOf(account.getPhotoUrl()));
            if (String.valueOf(account.getPhotoUrl()) != null) {
                image = String.valueOf(account.getPhotoUrl());
            }
            createLogin();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //   Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }


    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof LoginModel) {

                LoginModel loginModel = (LoginModel) obj;
                if (loginModel.isStatus()) {
                    setUpQuickboxSignUp(loginModel.getData().getUserInfo().getUserId(), loginModel.getData().getUserInfo().getName(),
                            loginModel.getData().getUserInfo().getProfileImage());
                    Prefrence.setLogin(true);
                    Prefrence.setUserId(loginModel.getData().getUserInfo().getUserId());
                    Prefrence.setName(loginModel.getData().getUserInfo().getName());
                    Prefrence.setEmail(loginModel.getData().getUserInfo().getEmail());
                    Prefrence.setProfileImage(loginModel.getData().getUserInfo().getProfileImage());
                    if (loginModel.getData().isIsAddressFound()) {
                        Prefrence.setCountryName(loginModel.getData().getUserAddress().getGCountry());
                        Prefrence.setCityName(loginModel.getData().getUserAddress().getGCity());
                        Prefrence.settahsil(loginModel.getData().getUserAddress().getGThasil());
                        Prefrence.setStateName(loginModel.getData().getUserAddress().getGState());
                    }

                    setDataOnLocation(loginModel.getData().isIsAddressFound());

                    //Toast.makeText(LoginActivity.this, loginModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_reporter:
                signIn();
                break;
            case R.id.but_user:
                login_button.performClick();
                break;
        }
    }

    private void createLogin() {
        String deviceType = "ANDROID";
        String deviceId = Prefrence.getDeviceId();
        String deviceToken = Prefrence.getDeviceToken();
        // String deviceToken = Application.deviceToken;

        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.loginUser(name, email, deviceType, deviceId, deviceToken, image, prouserid, provider);
        // rest.forgotPassword();
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            Prefrence.setFirstName(first_name);
                            Prefrence.setLastName(last_name);
                            String email1 = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            name = first_name + " " + last_name;
                            email = email1;
                            prouserid = id;
                            image = image_url;
                            provider = "FACEBOOK";
                            createLogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    /* public void FirebaseLoginForChat(String name){
         String user = name;
         String pass = "news";

         if (user.equals("")) {
             Toast.makeText(LoginActivity.this, "user blank", Toast.LENGTH_SHORT).show();
         } else if (pass.equals("")) {
             Toast.makeText(LoginActivity.this, "pass blank", Toast.LENGTH_SHORT).show();
         }
                *//* else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    username.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("at least 5 characters long");
                }*//*
        else {
           // final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
          //  pd.setMessage("Loading...");
          //  pd.show();

            String url = "https://nmnewsagency-24e4e-default-rtdb.firebaseio.com/users.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Firebase reference = new Firebase("https://nmnewsagency-24e4e-default-rtdb.firebaseio.com/users");

                    if (s.equals("null")) {
                        reference.child(user).child("password").setValue(pass);
                       // Toast.makeText(LoginActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(user)) {
                                reference.child(user).child("password").setValue(pass);
                               // Toast.makeText(LoginActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                            } else {
                               // Toast.makeText(LoginActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                   // pd.dismiss();
                }

            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("" + error);
                   // pd.dismiss();
                }


            });

            RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
        }
    }*/
    public void setUpQuickboxSignUp(String userid, String name, String imagde) {
        user.setLogin(userid);
        user.setPassword("nmnewsnmnews");
        user.setFullName(name);
        user.setCustomData(imagde);

        QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("QBNSERVER=======", qbUser.toString());
                Prefrence.setQbidId(qbUser.getId());
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("QBNSERVERERROR=======", e.toString());
            }
        });
    }

}