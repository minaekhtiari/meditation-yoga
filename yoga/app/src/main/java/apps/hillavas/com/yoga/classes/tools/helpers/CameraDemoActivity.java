package apps.hillavas.com.yoga.classes.tools.helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import apps.hillavas.com.yoga.R;

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