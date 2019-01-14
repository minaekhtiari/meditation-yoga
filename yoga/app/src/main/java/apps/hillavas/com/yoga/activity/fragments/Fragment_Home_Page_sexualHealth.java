package apps.hillavas.com.yoga.activity.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;


import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.classes.Home_Pager_Page;
import apps.hillavas.com.yoga.classes.tools.TimerPager;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_Home_Page_sexualHealth extends Fragment {
    public static final String MOBILE_NUMBER="MOBILE_NUMBER";
    public static final String PASSWORD="PASSWORD";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;

    FloatingActionButton fabViewChanger;
    int styleMode = 1;
    android.app.FragmentTransaction transaction;

    FrameLayout frameLayout0;
    FrameLayout frameLayout1;
    FrameLayout frameLayout2;
    FrameLayout frameProgress;

    RecyclerView recyclerView;
    List<Home_Pager_Page> home_pager_pageList = new ArrayList<>();

    RecyclerView recyclerView1_frame1;
    RecyclerView recyclerView2_frame2;
    RecyclerView recyclerView3_frame3;


    List<Home_Pager_Page> home_pager_pageList2 = new ArrayList<>();

    ViewPager viewPager;
    TimerPager timerPager;
    ScrollView scrollView;

    String token  = null;
    int progressBarEnableNumber= 0;
    boolean loaded = false;
    Vibrator vibrator;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page_sextual_health, container , false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}

