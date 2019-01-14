package apps.hillavas.com.yoga.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.hillavas.messaging.classes.Answer;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.data.models.FileGiver;
import apps.hillavas.com.yoga.data.models.FileResult;
import apps.hillavas.com.yoga.classes.tools.MediaPlayerTest;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactoryForFileManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_messagesQuestion_Answers_Adapter extends RecyclerView.Adapter<RecyclerView_messagesQuestion_Answers_Adapter.MVHolder> {

    String token = null;
    SharedPreferences sharedPreferencesHome;
    Context context;
    List<Answer> answers = new ArrayList<>();
    LayoutInflater inflater;
    MediaPlayerTest playerTest;
    MediaPlayer mediaPlayer;
    boolean playing = false;
    CountDownTimer coTimerPlaying;
    ImageView ivPlay;


    public RecyclerView_messagesQuestion_Answers_Adapter(Context context, List<Answer> answers) {
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
        this.answers = answers;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_fragment_question_answer , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        Answer  answer = answers.get(position);
        holder.setData(answer,position);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView tvDoctorName;
        TextView tvAnswerText;
        TextView tvAnswerDate;
        ImageView ivImage;


        public MVHolder(View itemView) {
            super(itemView);

            tvDoctorName = (TextView) itemView.findViewById(R.id.card_fragment_messaging_textMessage_answer_text_personName);
            tvAnswerText = (TextView) itemView.findViewById(R.id.card_fragment_messaging_textMessage_answer_text_answer);
            tvAnswerDate = (TextView) itemView.findViewById(R.id.card_fragment_messaging_textMessage_answer_text_answerDate);
            ivImage = (ImageView) itemView.findViewById(R.id.card_fragment_messaging_textMessage_answer_image_amswer);
            ivPlay = (ImageView) itemView.findViewById(R.id.card_fragment_messaging_textMessage_answer_image_play_answer);

        }

        private void setData(final Answer answer , int position){


            Answer adss = answer;
            tvDoctorName.setText(answer.getDoctorName());
            tvAnswerText.setText(Html.fromHtml(answer.getAnswer()).toString());
            tvAnswerDate.setText(answer.getAnswerDateSh());
            if(answer.getAttachedFiles() != null && answer.getAttachedFiles().size() > 0){
                if(answer.getAttachedFiles().get(0).getFileType() == 1){
                    //image
                    ivPlay.setVisibility(View.INVISIBLE);
                    ivImage.setVisibility(View.VISIBLE);
                    ivImage.getLayoutParams().height = 600;
                    ivImage.getLayoutParams().width = 600;

                    RetrofitFactoryForFileManager.getRetrofitClient().getFiles(answer.getAttachedFiles().get(0).getFileID() , 1).enqueue(new Callback<FileGiver>() {
                        @Override
                        public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {

                            if(response.body().isIsSuccessfull()){
                                FileResult result = response.body().getFileResult();
                                if(result  != null){
                                    Glide.with(context)
                                            .load(result.getFileAddress())
                                            .asBitmap()
                                            .override(200,200)
                                            .centerCrop()
                                            .into(ivImage);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<FileGiver> call, Throwable t) {

                        }
                    });
                }
                if(answer.getAttachedFiles().get(0).getFileType() == 4){
                    //video
                    ivPlay.setVisibility(View.VISIBLE);
                    ivImage.setVisibility(View.INVISIBLE);

                    RetrofitFactoryForFileManager.getRetrofitClient().getFiles(answer.getAttachedFiles().get(0).getFileID() , 4).enqueue(new Callback<FileGiver>() {
                        @Override
                        public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {

                            if(response.body().isIsSuccessfull()){
                                FileResult result = response.body().getFileResult();
                                if(result  != null){
                                    ivPlay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                if (playing) {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.release();
                                                    ivPlay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate));
//                                                    ivPlay.setImageResource(R.drawable.play_blue);
                                                    playing = false;
                                                } else {
                                                    String attachesString = answer.getAttachedFiles().get(0).getFileID().toString();
                                                    new TaskAudioFileUrl().execute(attachesString);
                                                }
                                            }catch (Exception e){}
                                        }
                                    });


                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<FileGiver> call, Throwable t) {

                        }
                    });
                }
            }

        }

        @Override
        public void onClick(View v) {

        }
    }

    class TaskAudioFileUrl extends AsyncTask<String , Void  ,String > {

        @Override
        protected String doInBackground(String... params) {
            return FileManagerHelper.getFileURL(params[0] , "audio").getFileAddress();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(context != null) {
                playerTest = new MediaPlayerTest(context, s);
                mediaPlayer = playerTest.getPlayer();
                playerTest.start();
                playing = true;
                if (playing) {
                    coTimerPlaying = new CountDownTimer(mediaPlayer.getDuration(), 500) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            ivPlay.callOnClick();
                        }
                    };
                    coTimerPlaying.start();
                }
                ivPlay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate));
            }
        }
    }

}






