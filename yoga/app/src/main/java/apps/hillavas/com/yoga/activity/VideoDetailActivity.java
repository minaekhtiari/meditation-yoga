package apps.hillavas.com.yoga.activity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import apps.hillavas.com.yoga.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoDetailActivity extends AppCompatActivity implements OnPreparedListener {


    TextView txtTitle,txtDesc,textScore,textCallory;
    ImageView videoImage,imgPlay;
    String urlString,title,desc,img,score,callories;
  VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Bundle extras = getIntent().getExtras();

       urlString = extras.getString("videoUrl");
        title = extras.getString("title");
       desc= extras.getString("desc");
         img=extras.getString("img");
         score=extras.getString("score");
         callories=extras.getString("callories");
        videoView = findViewById(R.id.mpw_video_player);
        videoView.setOnPreparedListener(VideoDetailActivity.this);
        txtTitle=findViewById(R.id.txt_title);
        txtDesc=findViewById(R.id.txt_desc);
        videoImage=findViewById(R.id.video_img);
        imgPlay=findViewById(R.id.img_play);
        textScore=findViewById(R.id.text_score);
        textCallory=findViewById(R.id.text_callory);
        txtTitle.setText(title);
        txtDesc.setText(desc);
        textScore.setText(score);
        textCallory.setText(callories);

        Glide.with(VideoDetailActivity.this)
                .load(img)
                .asBitmap()
                .override(424, 240)
                .centerCrop()
                .into(videoImage);
        //MxVideoPlayerWidget.startFullscreen(this, MxVideoPlayerWidget.class, urlString, "");
           imgPlay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {


                   imgPlay.setVisibility(View.INVISIBLE);
                    videoImage.setVisibility(View.INVISIBLE);
                   videoView.setVisibility(View.VISIBLE);

                   videoView.setVideoURI(Uri.parse(urlString));
               }
           });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onPause() {
        super.onPause();
      //  MxVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
//        if (MxVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();}

    @Override
    public void onPrepared() {
        videoView.start();
    }
}
