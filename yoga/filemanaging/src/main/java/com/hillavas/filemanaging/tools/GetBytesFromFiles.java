package com.hillavas.filemanaging.tools;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by A.Mohammadi on 8/13/2017.
 */

public class GetBytesFromFiles {
    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream.toByteArray();
    }
}
