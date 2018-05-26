package com.hillavas.messaging.factories;

import com.hillavas.messaging.helpers.IRetrofitMessaging;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 8/9/2017.
 */

public class RetrofitMessagingFactory {

    private static final String BASE_URL="http://79.175.138.95:1010/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            Retrofit.Builder reBuilder = new Retrofit.Builder();
            reBuilder
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = reBuilder.build();

        }
        return retrofit;
    }

    public static IRetrofitMessaging getRetrofitClient(){
        IRetrofitMessaging retroClient = getRetrofit().create(IRetrofitMessaging.class);
        return retroClient;
    }
}
