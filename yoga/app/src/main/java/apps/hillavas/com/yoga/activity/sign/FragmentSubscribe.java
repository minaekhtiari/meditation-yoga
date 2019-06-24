package apps.hillavas.com.yoga.activity.sign;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.util.IabBroadcastReceiver;
import com.android.billingclient.util.IabHelper;
import com.android.billingclient.util.IabResult;
import com.android.billingclient.util.Inventory;
import com.android.billingclient.util.Purchase;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.activity.ChooseOperator;
import apps.hillavas.com.yoga.activity.FirstContentActivity;
import apps.hillavas.com.yoga.activity.HOME;
import apps.hillavas.com.yoga.activity.ProfileInfo_Activity;
import apps.hillavas.com.yoga.classes.CTouchyWebView;
import apps.hillavas.com.yoga.classes.tools.Utill;
import apps.hillavas.com.yoga.classes.tools.helpers.RetrofitFactory;
import apps.hillavas.com.yoga.data.models.OtpResultJson;
import apps.hillavas.com.yoga.data.models.ResultJson;
import apps.hillavas.com.yoga.data.models.ResultJsonIrancellMemberSignUp;
import apps.hillavas.com.yoga.data.models.ResultJsonMemberSignUp;
import apps.hillavas.com.yoga.data.models.SubscribeModel;
import apps.hillavas.com.yoga.data.remote.OtpApiFactory;
import apps.hillavas.com.yoga.factories.FragmentHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSubscribe extends Fragment implements View.OnClickListener {
    TextView acceptServiceText, membershipServiceText;
    Button btnRegister;
    EditText editMobileNumber;
    ProgressBar progressBar;
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String GUID = "GUID";
    public static final String TRANSACTIONID = "TRANSACTIONID";
    public static final String SUBSCRIBEDUSER = "SubscribedUser";
    public static final String IS_IRANCELL = "IS_IRANCELL";
    public static final String IS_HAMRAHAVAL = "IS_HAMRAHAVAL";
    SharedPreferences sharedPreferencesHome;


    //Charkhune
    // Debug tag, for logging
    private static final String TAG = "Charkhone";
    // Does the user have the premium upgrade?
    private boolean mIsPremium = false;
    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    private static final String SKU_PREMIUM = "Meditation_SKU";
    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;
    // The helper object
    private IabHelper mHelper;
    // Provides purchase notification while this app is running
    private IabBroadcastReceiver mBroadcastReceiver;
    String payload = "";
    String phoneNumber;
    FragmentActivity activity;


    String purchaseToken;
    public static final String Purchase_Token = "Purchase_Token";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscribe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnRegister = getActivity().findViewById(R.id.fragment_register_Btn_register);
        progressBar = getActivity().findViewById(R.id.progressRegister);

        editMobileNumber = getActivity().findViewById(R.id.txt_subscribe_number);
        acceptServiceText = getActivity().findViewById(R.id.accept_service_text);
        membershipServiceText = getActivity().findViewById(R.id.membership_service_txt);
        acceptServiceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "<html>\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf-8\" />\n" +
                        "    <title></title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div style=\"text-align:justify; direction:rtl\">\n" +
                        "        <p>\n" +
                        "اپلیکیشن یوگا، شامل بخش\u200Cهای مختلف ویدئویی است که با آن یک مربی یوگای آنلاین خواهید داشت. \n" +
                        "        </p>\n" +
                        "        <p>\n" +
                        " این اپلیکیشن در حوزه ورزشی و سلامت می باشد.\n" +
                        "        </p>\n" + "<p>\n "+"تمامی ویدئوها دارای نکات آموزشی است که با موسیقی\u200Cهای آرامش\u200Cبخش همراه شده است. دانستنی\u200Cهای مورد نیاز به مشترک داده می\u200Cشود و تمامی محتواهای یوگا زیر نظر استاد تهیه شده و محتواها مورد تایید ایشان است."+"</p>\n"+
                        "        <p>\n" +
                      "بعلاوه در بخش ویدئوهای دیگر، مشترک می\u200Cتواند ویدئوهای دیگر کشورها و نحوه انجام حرکات ورزشی را نیز مشاهده کند.\n" +
                        "ویژگی\u200Cهای اپیلکیشن یوگا به شرح زیر است:\n"+
                        "            <ul>\n" +
                        "                <li>خانه</li>\n" +
                        "                <li>پروفایل</li>\n" +
                        "                <li>آمار و اطلاعات</li>\n" +
                        "                <li>آموزش حرکات بدنی</li>\n" +
                        "                <li>برنامه پیشنهادی</li>\n" +
                        "                 <li>آمادگی</li>\n" +
                        "                 <li>حرکات بدنی</li>\n" +
                        "                <li>آسانا</li>\n" +
                        "                <li>پاکسازی</li>\n" +
                        "                <li>ذن</li>\n" +
                        "                <li>سیکلپ</li>\n"+
                        "                <li>  ویدئوهای بیشتر</li>\n"+
                        "            </ul>\n" +
                        "شرکت هیلا وسام سبا به عنوان یکی از موفق\u200Cترین شرکت\u200Cهای تولید کننده محتوا در حوزه خدمات ارزش افزوده قصد دارد تا با بهره\u200Cگیری از تجربه خود اقدام به ارائه این محصول ورزشی کند.\n" +
                        "چنانچه کاربران اپلیکیشن مشکلی در سرویس مربوطه داشته باشند می\u200Cتوانند با شماره 02196669152   یا با پست الکترونیکی info@hillavas.com در ارتباط باشند.\n" +
                        "هزینه سرویس برای مشترکین دائمی روی قبض اعمال می\u200Cگردد و در مشترکین اعتباری از شارژ سیم\u200Cکارت کسر می\u200Cشود.\n"+
                        "        </p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";
                showDialog(title);
            }




        });
        btnRegister.setOnClickListener(this);

        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (sharedPreferencesHome.getBoolean(IS_IRANCELL, true)) {
            String base64EncodedPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIHg4cW9avnZYkKOet/k/TSsXngpWXtVpuwvxXhfn3HdrWV47LA28aBrODL1n9+corT+F5nIVa5pd3p2xR99ob8rv3pssYkkBU9Z21d+JVx4tLxTXetazZCaL8Uux3sJTHNHC6Yuab/SXtZLK/2ArYRKbmbaNBo8CJgHXTNMRSBwIDAQAB";

            // Create the helper, passing it our context and the public key to verify signatures with
            Log.d(TAG, "Creating IAB helper.");
            mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

            // enable debug logging (for a production application, you should set this to false).
            mHelper.enableDebugLogging(true);


            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(TAG, "Setup finished.");

                    if (!result.isSuccess()) {
                        // Oh noes, there was a problem.
                        complain("Problem setting up in-app billing: " + result);
                        return;
                    }

                    // Have we been disposed of in the meantime? If so, quit.
                    if (mHelper == null) return;


                }
            });
            editMobileNumber.setHint("شماره ایرانسل خود را وارد کنید");
        } else {

            acceptServiceText.setVisibility(View.VISIBLE);
            membershipServiceText.setVisibility(View.VISIBLE);
            editMobileNumber.setHint("شماره همراه اول خود را وارد کنید");
        }

    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = (FragmentActivity) activity;
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fragment_register_Btn_register:


                if (editMobileNumber.length() < 11) {
                    Toast.makeText(getActivity(), R.string.errorMobileNumber, Toast.LENGTH_SHORT).show();
                    return;
                }


                if (sharedPreferencesHome.getBoolean(IS_IRANCELL, true)) {
                    Intent fillInIntent = new Intent();

//                checkAccount(phoneNumber); //todo check user subscribe
                    fillInIntent.putExtra("msisdn", String.valueOf(editMobileNumber.getText()));
                    mHelper.setFillInIntent(fillInIntent);

                    payload = "";
                    try {
                        mHelper.launchPurchaseFlow(getActivity(), SKU_PREMIUM, RC_REQUEST,
                                mPurchaseFinishedListener, payload);

                        //btnRegister.setVisibility(View.INVISIBLE);

                    } catch (IabHelper.IabAsyncInProgressException e) {
                        alert("Error launching purchase flow. Another async operation in progress.");
                    }


//                    new FragmentHelper(new FragmentSubscribeConfirm(),
//                            R.id.frameLayout_base,
//                            getActivity().getSupportFragmentManager()
//                    ).replace(true);

                } else {

                    SubscribeModel subscribeModel = new SubscribeModel();
                    subscribeModel.setMobileNumber(String.valueOf(editMobileNumber.getText()));
                    subscribeModel.setAppName(Utill.getPackageName(getActivity()));
                    subscribeModel.setAppVersion(Utill.getApplicationVersionName(getActivity()));
                    subscribeModel.setDeviceManufacture(Utill.getManufacturer());
                    subscribeModel.setDeviceModel(Utill.getModelName());
                    subscribeModel.setOs("Android");
                    subscribeModel.setOsVersion(Utill.getOsVersion(getActivity()));

                    btnRegister.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    OtpApiFactory.getOtpClient().subscribe(subscribeModel).enqueue(new Callback<OtpResultJson>() {
                        @Override
                        public void onResponse(Call<OtpResultJson> call, Response<OtpResultJson> response) {
                            if (response != null && response.body() != null) {

                                if (response.body().isIsSuccessfull() && response.body().getResult().equals("-1")) { //user is subscribed via phoneNumber

                                    sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editMobileNumber.getText().toString())).commit();
                                    sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getMessage()).commit();
                                    sharedPreferencesHome.edit().putBoolean(SUBSCRIBEDUSER, true).commit();

                                    new FragmentHelper(new FragmentSubscribeConfirm(),
                                            R.id.frameLayout_base,
                                            getActivity().getSupportFragmentManager()
                                    ).replace(true);


                                } else if (response.body().isIsSuccessfull()) {

                                    sharedPreferencesHome.edit().putLong(MOBILE_NUMBER, Long.valueOf(editMobileNumber.getText().toString())).commit();
                                    sharedPreferencesHome.edit().putString(TRANSACTIONID, response.body().getResult().toString()).commit();

                                    new FragmentHelper(new FragmentSubscribeConfirm(),
                                            R.id.frameLayout_base,
                                            getActivity().getSupportFragmentManager()
                                    ).replace(true);


                                } else {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }

                            btnRegister.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onFailure(Call<OtpResultJson> call, Throwable t) {
                            Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                    });
                }

//            case R.id.accept_service_text:

//                break;

        }

    }

    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayload(Purchase p) {
        String verifyPayload = p.getDeveloperPayload();

        if (verifyPayload.equals(payload)) {

            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            btnRegister.setVisibility(View.VISIBLE);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("خطا در پرداخت.");
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("خطا در تایید پرداخت");
                return;
            }

            Log.d(TAG, "Purchase successful." + purchase.getToken());
//            //  CharkhunePurchaseToken
            purchaseToken = purchase.getToken();
            sharedPreferencesHome.edit().putString("PurchaseToken", purchaseToken).commit();

            if (purchase.getSku().equals(SKU_PREMIUM)) {
                // bought the premium upgrade!
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                //alert("Thank you for upgrading to premium!");
                mIsPremium = true;


                getIrancellToken();


            }
        }
    };

    public void getIrancellToken() {

        RetrofitFactory.getRetrofitClient().memberIrancellSignUp(String.valueOf(editMobileNumber.getText())).enqueue(new Callback<ResultJsonIrancellMemberSignUp>() {
            @Override
            public void onResponse(Call<ResultJsonIrancellMemberSignUp> call, Response<ResultJsonIrancellMemberSignUp> response) {


                if (response.isSuccessful()) {


                    sharedPreferencesHome.edit().putString(GUID, response.body().getResult()).commit();
                    Intent intent = new Intent(getActivity(), FirstContentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "خطا در دریافت توکن", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<ResultJsonIrancellMemberSignUp> call, Throwable t) {

                Toast.makeText(getActivity(), R.string.noConnection, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();


            }

        });
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    private void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert(message);
    }

    private void alert(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    public void showDialog(String title) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_condition);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        TextView txtOk = dialog.findViewById(R.id.txtOk);
        CTouchyWebView tvTitle = dialog.findViewById(R.id.content);

        Utill.setWebViewJastify(tvTitle, title);
        tvTitle.setVerticalScrollBarEnabled(false);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //  register();
            }
        });

        dialog.show();
    }
}
