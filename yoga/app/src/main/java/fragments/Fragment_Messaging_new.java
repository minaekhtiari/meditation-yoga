package fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import apps.hillavas.com.yoga.R;
import classes.justifiers.JustifiedTextView;
import factories.FragmentHelper;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_Messaging_new extends Fragment {

    public static final String SENT_MESSAGE="SENT_MESSAGE";
    SharedPreferences sharedPreferencesHome;
    LinearLayout linearLayoutTextMessage;
    LinearLayout linearLayoutAudioMessage;
    JustifiedTextView justifiedTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messaging_new , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        justifiedTextView = (JustifiedTextView) getActivity().findViewById(R.id.fragment_messaging_new_jText);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        justifiedTextView.setText(R.string.messageNewManual);
        justifiedTextView.setLineSpacing(50);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets() , "fonts/iransans.ttf");
        justifiedTextView.setTypeFace(tf);

        linearLayoutTextMessage = (LinearLayout) getActivity().findViewById(R.id.fragment_messaging_new_linear_text);
        linearLayoutAudioMessage = (LinearLayout) getActivity().findViewById(R.id.fragment_messaging_new_linear_audio);
        linearLayoutTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentHelper(
                        new Fragment_messaging_text_new(),
                        R.id.fragment_messaging_frameBase,
                        getActivity().getSupportFragmentManager()
                ).replace(true);
            }
        });
        linearLayoutAudioMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentHelper(
                        new Fragment_messaging_voiceMessage(),
                        R.id.fragment_messaging_frameBase,
                        getActivity().getSupportFragmentManager()
                ).replace(true);
            }
        });

    }

}
