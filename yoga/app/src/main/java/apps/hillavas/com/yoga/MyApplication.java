package apps.hillavas.com.yoga;

import android.app.Application;



import com.batch.android.Config;

import apps.hillavas.com.yoga.R;
import io.appnex.android.notification.Appnex;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by A.Mohammadi on 7/3/2017.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Appnex.init(this);

//        Batch.setConfig(new Config("5BC1EBED7564F04C977ABF69B16F79"));
//        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );



    }
}
