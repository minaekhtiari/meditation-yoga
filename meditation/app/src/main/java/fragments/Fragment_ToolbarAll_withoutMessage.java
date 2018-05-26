package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 7/18/2017.
 */

public class Fragment_ToolbarAll_withoutMessage extends Fragment implements View.OnClickListener{


    RelativeLayout relativeLayoutBack;
    TextView tvTitr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbar_all_without_message , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        relativeLayoutBack = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageBack_relative);
        relativeLayoutBack.setOnClickListener(this);
        tvTitr = (TextView) getActivity().findViewById(R.id.toolbar_all_frameTitle_text);
        if(getArguments() != null){
            if(getArguments().getString("TITLE") != null){
                tvTitr.setText(getArguments().getString("TITLE"));
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_all_imageBack_relative:{
                getActivity().onBackPressed();
                break;
            }
        }
    }
}
