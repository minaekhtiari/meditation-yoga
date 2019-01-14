package apps.hillavas.com.yoga.activity.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.adapters.RecyclerView_record_adapter;
import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.data.models.Record;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_contents extends Fragment {


    private static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String NAME="NAME";
    public static final String SEX="SEX";
    public static final String WEIGHT="WEIGHT";
    public static final String PICTURE_PROFILE_ADDRESS="PICTURE_PROFILE_ADDRESS";
    RelativeLayout relativeToolbarSetting;
    RelativeLayout relativeToolbarBack;
    DrawerLayout drawerLayout;
    CircleImageView ivProfilePicture;
    TextView tvNameProfileInfo;
    TextView tvWeightProfileInfo;

    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;
    RecyclerView recyclerViewCategories;
    RecyclerView recyclerViewContents;
    String token = null;

    int levelId = 0;
    int categoryId = 0;
    boolean hasChild = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_content , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_content_drawerLayout);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");

//        Bundle bundle = getArguments();
//        if(bundle != null){
//            if(bundle.containsKey(CATEGORY_ID)){
//                categoryId  = bundle.getInt(CATEGORY_ID , 0);
//            }
//        }
//
//        if(categoryId > 0){
//            if(categoryId != sharedPreferencesHome.getInt(CATEGORY_ID , 0))
//                new TaskLoadContents().execute(categoryId);
//        }
//        if(categoryId == 10)
//            new TaskLoadContents().execute(10);

    }


    class TaskLoadContents extends AsyncTask<Integer,Void,List<Record>> {

        List<Record> records = new ArrayList<>();
        @Override
        protected List<Record> doInBackground(Integer... params) {

            try {
                if (RetrofitFactory.getRetrofitClient().getContents(token,categoryId,1,100).execute().body().isIsSuccessfull())
                    records = RetrofitFactory.getRetrofitClient().getContents(token,categoryId,1,100).execute().body().getResult().getRecords();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        }
        @Override
        protected void onPostExecute(List<Record> records) {
            super.onPostExecute(records);
            if(records != null && records.size() > 0){
                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.content_recycler_content);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                RecyclerView_record_adapter records_adapter = new RecyclerView_record_adapter(getActivity() , records , token);
                recyclerView.setAdapter(records_adapter);
            }
        }
    }
}
