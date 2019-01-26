package apps.hillavas.com.yoga.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.hillavas.com.yoga.R;


public class intro_fragment3 extends Fragment {
    FragmentTransaction transaction;
    String simType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_final, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ((TextView)getActivity().findViewById(R.id.fragment_enter_button_btnGotoMobileGiver)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();


            }
        });
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
}
