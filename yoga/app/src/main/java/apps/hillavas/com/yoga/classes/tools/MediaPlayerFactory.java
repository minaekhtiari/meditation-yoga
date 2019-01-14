package apps.hillavas.com.yoga.classes.tools;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import java.io.IOException;

/**
 * Created by mohsen.mohammadi on 6/19/2017.
 */

public class MediaPlayerFactory {

    String logStr = "";
    String url;

    private android.media.MediaPlayer mediaPlayer;
    public MediaPlayerFactory(Context context , String url) {

        this.url = url;
        AssetFileDescriptor afd = null;
        try {
            afd = context.getAssets().openFd(url);
        } catch (IOException e) {
            e.printStackTrace();
            logStr += "\n" + e.getMessage().toString();
        }

        mediaPlayer = new android.media.MediaPlayer();

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            logStr += "\n" + e.getMessage().toString();
        }
        mediaPlayer.start();
        Log.d("LOGSTR" , logStr);
    }

    public android.media.MediaPlayer getPlayer(){
        return mediaPlayer;
    }
    public  void start(){
        mediaPlayer.start();
    }

    public  void pause(){
        mediaPlayer.pause();
    }
    public  void stop(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
