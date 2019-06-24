package apps.hillavas.com.yoga;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;

import net.jhoobin.amaroidsdk.Amaroid;
import net.jhoobin.jhub.CharkhoneSdkApp;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by A.Mohammadi on 7/3/2017.
 */

public class MyApplication extends Application{
    public static final String IS_IRANCELL="IS_IRANCELL";
    public static final String IS_HAMRAHAVAL="IS_HAMRAHAVAL";
    SharedPreferences sharedPreferencesHome;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // Appnex.init(this);

        Batch.setConfig(new Config("DEV5CC989AD5299BB4DF8C71B9D1F5"));
        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

           CharkhoneSdkApp.initSdk(getApplicationContext(), getSecrets(),true);

        Amaroid.getInstance().submitEventPageView(this,"MyApplication");



    }



    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }




}
