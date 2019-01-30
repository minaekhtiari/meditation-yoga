package apps.hillavas.com.yoga.RetrofitIrancell;



import apps.hillavas.com.yoga.data.models.IsSubMtn;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CharkhonehHttpClient {
    @GET("applications/{packageName}/purchases/subscriptions/{subscriptionId}/tokens/{token}:cancel")
    Call<ResponseBody> UnSubscribe(@Path("packageName") String packageName, @Path("subscriptionId") String subscriptionId, @Path("token") String token, @Query("access_token") String accessToken);

@GET("applications/{packageName}/purchases/subscriptions/{subscriptionId}/tokens/{token}")
Call<IsSubMtn> IsSubscribe(@Path("packageName") String packageName, @Path("subscriptionId") String subscriptionId, @Path("token") String token, @Query("access_token") String access_token);

}

