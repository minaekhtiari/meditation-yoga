package classes.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arashjahani on 12/23/2017 AD.
 */

public class LocalStorage {

    private SharedPreferences sharedPreferences;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("qwes11hfs", Context.MODE_PRIVATE);
    }

    public void saveLocalData(Object... items) {

        try {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (int i = 0; i < items.length; i += 2) {

                if (items[i + 1] == null) {
                    continue;
                }
                String key = items[i].toString();
                Object value = items[i + 1];

                if (value instanceof Boolean) {
                    editor.putBoolean(key, Boolean.valueOf(value.toString()));
                } else if (value instanceof Integer) {
                    editor.putInt(key, ((Integer) value).intValue());

                } else {
                    editor.putString(key, value.toString());
                }


            }
            editor.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
