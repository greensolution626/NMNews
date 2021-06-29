package com.android.nmnewsagency.rest;



import android.util.Log;

import com.android.nmnewsagency.model.LoginModel;
import com.android.nmnewsagency.model.LoginRegisterModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Vishnu Saini on 2/20/2018.
 * vishnusainideveloper27@gmail.com
 */

public interface RestService {
    @FormUrlEncoded
    @POST(ApiUrls.URL_LOGIN)
    Call<LoginModel> loginUser(@Field(ParaName.KEY_NAME) String userLogin,
                               @Field(ParaName.KEY_EMAIL) String password,
                               @Field(ParaName.KEY_DEVICE_ID) String deviceId,
                               @Field(ParaName.KEY_DEVICE_TOKEN) String deviceToken,
                               @Field(ParaName.KEY_DEVICE_TYPE) String deviceType,
                               @Field(ParaName.KEY_PROFILEIAMGE) String userType,
                               @Field(ParaName.KEY_LOGINPROVIDER) String provider
    );
    @FormUrlEncoded
    @POST(ApiUrls.URL_LOGIN)
    Call<LoginRegisterModel> loginUser1(@Field(ParaName.KEY_PHONE_NO) String userLogin,
                                        @Field(ParaName.KEY_PASSWORD) String password,
                                        @Field(ParaName.KEY_DEVICE_ID) String deviceId,
                                        @Field(ParaName.KEY_DEVICE_TOKEN) String deviceToken,
                                        @Field(ParaName.KEY_DEVICE_TYPE) String deviceType,
                                        @Field(ParaName.KEY_USER_TYPE) String userType
    );
    /*@Multipart
    @POST(ApiUrls.URL_REGISTER)
    Call<UserRegisterModel> registerUser(@PartMap() Map<String, RequestBody> partMap,
                                         @Part MultipartBody.Part pic1);


    @FormUrlEncoded
    @POST(ApiUrls.URL_LOGIN)
    Call<LoginRegisterModel> loginUser(@Field(ParaName.KEY_PHONE_NO) String userLogin,
                                       @Field(ParaName.KEY_PASSWORD) String password,
                                       @Field(ParaName.KEY_DEVICE_ID) String deviceId,
                                       @Field(ParaName.KEY_DEVICE_TOKEN) String deviceToken,
                                       @Field(ParaName.KEY_DEVICE_TYPE) String deviceType,
                                       @Field(ParaName.KEY_USER_TYPE) String userType
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_UPDATE_PASS)
    Call<LogoutModel> updatePass(@Field(ParaName.KEY_PHONE_NO) String userLogin,
                                 @Field(ParaName.KEY_PASSWORD) String password,
                                 @Field(ParaName.KEY_RE_PASS) String deviceId

    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_FORGOT_PASSWORD)
    Call<MobileVerifyModel> forgotPass(@Field(ParaName.KEY_PHONE_NO) String userLogin,
                                       @Field(ParaName.KEY_USER_TYPE) String userType
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_VEHICLE_TYPE)
    Call<RegistrationStepTwoModel> getVehicleTrype(@Field(ParaName.KEY_USERID) String userId,
                                                   @Field(ParaName.KEY_CITY) String city,
                                                   @Field(ParaName.KEY_STATE) String State,
                                                   @Field(ParaName.KEY_COUNTRY) String country);

    @POST(ApiUrls.URL_DOCUMENT_TYPE)
    Call<GetDocumentModel> getDocumentType();

    @FormUrlEncoded
    @POST(ApiUrls.URL_USER_DOCUMENT_TYPE)
    Call<GetDocumentUserModel> getDocumentUser(@Field(ParaName.KEY_USERID) String userId);

    *//*@Multipart
    @POST(ApiUrls.URL_REGISTER)
    Call<DriverRegisterModel> creatDriverAccount(@PartMap() HashMap<String, RequestBody> multipartRequest,
                                           @Part ArrayList<MultipartBody.Part> params);*//*

    @Multipart
    @POST(ApiUrls.URL_VEHCAL_REGISTER)
    Call<VichcalRegisterModel> creatDriverAccount(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_UPLOAD_DOCUMENT)
    Call<UploadDocumentModel> uploadDocument(@PartMap() HashMap<String, RequestBody> multipartRequest,
                                             @Part MultipartBody.Part imageresume);

    @FormUrlEncoded
    @POST(ApiUrls.URL_UPDATE_LOCATION)
    Call<LocationUpdateModel> updateCurrentLocation(@Field(ParaName.KEY_USERID) String userLogin,
                                                    @Field(ParaName.KEY_LATTITUDE) String lat,
                                                    @Field(ParaName.KEY_LONGITUDE) String log,
                                                    @Field(ParaName.KEY_DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST(ApiUrls.URL_IS_VISIBLE)
    Call<IsProfileVisibleModel> setVisibality(@Field(ParaName.KEY_USERID) String userLogin,
                                              @Field(ParaName.KEY_IS_VISIBLE) String password,
                                              @Field(ParaName.KEY_DEVICE_ID) String deviceId);

    @Multipart
    @POST(ApiUrls.URL_UPDATE_PROFILE)
    Call<UpdateProfileModel> updateProfile(@PartMap() HashMap<String, RequestBody> multipartRequest,
                                           @Part MultipartBody.Part imageresume);

    @Multipart
    @POST(ApiUrls.URL_CHANGE_PASSWORD)
    Call<ChangePasswordNewModel> updatePassword(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_BOOKING_ACCEPT)
    Call<BookingAcceptModel> acceptBooking(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_DRIVER_ARRIVED)
    Call<DriverArrivedModel> arrivedBooking(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_START_RIDE)
    Call<StartRideModel> startRide(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_END_RIDE)
    Call<EndRideModel> endRide(@PartMap() HashMap<String, RequestBody> multipartRequest,
                               @Part MultipartBody.Part imageresume);

    @Multipart
    @POST(ApiUrls.URL_RATE_USER)
    Call<RatingModel> giveRating(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_LOGOUT)
    Call<LogoutModel> logoutUser(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @FormUrlEncoded
    @POST(ApiUrls.URL_DRIVER_RIDE)
    Call<DriverPreviousRideModel> driverPreviourRide(@Field(ParaName.KEY_USERID) String userLogin,
                                                     @Field(ParaName.KEY_USER_TYPE) String password,
                                                     @Field(ParaName.KEY_DEVICE_ID) String deviceId);

    @FormUrlEncoded
    @POST(ApiUrls.URL_UPDATERUNNINGRIDEDRIVER)
    Call<ChangeDestinationModel> changeRideDestination(@Field(ParaName.KEY_PICK_UP_BOOKINGROUTEID) String userLogin,
                                                       @Field(ParaName.KEY_PICK_UP_LAT) String password,
                                                       @Field(ParaName.KEY_PICKUP_LNG) String deviceId,
                                                       @Field(ParaName.KEY_PICKUP_ADDRESS) String add);

    @FormUrlEncoded
    @POST(ApiUrls.URL_GET_BOOKING_SUPORT)
    Call<RideDetailModel> driverRideSubject(@Field(ParaName.KEY_USERID) String userLogin
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_DELETEUSERDOCUMENT)
    Call<DeleteDocumentModel> deleteDOcument(@Field(ParaName.KEY_USERID) String userLogin,
                                             @Field(ParaName.KEY_USER_DOCUMENT_ID) String docId
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_GET_BOOKING_SUPORT1)
    Call<CustomerSuportModel> driverRideSubjectCus(@Field(ParaName.KEY_BOOKING_ID) String bookinId,
                                                   @Field(ParaName.KEY_USERID) String userId,
                                                   @Field(ParaName.KEY_COMMENT) String comment,
                                                   @Field(ParaName.KEY_SUBJECT_ID) String subjectId
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_DRIVER_REVIEW)
    Call<ReviewModel> driverReview(@Field(ParaName.KEY_DRIVER_ID) String userLogin
    );

    @FormUrlEncoded
    @POST(ApiUrls.URL_RATE_USER)
    Call<LogoutModel> driverReviewSubmit(@Field(ParaName.KEY_USERID) String userid,
                                         @Field(ParaName.KEY_USER_TYPE) String usertype,
                                         @Field(ParaName.KEY_DEVICE_ID) String deviceId,
                                         @Field(ParaName.KEY_PARENT_ID) String parentId,
                                         @Field(ParaName.KEY_COMPLEMENT_ID) String conplementId,
                                         @Field(ParaName.KEY_BOOKING_ID) String bookingId,
                                         @Field(ParaName.KEY_RATING) String rating,
                                         @Field(ParaName.KEY_COMMENT) String comment
    );

    @Multipart
    @POST(ApiUrls.URL_FINISH_RIDE)
    Call<FinishRideModel> finishRide(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_FORGOT_PASSWORD)
    Call<FinishRideModel> forgotPassword(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GET_DRIVER_CURRENT_STATUS)
    Call<DriverCurrentStatusModel> getDriverStatus(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GET_RATING)
    Call<UserRatingGiveModel> getUserRating(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_ISMOBILE_NUMBEREXIST)
    Call<MobileVerifyModel> validateMobileNumber(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_STATELIST)
    Call<StateListModel> getStateList(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_COUNTRYLIST)
    Call<CountryModel> getCountrtList(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_CITYLIST)
    Call<CityListModel> getCityList(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GET_BOOKING_SUPPORT)
    Call<SupportModel> getSupport(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_BOOKINGINFO)
    Call<DashBoardModel> getDashBoard(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GETCOMPLIMENTS)
    Call<DashBoardComplimentModel> getDashBoardCompliment(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GETALLBOOKINGMONTHELY)
    Call<DashBoardRideModel> getDashBoardRide(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GETALLEARNINGMONTHELY)
    Call<DashBoardEarningModel> getDashBoardEarning(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GETALLBOOKINGINFO)
    Call<DashBoardRideDetailModel> getDashBoardRideDeatil(@PartMap() HashMap<String, RequestBody> multipartRequest);

    @Multipart
    @POST(ApiUrls.URL_GETMONTHWISEEARNING)
    Call<DashBoardEarningDetailModel> getDashBoardEarningDeatil(@PartMap() HashMap<String, RequestBody> multipartRequest);

*/
}

