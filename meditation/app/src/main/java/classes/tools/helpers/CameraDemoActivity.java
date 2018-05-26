package classes.tools.helpers;

import android.app.Activity;
import android.os.Bundle;

import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 11/26/2017.
 */

public class CameraDemoActivity extends Activity {
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


    }


}