package apps.hillavas.com.yoga.data.remote;


import apps.hillavas.com.yoga.data.models.OtpResultJson;
import apps.hillavas.com.yoga.data.models.ResultJson;
import apps.hillavas.com.yoga.data.models.ResultJsonMemberSignUp;
import apps.hillavas.com.yoga.data.models.SubscribeConfirmModel;
import apps.hillavas.com.yoga.data.models.SubscribeModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public interface OtpApi {


    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("OTP/SubScribeRequest")
    Call<OtpResultJson> subscribe(@Body SubscribeModel subscribeModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("OTP/SubscribeConfirm") // user ke ozv servic nist
    Call<OtpResultJson> subscribeConfirm(@Body SubscribeConfirmModel subscribeConfirmModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("Otp/SubscribeConfirmViaCode")//user ke ozv service bode
    Call<OtpResultJson> subscribeConfirmViaCode(@Body SubscribeConfirmModel subscribeConfirmModel);




}
