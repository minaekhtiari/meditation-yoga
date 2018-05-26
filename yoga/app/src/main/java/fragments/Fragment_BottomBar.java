package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hillavas.messaging.classes.Answer;

import java.util.ArrayList;
import java.util.List;

import adapters.Recycler_Adapter_CommonQuestions;
import apps.hillavas.com.yoga.R;
import classes.justifiers.JustifiedTextView;
import classes.models.ResultJson;
import classes.tools.TextAndBacks;
import classes.tools.helpers.RetrofitFactory;
import factories.FragmentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by A.Mohammadi on 7/8/2017.
 */

public class Fragment_BottomBar extends Fragment implements View.OnClickListener{

    private static final String CONTENT_ID = "CONTENT_ID";
    private static final String TOKEN = "TOKEN";
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";


    ImageView ivFontSize;
    ImageView ivNightWhite;
    ImageView ivShare;
    ImageView ivLike;

    LinearLayout linearLayout;
    LinearLayout linearFontSize;
    LinearLayout linearShare;
    LinearLayout linearLike;
    LinearLayout linearNightWhite;

    boolean whiteOrNight = false;
    boolean likeOrNo = false;

    SharedPreferences sharedPreferencesHome;

    int backId;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();
    boolean fromRecyclerQuestions = false;
    String token = null;
    int contentId = 0;
    Vibrator vibrator;
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();
    List<com.hillavas.messaging.classes.Question> lstquestions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottombar, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
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
            if(bundle.containsKey(CONTENT_ID))
                contentId = bundle.getInt(CONTENT_ID);
            if(bundle.containsKey(TOKEN))
                token = bundle.getString(TOKEN);
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





        RetrofitFactory.getRetrofitClient().getisLikedOrNot(token,contentId).enqueue(new Callback<ResultJson>() {
            @Override
            public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {
                  if(response.body() != null){
                      likeOrNo = response.body().isIsSuccessfull();
                      if(likeOrNo)
                          ivLike.setImageResource(R.drawable.liked);
                  }
            }

            @Override
            public void onFailure(Call<ResultJson> call, Throwable t) {

            }
        });

            new TextAndBacks(getActivity()).changer(textIds , titrIds , backId);

            ivFontSize = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_image_fontsize);
            ivNightWhite = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_image_nightwhite);
            ivShare = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_image_share);
            ivLike = (ImageView) getActivity().findViewById(R.id.fragment_bottombar_image_like);
            linearLayout = (LinearLayout) getActivity().findViewById(R.id.fragment_bottombar_linear);

            linearFontSize = (LinearLayout) getActivity().findViewById(R.id.fragment_bottombar_linear_image_fontsize);
            linearShare = (LinearLayout) getActivity().findViewById(R.id.fragment_bottombar_linear_image_share);
            linearLike = (LinearLayout) getActivity().findViewById(R.id.fragment_bottombar_linear_image_like);
            linearNightWhite = (LinearLayout) getActivity().findViewById(R.id.fragment_bottombar_linear_image_nightwhite);

            linearFontSize.setOnClickListener(this);
            linearShare.setOnClickListener(this);
            linearLike.setOnClickListener(this);
            linearNightWhite.setOnClickListener(this);



            if (whiteOrNight) {
                bottomBarNight();
            }else {
                bottomBarWhite();
            }
    }

    @Override
    public void onClick(View v) {
        vibrator.vibrate(65);
        if(fromRecyclerQuestions){
            RecyclerView recyclerViewQuestions = (RecyclerView) getActivity().findViewById(R.id.fraqment_faq_recyclerQuestions);
            Recycler_Adapter_CommonQuestions adapterCommonQuestions = new Recycler_Adapter_CommonQuestions(getActivity() , lstquestions);
            recyclerViewQuestions.setAdapter(adapterCommonQuestions);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerViewQuestions.setLayoutManager(layoutManager);
        }
        switch (v.getId()){

            case R.id.fragment_bottombar_linear_image_share: {

                try {
                    String titr = null;
                    String text = null;

                    View view = getActivity().findViewById(titrIds.get(0));
                    if (view instanceof TextView) {
                        titr = ((TextView) view).getText().toString();
                    }
                    if (view instanceof JustifiedTextView) {
                        titr = ((JustifiedTextView) view).getText().toString();
                    }

                    View viewText = getActivity().findViewById(textIds.get(0));
                    if (viewText instanceof TextView) {
                        text = ((TextView) view).getText().toString();
                    }
                    if (viewText instanceof JustifiedTextView) {
                        text = ((JustifiedTextView) viewText).getText().toString();
                    }


                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, titr + "\n" + text + "\n");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }catch (Exception e){}
                break;
            }

            case R.id.fragment_bottombar_linear_image_nightwhite: {
                if (!whiteOrNight) {
                    bottomBarNight();
                } else {
                    bottomBarWhite();
                }
                sharedPreferencesHome.edit().putBoolean(NIGHT_MODE , whiteOrNight).commit();
                new TextAndBacks(getActivity()).changer(textIds , titrIds , backId);
                break;
            }
            case R.id.fragment_bottombar_linear_image_like:{
                if(!likeOrNo){
                    ivLike.setImageResource(R.drawable.liked);
                    likeOrNo = true;
                }else {
//                    if(whiteOrNight)
////                        ivLike.setImageResource(R.drawable.like);
//                    else
////                        ivLike.setImageResource(R.drawable.like_night);
                    likeOrNo = false;
                }
                if(likeOrNo){
                    RetrofitFactory.getRetrofitClient().getContentLikeCount(token,contentId).enqueue(new Callback<ResultJson>() {
                        @Override
                        public void onResponse(Call<ResultJson> call, Response<ResultJson> response) {

                        }

                        @Override
                        public void onFailure(Call<ResultJson> call, Throwable t) {

                        }
                    });
                }else{

                }
                break;
            }
            case R.id.fragment_bottombar_linear_image_fontsize:{
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(TEXT_IDS , textIds);
                bundle.putIntegerArrayList(TITR_IDS , titrIds);
                bundle.putStringArrayList("QUESTION_LIST" , questionList);
                bundle.putStringArrayList("ANSWER_LIST" , answerList);

                bundle.putInt(BACK_ID , backId);
                if(fromRecyclerQuestions)
                    bundle.putBoolean( "FROM_RECYCLER_QUESTIONS" , true);
                else
                    bundle.putBoolean( "FROM_RECYCLER_QUESTIONS" , false);
                Fragment_BottomBar_fontSize bottomBar_fontSize = new Fragment_BottomBar_fontSize();
                bottomBar_fontSize.setArguments(bundle);
                new FragmentHelper(
                        bottomBar_fontSize,
                        R.id.frameLayout_bottom_bar,
                        getActivity().getSupportFragmentManager()
                ).replace(false);
                break;
            }

        }
    }

    private void likeOrNotLike(){
        if(!likeOrNo){
            if(!whiteOrNight){
//                ivLike.setImageResource(R.drawable.like_night);
            }else{
//                ivLike.setImageResource(R.drawable.like);
            }
        }else
            ivLike.setImageResource(R.drawable.liked);
    }

    private void bottomBarWhite(){
//        ivFontSize.setImageResource(R.drawable.font_size);
        likeOrNotLike();
//        ivLike.setImageResource(R.drawable.like);
//        ivNightWhite.setImageResource(R.drawable.night_mode);
        ivShare.setImageResource(R.drawable.share);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        whiteOrNight = false;
    }

    private void bottomBarNight(){
//        ivFontSize.setImageResource(R.drawable.font_size_night);
        likeOrNotLike();
//        ivLike.setImageResource(R.drawable.like_night);
//        ivNightWhite.setImageResource(R.drawable.night_mode_on);
        ivShare.setImageResource(R.drawable.share_night);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray_700));
        whiteOrNight = true;
    }
}
