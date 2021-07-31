package com.android.nmnewsagency.rest;


import com.android.nmnewsagency.model.CountryModel;
import com.android.nmnewsagency.modelclass.AddNewsModel;
import com.android.nmnewsagency.modelclass.AddUserDocumentModel;
import com.android.nmnewsagency.modelclass.DeleteCommentsModel;
import com.android.nmnewsagency.modelclass.DeleteNewsByIdModel;
import com.android.nmnewsagency.modelclass.GetCommentsModel;
import com.android.nmnewsagency.modelclass.GetDocumentModel;
import com.android.nmnewsagency.modelclass.GetFollowersModel;
import com.android.nmnewsagency.modelclass.GetFollowingModel;
import com.android.nmnewsagency.modelclass.GetNewsByIdModel;
import com.android.nmnewsagency.modelclass.GetNewsListModel;
import com.android.nmnewsagency.modelclass.GetProfileDataModel;
import com.android.nmnewsagency.modelclass.GetTahsilModel;
import com.android.nmnewsagency.modelclass.GetUserHashTagModel;
import com.android.nmnewsagency.modelclass.GetUserOwnNewsModel;
import com.android.nmnewsagency.modelclass.GetUserSaveNewsModel;
import com.android.nmnewsagency.modelclass.LikeModelClass;
import com.android.nmnewsagency.modelclass.LoginModel;
import com.android.nmnewsagency.modelclass.NewVideoHashtagModelClass;
import com.android.nmnewsagency.modelclass.NewVideoMentionModelClass;
import com.android.nmnewsagency.modelclass.NewsCountModel;
import com.android.nmnewsagency.modelclass.ReportModelClass;
import com.android.nmnewsagency.modelclass.RequestModel.RequestAddComents;
import com.android.nmnewsagency.modelclass.RequestModel.RequestAddNews;
import com.android.nmnewsagency.modelclass.RequestModel.RequestFollow;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetComments;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetDocument;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetNewsById;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetNewsListingModel;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetProfile;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetSaveNews;
import com.android.nmnewsagency.modelclass.RequestModel.RequestGetTahsil;
import com.android.nmnewsagency.modelclass.RequestModel.RequestHashTag;
import com.android.nmnewsagency.modelclass.RequestModel.RequestLike;
import com.android.nmnewsagency.modelclass.RequestModel.RequestLoginModel;
import com.android.nmnewsagency.modelclass.RequestModel.RequestReportModel;
import com.android.nmnewsagency.modelclass.RequestModel.RequestSearchTopSaerch;
import com.android.nmnewsagency.modelclass.RequestModel.RequestSetAddress;
import com.android.nmnewsagency.modelclass.RequestModel.RequestuserOwnVideo;
import com.android.nmnewsagency.modelclass.SaveModelClass;
import com.android.nmnewsagency.modelclass.SearchTopSearchModel;
import com.android.nmnewsagency.modelclass.SetAddressModelClass;
import com.android.nmnewsagency.modelclass.UpdateProfileModel;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RestService {
    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_LOGIN)
    Call<LoginModel> loginUser(@Header("TokenId") String auth,
                               @Body RequestLoginModel requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_fOLLOW)
    Call<AddNewsModel> followUser(@Header("TokenId") String auth,
                               @Body RequestFollow requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETCOMMENTS)
    Call<GetCommentsModel> commentsUser(@Header("TokenId") String auth,
                                        @Body RequestGetComments requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_ADDCOMMENTS)
    Call<ReportModelClass> AddcommentsUser(@Header("TokenId") String auth,
                                        @Body RequestAddComents requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_REPORT)
    Call<ReportModelClass> reportUser(@Header("TokenId") String auth,
                                      @Body RequestReportModel requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_LIKE)
    Call<LikeModelClass> LikeUser(@Header("TokenId") String auth,
                                  @Body RequestLike requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_SAVE)
    Call<SaveModelClass> SaveUser(@Header("TokenId") String auth,
                                  @Body RequestLike requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_DELETESAVE)
    Call<SaveModelClass> DeleteSaveUser(@Header("TokenId") String auth,
                                  @Body RequestLike requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_DISLIKE)
    Call<LikeModelClass> disLikeUser(@Header("TokenId") String auth,
                                      @Body RequestLike requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_UNfOLLOW)
    Call<AddNewsModel> UNfollowUser(@Header("TokenId") String auth,
                               @Body RequestFollow requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_HASHtAG)
    Call<NewVideoHashtagModelClass> HashTag(@Header("TokenId") String auth,
                                                 @Body RequestHashTag requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_USERPROFILE)
    Call<GetProfileDataModel> getProfile(@Header("TokenId") String auth,
                                      @Body RequestGetProfile requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETUSEROWNNEWS)
    Call<GetUserOwnNewsModel> getuserOwnVideo(@Header("TokenId") String auth,
                                              @Body RequestuserOwnVideo requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETUSERHASHTAGNEWS)
    Call<GetUserHashTagModel> getuserHashTagVideo(@Header("TokenId") String auth,
                                              @Body RequestuserOwnVideo requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETUSERSAVENEWS)
    Call<GetUserSaveNewsModel> getusersaveNews(@Header("TokenId") String auth,
                                          @Body RequestGetSaveNews requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
  //  @FormUrlEncoded
    @POST(ApiUrls.URL_MENTION)
    Call<NewVideoMentionModelClass> Mention(@Header("TokenId") String auth,
                                                 @Body RequestHashTag requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETLIST)
    Call<GetNewsListModel> getList(@Header("TokenId") String auth,
                                     @Body RequestGetNewsListingModel requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETUSERDOCUMENT)
    Call<GetDocumentModel> getDocument(@Header("TokenId") String auth,
                                       @Body RequestGetDocument requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETFOLLOWERS)
    Call<GetFollowersModel> getFollowers(@Header("TokenId") String auth,
                                         @Body RequestGetDocument requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETFOLOWING)
    Call<GetFollowingModel> getFollowING(@Header("TokenId") String auth,
                                         @Body RequestGetDocument requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_SETUSERADDRESS)
    Call<SetAddressModelClass> setAddress(@Header("TokenId") String auth,
                                          @Body RequestSetAddress requestLoginModel
    );

    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.URL_GETTAHSIL)
    Call<GetTahsilModel> getTahsil(@Header("TokenId") String auth,
                                    @Body RequestGetTahsil requestLoginModel
    );
    @Headers({"Content-Type: application/json"})
    @GET(ApiUrls.GET_COUNTRY_TRUE)
    Call<CountryModel> getCountryList(@Header("TokenId") String auth);

    @Headers({"Content-Type: application/json"})
    @GET("Region/GetStateByCountryId/true/{id}")
    Call<CountryModel> getStateList(@Header("TokenId") String auth,
                                    @Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("News/IncreaseNewsViewCount/{id}")
    Call<NewsCountModel> setnewsCount(@Header("TokenId") String auth,
                                      @Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @POST(ApiUrls.GET_NEWSBYID)
    Call<GetNewsByIdModel> getNewsById(@Header("TokenId") String auth,
                                       @Body RequestGetNewsById requestLoginModel);

    @Headers({"Content-Type: application/json"})
    @POST(ApiUrls.GET_SEARCHTOPSESRCH)
    Call<SearchTopSearchModel> getTopNewsSearch(@Header("TokenId") String auth,
                                                @Body RequestSearchTopSaerch requestLoginModel);

    @Headers({"Content-Type: application/json"})
    @GET("News/DeleteNewsByNewsId/{id}")
    Call<DeleteNewsByIdModel> getDeleteNewsById(@Header("TokenId") String auth,
                                          @Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("Comment/DeleteComment/{id}")
    Call<DeleteCommentsModel> deleteComments(@Header("TokenId") String auth,
                                             @Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("Region/GetCityByStateId/{id}/true")
    Call<CountryModel> getCityList(@Header("TokenId") String auth,
                                    @Path("id") int id);

    @Headers({"Content-Type: application/json"})
    @GET("Region/GetTahsilByCityId/{id}/true")
    Call<CountryModel> getTahsilList(@Header("TokenId") String auth,
                                    @Path("id") int id);

   // @Headers({"Content-Type: application/json"})
    @Multipart
    @POST(ApiUrls.UPLOADNEWS)
    Call<UploadNewsModel> UploadNews(@Header("TokenId") String auth,
                                     @Part MultipartBody.Part pic1);

    @Multipart
    @POST(ApiUrls.UPDATEPROFILE)
    Call<UpdateProfileModel> updateProfile(@Header("TokenId") String auth,
                                           @Part("FirstName") RequestBody fname,
                                           @Part("LastName") RequestBody lname,
                                           @Part("AboutMe") RequestBody aboutme,
                                           @Part("UserId") RequestBody userid,
                                           @Part("Avatar") RequestBody avtar,
                                           @Part MultipartBody.Part imageresume);

    @Multipart
    @POST(ApiUrls.ADDUSERDOCUMENT)
    Call<AddUserDocumentModel> addUserDoc(@Header("TokenId") String auth,
                                             @Part("DocumentType") RequestBody doctype,
                                             @Part("UserId") RequestBody userid,
                                             @Part MultipartBody.Part imageresume);




    @Headers({"Content-Type: application/json"})
    //  @FormUrlEncoded
    @POST(ApiUrls.SETADDNEWS)
    Call<AddNewsModel> addNews(@Header("TokenId") String auth,
                                 @Body RequestAddNews requestLoginModel
    );



  /*  @Multipart
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

