package apps.hillavas.com.yoga.classes.tools.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public class RetrofitFactory {


    private static final String URL = "http://yoga.hillavas.com/";
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

    public static IRetrofit getRetrofitClient(){
            IRetrofit retroClient = RetrofitFactory.getRetrofit().create(IRetrofit.class);
        return retroClient;
    }
}
