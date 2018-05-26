package fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.AboutUs;
import apps.hillavas.com.yoga.ChangeStateActivity;
import apps.hillavas.com.yoga.CommonFragment;
import apps.hillavas.com.yoga.ConsultantActivity;
import apps.hillavas.com.yoga.CustPagerTransformer;
import apps.hillavas.com.yoga.MessagingActivity;
import apps.hillavas.com.yoga.NewsActivity;
import apps.hillavas.com.yoga.R;
import classes.models.ProfileInfo;
import classes.models.Record;
import classes.tools.helpers.RetrofitFactory;
import factories.FragmentHelper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

import static apps.hillavas.com.yoga.R.id.toolbar_home_imageMessage;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */
public class Fragment_pager extends Fragment implements View.OnClickListener{

    public static final String LAST_CATEGORYID_SELECTED = "LAST_CATEGORYID_SELECTED";
    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String UNREAD_ANSWERS="UNREAD_ANSWERS";
    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";
    public static final String GUID="GUID";
    private static final String SELECTED_CATEGORYID_ID = "SELECTED_CATEGORYID_ID";
    int newAnswerCount = 0;
    SharedPreferences sharedPreferencesHome;
    String token=null;
    TextView tvNewAnswerCount;
    ImageView ivNewAnswerCountBack;
    NavigationView nav;
    DrawerLayout drawerLayout;
    RelativeLayout relativeLayoutImageMessage;
    RelativeLayout relativeLayoutImageMenu;
    TextView tvAnswerCountNav;
    ImageView ivAnswerCountNavBack;
    Vibrator vibrator;
    Toolbar toolbar;
    private TextView indicatorTv;
    private View positionView;
    private ViewPager viewPager;
    private List<CommonFragment> fragments = new ArrayList<>();
    private String[] imageArray;
    TextView tvTitr;
    List<Record> allRecords = null;
    RelativeLayout relativeLayoutFilterImage;
    int selectedCategoryId = 0;
    int catIdFromBundle = 0;
    LinearLayout linearLayoutLearningVideo;
    LinearLayout linearLayoutPhoneConsultnt;
    LinearLayout linearLayoutNews;
    LinearLayout linearLayoutConsultantVideos;
    LinearLayout linearLayoutMessaging;
    LinearLayout linearLayoutAboutUs;
    LinearLayout linearLayoutStatusHeader;
    LinearLayout linearLayoutStatus;
    LinearLayout linearLayoutExit;
    int globalSize = 0;
    ProfileInfo profileInformation = null;
    FrameLayout frameLayoutNavContent ;

    TextView tvNavCredit;
    TextView tvNavNameAndFamily;
    TextView tvLineStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        token = sharedPreferencesHome.getString(GUID, "");
        selectedCategoryId = sharedPreferencesHome.getInt(SELECTED_CATEGORYID_ID , 0);
        tvTitr = (TextView) getActivity().findViewById(R.id.activity_main_text_titr);
//        tvLineStatus = (TextView) getActivity().findViewById(R.id.fragment_personalInfon_tvLine_stats);
        relativeLayoutFilterImage = (RelativeLayout) getActivity().findViewById(R.id.main_relative_filter);
        relativeLayoutImageMessage = (RelativeLayout)getActivity().findViewById(R.id.toolbar_home_imageMessage_relative);
//        relativeLayoutImageMenu = (RelativeLayout)getActivity().findViewById(R.id.toolbar_home_imageMenu_relative);
        tvAnswerCountNav = (TextView) getActivity().findViewById(R.id.fragment_home_nav_text_newAnswerCount);
        ivAnswerCountNavBack = (ImageView) getActivity().findViewById(R.id.fragment_home_nav_image_newAnswerCountBack);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        nav = (NavigationView)getActivity().findViewById(R.id.fragment_home_nav);
        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.activity_main_drawer);
        tvNavNameAndFamily = (TextView) getActivity().findViewById(R.id.fragment_personalInfo_text_nameAndFamily);
        tvNavCredit = (TextView) getActivity().findViewById(R.id.fragment_personalInfo_text_credit);
        ivAnswerCountNavBack = (ImageView) getActivity().findViewById(R.id.fragment_home_nav_image_newAnswerCountBack);
        linearLayoutLearningVideo = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_learningVideos);
        linearLayoutPhoneConsultnt = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_phoneConsultant);
        linearLayoutNews = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_news);
        linearLayoutConsultantVideos = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_consultantVideos);
        linearLayoutMessaging = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_messages);
        linearLayoutAboutUs = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_aboutUs);
        linearLayoutExit = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_exit);
//        linearLayoutStatus = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_status);
//        linearLayoutStatusHeader = (LinearLayout) getActivity().findViewById(R.id.fragment_personalInfon_linear_statusHeader);
        linearLayoutLearningVideo.setOnClickListener(this);
        linearLayoutPhoneConsultnt.setOnClickListener(this);
        linearLayoutNews.setOnClickListener(this);
        linearLayoutConsultantVideos.setOnClickListener(this);
        linearLayoutMessaging.setOnClickListener(this);
        linearLayoutAboutUs.setOnClickListener(this);
        linearLayoutExit.setOnClickListener(this);
        relativeLayoutImageMessage.setOnClickListener(this);
        relativeLayoutImageMenu.setOnClickListener(this);
        linearLayoutStatus.setOnClickListener(this);

        int lastSelectedCategoryId = 0;

        if(sharedPreferencesHome.contains(LAST_CATEGORYID_SELECTED))
            lastSelectedCategoryId = sharedPreferencesHome.getInt(LAST_CATEGORYID_SELECTED , 0);

        if(getArguments().containsKey("CATEGORY_ID_SELECTED"))
            selectedCategoryId = getArguments().getInt("CATEGORY_ID_SELECTED");

        if(lastSelectedCategoryId > 0)
            selectedCategoryId = lastSelectedCategoryId;

        if(selectedCategoryId > 0) {
            new TaskLoadContents().execute(selectedCategoryId);
        }
        else
            new TaskLoadContents().execute(0);
        checkNewAnswerCount();
        new TaskLoadProfileInfo().execute();

    }

    private void fillViewPager(final int size) {
        indicatorTv = (TextView) getActivity().findViewById(R.id.indicator_tv);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        viewPager.setPageTransformer(false, new CustPagerTransformer(getActivity()));

        for (int i = 0; i < 10; i++) {
            fragments.add(new CommonFragment());
        }

        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = fragments.get(position % 10);
                fragment.bindData(imageArray[position % imageArray.length]);
                fragment.bindData(allRecords.get(position));
                fragment.bindData(selectedCategoryId);
                fragment.bindPosition(position);
                return fragment;
            }
            @Override
            public int getCount() {
                globalSize = size;
                return size;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
                tvTitr.setText(position + "");
                tvTitr.setTextColor(getResources().getColor(R.color.gray_500));
                if(allRecords.size()>0) {
                    tvTitr.setVisibility(View.VISIBLE);
                    tvTitr.setText(Html.fromHtml(allRecords.get(position).getBody()).toString());
                }else {
                    tvTitr.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    private void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#455a64'>" + currentItem + "</font>  /  " + totalNum));
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        if(getActivity() != null) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    getActivity())
                    .memoryCacheExtraOptions(480, 800)
                    // default = device screen dimensions
                    .threadPoolSize(3)
                    // default
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                    // default
                    .tasksProcessingOrder(QueueProcessingType.FIFO)
                    // default
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                    .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                    .discCacheFileCount(100) // 缓冲文件数目
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                    .imageDownloader(new BaseImageDownloader(getActivity())) // default
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                    .writeDebugLogs().build();

            // 2.单例ImageLoader类的初始化
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
        }
    }

    class TaskLoadContents extends AsyncTask<Integer,Void,List<Record>> {
        List<Record> records = new ArrayList<>();
        @Override
        protected List<Record> doInBackground(Integer... params) {
            int rowNumbers = 100;
            if(params[0] == 0)
                rowNumbers = 15;
                try {
                    if (RetrofitFactory.getRetrofitClient().getContents(token, params[0], 1, rowNumbers).execute().body().isIsSuccessfull())
                        records = RetrofitFactory.getRetrofitClient().getContents(token, params[0], 1, rowNumbers).execute().body().getResult().getRecords();
                    else
                        records = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return records;
        }
        @Override
        protected void onPostExecute(List<Record> records) {
            super.onPostExecute(records);
            if(records != null && records.size() > 0){
                    allRecords = records;
                    List<String> imageIds = new ArrayList<>();
                    for (Record r : records) {
                        imageIds.add(r.getThumbnailImageId());
                    }
                    new TaskImageFileUrlGiver().execute(imageIds);

                }else {
                ((RelativeLayout) getActivity().findViewById(R.id.activity_main_relative))
                        .findViewById(R.id.activity_main_relative_progress).setVisibility(View.INVISIBLE);
                ((RelativeLayout) getActivity().findViewById(R.id.activity_main_relative))
                        .findViewById(R.id.activity_main_relative_textNoContent).setVisibility(View.VISIBLE);
            }
        }
    }

    class TaskImageFileUrlGiver extends AsyncTask<List<String> , Void ,List<String>>{
        @Override
        protected List<String> doInBackground(List<String>... params) {
            return FileManagerHelper.getFileAddress(params[0] , "image");
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            imageArray = strings.toArray(new String[strings.size()]);
            initImageLoader();
            fillViewPager(strings.size());
            int position = 0;
            if(sharedPreferencesHome.contains("POSITION"))
                position = sharedPreferencesHome.getInt("POSITION" , -1);
            if(position == -1 || position >= allRecords.size())
                position = 0;
            viewPager.setCurrentItem(position);
            ((RelativeLayout)getActivity().findViewById(R.id.activity_main_relative)).setVisibility(View.INVISIBLE);

            if(allRecords.size()>1) {
                    String titr = Html.fromHtml(allRecords.get(position).getBody()).toString();
                    tvTitr.setVisibility(View.VISIBLE);
                    tvTitr.setText(titr);
            }else if (allRecords.size() == 1) {
                String titr = Html.fromHtml(allRecords.get(0).getBody()).toString();
                tvTitr.setVisibility(View.VISIBLE);
                tvTitr.setText(titr);
            }else
                tvTitr.setVisibility(View.INVISIBLE);

        }
    }
    class TaskLoadProfileInfo extends AsyncTask<Void,Void,ProfileInfo> {
        List<Record> records = new ArrayList<>();
        @Override
        protected ProfileInfo doInBackground(Void... params) {
            ProfileInfo profileInfo=  new ProfileInfo();
            try {
                if (RetrofitFactory.getRetrofitClient().getProfileInfo(token).execute().body().isIsSuccessfull())
                        profileInfo = RetrofitFactory.getRetrofitClient().getProfileInfo(token).execute().body().getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return profileInfo;
        }

        @Override
        protected void onPostExecute(ProfileInfo profileInfo) {
            super.onPostExecute(profileInfo);
            profileInformation = profileInfo;
            if(profileInfo != null){
                if(!profileInfo.isIsServiceOn()){
                    linearLayoutStatus.setVisibility(View.VISIBLE);
                    linearLayoutStatusHeader.setVisibility(View.VISIBLE);
                    tvLineStatus.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutStatus.setVisibility(View.INVISIBLE);
                    linearLayoutStatusHeader.setVisibility(View.INVISIBLE);
                    linearLayoutStatus.getLayoutParams().height = 1;
                    linearLayoutStatusHeader.getLayoutParams().height = 1;
                    linearLayoutStatus.requestLayout();
                    linearLayoutStatusHeader.requestLayout();
                    tvLineStatus.setVisibility(View.INVISIBLE);
                }
                tvNavCredit.setText(" اعتبار : "+ profileInfo.getCredit() + " ریال ");
                tvNavNameAndFamily.setText(profileInfo.getFirstName() + " " + profileInfo.getLastName());
                sharedPreferencesHome.edit().putInt("CREDIT" , profileInfo.getCredit()).commit();

                relativeLayoutImageMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.toolbar_home_imageMenu_relative:{
//                drawerLayout.openDrawer(GravityCompat.START);
//                break;
//            }
            case R.id.toolbar_home_imageMessage_relative:{
                Intent in = new Intent(getActivity() , MessagingActivity.class);
                startActivity(in);
                break;
            }
            case R.id.fragment_personalInfon_linear_messages:{
                Intent intent = new Intent(getActivity() , MessagingActivity.class);
                startActivity(intent);
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_exit:{

                //goto home page and ...
                getActivity().finishAffinity();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_aboutUs:{
                Intent intent = new Intent(getActivity() , AboutUs.class);
                startActivity(intent);
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_learningVideos:{
                drawerLayout.closeDrawer(GravityCompat.START);
                checkNewAnswerCount();
                Bundle bundleGive = getActivity().getIntent().getExtras();
                if(bundleGive != null && bundleGive.containsKey("CATEGORY_ID_SELECTED"))
                    catIdFromBundle = bundleGive.getInt("CATEGORY_ID_SELECTED");

                Bundle bundle = new Bundle();
                bundle.putInt("CATEGORY_ID_SELECTED" , catIdFromBundle);
                Fragment_pager fragment_pager = new Fragment_pager();
                fragment_pager.setArguments(bundle);

//                new FragmentHelper(
//                        fragment_pager,
//                        R.id.activity_menu_frame,
//                        getActivity().getSupportFragmentManager()
//                ).replace(false);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_phoneConsultant:{
                Intent intent = new Intent(getActivity(), ConsultantActivity.class);
                startActivity(intent);
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_news:{
                Intent intent = new Intent(getActivity() , NewsActivity.class);
                startActivity(intent);
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.fragment_personalInfon_linear_consultantVideos:{
                vibrator.vibrate(65);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

//            case R.id.fragment_personalInfon_linear_status:{
//                drawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(getActivity() , ChangeStateActivity.class);
//                startActivity(intent);
//                vibrator.vibrate(65);
//                break;
//            }
        }
    }
    @Override
    public void onResume() {



        super.onResume();
        if (sharedPreferencesHome.getBoolean("BUY", false)) {
            int positionFromBuying = (sharedPreferencesHome.getInt("BUY_POSITION", -1));
            sharedPreferencesHome.edit().putInt("BUY_POSITION", -1).commit();
            sharedPreferencesHome.edit().putBoolean("BUY", false).commit();
            sharedPreferencesHome.edit().putBoolean("FROM_PAGER_BUY", false).commit();
            new TaskLoadContents().execute(selectedCategoryId);
            tvNavCredit.setText(" اعتبار : " + sharedPreferencesHome.getInt("CREDIT", 0) + " ریال ");
        }

        final TextView tvTitle;
        final TextView tvContent;
        final TextView tvDismiss;


//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.activity_menu_fabChangePage);
        final ImageView iv = (ImageView) getActivity().findViewById(R.id.iv);
        final ImageView ivMessage = (ImageView) getActivity().findViewById(toolbar_home_imageMessage);

        final MaterialShowcaseView mv = new MaterialShowcaseView.Builder(getActivity())
//                .setTarget(fab)
                .setDismissText("خُب")
                .setContentText("با استفاده از کلید فیلتر ، می توانید مقطع تحصیلی و درس مورد نظر خود را انتخاب و فیلتر نمائید")
                .setTitleText("اعمال فیتر")
                .setContentTextColor(R.color.white)
                .setMaskColour(Color.parseColor("#DD0033a7"))
                .setDelay(500)
                .setShapePadding(30)
                .singleUse("1")
                .show();


        tvTitle = (TextView) getActivity().findViewById(R.id.tv_title);
        tvContent = (TextView) getActivity().findViewById(R.id.tv_content);
        tvDismiss = (TextView) getActivity().findViewById(R.id.tv_dismiss);

        if(tvTitle != null && tvContent != null && tvDismiss != null) {


            final LinearLayout.LayoutParams llpTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llpTitle.setMargins(0, 50, 0, 50);
            tvTitle.setLayoutParams(llpTitle);
            tvTitle.setTextSize(16);

            final LinearLayout.LayoutParams llpContent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llpContent.setMargins(0, 50, 0, 50);
            tvContent.setLayoutParams(llpContent);
            tvContent.setTextSize(14);
            tvContent.setTextColor(Color.WHITE);
            tvContent.setLinkTextColor(Color.WHITE);
            tvContent.setHighlightColor(Color.GRAY);
            tvContent.setLineSpacing(1f, 2f);
            tvContent.setAlpha(1.0f);

            tvDismiss.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 100, 0, 0);
            tvDismiss.setLayoutParams(llp);
            tvDismiss.setTextColor(Color.GREEN);
            tvDismiss.setPadding(32, 0, 32, 0);
            tvDismiss.setTextSize(16);
            tvDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mv.hide();

                    final MaterialShowcaseView mv2 = new MaterialShowcaseView.Builder(getActivity())
                            .setTarget(iv)
                            .setDismissText("خُب")
                            .setContentText("با یک لمس جزئیات و با لمس مجدد این گزینه ویدئوی مربوطه را مشاهده نمائید")
                            .setTitleText("مشاهده جزئیات")
                            .setContentTextColor(R.color.white)
                            .setMaskColour(Color.parseColor("#DD0033a7"))
                            .setDelay(500)
                            .setShapePadding(30)
                            .singleUse("2")
                            .show();

                    TextView tvTitle = (TextView) mv2.findViewById(R.id.tv_title);
                    LinearLayout.LayoutParams llpTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llpTitle.setMargins(0, 50, 0, 50);
                    tvTitle.setLayoutParams(llpTitle);
                    tvTitle.setTextSize(16);


                    TextView tvContent = (TextView) mv2.findViewById(R.id.tv_content);
                    LinearLayout.LayoutParams llpContent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llpContent.setMargins(0, 50, 0, 50);
                    tvContent.setLayoutParams(llpContent);
                    tvContent.setTextSize(14);
                    tvContent.setTextColor(Color.WHITE);
                    tvContent.setLinkTextColor(Color.WHITE);
                    tvContent.setHighlightColor(Color.GRAY);
                    tvContent.setLineSpacing(1f, 2f);
                    tvContent.setAlpha(1.0f);


                    TextView tvDismiss = (TextView) mv2.findViewById(R.id.tv_dismiss);
                    tvDismiss.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llp.setMargins(0, 100, 0, 0);
                    tvDismiss.setLayoutParams(llp);
                    tvDismiss.setTextColor(Color.GREEN);
                    tvDismiss.setPadding(32, 0, 32, 0);
                    tvDismiss.setTextSize(16);
                    tvDismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mv2.hide();

                            final MaterialShowcaseView mv3 = new MaterialShowcaseView.Builder(getActivity())
                                    .setTarget(ivMessage)
                                    .setDismissText("خُب")
                                    .setContentText("با استفاده از این قسمت می توانید پیام خصوصی برای اساتید ارسال نمائید")
                                    .setTitleText("ارسال پیام و یا سوال")
                                    .setContentTextColor(R.color.white)
                                    .setMaskColour(Color.parseColor("#DD0033a7"))
                                    .setDelay(500)
                                    .setShapePadding(30)
                                    .singleUse("3")
                                    .show();

                            TextView tvTitle = (TextView) mv3.findViewById(R.id.tv_title);
                            LinearLayout.LayoutParams llpTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            llpTitle.setMargins(0, 50, 0, 50);
                            tvTitle.setLayoutParams(llpTitle);
                            tvTitle.setTextSize(16);


                            TextView tvContent = (TextView) mv3.findViewById(R.id.tv_content);
                            LinearLayout.LayoutParams llpContent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            llpContent.setMargins(0, 50, 0, 50);
                            tvContent.setLayoutParams(llpContent);
                            tvContent.setTextSize(14);
                            tvContent.setTextColor(Color.WHITE);
                            tvContent.setLinkTextColor(Color.WHITE);
                            tvContent.setHighlightColor(Color.GRAY);
                            tvContent.setLineSpacing(1f, 2f);
                            tvContent.setAlpha(1.0f);


                            TextView tvDismiss = (TextView) mv3.findViewById(R.id.tv_dismiss);
                            tvDismiss.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            llp.setMargins(0, 100, 0, 0);
                            tvDismiss.setLayoutParams(llp);
                            tvDismiss.setTextColor(Color.GREEN);
                            tvDismiss.setPadding(32, 0, 32, 0);
                            tvDismiss.setTextSize(16);
                            tvDismiss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mv3.hide();
                                }
                            });
                        }

                    });
                }
            });
        }

    }

    private void checkNewAnswerCount() {
        newAnswerCount = sharedPreferencesHome.getInt(UNREAD_ANSWERS , 0);
        if(newAnswerCount > 0){
            tvNewAnswerCount.setVisibility(View.VISIBLE);
            ivNewAnswerCountBack.setVisibility(View.VISIBLE);
            tvAnswerCountNav.setVisibility(View.VISIBLE);
            ivAnswerCountNavBack.setVisibility(View.VISIBLE);
            tvNewAnswerCount.setText(newAnswerCount + "");
            tvAnswerCountNav.setText(newAnswerCount + "");
        }else {
            tvNewAnswerCount.setVisibility(View.INVISIBLE);
            ivNewAnswerCountBack.setVisibility(View.INVISIBLE);
            tvAnswerCountNav.setVisibility(View.INVISIBLE);
            ivAnswerCountNavBack.setVisibility(View.INVISIBLE);
        }
    }
}

