package apps.hillavas.com.yoga.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.hillavas.com.yoga.activity.fragments.Fragment_training_mobtadi;
import apps.hillavas.com.yoga.activity.fragments.Fragment_training_pishrafte;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class ViewPager_FragmentAdapter_trainingBarnamePishnahadi extends FragmentPagerAdapter {


    public ViewPager_FragmentAdapter_trainingBarnamePishnahadi(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Fragment_training_mobtadi();
            case 1 : return new Fragment_training_pishrafte();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
