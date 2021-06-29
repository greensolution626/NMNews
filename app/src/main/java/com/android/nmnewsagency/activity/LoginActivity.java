
package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.model.LoginModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_login);
        iniIt();
        rest = new Rest(this,this);
    }

    private void iniIt() {
        but_reporter = (RelativeLayout) findViewById(R.id.but_reporter);
        but_user = (RelativeLayout) findViewById(R.id.but_user);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        keyHash();
        but_reporter.setOnClickListener(this);
        but_user.setOnClickListener(this);
    }

    private void setDataOnLocation() {
        Prefrence.setlogin("yes");
        Intent mainIntent = new Intent(LoginActivity.this, LocationReqActivity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
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
            // handleSignInResult(task);
            setDataOnLocation();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //  updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //   Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    public void keyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.android.nmnewsagency", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    private void custom_facebook() {
        //  LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile", "user_posts", "user_videos", "user_birthday", "public_profile"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setDataOnLocation();
                       /* GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        setDataOnLocation();
                                        // Application code
                                        try {*/


                        //String id = object.getString("id");
                        // String first_name = object.getString("first_name");
                        // String last_name = object.getString("last_name");
                        // String birthday = object.getString("birthday");
                        // String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
                        // String email = "N/A";
                        // if (object.has("email")) {
                        // email = object.getString("email");
                        // }

                                            /*User user = new User();
                                            user.setUsername(first_name + " " + last_name);
                                            user.setPassword(id);
                                            user.setEmail(email);
                                            user.setPhone_NO("9999999999");
                                            user.setDOB(new Date(birthday));
                                            user.setFollowers(0);
                                            user.setFollowing(0);
                                            user.setDescription(String.format("Its empty here... %s has not set his/her description.", user.getUsername()));

                                            Uri profilePictureUri = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 512 , 512 );
                                            String urlPfp = profilePictureUri.toString();
                                            File f = Image.convertUrlToFile(getContext(), urlPfp);
                                            user.setAvatar(f);

                                            UserDatabase db = new UserDatabase();
                                            try{
                                                if(db.isUsernameUnique(user.getUsername()) == Constants.ERROR){
                                                    facebook_sign_in_status = Constants.ERROR;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            if(facebook_sign_in_status == Constants.SUCCESS) db.insertObj(user);

                                            Handler handler = new Handler(Looper.getMainLooper());
                                            assert act != null;

                                            if(facebook_sign_in_status == Constants.SUCCESS) {
                                                ((MainActivity) act).main_dialog.startSuccessDialog("Successfully Registered User!");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((MainActivity)act).main_dialog.dismissDialog();
                                                        navToLogin();
                                                    }
                                                }, 2500);
                                            } else{
                                                ((MainActivity) act).main_dialog.startErrorDialog("Facebook duplicate usernames!");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((MainActivity)act).main_dialog.dismissDialog();
                                                    }
                                                }, 5000);
                                            }


                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();*/
                                        /*} catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                });*/
                        // Bundle parameters = new Bundle();
                        // parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                        // request.setParameters(parameters);
                        //  request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("CANCEL");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("ERROR");
                        // App code
                    }
                });

    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof LoginModel) {

                LoginModel loginModel = (LoginModel) obj;
                if (loginModel.isStatus()) {
                    /*Utils.saveDriverData(loginRegisterModel);
                    if (loginRegisterModel.getData().getIdentity_verification()==0)
                    {
                        Intent home = new Intent(LoginActivity.this, RegistrationBasicActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }else if (loginRegisterModel.getData().getVehicle_verification()==0)
                    {
                        Intent home = new Intent(LoginActivity.this, VehicleRegistrationActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }else if (loginRegisterModel.getData().getDocument_verification()==0)
                    {
                        Intent home = new Intent(LoginActivity.this, DocumentUploadActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }else
                    {
                        Config.setIsRegister(true);
                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }

                }
                else {

                    if (loginRegisterModel!=null && loginRegisterModel.getData()!=null)
                    {
                        Utils.saveDriverData(loginRegisterModel);
                        if (loginRegisterModel.getData().getIdentity_verification()==0)
                        {
                            Intent home = new Intent(LoginActivity.this, RegistrationBasicActivity.class);
                            //home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(home);
                        }else if (loginRegisterModel.getData().getVehicle_verification()==0)
                        {
                            Intent home = new Intent(LoginActivity.this, VehicleRegistrationActivity.class);
                            //home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(home);
                        }else if (loginRegisterModel.getData().getDocument_verification()==0)
                        {
                            Intent home = new Intent(LoginActivity.this, DocumentUploadActivity.class);
                            //home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(home);
                        }
                    }

                   */ Toast.makeText(LoginActivity.this, loginModel.getMessage(), Toast.LENGTH_LONG).show();
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
                String email = "sundarpichai@gmail.com";
                String name = "Sundar Pichai";
                String deviceType = "IOS";
                String deviceId = "7894563";
                String deviceToken = "1234562";
                String image = "https://pbs.twimg.com/profile_images/864282616597405701/M-FEJMZ0.jpg";
                String provider = "FACEBOOK";
                rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
                rest.loginUser(name,email, deviceType, deviceId, deviceToken,image,provider);
               // signIn();
                break;
            case R.id.but_user:
                custom_facebook();
                break;
        }
    }
    /*private void createLogin() {
        String email = "sundarpichai@gmail.com";
        String password = "7878787";
        String deviceType = "oigh";
        String deviceId = "7894563";
        String deviceToken = "1234562";
        String userType = "driver";


            rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
            rest.loginUser1(email, password, deviceType, deviceId, deviceToken,userType);


    }*/
}