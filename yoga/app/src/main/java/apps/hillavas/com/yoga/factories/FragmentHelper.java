package apps.hillavas.com.yoga.factories;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class FragmentHelper {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private int frameLayoutId;

    public FragmentHelper(Fragment fragment, int frameLayoutId, FragmentManager fragmentManager) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.frameLayoutId = frameLayoutId;
    }

    public void replace(boolean addToBackStack){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(addToBackStack)
            transaction.addToBackStack(null);
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(frameLayoutId , fragment).commitAllowingStateLoss();


    }
}
