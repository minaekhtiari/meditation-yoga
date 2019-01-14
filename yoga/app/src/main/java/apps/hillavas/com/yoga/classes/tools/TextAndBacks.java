package apps.hillavas.com.yoga.classes.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.classes.justifiers.JustifiedTextView;

/**
 * Created by A.Mohammadi on 7/15/2017.
 */

public class TextAndBacks {
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    int textSize = 12;
    int titrSize = 14;
    boolean whiteOrNight = false;


    SharedPreferences sharedPreferences;
    Context context;

    public TextAndBacks(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        textSize = sharedPreferences.getInt(FONT_SIZE , 0);
        titrSize = textSize + 2;
        whiteOrNight = sharedPreferences.getBoolean(NIGHT_MODE , false);
    }

    public void changer(ArrayList<Integer> textIds , ArrayList<Integer> titrIds , int backId) {

        ArrayList<Integer> allViews = new ArrayList<>();

        if(textIds != null) {
            if (textIds.size() > 0) {
                for (int id : textIds) {
                    View view = ((Activity) context).findViewById(id);
                    if (view instanceof JustifiedTextView)
                        ((JustifiedTextView) ((Activity) context).findViewById(id)).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    if (view instanceof TextView)
                        ((TextView) ((Activity) context).findViewById(id)).setTextSize(textSize);
                    allViews.add(id);
                }
            }
        }
        if(titrIds != null){
        if (titrIds.size() > 0) {
            for (int id : titrIds) {
                View view = ((Activity) context).findViewById(id);
                if (view instanceof JustifiedTextView)
                    ((JustifiedTextView) ((Activity) context).findViewById(id)).setTextSize(TypedValue.COMPLEX_UNIT_SP, titrSize);
                if (view instanceof TextView)
                    ((TextView) ((Activity) context).findViewById(id)).setTextSize(titrSize);
                allViews.add(id);
            }
          }
        }
        if (backId > 0) {
            allViews.add(backId);
        }

        if (whiteOrNight) {
            for (int id : allViews) {
                View view = ((Activity) context).findViewById(id);
                if (view instanceof JustifiedTextView) {
                    ((JustifiedTextView) view).setTextColor(context.getResources().getColor(R.color.white));
                }
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(context.getResources().getColor(R.color.white));
                }
                if (view instanceof NestedScrollView) {
                    ((NestedScrollView) view).setBackgroundColor(context.getResources().getColor(R.color.gray_700));
                }
                if (view instanceof RelativeLayout) {
                    ((RelativeLayout) view).setBackgroundColor(context.getResources().getColor(R.color.gray_700));
                }
            }
        }else {
            for (int id : allViews) {
                View view = ((Activity) context).findViewById(id);
                if (view instanceof JustifiedTextView) {
                    ((JustifiedTextView) view).setTextColor(context.getResources().getColor(R.color.black));
                }
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(context.getResources().getColor(R.color.black));
                }
                if (view instanceof NestedScrollView) {
                    ((NestedScrollView) view).setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                if (view instanceof RelativeLayout) {
                    ((RelativeLayout) view).setBackgroundColor(context.getResources().getColor(R.color.white));
                }
            }
        }
    }
}
