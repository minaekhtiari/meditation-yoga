package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.hillavas.messaging.classes.Question;
import com.hillavas.messaging.helpers.QuestionHelper;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import classes.tools.MediaPlayerTest;
import factories.FragmentHelper;
import fragments.Fragment_messaging_Answers;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_messagesQuestion_Adapter extends RecyclerView.Adapter<RecyclerView_messagesQuestion_Adapter.MVHolder> {

    RelativeLayout relativeLayoutBack;
    Context context;
    List<Question> questions = new ArrayList<>();
    LayoutInflater inflater;
    List<Integer> unreadedQuestionIds = null;
    String token = null;
    SharedPreferences sharedPreferencesHome;
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    List<ImageView> lstIvPlay = new ArrayList<>();
    boolean isPlaying = false;

    MediaPlayerTest playerTest;
    MediaPlayer mediaPlayer;

    public RecyclerView_messagesQuestion_Adapter(Context context, List<Question> questions , String token) {
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
        this.questions = questions;
        this.token = token;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_messaging_question , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        Question question = questions.get(position);
        holder.setData(question,position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView tvSubject;
        TextView tvBody;
        TextView tvDate;
        ImageView ivHasAnswer;
        ImageView ivText;
        ImageView ivAudio;


        public MVHolder(View itemView) {
            super(itemView);
            tvSubject = (TextView) itemView.findViewById(R.id.card_messaging_question_subject);
            tvBody = (TextView) itemView.findViewById(R.id.card_messaging_question_body);
            tvDate = (TextView) itemView.findViewById(R.id.card_messaging_question_date);
            relativeLayoutBack = (RelativeLayout) itemView.findViewById(R.id.card_messaging_question_relativeBack);
            ivHasAnswer = (ImageView) itemView.findViewById(R.id.card_messaging_question_image_hasAnswer);
            ivText = (ImageView) itemView.findViewById(R.id.card_messaging_question_image_Text);
            ivAudio = (ImageView) itemView.findViewById(R.id.card_messaging_question_image_Audio);
        }

        private void setData(Question question , int position){
            new TaskGetUnreadAnswerCount().execute();
            if(question.isHasFileAttach()){
                ivAudio.setVisibility(View.VISIBLE);
                ivText.setVisibility(View.INVISIBLE);
                tvBody.setVisibility(View.INVISIBLE);
            }else {
                ivAudio.setVisibility(View.INVISIBLE);
                ivText.setVisibility(View.VISIBLE);
                tvBody.setVisibility(View.VISIBLE);
            }
            if(tvBody.getVisibility() == View.VISIBLE){
                tvBody.setText("\n" + Html.fromHtml(question.getBody()).toString());
            }
            tvSubject.setText(Html.fromHtml(question.getSubject()).toString());
            tvDate.setText((String)question.getInsertDateSh());
            if(question.getIsReaded() instanceof Boolean) {
                if ((boolean) question.getIsReaded()) {
                    ivHasAnswer.setVisibility(View.INVISIBLE);
                    relativeLayoutBack.setBackgroundColor(context.getResources().getColor(R.color.white));
                } else {
                    ivHasAnswer.setVisibility(View.VISIBLE);
                    relativeLayoutBack.setBackgroundColor(context.getResources().getColor(R.color.greenGrass));
                }
            }
            relativeLayoutBack.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                if (v.getId() == R.id.card_messaging_question_relativeBack) {
                    if (questions.get(getAdapterPosition()).getQuestionId() > 0) {
                        int questionId = questions.get(getAdapterPosition()).getQuestionId();
                        for (int i : unreadedQuestionIds) {
                            if (questionId == i) {
                                sharedPreferencesHome.edit().putInt(UNREAD_ANSWERS, sharedPreferencesHome.getInt(UNREAD_ANSWERS, 0) - 1).commit();
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("QUESTION_ID", questionId);
                        Fragment_messaging_Answers fragment_messaging_answers = new Fragment_messaging_Answers();
                        fragment_messaging_answers.setArguments(bundle);
                        new FragmentHelper(
                                fragment_messaging_answers,
                                R.id.fragment_messaging_frameBase,
                                ((AppCompatActivity) context).getSupportFragmentManager()
                        ).replace(true);
                    }
                }
            }catch (Exception e){}
        }
    }


    class TaskSetReadedAnswer extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            QuestionHelper.readedAnswer(token , params[0]);
            return null;
        }
    }

    class TaskGetUnreadAnswerCount extends AsyncTask<Void , Void , List<Integer>> {

        @Override
        protected List<Integer> doInBackground(Void... params) {
            List<Integer> questionIdsWithNewAnswers = null;
            questionIdsWithNewAnswers = QuestionHelper.getUnreadAnswerQuestionIds(token);
            return questionIdsWithNewAnswers;
        }

        @Override
        protected void onPostExecute(List<Integer> integers) {
            super.onPostExecute(integers);
            if (integers == null)
                return;
            unreadedQuestionIds = integers;

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
            playerTest = new MediaPlayerTest(context , s);
            mediaPlayer= playerTest.getPlayer();
            playerTest.start();
        }
    }

}






