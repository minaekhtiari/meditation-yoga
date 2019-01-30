package apps.hillavas.com.yoga.classes.tools.helpers;

import apps.hillavas.com.yoga.data.models.CaloryAdd;
import apps.hillavas.com.yoga.data.models.ContentGiver;
import apps.hillavas.com.yoga.data.models.ContentsGiver;
import apps.hillavas.com.yoga.data.models.JsonCalloryHistoryDaily;
import apps.hillavas.com.yoga.data.models.JsonTotalCalloryAndPoints;
import apps.hillavas.com.yoga.data.models.OtpConfirmation;
import apps.hillavas.com.yoga.data.models.Profile;
import apps.hillavas.com.yoga.data.models.RequestCodeGiverModel;
import apps.hillavas.com.yoga.data.models.RequestMobileGiverModel;
import apps.hillavas.com.yoga.data.models.ResultJson;
import apps.hillavas.com.yoga.data.models.ResultJsonBoolean;
import apps.hillavas.com.yoga.data.models.ResultJsonCaloryHistory;
import apps.hillavas.com.yoga.data.models.ResultJsonCategory;
import apps.hillavas.com.yoga.data.models.ResultJsonCategoryWithParentChild;
import apps.hillavas.com.yoga.data.models.ResultJsonForBuy;
import apps.hillavas.com.yoga.data.models.ResultJsonForProfileInfo;
import apps.hillavas.com.yoga.data.models.ResultJsonForViewCount;
import apps.hillavas.com.yoga.data.models.ResultJsonInteger;
import apps.hillavas.com.yoga.data.models.ResultJsonIrancellMemberSignUp;
import apps.hillavas.com.yoga.data.models.ResultJsonLevels;
import apps.hillavas.com.yoga.data.models.ResultJsonMemberSignUp;
import apps.hillavas.com.yoga.data.models.SignUpMemberModel;
import apps.hillavas.com.yoga.data.models.UnsubIrancellResult;
import apps.hillavas.com.yoga.data.models.UnsubscribeMemberModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public interface IRetrofit {


    //on sexualHealth URL
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/OAuth/GetVerificationCode")
    Call<ResultJson> register(@Body SignUpMemberModel signUpMemberModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/OAuth/ValidateVerificationCode")
    Call<ResultJson> requestCodeGiver(@Body RequestCodeGiverModel requestCodeGiverModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/OAuth/LoginMember")
    Call<ResultJson> getGUID(@Body RequestCodeGiverModel requestCodeGiverModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/Otp/SubScribeRequest")
    Call<ResultJsonInteger> registerOtp(@Body RequestMobileGiverModel mobileNumber);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/Member/UpdateMemberProfile")
    Call<ResultJsonBoolean> updateProfileInfo(@Body Profile profile);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/Histories/AddCaloryHistory")
    Call<ResultJsonInteger> addToCaloryHistory(@Body CaloryAdd caloryAdd);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("mobileApi/Otp/ConfirmOtp")
    Call<ResultJson> confirmOtp(@Body OtpConfirmation otpConfirmation);

    @GET("mobileApi/level/getlevels")
    Call<ResultJsonLevels> getLevels(@Query("token") String token);

    @GET("mobileApi/Histories/GetCaloryHistories")
    Call<JsonCalloryHistoryDaily>getCaloryTimeHistoryDaily(@Query("token") String token);

    @GET("mobileApi/Histories/GetTotalCaloryAndPoint")
    Call<JsonTotalCalloryAndPoints>getTotalCalloryAndPoint(@Query("token") String token);

    @GET("mobileApi/Histories/GetMemberHistory")
    Call<ResultJsonCaloryHistory>getMemberHistory(@Query("token") String token,
                                                  @Query("HistoryType") int historyType,
                                                  @Query("DateType") int dateType,
                                                  @Query("PageNumber") int pagenumber,
                                                  @Query("RowCount") int rowcount);

    @GET("mobileApi/Category/GetCategory")
    Call<ResultJsonCategory> getCategories(@Query("token") String token,
                                           @Query("levelId") int levelId);
    @GET("mobileApi/Category/GetCategoryTree")
    Call<ResultJsonCategoryWithParentChild> getAllCategoryOfALevel(@Query("token") String token,
                                                                   @Query("levelId") int levelId);
    @GET("mobileApi/category/GetCategory")
    Call<ResultJsonCategory> getCategories(@Query("token") String token,
                                           @Query("levelId") int levelId,
                                           @Query("categoryid") int categoryid);

    @GET("mobileApi/Purchase/AddMemberCredit")
    Call<ResultJsonForBuy> buy(@Query("token") String token,
                               @Query("ContentId") int contentId,
                               @Query("Price") int price);

    @GET("mobileApi/Member/GetProfileInfo")
    Call<ResultJsonForProfileInfo> getProfileInfo(@Query("token") String token);


    @GET("mobileApi/Content/GetAllPredicate")
    Call<ContentsGiver> getContents(@Query("token") String token,
                                    @Query("categoryid") int categoryid,
                                    @Query("PageNumber") int pagenumber,
                                    @Query("RowCount") int rowcount);

    @GET("mobileApi/content/GetContentById")
    Call<ContentGiver> getContentById(@Query("token") String token,
                                      @Query("contentid") int contentId);


    @GET("mobileApi/content/GetContentViewCount")
    Call<ResultJsonForViewCount> getContentViewCount(@Query("token") String token,
                                                     @Query("contentId") int contentId);

    @GET("mobileApi/content/GetLikeContent")
    Call<ResultJson> getContentLikeCount(@Query("token") String token,
                                         @Query("recordId") int recordId);

    @GET("mobileApi/content/GetIsLikedMe")
    Call<ResultJson> getisLikedOrNot(    @Query("token") String token,
                                         @Query("recordId") int recordId);

    @POST("MobileApi/Member/SignUp")
    Call<ResultJsonMemberSignUp> memberSignUp(@Query("mobileNumber")  String appType );


    @POST("MobileApi/Member/IrancellSignUp")
    Call<ResultJsonIrancellMemberSignUp> memberIrancellSignUp(@Query("MobileNumber")  String appType );

    @POST("Member/IrancellSignOut")
    Call<UnsubIrancellResult> unregister(@Body UnsubscribeMemberModel unsubscribeMemberModel);
    }
