package classes.tools;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by A.Mohammadi on 9/13/2017.
 */

public class JsonParserFromAsset {
    public static String loadJSONFromAsset(Context context , String jsonFileName) {
        String json = null;
        try {
            InputStream is =  context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            return null;
        }
        return json;
    }
}
