package apps.hillavas.com.yoga;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by A.Mohammadi on 7/3/2017.
 */

public class FontSetterOnApllication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        FirebaseApp.initializeApp(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }
}
