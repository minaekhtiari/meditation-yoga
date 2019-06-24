package apps.hillavas.com.yoga.classes.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import apps.hillavas.com.yoga.classes.CTouchyWebView;

public class Utill {

    public static void setWebViewJastify(CTouchyWebView contentFullDescription, String myData) {

        contentFullDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        contentFullDescription.setLongClickable(false);
        contentFullDescription.setHapticFeedbackEnabled(false);
        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSans.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish + myData + pas;

        contentFullDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);

    }
    public static String getAndroidVersionName() {
        return Build.VERSION.RELEASE;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModelName() {
        String model = Build.MODEL;
        if (model.length() > 20) {
            model = model.substring(0, 20);
        }
        return model;
    }

    public Integer getApplicationVersionCode(Context _context) {
        Integer appVersionCode = 0;
        try {
            PackageInfo pInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            appVersionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionCode;
    }

    public static String getApplicationVersionName(Context _context) {
        String appVersionName = "";
        try {
            PackageInfo pInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            appVersionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getOsVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }
}
