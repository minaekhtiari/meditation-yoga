package fragments;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.hillavas.messaging.classes.Answer;
import com.hillavas.messaging.classes.Question;
import com.hillavas.messaging.helpers.QuestionHelper;

import java.util.List;

import adapters.RecyclerView_messagesQuestion_Answers_Adapter;
import apps.hillavas.com.meditation.R;
import classes.tools.MediaPlayerTest;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_messaging_Answers extends Fragment {

    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;
    CountDownTimer coTimerPlaying;
    MediaPlayerTest playerTest;
    MediaPlayer mediaPlayer;
    TextView tvQuestionSubject;
    TextView tvQuestionBody;
    TextView tvQuestionDate;
    TextView tvNoAnswer;
    ImageView ivPlay;

    RecyclerView recyclerViewAnswer;

    int questionId = 0;
    String token = null;

    TaskLoadQuestion taskLoadQuestion;
    TaskGetAllAnswers taskGetAllAnswers;

    boolean playing = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messaging_answers, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");

        if(getArguments() != null)
            questionId = getArguments().getInt("QUESTION_ID");

        tvQuestionSubject = (TextView) getActivity().findViewById(R.id.fragment_messaging_answer_text_question_subject);
        tvQuestionBody = (TextView) getActivity().findViewById(R.id.fragment_messaging_answer_text_question_body);
        tvQuestionDate = (TextView) getActivity().findViewById(R.id.fragment_messaging_answer_text_question_date);
        recyclerViewAnswer = (RecyclerView) getActivity().findViewById(R.id.fragment_messaging_answer_recyclerView);
        tvNoAnswer = (TextView) getActivity().findViewById(R.id.fragment_messaging_answer_text_noAnswer);
        ivPlay = (ImageView) getActivity().findViewById(R.id.fragment_messaging_answer_image_play);



    }

    class TaskLoadQuestion extends AsyncTask<Integer,Void,Question>{
        @Override
        protected Question doInBackground(Integer... params) {
            Question question = null;
            question= QuestionHelper.getQuestion(token , params[0]);
            return question;
        }

        @Override
        protected void onPostExecute(final Question question) {
            super.onPostExecute(question);
            tvQuestionSubject.setText(question.getSubject());
            if(question.getBody().equals("media")) {
                tvQuestionBody.setText("");
                ivPlay.setVisibility(View.VISIBLE);
            }else {
                tvQuestionBody.setText(question.getBody());
                ivPlay.setVisibility(View.INVISIBLE);
            }
            tvQuestionDate.setText((String)question.getInsertDateSh());

            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (playing) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            ivPlay.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
//                            ivPlay.setImageResource(R.drawable.play_blue);
                            playing = false;
                        } else {
                            String attachesString = question.getAttachs().toString();
                            String fileString = attachesString.substring(12, attachesString.length()).split("\"")[0];
                            new TaskAudioFileUrl().execute(fileString);
                        }
                    }catch (Exception e){}
                }
            });

        }
    }

    class TaskGetAllAnswers extends AsyncTask<Integer , Void , List<Answer>>{

        @Override
        protected List<Answer> doInBackground(Integer... params) {
            List<Answer> answers = null;
            answers = QuestionHelper.getAllAnswerByQuestionId(token , params[0]);
            return answers;
        }

        @Override
        protected void onPostExecute(List<Answer> answers) {
            super.onPostExecute(answers);
            if(answers == null) {
                tvNoAnswer.setVisibility(View.VISIBLE);
                return;
            }
            if(answers.size() > 0) {
                new TaskSetReadedAnswer().execute(questionId);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);
                recyclerViewAnswer.setLayoutManager(layoutManager);
                RecyclerView_messagesQuestion_Answers_Adapter recyclerView_messagesQuestion_answers_adapter = new RecyclerView_messagesQuestion_Answers_Adapter(getActivity(), answers);
                if (recyclerView_messagesQuestion_answers_adapter != null)
                    recyclerViewAnswer.setAdapter(recyclerView_messagesQuestion_answers_adapter);
                    tvNoAnswer.setVisibility(View.INVISIBLE);
            }else {
                tvNoAnswer.setVisibility(View.VISIBLE);
            }

        }
    }

    class TaskAudioFileUrl extends AsyncTask<String , Void  ,String >{

        @Override
        protected String doInBackground(String... params) {
            return FileManagerHelper.getFileURL(params[0] , "audio").getFileAddress();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(getActivity() != null) {
                playerTest = new MediaPlayerTest(getActivity(), s);
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
                ivPlay.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
//                ivPlay.setImageResource(R.drawable.stop_blue);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(questionId > 0){
            taskLoadQuestion =  new TaskLoadQuestion();
            taskLoadQuestion.execute(questionId);
            taskGetAllAnswers = new TaskGetAllAnswers();
            taskGetAllAnswers.execute(questionId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        taskLoadQuestion.cancel(true);
        taskGetAllAnswers.cancel(true);
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        }catch (Exception e){}

    }

    class TaskSetReadedAnswer extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            QuestionHelper.readedAnswer(token , params[0]);
            return null;
        }
    }
}
