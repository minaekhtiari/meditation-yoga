package apps.hillavas.com.yoga.classes.tools;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by mohsen.mohammadi on 6/19/2017.
 */

public class MediaPlayerTest {

    String logStr = "";
    String url;

    private  MediaPlayer mediaPlayer;
    public MediaPlayerTest(Context context , String url) {

        this.url = url;
        mediaPlayer = new MediaPlayer();
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

    public MediaPlayer getPlayer(){
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
