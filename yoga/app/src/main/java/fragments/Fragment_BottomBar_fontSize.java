package fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hillavas.messaging.classes.Answer;

import java.util.ArrayList;
import java.util.List;

import adapters.Recycler_Adapter_CommonQuestions;
import apps.hillavas.com.yoga.R;
import classes.justifiers.JustifiedTextView;
import factories.FragmentHelper;


/**
 * Created by A.Mohammadi on 7/8/2017.
 */

public class Fragment_BottomBar_fontSize extends Fragment implements View.OnClickListener{

    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";

    SharedPreferences sharedPreferencesHome;

    ImageView ivClose;
    ImageView ivFontSmall;
    ImageView ivFontLarg;
    SeekBar seekBar;
    RelativeLayout relativeLayout;
    int fontSizeText = 12;
    boolean whiteOrNight = false;
    int backId;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();
    boolean fromRecyclerQuestions = false;
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();
    List<com.hillavas.messaging.classes.Question> lstquestions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottombar_fontsize, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ivFontSmall = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_fontSize_image_fontSmall);
        ivFontLarg = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_fontSize_image_fontLarg);
        seekBar = (SeekBar) getActivity().findViewById(R.id.fragment_bottombar_fontSize_seekbar);
        ivClose = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_fontSize_image_close);
        relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.fragment_bottombar_relative);

        if(sharedPreferencesHome.contains(FONT_SIZE))
            fontSizeText = sharedPreferencesHome.getInt(FONT_SIZE , 12);
        if(sharedPreferencesHome.contains(NIGHT_MODE))
            whiteOrNight = sharedPreferencesHome.getBoolean(NIGHT_MODE , false);

        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey(BACK_ID))
                backId = bundle.getInt(BACK_ID);
            if(bundle.containsKey(TEXT_IDS))
                textIds = bundle.getIntegerArrayList(TEXT_IDS);
            if(bundle.containsKey(TITR_IDS))
                titrIds = bundle.getIntegerArrayList(TITR_IDS);
            if(bundle.containsKey("FROM_RECYCLER_QUESTIONS"))
                fromRecyclerQuestions = bundle.getBoolean("FROM_RECYCLER_QUESTIONS");
            if(bundle.containsKey("QUESTION_LIST"))
                questionList = bundle.getStringArrayList("QUESTION_LIST");
            if(bundle.containsKey("ANSWER_LIST"))
                answerList = bundle.getStringArrayList("ANSWER_LIST");

        }

        if(questionList.size() > 0 && answerList.size() > 0){
            for(int i = 0 ; i < questionList.size() ; i++){
                com.hillavas.messaging.classes.Question question = new com.hillavas.messaging.classes.Question();
                question.setBody(questionList.get(i));
                List<Answer> answers = new ArrayList<>();
                Answer answer = new Answer();
                answer.setAnswer(answerList.get(i));
                answers.add(answer);
                question.setAnswers(answers);
                lstquestions.add(i , question );
            }
        }



        switch (fontSizeText){
            case 12 : {
                seekBar.setProgress(0);
                break;
            }
            case 14 : {
                seekBar.setProgress(1);
                break;
            }
            case 16 : {
                seekBar.setProgress(2);
                break;
            }
            case 18 : {
                seekBar.setProgress(3);
                break;
            }
            case 20 : {
                seekBar.setProgress(4);
                break;
            }
            case 22 : {
                seekBar.setProgress(5);
                break;
            }
        }


        ivClose.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (progress){
                    case 0:{
                        fontSizeText = 12;
                        textSizeSetter(12);
                        break;
                    }
                    case 1:{

                        fontSizeText = 14;
                        textSizeSetter(14);
                        break;
                    }
                    case 2:{
                        fontSizeText = 16;
                        textSizeSetter(16);
                        break;
                    }
                    case 3:{
                        fontSizeText = 18;
                        textSizeSetter(18);
                        break;
                    }
                    case 4:{
                        fontSizeText = 20;
                        textSizeSetter(20);
                        break;
                    }
                    case 5:{
                        fontSizeText = 22;
                        textSizeSetter(22);
                        break;
                    }
                }
                sharedPreferencesHome.edit().putInt(FONT_SIZE , fontSizeText).commit();


                if(fromRecyclerQuestions){
                    RecyclerView recyclerViewQuestions = (RecyclerView) getActivity().findViewById(R.id.fraqment_faq_recyclerQuestions);
                    Recycler_Adapter_CommonQuestions adapterCommonQuestions = new Recycler_Adapter_CommonQuestions(getActivity() , lstquestions);
                    recyclerViewQuestions.setAdapter(adapterCommonQuestions);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    layoutManager.scrollToPosition(0);
                    recyclerViewQuestions.setLayoutManager(layoutManager);

                }
            }




            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(whiteOrNight){
//            ivClose.setImageResource(R.drawable.close_night);
//            ivFontSmall.setImageResource(R.drawable.font_size_night);
//            ivFontLarg.setImageResource(R.drawable.font_size_night);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.gray_700));
        }else {
//            ivClose.setImageResource(R.drawable.close);
//            ivFontSmall.setImageResource(R.drawable.font_size);
//            ivFontLarg.setImageResource(R.drawable.font_size);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_bottombar_fontSize_image_close : {
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(TEXT_IDS , textIds);
                bundle.putIntegerArrayList(TITR_IDS , titrIds);
                bundle.putBoolean("FROM_RECYCLER_QUESTIONS" , true);
                bundle.putStringArrayList("QUESTION_LIST" , questionList);
                bundle.putStringArrayList("ANSWER_LIST" , answerList);
                bundle.putInt(BACK_ID , backId);
                if(fromRecyclerQuestions)
                    bundle.putBoolean( "FROM_RECYCLER_QUESTIONS" , true);
                else
                    bundle.putBoolean( "FROM_RECYCLER_QUESTIONS" , false);
                Fragment_BottomBar fragment_bottomBar = new Fragment_BottomBar();
                fragment_bottomBar.setArguments(bundle);
                new FragmentHelper(
                        fragment_bottomBar,
                        R.id.frameLayout_bottom_bar,
                        getActivity().getSupportFragmentManager()
                ).replace(false);
                break;
            }
        }
    }


    private void textSizeSetter(int fontSize){

        for(int i = 0 ; i < textIds.size() ; i++){
            View view = getActivity().findViewById(textIds.get(i));
            if(view instanceof  TextView){
                ((TextView) view ).setTextSize(fontSizeText);
            }
            if(view instanceof JustifiedTextView){
                ((JustifiedTextView)view).setTextSize(TypedValue.COMPLEX_UNIT_SP , fontSizeText);
                ((JustifiedTextView)view).setLineSpacing(80);
            }
        }

        for(int i = 0 ; i < titrIds.size() ; i++){
            View view = getActivity().findViewById(titrIds.get(i));
            if(view instanceof  TextView){
                ((TextView) view ).setTextSize(fontSizeText +2);
            }
            if(view instanceof JustifiedTextView){
                ((JustifiedTextView)view).setTextSize(TypedValue.COMPLEX_UNIT_SP , fontSizeText +2);
                ((JustifiedTextView)view).setLineSpacing(80);
            }
        }
    }

}

