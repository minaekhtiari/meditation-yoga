package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hillavas.messaging.classes.Question;
import com.robinhood.spark.SparkView;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_Adapter_FirstContentActivity_category1;
import adapters.RecyclerView_Adapter_FirstContentActivity_category2;
import adapters.SparkView_Adapter;
import apps.hillavas.com.yoga.R;
import classes.models.YogaContent;

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

