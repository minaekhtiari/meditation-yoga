package apps.hillavas.com.yoga.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public class OtpApiFactory {


    private static final String URL = "http://79.175.138.118/";

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            Retrofit.Builder reBuilder = new Retrofit.Builder();
            reBuilder
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = reBuilder.build();
        }
        return retrofit;
    }

    public static OtpApi getOtpClient() {
        OtpApi retroClient = OtpApiFactory.getRetrofit().create(OtpApi.class);

        return retroClient;
    }
}
