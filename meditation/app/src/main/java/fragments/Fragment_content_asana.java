package fragments;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_record_adapter;
import apps.hillavas.com.meditation.R;
import classes.models.Record;
import classes.tools.helpers.RetrofitFactory;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_content_asana extends Fragment implements View.OnClickListener {

    public static final String GUID = "GUID";
    private static final String CATEGORY_ID = "CATEGORY_ID";
    SharedPreferences sharedPreferencesHome;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView tvToolbarTitle;


    String token = "";
    int categoryId = 0;
    int categoryIdSelected = 0;

    RelativeLayout relativeAsanaBasic;
    RelativeLayout relativeAsanaAdvance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yoga_category_asana, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");
        relativeAsanaBasic = (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_asana_relative_basic);
        relativeAsanaAdvance = (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_asana_relative_advance);
        relativeAsanaBasic.setOnClickListener(this);
        relativeAsanaAdvance.setOnClickListener(this);
        tvToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_home_frameTitle_text);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.content_recycler_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_yoga_category_asana_relative_basic: {
                categoryIdSelected = 9;
                if (sharedPreferencesHome.getInt(CATEGORY_ID, 0) != categoryIdSelected) {
                    if (recyclerView != null)
                        recyclerView.setVisibility(View.INVISIBLE);
                    new TaskLoadContents().execute(categoryIdSelected);
                }
//                sharedPreferencesHome.edit().putInt(CATEGORY_ID , 10).commit();
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase)).setVisibility(View.INVISIBLE);
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase2)).setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("آسانا مقدماتی");
                break;
            }
            case R.id.fragment_yoga_category_asana_relative_advance: {
                categoryIdSelected = 11;
                if (sharedPreferencesHome.getInt(CATEGORY_ID, 0) != categoryIdSelected) {
                    if (recyclerView != null)
                        recyclerView.setVisibility(View.INVISIBLE);
                    new TaskLoadContents().execute(categoryIdSelected);
                }
//                sharedPreferencesHome.edit().putInt(CATEGORY_ID , 10).commit();
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase)).setVisibility(View.INVISIBLE);
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase2)).setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("آسانا پیشرفته");
                break;
            }
        }

    }

    class TaskLoadContents extends AsyncTask<Integer, Void, List<Record>> {
        List<Record> records = new ArrayList<>();

        @Override
        protected List<Record> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getContents(token, params[0], 1, 100).execute().body().isIsSuccessfull())
                    records = RetrofitFactory.getRetrofitClient().getContents(token, params[0], 1, 100).execute().body().getResult().getRecords();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Record> records) {
            super.onPostExecute(records);
            try {
                recyclerView = (RecyclerView) getActivity().findViewById(R.id.content_recycler_content);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                RecyclerView_record_adapter records_adapter = new RecyclerView_record_adapter(getActivity(), records, token);
                recyclerView.setAdapter(records_adapter);
                sharedPreferencesHome.edit().putInt(CATEGORY_ID, categoryIdSelected).commit();

            } catch (Exception e) {
            }
        }
    }
}
