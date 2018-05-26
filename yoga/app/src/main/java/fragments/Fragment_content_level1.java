package fragments;


import android.content.Intent;
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
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_record_adapter;
import apps.hillavas.com.yoga.ContentActivity;
import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.Yoga_contents_all_for_categoryid;
import classes.models.Record;
import classes.tools.helpers.RetrofitFactory;
import factories.FragmentHelper;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_content_level1 extends Fragment implements View.OnClickListener{


    public static final String GUID="GUID";
    private static final String CATEGORY_ID = "CATEGORY_ID";
    SharedPreferences sharedPreferencesHome;
    Toolbar toolbar;
    RecyclerView recyclerView;

    RelativeLayout relativeBadani;
    RelativeLayout relativeAsana;
    RelativeLayout relativePaksazi;
    RelativeLayout relativeSikl;
    RelativeLayout relativeZen;
    RelativeLayout relativeAmadegi;
    TextView tvToolbarTitle;

    String token = "";
    int categoryId = 0;
    int categoryIdSelected = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yoga_category_level1, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");
        relativeBadani= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_badani);
        relativeAsana= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_asana);
        relativePaksazi= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_paksazi);
        relativeSikl= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_sikl);
        relativeZen= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_zen);
        relativeAmadegi= (RelativeLayout) getActivity().findViewById(R.id.fragment_yoga_category_amadegi);
        relativeBadani.setOnClickListener(this);
        relativeAsana.setOnClickListener(this);
        relativePaksazi.setOnClickListener(this);
        relativeSikl.setOnClickListener(this);
        relativeZen.setOnClickListener(this);
        relativeAmadegi.setOnClickListener(this);
        tvToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_home_frameTitle_text);
        new TaskLoadContents().execute(10);
    }


    @Override
    public void onResume() {
        super.onResume();
        resetRelativeBacks();
    }

    public void resetRelativeBacks(){
        relativeBadani.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativeAsana.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativePaksazi.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativeSikl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativeZen.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativeAmadegi.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_yoga_category_badani : {
                resetRelativeBacks();
                relativeBadani.setBackgroundColor(getResources().getColor(R.color.white));
                categoryIdSelected = 1;
                new FragmentHelper(new Fragment_content_badani(),R.id.firstContentActivity_frameBase,getActivity().getSupportFragmentManager()).replace(true);
                tvToolbarTitle.setText("حرکات بدنی");
                break;
            }
            case R.id.fragment_yoga_category_amadegi : {
                resetRelativeBacks();
                relativeAmadegi.setBackgroundColor(getResources().getColor(R.color.white));
                categoryIdSelected = 10;
                if(sharedPreferencesHome.getInt(CATEGORY_ID , 0) != 10) {
                    if(recyclerView != null)
                        recyclerView.setVisibility(View.INVISIBLE);

                    new TaskLoadContents().execute(10);
                }
//                sharedPreferencesHome.edit().putInt(CATEGORY_ID , 10).commit();
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase)).setVisibility(View.INVISIBLE);
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase2)).setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("آمادگی");
                break;
            }
            case R.id.fragment_yoga_category_asana : {
                resetRelativeBacks();
                relativeAsana.setBackgroundColor(getResources().getColor(R.color.white));
                new FragmentHelper(new Fragment_content_asana(),R.id.firstContentActivity_frameBase,getActivity().getSupportFragmentManager()).replace(true);
                tvToolbarTitle.setText("آسانا");
                break;
            }
            case R.id.fragment_yoga_category_paksazi : {
                resetRelativeBacks();
                relativePaksazi.setBackgroundColor(getResources().getColor(R.color.white));
                new FragmentHelper(new Fragment_content_paksazi(),R.id.firstContentActivity_frameBase,getActivity().getSupportFragmentManager()).replace(true);
                categoryIdSelected = 4;
                tvToolbarTitle.setText("پاکسازی");
                break;
            }
            case R.id.fragment_yoga_category_sikl : {
                resetRelativeBacks();
                relativeSikl.setBackgroundColor(getResources().getColor(R.color.white));
                categoryIdSelected = 14;
                if(sharedPreferencesHome.getInt(CATEGORY_ID , 0) != categoryIdSelected) {
                    if(recyclerView != null)
                    recyclerView.setVisibility(View.INVISIBLE);
                    new TaskLoadContents().execute(categoryIdSelected);
                }
//                sharedPreferencesHome.edit().putInt(CATEGORY_ID , categoryIdSelected).commit();
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase)).setVisibility(View.INVISIBLE);
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase2)).setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("سیکل");
                break;
            }
            case R.id.fragment_yoga_category_zen : {
                resetRelativeBacks();
                relativeZen.setBackgroundColor(getResources().getColor(R.color.white));
                categoryIdSelected = 15;
                if(sharedPreferencesHome.getInt(CATEGORY_ID , 0) != categoryIdSelected) {
                    if(recyclerView != null)
                    recyclerView.setVisibility(View.INVISIBLE);
                    new TaskLoadContents().execute(categoryIdSelected);
                }
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase)).setVisibility(View.INVISIBLE);
                ((FrameLayout) getActivity().findViewById(R.id.firstContentActivity_frameBase2)).setVisibility(View.VISIBLE);
                tvToolbarTitle.setText("ذن");
                break;
            }

        }

    }


    class TaskLoadContents extends AsyncTask<Integer,Void,List<Record>> {
        List<Record> records = new ArrayList<>();
        @Override
        protected List<Record> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getContents(token,params[0],1,100).execute().body().isIsSuccessfull())
                    records = RetrofitFactory.getRetrofitClient().getContents(token,params[0],1,100).execute().body().getResult().getRecords();
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

                }catch (Exception e){}
        }
    }
}
