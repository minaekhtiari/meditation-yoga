package apps.hillavas.com.yoga.activity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.classes.Home_Pager_Page;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_Knowing extends Fragment {

    RecyclerView recyclerView;
    List<Home_Pager_Page> home_pager_pageList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_knowing, container , false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.fragment_knowing_recyclerView);

        Home_Pager_Page page1 = new Home_Pager_Page();
//        page1.setImageBackId(R.drawable.l1);
        page1.setImageMovieIconId(R.drawable.video_type);
        page1.setTxtMovie("ویدئو");
        page1.setTxtText("از هر فرصتی برای به آغوش کشیدن یکدیگر استفاده کنید");
        page1.setDateDayNumber(15);
        page1.setDateMounthName("فروردین");
        page1.setDateYearName("1396");
        page1.setViewCount(457);
        page1.setLikeCount(79);

        Home_Pager_Page page2 = new Home_Pager_Page();
//        page2.setImageBackId(R.drawable.l2);
        page2.setImageMovieIconId(R.drawable.video_type);
        page2.setTxtMovie("ویدئو");
        page2.setTxtText("از هر فرصتی برای به آغوش کشیدن یکدیگر استفاده کنید");
        page2.setDateDayNumber(15);
        page2.setDateMounthName("فروردین");
        page2.setDateYearName("1396");
        page2.setViewCount(457);
        page2.setLikeCount(79);

        Home_Pager_Page page3 = new Home_Pager_Page();
//        page3.setImageBackId(R.drawable.l3);
        page3.setImageMovieIconId(R.drawable.video_type);
        page3.setTxtMovie("ویدئو");
        page3.setTxtText("از هر فرصتی برای به آغوش کشیدن یکدیگر استفاده کنید");
        page3.setDateDayNumber(15);
        page3.setDateMounthName("فروردین");
        page3.setDateYearName("1396");
        page3.setViewCount(457);
        page3.setLikeCount(79);


        home_pager_pageList.add(page1);home_pager_pageList.add(page2);home_pager_pageList.add(page3);


    }
}
