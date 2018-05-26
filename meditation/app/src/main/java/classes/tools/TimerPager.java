package classes.tools;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import java.util.TimerTask;

/**
 * Created by mohsen.mohammadi on 6/18/2017.
 */

public class TimerPager extends TimerTask {

    private Activity activity;
    private ViewPager pager;
    private int size;

    public TimerPager(Activity activity){
           this.activity = activity;
//        this.pager = pager;
//        this.size = size;
    }
    public void setViewPagerAndSize(ViewPager pager , int size){
        this.pager = pager;
        this.size = size;
    }
    @Override
    public void run() {
        activity.runOnUiThread(new TimerTask() {
            @Override
            public void run() {

                int currentItem = pager.getCurrentItem();

                if(currentItem == size-1){
                    currentItem = 0;
                }else {
                    currentItem = pager.getCurrentItem() + 1;
                }
                pager.setCurrentItem(currentItem);

            }
        });
    }
}
