package apps.hillavas.com.yoga.activity.fragments;




import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by A.Mohammadi on 7/4/2017.
 */

public class FragmentHomeBaseChanger {

    private static FragmentManager manager;
    private static FragmentTransaction transaction;
    private static Fragment fragment;
    private static int fragmentLayout;


    public FragmentHomeBaseChanger(FragmentManager manager, int fragmentLayout , Fragment fragment) {
        this.manager = manager;
        this.fragmentLayout = fragmentLayout;
        this.fragment = fragment;
    }

    public static void seter(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(fragmentLayout ,fragment ).commit();
    }
}
