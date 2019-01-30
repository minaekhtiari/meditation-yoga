package apps.hillavas.com.yoga.RetrofitIrancell;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinaPC on 7/20/2017.
 */

public class RetroClient {
    //private static final String ROOT_URL = "http://79.175.138.91:1035/";
    private static final String ROOT_URL = "http://79.175.138.77:7094/";


    public RetroClient() {

    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static IrancellApi getApiService() {
        return getRetroClient().create(IrancellApi.class);
    }

}
