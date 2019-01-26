package apps.hillavas.com.yoga.Intro;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import net.alexandroid.utils.indicators.IndicatorsView;


import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.adapters.Intro_FragmentPagerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


    public class IntroActivity extends AppCompatActivity {
        ViewPager viewPager;
        IndicatorsView mIndicatorsView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);

            viewPager = (ViewPager) findViewById(R.id.viewPagerTour);
            Intro_FragmentPagerAdapter adapter = new Intro_FragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            mIndicatorsView = (IndicatorsView) findViewById(R.id.indicatorsView);

            mIndicatorsView.setViewPager(viewPager);

            mIndicatorsView.setSmoothTransition(true);

            mIndicatorsView.setIndicatorsClickChangePage(true);

            mIndicatorsView.setIndicatorsClickListener(new IndicatorsView.OnIndicatorClickListener() {
                @Override
                public void onClick(int indicatorNumber) {

                }
            });

            mIndicatorsView.setSelectedIndicator(2);
        }

        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        }

        @Override
        public void onBackPressed() {
            //  android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
            //  android.support.v4.app.FragmentTransaction transaction=fm.beginTransaction();
//        for (int i=0;i<getFragmentManager().getBackStackEntryCount();i++)
//        {
//            if(getFragmentManager().getBackStackEntryCount()==2){
//                finish();
//                super.onBackPressed();
//            }
//        }



        }
    }


