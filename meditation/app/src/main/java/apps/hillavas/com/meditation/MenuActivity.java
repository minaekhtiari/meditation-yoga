package apps.hillavas.com.meditation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_Adapter_Category;
import adapters.RecyclerView_Adapter_CategoryTitrs;
import classes.models.CategoryWithParentChild;
import classes.models.Level;
import classes.tools.helpers.RetrofitFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";
    private static final String SELECTED_CATEGORYID_ID = "SELECTED_CATEGORYID_ID";
    TextView tvNewAnswerCount;
    ImageView ivNewAnswerCountBack;
    RelativeLayout relativeLayoutImageMessage;
    RelativeLayout relativeLayoutImageMenu;
    TextView tvAnswerCountNav;
    ImageView ivAnswerCountNavBack;
    ImageView ivMenu;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferencesHome.getString(GUID, "");
        title = getIntent().getStringExtra("MENU_ITEM");
        id = getIntent().getIntExtra("MENU_ID",0);
        ivMenu = (ImageView) findViewById(R.id.toolbar_home_imageMenu);
//        ivMenu.setImageResource(R.drawable.back_icon);
        ((TextView)findViewById(R.id.toolbar_home_frameTitle_text)).setText("فیلتر بر اساس مقطع تحصیلی");

        relativeLayoutImageMessage = (RelativeLayout)findViewById(R.id.toolbar_home_imageMessage_relative);
//        relativeLayoutImageMenu = (RelativeLayout)findViewById(R.id.toolbar_home_imageMenu_relative);
        tvAnswerCountNav = (TextView) findViewById(R.id.fragment_home_nav_text_newAnswerCount);
        ivAnswerCountNavBack = (ImageView) findViewById(R.id.fragment_home_nav_image_newAnswerCountBack);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        relativeLayoutImageMessage.setOnClickListener(this);
        relativeLayoutImageMenu.setOnClickListener(this);
        relativeLayoutImageMessage = (RelativeLayout) findViewById(R.id.toolbar_home_imageMessage_relative);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        checkNewAnswerCount();
        relativeSendToContent = (RelativeLayout) findViewById(R.id.activity_menu_relative_send);
        recyclerViewCategories = (RecyclerView)findViewById(R.id.activity_menu_recycler_categoris);
        recyclerViewContents = (RecyclerView)findViewById(R.id.activity_menu_recycler_contents);
        ivGoToContent = (ImageView) findViewById(R.id.activity_menu_image_send);
        linearLayoutManagerCategories = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerCategories.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCategories.setLayoutManager(linearLayoutManagerCategories);
        linearLayoutManagerContents = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerContents.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContents.setLayoutManager(linearLayoutManagerContents);
        EventBus.getDefault().register(this);
        selectedCategory = getIntent().getIntExtra("CATEGORY_ID_SELECTED",0);

        new TaskLoadLevels().execute();

        ((TextView)findViewById(R.id.card_category_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskLoadLevels().execute();
                listAllTitrs = new ArrayList<CategoryWithParentChild>();
                //recyclerViewCategories.setBackgroundColor(getResources().getColor(R.color.gray_200));
                ((TextView)findViewById(R.id.card_category_text)).setBackgroundColor(getResources().getColor(R.color.white));
                for(int i = 0 ; i < 20 ; i++){
                    try {
                        ((TextView) linearLayoutManagerCategories.findViewByPosition(i).findViewById(R.id.card_category_text))
                                .setBackgroundColor(getResources().getColor(R.color.gray_200));
                    }catch (Exception e){}
                }

            }
        });
        relativeSendToContent.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Subscribe
    public void onEvent(final CategoryWithParentChild category) {
        relativeSendToContent.setVisibility(View.INVISIBLE);

        if(!category.isHasChild()) {
//            Intent intent = new Intent(MenuActivity.this , FirstContentActivity.class);
//            intent.putExtra("CATEGORY_ID_SELECTED" , category.getCategoryId());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
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
                RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getApplicationContext(), listContents );
                recyclerViewContents.setAdapter(recyclerView_adapter_category);
            }

            relativeSendToContent.setVisibility(View.VISIBLE);

            relativeSendToContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this , FirstContentActivity.class);
                    intent.putExtra("CATEGORY_ID_SELECTED" , category.getCategoryId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }else {

            ((TextView) findViewById(R.id.card_category_text)).setBackgroundColor(getResources().getColor(R.color.gray_200));
            List<CategoryWithParentChild> listContents = new ArrayList<>();

            if (category.getCategoryId() == 0) {
                new TaskLoadCategoriesForLevel().execute(category.getLevelId());
            }
            if (category.getCategoryId() > 0) {
                for (CategoryWithParentChild c : listAll) {
                    if (c.getParentId() == category.getCategoryId())
                        listContents.add(c);
                }
                RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getApplicationContext(), listContents );
                recyclerViewContents.setAdapter(recyclerView_adapter_category);
            }

            RecyclerView_Adapter_CategoryTitrs recyclerView_adapter_categoryTitrs = new RecyclerView_Adapter_CategoryTitrs(getApplicationContext(), listAllTitrs, category.getIndexForColor() );
            recyclerViewCategories.setAdapter(recyclerView_adapter_categoryTitrs);

            if (category.isFromTitr() && category.getCategoryId() != listAllTitrs.get(listAllTitrs.size()).getCategoryId()) {

                List<CategoryWithParentChild> listTemp = new ArrayList<>();
                for (int i = 0; i <= category.getIndexFromList(); i++) {
                    listTemp.add(listAllTitrs.get(i));
                }
                listAllTitrs = new ArrayList<>();
                listAllTitrs = listTemp;
            } else {
                for (int i = category.getDepth() + 1; i < listAllTitrs.size(); i++)
                    listAllTitrs.remove(i);
                listAllTitrs.add(category);

                for (int i = 0; i < listAllTitrs.size(); i++) {
                    if (listAllTitrs.get(i).getCategoryId() != listAllTitrs.get(i + 1).getParentId())
                        listAllTitrs.remove(i + 1);
                }
                if (listAllTitrs.get(listAllTitrs.size()).getCategoryId() != category.getCategoryId())
                    listAllTitrs.remove(listAllTitrs.size());
            }
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.toolbar_home_imageMenu_relative:{
//                onBackPressed();
//                break;
//            }
            case R.id.toolbar_home_imageMessage_relative:{
                Intent in = new Intent(MenuActivity.this , MessagingActivity.class);
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
                listLevelCategories.add(child);
            }
            RecyclerView_Adapter_Category adapter_category = new RecyclerView_Adapter_Category(getApplicationContext() , listLevelCategories );
            recyclerViewContents.setAdapter(adapter_category);
        }
    }

    class TaskLoadCategoriesForLevel extends AsyncTask<Integer,Void,List<CategoryWithParentChild>> {
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
            listAll = list;
            List<CategoryWithParentChild> list1 = new ArrayList<>();
            for(CategoryWithParentChild c : listAll){
                if(c.getParentId() == 0)
                    list1.add(c);
            }

            RecyclerView_Adapter_Category recyclerView_adapter_category = new RecyclerView_Adapter_Category(getApplicationContext() , list1);
            recyclerViewContents.setAdapter(recyclerView_adapter_category);
        }
    }
}
