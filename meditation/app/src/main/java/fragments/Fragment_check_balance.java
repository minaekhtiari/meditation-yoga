package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class Fragment_check_balance extends android.support.v4.app.Fragment implements View.OnClickListener {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_balances, container , false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}

