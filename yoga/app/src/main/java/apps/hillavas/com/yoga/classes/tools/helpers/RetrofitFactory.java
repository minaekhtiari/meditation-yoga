package apps.hillavas.com.yoga.classes.tools.helpers;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public class RetrofitFactory {


    private static final String URL = "http://yoga.hillavas.com/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        if(retrofit == null){
            Retrofit.Builder reBuilder = new Retrofit.Builder();
            reBuilder
                    .baseUrl(URL).client(okHttpClient)
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
