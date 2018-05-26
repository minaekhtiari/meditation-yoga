package fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerView_Adapter_FirstContentActivity_category3;
import apps.hillavas.com.meditation.MessagingActivity;
import apps.hillavas.com.meditation.R;
import classes.Home_Pager_Page;
import classes.justifiers.JustifiedTextView;
import classes.models.AttachedFile;
import classes.models.Content;
import classes.models.FileGiver;
import classes.models.FileResult;
import classes.models.ResultJsonForBuy;
import classes.models.ResultJsonForViewCount;
import classes.models.YogaContent;
import classes.tools.DateMounthIntToStr;
import classes.tools.helpers.RetrofitFactory;
import classes.tools.helpers.RetrofitFactoryForFileManager;
import factories.FragmentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohsen.mohammadi on 6/21/2017.
 */

public class Fragment_ArticleTextView extends Fragment implements View.OnClickListener {

    public static final String LAST_CATEGORYID_SELECTED = "LAST_CATEGORYID_SELECTED";
    public static final String UNREAD_ANSWERS = "UNREAD_ANSWERS";
    private static final String CONTENT = "CONTENT";
    private static final String CONTENT_ID = "CONTENT_ID";
    private static final String TOKEN = "TOKEN";
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String TEXT_IDS = "TEXT_IDS";
    private static final String TITR_IDS = "TITR_IDS";
    private static final String BACK_ID = "BACK_ID";
    public static final String GUID="GUID";

    SharedPreferences sharedPreferencesHome;
    Toolbar toolbar;
    JustifiedTextView jText;
    boolean liked = false;
    FrameLayout frameLayout;
    ImageView ivBackToolbar;
    ImageView ivBackTextToolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvToolbarTitle;
    String toolbarTitle = "";
    TextView tvTextTitle;
    int backId;
    ArrayList<Integer> textIds = new ArrayList<>();
    ArrayList<Integer> titrIds = new ArrayList<>();
    TextView tvTitleToolbar;
    RelativeLayout relativeLayoutToolbarBack;
    RelativeLayout relativeLayoutImageMessage;
    RelativeLayout relativeLayoutImageBack;
    Home_Pager_Page page = new Home_Pager_Page();
    ImageView ivBanner;
    //VideoView videoView;
    String videoUrl = "";
    String audioUrl = "";
    boolean videoVisible = false;
    ImageView ivVideoIcon;
    String token = null;
    int newAnswerCount = 0;
    TextView tvNewAnswerCount;
    ImageView ivNewAnswerCountBack;

    TextView tvLikeCount;
    TextView tvViewCount;
    TextView tvDateYear;
    TextView tvDateMounth;
    TextView tvDateDay;
    TextView tvPrice;
    String imageFileId = null;
    TextView tvBtnBuy;
    boolean lock = false;
    int conentId = 0;
    int categoryId = 0;
    int position = 0;
    Content content = null;

    RecyclerView recyclerViewBottom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_text_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tvNewAnswerCount = (TextView) getActivity().findViewById(R.id.toolbar_all_transparent_text_newAnswerCount);
        ivNewAnswerCountBack = (ImageView) getActivity().findViewById(R.id.toolbar_all_transparent_image_newAnswerCountBack);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");
        tvPrice = (TextView) getActivity().findViewById(R.id.fragment_article_text_price);
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.statusBarColor));
        relativeLayoutToolbarBack = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_image_back);
        tvLikeCount = (TextView) getActivity().findViewById(R.id.fargment_articel_text_txtLikeCount);
        tvViewCount = (TextView) getActivity().findViewById(R.id.fargment_articel_text_txtViewCount);
        tvDateYear = (TextView) getActivity().findViewById(R.id.fragment_articel_text_txtDateYear);
        tvDateMounth = (TextView) getActivity().findViewById(R.id.fragment_articel_text_txtDateMounth);
        tvDateDay = (TextView) getActivity().findViewById(R.id.fragment_articel_text_txtDateDay);
//        tvBackToolbar = (TextView) getActivity().findViewById(R.id.fragment_article_text_tvBackActionBar);
//        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.fragment_article_appbar);
        tvTextTitle = (TextView) getActivity().findViewById(R.id.fragment_article_textTitle);
        jText = (JustifiedTextView) getActivity().findViewById(R.id.fragment_article_jText);
        tvTitleToolbar = (TextView) getActivity().findViewById(R.id.toolbar_all_frameTitle_text);
//        nestedScrollView = (NestedScrollView) getActivity().findViewById(R.id.fragment_article_nestedScroll);
        relativeLayoutImageMessage = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageMessage_relative);
        relativeLayoutImageBack = (RelativeLayout) getActivity().findViewById(R.id.toolbar_all_imageBack_relative);
        ivBanner = (ImageView) getActivity().findViewById(R.id.fragment_article_collaps_ImageBanner);
        ivVideoIcon = (ImageView) getActivity().findViewById(R.id.fragment_article_collaps_image_videoIcon);
        tvBtnBuy = (TextView) getActivity().findViewById(R.id.fragment_article_btn_buy);
        ((FrameLayout) getActivity().findViewById(R.id.frameLayout_bottom_bar)).setVisibility(View.VISIBLE);
        tvBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoBuy();
            }
        });
        recyclerViewBottom = (RecyclerView) getActivity().findViewById(R.id.fragment_article_recyclerBottom);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewBottom.setLayoutManager(layoutManager);
        YogaContent y1 = new YogaContent();
        List<YogaContent> yogaContentList = new ArrayList<>();
        yogaContentList.add(y1);
        yogaContentList.add(y1);
        yogaContentList.add(y1);
        yogaContentList.add(y1);
        yogaContentList.add(y1);
        RecyclerView_Adapter_FirstContentActivity_category3 recyclerView_adapter_firstContentActivity_category3 = new RecyclerView_Adapter_FirstContentActivity_category3(
                getActivity(),
                yogaContentList
        );
        recyclerViewBottom.setAdapter(recyclerView_adapter_firstContentActivity_category3);



        if (getArguments() != null) {
            if(getArguments().containsKey("CONTENT_ID")){
                conentId = getArguments().getInt("CONTENT_ID");
            }
            if(getArguments().containsKey("CATEGORY_ID")){
                categoryId = getArguments().getInt("CATEGORY_ID");
                sharedPreferencesHome.edit().putInt(LAST_CATEGORYID_SELECTED , categoryId).commit();
            }
            if(getArguments().containsKey("POSITION")){
                position = getArguments().getInt("POSITION");
                sharedPreferencesHome.edit().putInt("POSITION",position).commit();
            }
        }
        if(conentId > 0){
            new TaskLoadContentPage().execute(conentId);
        }
        new TaskLoadViewCount().execute(conentId);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class TaskLoadContentPage extends AsyncTask<Integer , Void , Home_Pager_Page>{

        @Override
        protected Home_Pager_Page doInBackground(Integer... params) {
            try {
                if (RetrofitFactory.getRetrofitClient().getContentById(token,params[0]).execute().body().isIsSuccessfull())
                    content = RetrofitFactory.getRetrofitClient().getContentById(token,params[0]).execute().body().getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(content != null) {
                page.setId(conentId);
                page.setTxtText(content.getBody());
                String dateShamsi = content.getInsertDateSh();
                String dateShamsiDay = "";
                String dateShamsiMounth = "";
                String dateShamsiYear = "";

                if (dateShamsi.length() > 0) {
                    dateShamsiYear = dateShamsi.substring(0, 4);
                    dateShamsiMounth = dateShamsi.substring(5, 7);
                    dateShamsiMounth = DateMounthIntToStr.getDateShamsiMounthName(Integer.valueOf(dateShamsiMounth));
                    dateShamsiDay = dateShamsi.substring(8, 10);
                }

                page.setDateDayNumber(Integer.valueOf(dateShamsiDay));
                page.setDateMounthName(dateShamsiMounth);
                page.setDateYearName(dateShamsiYear);
                page.setDescription(content.getBody());
                page.setTxtText(content.getSubject());
                page.setLikeCount(content.getLikeCount());
                page.setViewCount(content.getViewCount());
                page.setPrice(content.getPrice());
                page.setFree(content.isIsFree());

                //page.setImageFileIdString(content.getAttachedFiles().get());
            }
            return page;
        }

        @Override
        protected void onPostExecute(Home_Pager_Page page) {
            super.onPostExecute(page);
            new TaskLoadContent().execute(conentId);
            tvTextTitle.setText(Html.fromHtml(page.getTxtText()).toString());
            tvLikeCount.setText(page.getLikeCount() + "");
            tvViewCount.setText(page.getViewCount() + "");
            tvDateDay.setText(page.getDateDayNumber() + "");
            tvDateMounth.setText(page.getDateMounthName() + "");
            tvDateYear.setText(page.getDateYearName() + "");
            String price = "";
            int color = 0;
            if(content.isIsFree()){
                tvBtnBuy.setVisibility(View.INVISIBLE);
                lock = false;
                if(content.getPrice() > 0) {
                    price = "خریداری شده";
                    color = getActivity().getResources().getColor(R.color.green);
                }
                if(content.getPrice() <=0){
                    price = "رایگان";
                    color = getActivity().getResources().getColor(R.color.yellow);
                }
            }else {
                lock = true;
                price = content.getPrice() + " ریال";
                color = getActivity().getResources().getColor(R.color.red);
                tvBtnBuy.setVisibility(View.VISIBLE);
            }


            tvPrice.setText(price);
            tvPrice.setTextColor(color);

            if (page.getImage2FileIdString() != null && page.getImage2FileIdString().length() > 0)
                imageFileId = page.getImage2FileIdString();
            else
                imageFileId = page.getImageFileIdString();



            relativeLayoutImageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            relativeLayoutImageMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getActivity(), MessagingActivity.class);
                    startActivity(in);

                }
            });





            jText.setText(Html.fromHtml(page.getDescription()).toString());
            jText.setLineSpacing(80);
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/iransans.ttf");
            jText.setTypeFace(tf);

            ivBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoUrl.length() > 0) {
                        if(!lock) {
                            if (!videoVisible) {
                                if (videoUrl.length() > 0) {
                                    Intent intentVideo = new Intent(Intent.ACTION_VIEW);
                                    intentVideo.setDataAndType(Uri.parse(videoUrl), "video/*");
                                    startActivity(intentVideo);
                                }
                                videoVisible = true;
                            } else {
                                videoVisible = false;
                            }
                        }else {
                            gotoBuy();
                        }
                    }
                }
            });

                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(TEXT_IDS, textIds);
                bundle.putIntegerArrayList(TITR_IDS, titrIds);
                bundle.putString(TOKEN, token);
                bundle.putInt(CONTENT_ID, conentId);
                bundle.putInt(BACK_ID, backId);
                Fragment_BottomBar fragment_bottomBar = new Fragment_BottomBar();
                fragment_bottomBar.setArguments(bundle);
                new FragmentHelper(
                        fragment_bottomBar,
                        R.id.frameLayout_bottom_bar,
                        getActivity().getSupportFragmentManager()
                ).replace(false);
        }
    }

    private void gotoBuy(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_buy);
        TextView tvTitr = (TextView) dialog.findViewById(R.id.dialog_sendMessage_text_titr);
        String strTitrBuyDialog = "";
        strTitrBuyDialog = "آیا تمایل به خرید ویدئوی "+ content.getSubject() + " با قیمت : "+ content.getPrice() + " ریال " + "را دارید ؟ ";
        tvTitr.setText(strTitrBuyDialog);
//        ivBtnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new TaskBuyAContent().execute(content.getContentId(),content.getPrice());
//                dialog.hide();
//            }
//        });
//        ivBtnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.hide();
//            }
//        });
        dialog.show();
    }

    class TaskLoadContent extends AsyncTask<Integer, Void, List<AttachedFile>> {

        @Override
        protected List<AttachedFile> doInBackground(Integer... params) {
            Content content = null;
            List<AttachedFile> attaches = null;
            try {
                if (RetrofitFactory.getRetrofitClient().getContentById(token, params[0]).execute().body().isIsSuccessfull()) {
                    attaches = RetrofitFactory.getRetrofitClient().getContentById(token, params[0]).execute().body().getResult().getAttachedFiles();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return attaches;
        }

        @Override
        protected void onPostExecute(List<AttachedFile> attachedFiles) {
            super.onPostExecute(attachedFiles);

            for (AttachedFile attachedFile : attachedFiles) {
                if (attachedFile.getFileType() == 1 && attachedFile.getContentType().equals("HeaderImage")) {
                    RetrofitFactoryForFileManager.getRetrofitClient().getFiles(attachedFile.getFileID(), 1).enqueue(new Callback<FileGiver>() {
                        @Override
                        public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {
                            if (response.body().isIsSuccessfull()) {
                                FileResult result = response.body().getFileResult();
                                if (result != null) {
                                    Glide.with(getActivity())
                                            .load(result.getFileAddress())
                                            .asBitmap()
                                            .override(640, 360)
                                            .centerCrop()
                                            .into(ivBanner);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<FileGiver> call, Throwable t) {

                        }
                    });
                }
                if (attachedFile.getFileType() == 5) {
                    page.setVideoFileIdString(attachedFile.getFileID());
                    if (page.getVideoFileIdString() != null && page.getVideoFileIdString().length() > 0) {
//                        if(1 ==1) {
                        RetrofitFactoryForFileManager.getRetrofitClient().getFiles(page.getVideoFileIdString(), 5).enqueue(new Callback<FileGiver>() {
                            @Override
                            public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {
                                if (response.body().isIsSuccessfull()) {
                                    FileResult result = response.body().getFileResult();
                                    if (result != null) {
                                        videoUrl = result.getFileAddress();
                                        ivVideoIcon.setVisibility(View.VISIBLE);
                                        ivVideoIcon.setAlpha(0.8f);
                                    } else
                                        ivVideoIcon.setVisibility(View.INVISIBLE);
                                }
                            }
                            @Override
                            public void onFailure(Call<FileGiver> call, Throwable t) {

                            }
                        });
                    }
                }

            }
        }
    }

    class TaskBuyAContent extends AsyncTask<Integer, Void,String> {
        @Override
        protected String doInBackground(Integer... params) {
            ResultJsonForBuy resultJsonForBuy = null;
            String strMessage = null;
            try {
                resultJsonForBuy = RetrofitFactory.getRetrofitClient().buy(token,params[0],params[1]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(resultJsonForBuy != null) {
                if (resultJsonForBuy.isIsSuccessfull()) {
                    if (resultJsonForBuy.isResult())
                        strMessage = "YES";
                } else
                    strMessage = resultJsonForBuy.getMessage();
            }
            return strMessage;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            String message = null;

            if(str != null && str.length() > 0) {
                if (str.equals("YES")) {
                    //yes
                    sharedPreferencesHome.edit().putInt(LAST_CATEGORYID_SELECTED , categoryId).commit();
                    sharedPreferencesHome.edit().putBoolean("BUY" , true).commit();
                    sharedPreferencesHome.edit().putInt("BUY_POSITION" , position).commit();
                    sharedPreferencesHome.edit().putInt("CREDIT" , sharedPreferencesHome.getInt("CREDIT" , 0) - content.getPrice()).commit();

                    message = "خرید شما با موفقیت انجام شد";
                    refreshPageAfterBuy();

                } else {
                    //error
                    message = str;
                }
            }else
                message = "error";
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }



    class TaskLoadViewCount extends AsyncTask<Integer, Void,Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            ResultJsonForViewCount result = null;
            int viewCount = 0;
            try {
                result = RetrofitFactory.getRetrofitClient().getContentViewCount(token,params[0]).execute().body();
                if(result.isIsSuccessfull())
                    viewCount = result.getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return viewCount;
        }

        @Override
        protected void onPostExecute(Integer viewCount) {
            super.onPostExecute(viewCount);
            tvViewCount.setText(viewCount+"");
        }
    }


    private void refreshPageAfterBuy(){
        new TaskLoadContentPage().execute(conentId);
    }


}

