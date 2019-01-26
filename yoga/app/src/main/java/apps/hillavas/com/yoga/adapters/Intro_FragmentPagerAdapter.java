package apps.hillavas.com.yoga.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.hillavas.com.yoga.Intro.intro_fragment1;
import apps.hillavas.com.yoga.Intro.intro_fragment2;
import apps.hillavas.com.yoga.Intro.intro_fragment3;


public class Intro_FragmentPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public Intro_FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return new intro_fragment1();
            case 1 : return new intro_fragment2();
            case 2 : return new intro_fragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
