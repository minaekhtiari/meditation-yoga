package apps.hillavas.com.yoga.classes.tools;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by A.Mohammadi on 7/25/2017.
 */

public class HideSoftKeyboards {
    public HideSoftKeyboards(Activity activity) {
        if(activity != null) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }catch (Exception e){}
        }
    }
}
