package apps.hillavas.com.yoga.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.RetrofitIrancell.CharkhonehHttpFactory;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import apps.hillavas.com.yoga.data.models.UnsubIrancellResult;
import apps.hillavas.com.yoga.data.models.UnsubscribeMemberModel;
import io.appnex.android.utils.LocalStorage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnsubscribeActivity extends Activity implements View.OnClickListener {
    //
//

    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    String token;
    TextView img_unsubscribe;
    SharedPreferences sharedPreferencesHome;

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsubscribe);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        token = sharedPreferencesHome.getString("PurchaseToken", "");

        img_unsubscribe = (TextView) findViewById(R.id.img_unsubscribe);

        img_unsubscribe.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_unsubscribe:

                CharkhonehHttpFactory.getRetrofitClient().UnSubscribe("apps.hillavas.com.yoga",
                        "Meditation_SKU", token, "9d568991-f5cf-386f-a740-9e66fd992588")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                Log.d("--00", "" + response.body());
                                if (!response.isSuccessful()) {
                                    String errorMessage = "خطا در لغو اشتراک.";
                                    if (response.errorBody() != null) {
                                        JsonObject object = null;
                                        try {
                                            object = new JsonParser().parse(response.errorBody().string()).getAsJsonObject();
                                            errorMessage = object.get("error_description").getAsString();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    Toast.makeText(UnsubscribeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                                }else {
                                    UnSubscribeHilavas();
                                }

                            }


                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(UnsubscribeActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

                            }
                        });


                break;

        }
    }


    private void UnSubscribeHilavas() {


            sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String mobileNumber =String.valueOf(sharedPreferencesHome.getLong(MOBILE_NUMBER , 0));

            RetrofitFactory.getRetrofitClient().unregister(new UnsubscribeMemberModel(mobileNumber)).enqueue(new Callback<UnsubIrancellResult>() {
                @Override
                public void onResponse(Call<UnsubIrancellResult> call, Response<UnsubIrancellResult> response) {

                }

                @Override
                public void onFailure(Call<UnsubIrancellResult> call, Throwable t) {
                    //Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            sharedPreferencesHome.edit().clear().commit();

            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
//
//        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//            fm.popBackStack();
//        }

            Intent intent = new Intent(UnsubscribeActivity.this, ChooseOperator.class);

            startActivity(intent);
            finish();
    }
}

