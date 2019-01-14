package apps.hillavas.com.yoga.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hillavas.messaging.classes.Question;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.classes.justifiers.JustifiedTextView;
import apps.hillavas.com.yoga.factories.FragmentHelper;
import apps.hillavas.com.yoga.activity.fragments.Fragment_BottomBar;
import apps.hillavas.com.yoga.activity.fragments.Fragment_ToolbarAll;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CommonQuestionOne extends AppCompatActivity {

    TextView tvQuestion;
    JustifiedTextView tvAnswer;
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";
    public static final String GUID="GUID";

    String token = null;
    RelativeLayout relativeLayoutBack;
    RecyclerView recyclerViewQuestions;
    TextView tvToolbarTitle;
    RelativeLayout relativeLayoutSearchBox;
    boolean whiteOrNight = false;
    SharedPreferences sharedPreferencesHome;
    int backId;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();
    List<Question> allQuestions = new ArrayList<>();
    List<com.hillavas.messaging.classes.Question> allQuestionsSearch = null;
    EditText editSerach;
    ImageView ivSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_question_one);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        String question  = getIntent().getStringExtra("ONE_QUESTION");
        String answer  = getIntent().getStringExtra("ONE_ANSWER");
        tvQuestion = (TextView) findViewById(R.id.oneQuestion_Onequestion);
        tvAnswer = (JustifiedTextView) findViewById(R.id.oneQuestion_Oneanswer);
        tvQuestion.setText(Html.fromHtml(question));
        tvAnswer.setText(Html.fromHtml(answer).toString());
        tvAnswer.setLineSpacing(50);
        Typeface tf = Typeface.createFromAsset(getAssets() , "fonts/iransans.ttf");
        tvAnswer.setTypeFace(tf);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));

        whiteOrNight = sharedPreferencesHome.getBoolean(NIGHT_MODE , false);
        relativeLayoutBack = (RelativeLayout)findViewById(R.id.oneQuestion_relativeBack);
        if(whiteOrNight)
            relativeLayoutBack.setBackgroundColor(getResources().getColor(R.color.gray_700));
        else
            relativeLayoutBack.setBackgroundColor(getResources().getColor(R.color.white));

        Fragment_ToolbarAll fragment_toolbarAll = new Fragment_ToolbarAll();
        Bundle bundleToolBarTitle = new Bundle();
        String title = null;
        if(question.length() > 25){
            title = question.substring(0 , 25) + " ... ";
        }else
            title = question;
        
        bundleToolBarTitle.putString("TITLE" ,title);
        fragment_toolbarAll.setArguments(bundleToolBarTitle);

        new FragmentHelper(
                fragment_toolbarAll,
                R.id.oneQuestion_toolbarFrame,
                getSupportFragmentManager()
        ).replace(false);


        titrIds.add(tvQuestion.getId());
        textIds.add(tvAnswer.getId());

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(TEXT_IDS , textIds);
        bundle.putIntegerArrayList(TITR_IDS , titrIds);
        bundle.putInt(BACK_ID , relativeLayoutBack.getId());
        bundle.putBoolean("FROM_RECYCLER_QUESTIONS" , false);
        Fragment_BottomBar fragment_bottomBar = new Fragment_BottomBar();
        fragment_bottomBar.setArguments(bundle);
        new FragmentHelper(
                fragment_bottomBar,
                R.id.oneQuestion_bottom_bar,
                getSupportFragmentManager()
        ).replace(false);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
