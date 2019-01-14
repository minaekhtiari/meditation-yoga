package apps.hillavas.com.yoga.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.hillavas.com.yoga.activity.fragments.Fragment_training_asli1;
import apps.hillavas.com.yoga.activity.fragments.Fragment_training_asli2;
import apps.hillavas.com.yoga.activity.fragments.Fragment_training_asli3;
import apps.hillavas.com.yoga.activity.fragments.Fragment_training_asli4;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class ViewPager_FragmentAdapter_trainingAsli extends FragmentPagerAdapter {


    public ViewPager_FragmentAdapter_trainingAsli(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Fragment_training_asli1();
            case 1 : return new Fragment_training_asli2();
            case 2 : return new Fragment_training_asli3();
            case 3 : return new Fragment_training_asli4();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
