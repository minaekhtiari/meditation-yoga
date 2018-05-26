package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.hillavas.com.meditation.R;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class Fragment_firstContentCategory1 extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_insertion, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        YogaContent y1 = new YogaContent();
//        List<YogaContent> yogaContentList = new ArrayList<>();
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//        yogaContentList.add(y1);
//
//        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.firstContentActivity_Category1);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity() , 3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        RecyclerView_Adapter_FirstContentActivity_category1 recyclerView_adapter_firstContentActivity_category1 = new RecyclerView_Adapter_FirstContentActivity_category1(
//                getActivity(),
//                yogaContentList
//        );
//        recyclerView.setAdapter(recyclerView_adapter_firstContentActivity_category1);
    }
}
