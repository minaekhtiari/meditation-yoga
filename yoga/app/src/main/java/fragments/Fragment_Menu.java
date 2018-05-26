package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.RecyclerView_Adapter_Category;
import adapters.RecyclerView_Adapter_CategoryTitrs;
import apps.hillavas.com.yoga.MessagingActivity;
import apps.hillavas.com.yoga.R;
import classes.models.CategoryWithParentChild;
import classes.models.Level;
import classes.tools.helpers.RetrofitFactory;
import factories.FragmentHelper;

/**
 * Created by A.Mohammadi on 10/14/2017.
 */

public class Fragment_Menu extends Fragment implements View.OnClickListener{

    public static final String LAST_CATEGORYID_SELECTED = "LAST_CATEGORYID_SELECTED";
    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";
    private static final String SELECTED_CATEGORYID_ID = "SELECTED_CATEGORYID_ID";
    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    SharedPreferences sharedPreferencesHome;
    String token = null;
    String title = "";
    int id = 0;
    int newAnswerCount = 0;
    Vibrator vibrator;

    RecyclerView recyclerViewCategories;
    RecyclerView recyclerViewContents;
    int levelId = 0;
    int categoryId = 0;
    boolean hasChild = true;
    LinearLayoutManager linearLayoutManagerCategories;
    LinearLayoutManager linearLayoutManagerContents;
    List<CategoryWithParentChild> listAll;
    List<CategoryWithParentChild> listAllTitrs = new ArrayList<>();
    RelativeLayout relativeSendToContent;
    ImageView ivGoToContent;
    int selectedCategory = 0;
    FloatingActionButton fabCategorySelect;
    FrameLayout frameMenu;
    FrameLayout frameBase;
    FloatingActionButton fabPageChange;
    RelativeLayout relativeLayoutToolbarBack;
    RelativeLayout relativeLayoutToolbarMessage;
    TextView tvNewAnswerCount;
    ImageView ivNewAnswerCountBack;

    List<CategoryWithParentChild> categoriesList = new ArrayList<>();
    List<CategoryWithParentChild> contentsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_menu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");
        title = getActivity().getIntent().getStringExtra("MENU_ITEM");
        id = getActivity().getIntent().getIntExtra("MENU_ID",0);
        relativeLayoutToolbarBack = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageBack_relative);
        relativeLayoutToolbarMessage = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageMessage_relative);
        relativeLayoutToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        relativeLayoutToolbarMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity() , MessagingActivity.class);
                startActivity(in);
            }
        });
        ((TextView)getActivity().findViewById(R.id.toolbar_all_frameTitle_text)).setText("فیلتر بر اساس مقطع");
//        tvNewAnswerCount = (TextView) getActivity().findViewById(R.id.toolbar_all_text_newMessageCounter);
//        ivNewAnswerCountBack = (ImageView) getActivity().findViewById(R.id.toolbar_all_image_newMessageCounterBack);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        checkNewAnswerCount();
        relativeSendToContent = (RelativeLayout) getActivity().findViewById(R.id.activity_menu_relative_send);
        recyclerViewCategories = (RecyclerView)getActivity().findViewById(R.id.activity_menu_recycler_categoris);
        recyclerViewContents = (RecyclerView)getActivity().findViewById(R.id.activity_menu_recycler_contents);
        ivGoToContent = (ImageView) getActivity().findViewById(R.id.activity_menu_image_send);
        linearLayoutManagerCategories = new LinearLayoutManager(getActivity());
        linearLayoutManagerCategories.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCategories.setLayoutManager(linearLayoutManagerCategories);
        fabCategorySelect = (FloatingActionButton) getActivity().findViewById(R.id.activity_menu_fabCategorySelect);
        linearLayoutManagerContents = new LinearLayoutManager(getActivity());
        linearLayoutManagerContents.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContents.setLayoutManager(linearLayoutManagerContents);
//        frameMenu = (FrameLayout) getActivity().findViewById(R.id.activity_menu_frame_menu);
//        frameBase = (FrameLayout) getActivity().findViewById(R.id.activity_menu_frame);
//        fabPageChange = (FloatingActionButton) getActivity().findViewById(R.id.activity_menu_fabChangePage);
        EventBus.getDefault().register(this);
        selectedCategory = getActivity().getIntent().getIntExtra("CATEGORY_ID_SELECTED",0);

        new TaskLoadLevels().execute();

        ((TextView)getActivity().findViewById(R.id.card_category_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView_Adapter_CategoryTitrs recyclerView_adapter_categoryTitrs = new RecyclerView_Adapter_CategoryTitrs(getActivity(), categoriesList, -1);
                recyclerViewCategories.setAdapter(recyclerView_adapter_categoryTitrs);

                if(fabCategorySelect.getVisibility() == View.VISIBLE) {
                    fabCategorySelect.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.to_down_50));
                    fabCategorySelect.setVisibility(View.INVISIBLE);
                }

                new TaskLoadLevels().execute();
                listAllTitrs = new ArrayList<CategoryWithParentChild>();
                //recyclerViewCategories.setBackgroundColor(getResources().getColor(R.color.gray_200));
                ((TextView)getActivity().findViewById(R.id.card_category_text)).setBackgroundResource(R.drawable.test1);
                for(int i = 0 ; i < 20 ; i++){
                    try {
                        ((TextView) linearLayoutManagerCategories.findViewByPosition(i).findViewById(R.id.card_category_text))
                                .setBackgroundColor(getResources().getColor(R.color.gray_200));
                    }catch (Exception e){}
                }

            }
        });
    }

    @Subscribe
    public void onEvent(final CategoryWithParentChild category) {
        ((TextView)getActivity().findViewById(R.id.card_category_text)).setBackgroundResource(R.drawable.test2);

        if(!category.isHasChild()) {
            List<CategoryWithParentChild> listContents = new ArrayList<>();
            for(CategoryWithParentChild cc : listAll){
                cc.setSelected(false);
            }
            category.setSelected(true);
            CategoryWithParentChild cParent = null;
            if (category.getCategoryId() > 0) {
                for (CategoryWithParentChild c : listAll) {
                    if (c.getCategoryId() == category.getParentId())
                        cParent = c;
                }
                for (CategoryWithParentChild c : listAll) {
                    if (c.getParentId() == cParent.getCategoryId())
                        listContents.add(c);
                }

                if (listContents.size() > 0) {
                    Collections.sort(listContents, new Comparator<CategoryWithParentChild>() {
                        @Override
                        public int compare(CategoryWithParentChild o1, CategoryWithParentChild o2) {
                            int cat1 = o1.getCategoryId() , cat2 = o2.getCategoryId();
                            int compare = (cat1 > cat2) ? 1 : 0;
                            if (compare == 0) {
                                compare = (o1 == o2) ? 0 : -1;
                            }
                            return compare;
                        }
                    });
                }

                RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getActivity(), listContents );
                recyclerViewContents.setAdapter(recyclerView_adapter_category);
            }
            if(fabCategorySelect.getVisibility() != View.VISIBLE) {
                fabCategorySelect.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.to_up_50));
                fabCategorySelect.setVisibility(View.VISIBLE);
            }
            fabCategorySelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameBase.setVisibility(View.VISIBLE);
                    frameMenu.setVisibility(View.INVISIBLE);
                    fabPageChange.setVisibility(View.VISIBLE);
                    sharedPreferencesHome.edit().putInt(LAST_CATEGORYID_SELECTED , category.getCategoryId()).commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("CATEGORY_ID_SELECTED" , category.getCategoryId());
                    Fragment_pager fragment_pager = new Fragment_pager();
                    fragment_pager.setArguments(bundle);

//                    new FragmentHelper(
//                            fragment_pager,
//                            R.id.activity_menu_frame,
//                            getActivity().getSupportFragmentManager()
//                    ).replace(false);
                }
            });
        }else {

            if(fabCategorySelect.getVisibility() == View.VISIBLE) {
                fabCategorySelect.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.to_down_50));
                fabCategorySelect.setVisibility(View.INVISIBLE);

            }

            if (category.getParentId() == -1)
                new TaskLoadCategoriesForLevelBase().execute(category.getLevelId());
            else {
                List<CategoryWithParentChild> cList = new ArrayList<>();
                for (CategoryWithParentChild c : listAll) {
                    if (category.getCategoryId() == c.getParentId())
                        cList.add(c);
                }

                if (cList.size() > 0) {
                    Collections.sort(cList, new Comparator<CategoryWithParentChild>() {
                        @Override
                        public int compare(CategoryWithParentChild o1, CategoryWithParentChild o2) {
                            int cat1 = o1.getCategoryId() , cat2 = o2.getCategoryId();
                            int compare = (cat1 > cat2) ? 1 : 0;
                            if (compare == 0) {
                                compare = (o1 == o2) ? 0 : -1;
                            }
                            return compare;
                        }
                    });
                }


                RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getActivity(), cList);
                recyclerViewContents.setAdapter(recyclerView_adapter_category);
            }

            boolean isThere = false;
            if (!category.isFromTitr()) {
                for (CategoryWithParentChild c : categoriesList) {
                    if (c.getCategoryId() == category.getCategoryId())
                        isThere = true;
                }
                if (!isThere) {
                    if (categoriesList.size() > category.getDepth()) {
                        int size = categoriesList.size() - 1;
                        for (int j = size; j >= category.getDepth(); j--)
                            categoriesList.remove(j);
                    }
                    categoriesList.add(category);
                }

            }
            int selectedRow = categoriesList.size();

            if(category.isFromTitr()){
                selectedRow = category.getIndexForColor()+1;
            }else {
                selectedRow = category.getDepth()+1;
            }
            RecyclerView_Adapter_CategoryTitrs recyclerView_adapter_categoryTitrs = new RecyclerView_Adapter_CategoryTitrs(getActivity(), categoriesList, selectedRow);
            recyclerViewCategories.setAdapter(recyclerView_adapter_categoryTitrs);

        }



    }








    private void checkNewAnswerCount() {
        newAnswerCount = sharedPreferencesHome.getInt(UNREAD_ANSWERS , 0);
        if(newAnswerCount > 0){
            tvNewAnswerCount.setVisibility(View.VISIBLE);
            ivNewAnswerCountBack.setVisibility(View.VISIBLE);
            tvNewAnswerCount.setText(newAnswerCount + "");
        }else {
            tvNewAnswerCount.setVisibility(View.INVISIBLE);
            ivNewAnswerCountBack.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//
//            case R.id.toolbar_home_imageMenu_relative:{
//                getActivity().onBackPressed();
//                break;
//            }
            case R.id.toolbar_home_imageMessage_relative:{
                Intent in = new Intent(getActivity() , MessagingActivity.class);
                startActivity(in);
                break;
            }

        }
    }

    class TaskLoadLevels extends AsyncTask<Void,Void,List<Level>> {
        @Override
        protected List<Level> doInBackground(Void... params) {
            List<Level> levels = new ArrayList<>();
            try {
                if (RetrofitFactory.getRetrofitClient().getLevels(token).execute().body().isIsSuccessfull())
                    levels = RetrofitFactory.getRetrofitClient().getLevels(token).execute().body().getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return levels;
        }

        @Override
        protected void onPostExecute(List<Level> levels) {
            super.onPostExecute(levels);
            List<CategoryWithParentChild> listLevelCategories = new ArrayList<>();
            for(Level l : levels){
                CategoryWithParentChild child = new CategoryWithParentChild();
                child.setDepth(0);
                child.setHasChild(true);
                child.setLevelId(l.getLevelId());
                child.setName(l.getInfoRecord());
                child.setParentId(-1);
                child.setCategoryId(l.getLevelId());
                listLevelCategories.add(child);
            }

            if (listLevelCategories.size() > 0) {
                Collections.sort(listLevelCategories, new Comparator<CategoryWithParentChild>() {
                    @Override
                    public int compare(CategoryWithParentChild o1, CategoryWithParentChild o2) {
                        int cat1 = o1.getCategoryId() , cat2 = o2.getCategoryId();
                        int compare = (cat1 > cat2) ? 1 : 0;
                        if (compare == 0) {
                            compare = (o1 == o2) ? 0 : -1;
                        }
                        return compare;
                    }
                });
            }

            RecyclerView_Adapter_Category adapter_category = new RecyclerView_Adapter_Category(getActivity() , listLevelCategories );
            recyclerViewContents.setAdapter(adapter_category);
            ((RelativeLayout)getActivity().findViewById(R.id.activity_menu_relative_deActive)).setVisibility(View.INVISIBLE);
            ((RelativeLayout)getActivity().findViewById(R.id.activity_menu_relative_active)).setVisibility(View.VISIBLE);
        }
    }

    class TaskLoadCategoriesForLevelBase extends AsyncTask<Integer,Void,List<CategoryWithParentChild>> {
        @Override
        protected List<CategoryWithParentChild> doInBackground(Integer... params) {
            List<CategoryWithParentChild> list = new ArrayList<>();
            try {
                if (RetrofitFactory.getRetrofitClient().getAllCategoryOfALevel(token , params[0]).execute().body().isIsSuccessfull())
                    list = RetrofitFactory.getRetrofitClient().getAllCategoryOfALevel(token , params[0]).execute().body().getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<CategoryWithParentChild> list) {
            super.onPostExecute(list);
            for(CategoryWithParentChild cc :list){
                if(cc.getParentId() == 0)
                    cc.setParentId(cc.getLevelId());
                cc.setDepth(cc.getDepth()+1);
            }
            listAll = list;
            List<CategoryWithParentChild> list1 = new ArrayList<>();
            for(CategoryWithParentChild c : listAll){
                if(c.getParentId() == c.getLevelId())
                    list1.add(c);
            }

            if (list1.size() > 0) {
                Collections.sort(list1, new Comparator<CategoryWithParentChild>() {
                    @Override
                    public int compare(CategoryWithParentChild o1, CategoryWithParentChild o2) {
                        int cat1 = o1.getCategoryId() , cat2 = o2.getCategoryId();
                        int compare = (cat1 > cat2) ? 1 : 0;
                        if (compare == 0) {
                            compare = (o1 == o2) ? 0 : -1;
                        }
                        return compare;
                    }
                });
            }

            RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getActivity() , list1);
            recyclerViewContents.setAdapter(recyclerView_adapter_category);


        }
    }
}
