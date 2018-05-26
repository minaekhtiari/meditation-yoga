package fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apps.hillavas.com.meditation.R;
import classes.Question;
import classes.justifiers.JustifiedTextView;
import classes.tools.TextAndBacks;
import factories.FragmentHelper;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_FAQ_OneQuestion extends Fragment {


    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";

    SharedPreferences sharedPreferencesHome;
    int fontSize = 12;
    int fontSizeText = 12;
    int fontSizeTitr = 14;
    boolean whiteOrNight = false;
    RelativeLayout relativeLayoutBack;
    TextView tvToolbarTitle;
    Question question = null;
    int questionId = 0;
    TextView tvOneQuestion;
    JustifiedTextView jTextOneAnswer;
    int backId;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faq_one_question , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        new FragmentHelper(
                new Fragment_ToolbarAll(),
                R.id.fraqment_faqOne_toolbarFrame,
                getActivity().getSupportFragmentManager()
        ).replace(false);

        relativeLayoutBack = (RelativeLayout) getActivity().findViewById(R.id.card_fragment_commonQuestion_relativeBack);
        tvOneQuestion = (TextView) getActivity().findViewById(R.id.card_fragment_commonQuestion_Onequestion);
        jTextOneAnswer = (JustifiedTextView) getActivity().findViewById(R.id.card_fragment_commonQuestion_Oneanswer);
        backId = relativeLayoutBack.getId();
        tvOneQuestion.setTextSize(fontSizeText);

        jTextOneAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP , fontSizeText + 2);
        jTextOneAnswer.setLineSpacing(80);

        ((FrameLayout)getActivity().findViewById(R.id.frameLayout_bottom_bar)).setVisibility(View.VISIBLE);

        titrIds.add(tvOneQuestion.getId());
        textIds.add(jTextOneAnswer.getId());

        new TextAndBacks(getActivity()).changer(textIds , titrIds , backId);

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(TEXT_IDS , textIds);
        bundle.putIntegerArrayList(TITR_IDS , titrIds);
        bundle.putInt(BACK_ID , backId);
        Fragment_BottomBar fragment_bottomBar = new Fragment_BottomBar();
        fragment_bottomBar.setArguments(bundle);
        new FragmentHelper(
                fragment_bottomBar,
                R.id.frameLayout_bottom_bar,
                getActivity().getSupportFragmentManager()
        ).replace(false);

        //temp
        Question question1 = new Question();
        question1.setQuestion("علائم بارداری چه مواردی است ؟ ");
        question1.setAnswer("مواردی که در این مقاله آورده شده است، نتیجه سال\u200Cها تحقیقات، آزمون\u200Cها و تجربیات نویسنده است. نکاتی\u200C بسیار ظریف که  بی\u200C توجهی\u200C به هر کدام از آنها میتواند فاجعه  بار و عمل به آنها میتواند موفقیت را چند برابر کند. امیدوارم که دوستان عزیز نیز قدم پیش نهاده و تجربه شخصی\u200C\u200Cشان را در رابطه با این مقاله و نه موضوعات حاشیه\u200Cای دیگر با همه به اشتراک بگذارند. پیشنهاد من به دوستان این است که این  مقاله  را خلاصه نویسی کرده و بشکل برگ راهنما در محل جوجه کشی\u200C نصب کرده تا با در دسترس داشتن آن بتوانند به نحو احسن موارد را رعایت کنند.");
        tvOneQuestion.setText(question1.getQuestion());
        jTextOneAnswer.setText(question1.getAnswer());
        Typeface tf = Typeface.createFromAsset(getContext().getAssets() , "fonts/iransans.ttf");
        jTextOneAnswer.setTypeFace(tf);

    }
}
