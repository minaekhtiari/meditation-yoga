package fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apps.hillavas.com.meditation.MessagingActivity;
import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 7/18/2017.
 */

public class Fragment_ToolbarAll extends Fragment implements View.OnClickListener{


    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    SharedPreferences sharedPreferencesHome;

    RelativeLayout relativeLayoutMessages;
    RelativeLayout relativeLayoutBack;
    TextView tvNewMessageCount;
    ImageView ivViewMessageCounterBack;
    int newMessageCount = 0;
    TextView tvTitr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbar_all , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());

        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        relativeLayoutMessages = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageMessage_relative);
        relativeLayoutBack = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageBack_relative);
//        tvNewMessageCount = (TextView) getActivity().findViewById(R.id.toolbar_all_text_newMessageCounter);
//        ivViewMessageCounterBack = (ImageView) getActivity().findViewById(R.id.toolbar_all_image_newMessageCounterBack);
        relativeLayoutMessages.setOnClickListener(this);
        relativeLayoutBack.setOnClickListener(this);
        checkNewAnswerCount();
        tvTitr = (TextView) getActivity().findViewById(R.id.toolbar_all_frameTitle_text);
        tvTitr.setTextSize(14);

        if(getArguments() != null){
            if(getArguments().getString("TITLE") != null){
                tvTitr.setText(getArguments().getString("TITLE"));
            }
        }
    }

    private void checkNewAnswerCount() {
        newMessageCount = sharedPreferencesHome.getInt(UNREAD_ANSWERS , 0);
        if(newMessageCount > 0){
            tvNewMessageCount.setVisibility(View.VISIBLE);
            ivViewMessageCounterBack.setVisibility(View.VISIBLE);
            tvNewMessageCount.setText(newMessageCount + "");
        }else {
            tvNewMessageCount.setVisibility(View.INVISIBLE);
            ivViewMessageCounterBack.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_all_imageMessage_relative:{
                Intent in = new Intent(getActivity() , MessagingActivity.class);
                startActivity(in);
                break;
            }
            case R.id.toolbar_all_imageBack_relative:{
                getActivity().onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNewAnswerCount();
    }
}
