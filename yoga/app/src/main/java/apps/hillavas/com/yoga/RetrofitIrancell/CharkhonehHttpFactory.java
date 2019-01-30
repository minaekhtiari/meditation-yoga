package apps.hillavas.com.yoga.RetrofitIrancell;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public class CharkhonehHttpFactory {


    private static final String URL = "https://seller.jhoobin.com/ws/androidpublisher/v2/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            Retrofit.Builder reBuilder = new Retrofit.Builder();
            reBuilder
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = reBuilder.build();

        }
        return retrofit;
    }

    public static CharkhonehHttpClient getRetrofitClient(){
        CharkhonehHttpClient retroClient = CharkhonehHttpFactory.getRetrofit().create(CharkhonehHttpClient.class);
        return retroClient;
    }
}
