package adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hillavas.messaging.classes.Question;
import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.meditation.CommonQuestionOne;
import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Recycler_Adapter_CommonQuestions extends RecyclerView.Adapter<Recycler_Adapter_CommonQuestions.VHolder> {


    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";
    public static final String GUID = "GUID";

    String token = null;
    SharedPreferences sharedPreferencesHome;
    int fontSize = 12;
    int fontSizeText = 12;
    int fontSizeTitr = 14;
    boolean whiteOrNight = false;


    RecyclerView recyclerView;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();
    int textSize = 12;
    SharedPreferences getSharedPreferencesHome;

    private Context context;
    private List<Question> questions = new ArrayList<>();
    private LayoutInflater inflater;


    public Recycler_Adapter_CommonQuestions(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
        inflater = LayoutInflater.from(context);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(context);
        this.token = sharedPreferencesHome.getString(GUID, "");
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHolder(inflater.inflate(R.layout.card_fragment_faq_common_questions, parent, false));
    }


    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        Question question = questions.get(position);
        holder.setData(question, position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textQuestion;
        TextView textAnswer;
        TextView textReadMore;

        public VHolder(View itemView) {

            super(itemView);
            sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(context);
            fontSizeTitr = fontSizeText + 2;
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");

            textQuestion = (TextView) itemView.findViewById(R.id.card_fragment_commonQuestion_question);
            textAnswer = (TextView) itemView.findViewById(R.id.card_fragment_commonQuestion_answer);
            textReadMore = (TextView) itemView.findViewById(R.id.card_fragment_commonQuestion_text_readMore);

            textQuestion.setTypeface(typeface);
            textAnswer.setTypeface(typeface);
            textReadMore.setTypeface(typeface);

            textReadMore.setOnClickListener(this);
            textSize = sharedPreferencesHome.getInt(FONT_SIZE, 0);
            whiteOrNight = sharedPreferencesHome.getBoolean(NIGHT_MODE, false);
            if (whiteOrNight) {
                textAnswer.setTextColor(context.getResources().getColor(R.color.white));
                textQuestion.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                textAnswer.setTextColor(context.getResources().getColor(R.color.black));
                textQuestion.setTextColor(context.getResources().getColor(R.color.black));
            }
            textAnswer.setTextSize(textSize);
            textQuestion.setTextSize(textSize + 2);
        }

        public void setData(com.hillavas.messaging.classes.Question question, int position) {
            String answer = question.getAnswerBody();
            if(answer.length() > 150)
                answer = answer.substring(0,150) + " ... ";
            textQuestion.setText(Html.fromHtml(question.getBody()));
            textAnswer.setText(Html.fromHtml(answer));
        }
        @Override
        public void onClick(View v) {
            try {
                String question = questions.get(getAdapterPosition()).getBody();
                String answer = questions.get(getAdapterPosition()).getAnswerBody();
                Intent intent = new Intent(context, CommonQuestionOne.class);
                intent.putExtra("ONE_QUESTION", question);
                intent.putExtra("ONE_ANSWER", answer);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }catch (Exception e){}
        }
    }
}


