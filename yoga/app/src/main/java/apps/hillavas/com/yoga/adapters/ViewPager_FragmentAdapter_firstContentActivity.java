package apps.hillavas.com.yoga.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.hillavas.com.yoga.activity.fragments.Fragment_firstContentCategory1;
import apps.hillavas.com.yoga.activity.fragments.Fragment_check_balance;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class ViewPager_FragmentAdapter_firstContentActivity extends FragmentPagerAdapter {


    public ViewPager_FragmentAdapter_firstContentActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Fragment_firstContentCategory1();
            case 1 : return new Fragment_check_balance();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
