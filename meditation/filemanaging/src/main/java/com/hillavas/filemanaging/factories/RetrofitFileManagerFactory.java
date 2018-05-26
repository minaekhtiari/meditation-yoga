package com.hillavas.filemanaging.factories;

import com.hillavas.filemanaging.helpers.IRetrofitFileManaging;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by A.Mohammadi on 8/9/2017.
 */

public class RetrofitFileManagerFactory {

    private static final String BASE_URL="http://79.175.138.77:7091/";
//                                            "http://79.175.138.77:7091/"  original;
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

    public static IRetrofitFileManaging getRetrofitClient(){
        IRetrofitFileManaging retroClient = getRetrofit().create(IRetrofitFileManaging.class);
        return retroClient;
    }
}
